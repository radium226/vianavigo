package com.github.radium226.vianavigo.ws;

import com.damnhandy.uri.template.MalformedUriTemplateException;
import com.damnhandy.uri.template.UriTemplate;
import com.damnhandy.uri.template.VariableExpansionException;
import com.github.radium226.util.JsonUtil;
import com.github.radium226.vianavigo.DateTimeVarExploder;
import com.github.radium226.vianavigo.Itinerary;
import com.github.radium226.vianavigo.JsonResponseHandler;
import com.github.radium226.vianavigo.Location;
import com.github.radium226.vianavigo.LocationDateTime;
import com.github.radium226.vianavigo.LocationVarExploder;
import com.github.radium226.vianavigo.Step;
import com.github.radium226.vianavigo.Transportation;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.net.URI;
import java.util.EnumSet;
import java.util.List;

import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonStructure;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

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
	public List<Itinerary> usingHttpClient(HttpClient httpClient) throws MalformedUriTemplateException, VariableExpansionException, ClientProtocolException, IOException {
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
		List<JsonString> itineraryTypesAsJson = JsonUtil.query(httpClient.execute(new HttpGet(uri), new JsonResponseHandler()), "$.results[*].type");
		List<Integer> itineraryTypes = Lists.transform(itineraryTypesAsJson, JsonStringToIntegerFunction.get());
		List<Itinerary> itineraries = Lists.newArrayList();
		for (int itineraryType : itineraryTypes) {
			uri = URI.create(uriTemplate
				.set("itineraryType", itineraryType)
			.expand());
		
			JsonStructure responseAsJson = httpClient.execute(new HttpGet(uri), new JsonResponseHandler());
			List<JsonObject> stepsAsJson = JsonUtil.query(responseAsJson, "$.result.iti[?(@.mode != 'Attente' && @.mode != 'Marche')]");
			List<Step> steps = Lists.transform(stepsAsJson, JsonObjectToStepFunction.get());
			Itinerary itinerary = Itinerary.valueOf(steps);
			itineraries.add(itinerary);
		}
		
		System.out.println(itineraries);
		
		return itineraries; 
	}
	
}
