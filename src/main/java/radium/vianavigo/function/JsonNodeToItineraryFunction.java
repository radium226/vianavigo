package radium.vianavigo.function;
/*
import java.util.List;

import org.apache.http.impl.client.CloseableHttpClient;

import com.fasterxml.jackson.databind.JsonNode;

import static adrien.json.Json.*;

import com.github.radium226.vianavigo.Itinerary;
import com.github.radium226.vianavigo.Location;
import com.github.radium226.vianavigo.Step;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class JsonNodeToItineraryFunction implements Function<JsonNode, Itinerary> {

	private Location arrival;
	
	protected JsonNodeToItineraryFunction(Location arrival) {
		super();
		
		this.arrival = arrival;
	}
	
	@Override
	public Itinerary apply(JsonNode itineraryAsJson) {
		List<JsonNode> stepsAsJson = select(path("$.etapes[*]")).on(itineraryAsJson).asNodeList();
		List<Step> steps = Lists.transform(stepsAsJson, JsonNodeToStepFunction.get());
		Itinerary itinerary = Itinerary.valueOf(steps);
		return itinerary;
	}

	public static JsonNodeToItineraryFunction get(CloseableHttpClient httpClient, Location arrival) {
		return new JsonNodeToItineraryFunction(httpClient, arrival);
	}
	
}
*/