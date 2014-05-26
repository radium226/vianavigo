package radium.vianavigo;

import static radium.json.Json.path;
import static radium.json.Json.select;

import java.io.IOException;
import java.net.URI;
import java.util.EnumSet;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import radium.vianavigo.function.JsonNodeToIntegerFunction;
import radium.vianavigo.function.JsonNodeToStepFunction;
import radium.vianavigo.http.DateTimeVarExploder;
import radium.vianavigo.http.JsonResponseHandler;
import radium.vianavigo.http.LocationVarExploder;
import radium.vianavigo.http.TransportationTypeVarExploder;

import com.damnhandy.uri.template.MalformedUriTemplateException;
import com.damnhandy.uri.template.UriTemplate;
import com.damnhandy.uri.template.VariableExpansionException;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class ItinerarySearcher {
	
	private LocationDateTime departure;
	private Location arrival;
	private EnumSet<Transportation.Type> transportationTypes;
	
	protected ItinerarySearcher(LocationDateTime departure, Location arrival, EnumSet<Transportation.Type> transportationTypes) {
		super();
		
		this.departure = departure;
		this.arrival = arrival;
		this.transportationTypes = transportationTypes;
	}
	
	public static ItinerarySearcher searchItineraries(LocationDateTime departure, Location arrival, EnumSet<Transportation.Type> transportationTypes) {
		return new ItinerarySearcher(departure, arrival, transportationTypes);
	}
	
	//https://ws.vianavigo.com/stif_web_services/rest/itinerarySearch?enableNewCriteria=true&departureType=StopArea&departureCity=Paris&departure=GARE+DE+L%27EST%2C+Paris&arrival=Richelieu+Drouot&modes=11111&walkSpeed=1&changeDate=1&&itineraryType=5
	public List<Itinerary> usingHttpClient(CloseableHttpClient httpClient) throws MalformedUriTemplateException, VariableExpansionException, ClientProtocolException, IOException {
		UriTemplate uriTemplate = UriTemplate.fromTemplate("https://ws.vianavigo.com/stif_web_services/rest/itinerarySearch{?enableNewCriteria,departure*,arrival*,modes*,walkSpeed,date*,itineraryType}");
		URI uri = null; 
		
		uriTemplate = uriTemplate
			.set("enableNewCriteria", "true")
			.set("departure", LocationVarExploder.forDepartureLocation(departure.getLocation()))
			.set("arrival", LocationVarExploder.forArrivalLocation(arrival))
			.set("modes", TransportationTypeVarExploder.forTypes(transportationTypes))
			.set("walkSpeed", 1)
			.set("date", DateTimeVarExploder.forDateTime(departure.getDateTime()));
		uri = URI.create(uriTemplate.expand());
		
		JsonNode json = httpClient.execute(new HttpGet(uri), new JsonResponseHandler());
		
		JsonNode itineraryTypesAsJson = select(path("$.results[].type")).on(json).asNode();
		Iterable<Integer> itineraryTypes = Iterables.transform(itineraryTypesAsJson, JsonNodeToIntegerFunction.get());
		List<Itinerary> itineraries = Lists.newArrayList();
		for (int itineraryType : itineraryTypes) {
			uri = URI.create(uriTemplate
				.set("itineraryType", itineraryType)
			.expand());
		
			JsonNode responseAsJson = httpClient.execute(new HttpGet(uri), new JsonResponseHandler());
			List<JsonNode> stepsAsJson = select(path("$.result.iti[]")).on(responseAsJson).asNodeList(/*new Predicate<JsonNode>() {
				
				@Override
				public boolean apply(JsonNode node) {
					String mode = node.get("mode").asText();
					return !mode.equals("Attente");
				}
				
			}*/);
			List<Step> steps = Lists.transform(stepsAsJson, JsonNodeToStepFunction.get(httpClient));
			Itinerary itinerary = Itinerary.valueOf(fixSteps(steps));
			itineraries.add(itinerary);
		}
		
		return itineraries; 
	}
	
	private List<Step> fixSteps(List<Step> stepsToFix) {
		List<Step> fixedSteps = Lists.newArrayList();
		int count = stepsToFix.size();
		for (int i = 0; i < count; i++) {
			Step stepToFix = stepsToFix.get(i);
			
			Location arrival = i == count - 1 ? this.arrival : stepsToFix.get(i + 1).getDeparture().getLocation();
			Step fixedStep = new Step(stepToFix.getDeparture(), stepToFix.getTransportation(), arrival);
			fixedSteps.add(fixedStep);
		}
		return fixedSteps;
	}
	
}
