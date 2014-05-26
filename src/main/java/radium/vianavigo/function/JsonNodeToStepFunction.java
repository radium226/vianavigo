package radium.vianavigo.function;

import static radium.vianavigo.LocationFilters.code;
import static radium.vianavigo.LocationFilters.name;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.CloseableHttpClient;

import radium.vianavigo.Location;
import radium.vianavigo.LocationDateTime;
import radium.vianavigo.LocationSearcher;
import radium.vianavigo.Step;
import radium.vianavigo.Transportation;

import com.damnhandy.uri.template.MalformedUriTemplateException;
import com.damnhandy.uri.template.VariableExpansionException;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Function;

public class JsonNodeToStepFunction implements Function<JsonNode, Step> {

	private CloseableHttpClient httpClient;
	
	protected JsonNodeToStepFunction(CloseableHttpClient client) {
		super();
		
		this.httpClient = client;
	}
	
	@Override
	public Step apply(JsonNode stepAsJson) {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		LocationDateTime departure = null;
		try {
			String departureLocationName = stepAsJson.get("nom").asText();
			String departureLocationCode = stepAsJson.get("externalCode").asText();
			Location departureLocation = null;
			try {
				departureLocation = LocationSearcher.searchLocationsBy(name(departureLocationName).and(code(departureLocationCode))).usingHttpClient(httpClient).get(0);
			} catch (ClientProtocolException e) {
				departureLocation = Location.valueOf(departureLocationName, null, null);
				e.printStackTrace(System.err);
			} catch (MalformedUriTemplateException e) {
				departureLocation = Location.valueOf(departureLocationName, null, null);
				e.printStackTrace(System.err);
			} catch (VariableExpansionException e) {
				departureLocation = Location.valueOf(departureLocationName, null, null);
				e.printStackTrace(System.err);
			} catch (IOException e) {
				departureLocation = Location.valueOf(departureLocationName, null, null);
				e.printStackTrace(System.err);
			}
			
			departure = LocationDateTime.valueOf(departureLocation, 
					dateFormat.parse(stepAsJson.get("depart").asText()));
			
			String transportationTypeAsString = stepAsJson.get("mode").asText();
			Transportation.Type transportationType = null; 
			if (transportationTypeAsString.equals("Metro")) {
				transportationType = Transportation.Type.METRO;
			} else if (transportationTypeAsString.equals("Marche")) {
				transportationType = Transportation.Type.WALK;
			} else if (transportationTypeAsString.equals("Bus")) {
				transportationType = Transportation.Type.BUS;
			} else if (transportationTypeAsString.equals("RER")) {
				transportationType = Transportation.Type.RER; 
			} else if (transportationTypeAsString.equals("Attente")) {
				transportationType = Transportation.Type.WAIT; 
			} else {
				throw new RuntimeException("Unknow transportation type (" + transportationTypeAsString + ")");
			}
			
			JsonNode transportationNameAsJson = stepAsJson.get("ligneCode");
			JsonNode directionAsJson = stepAsJson.get("dir");
			
			Transportation transportation = null; 
			if (directionAsJson != null && transportationNameAsJson != null) {
				Location direction = null;
				String directionName = directionAsJson.asText();
				try {
					direction = LocationSearcher.searchLocationsBy(name(directionName)).usingHttpClient(httpClient).get(0);
				} catch (ClientProtocolException e) {
					e.printStackTrace(System.err);
					direction = Location.valueOf(directionName, null, null);
				} catch (MalformedUriTemplateException e) {
					e.printStackTrace(System.err);
					direction = Location.valueOf(directionName, null, null);
				} catch (VariableExpansionException e) {
					e.printStackTrace(System.err);
					direction = Location.valueOf(directionName, null, null);
				} catch (IOException e) {
					e.printStackTrace(System.err);
					direction = Location.valueOf(directionName, null, null);
				}
				String name = transportationNameAsJson.asText();
				transportation = new Transportation(transportationType, name, direction);
			} else {
				transportation = new Transportation(transportationType);
			}
			
			return new Step(departure, transportation, null);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}	
	}
	
	public static JsonNodeToStepFunction get(CloseableHttpClient httpClient) {
		return new JsonNodeToStepFunction(httpClient);
	}

}
