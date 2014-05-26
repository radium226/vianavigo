package radium.vianavigo.function;

import radium.vianavigo.City;
import radium.vianavigo.Location;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Function;

public class JsonNodeToLocationFunction implements Function<JsonNode, Location> {

	private static JsonNodeToLocationFunction INSTANCE = new JsonNodeToLocationFunction();
	
	@Override
	public Location apply(JsonNode locationAsJson) {
		String name = locationAsJson.get("name").asText();
		name = name.substring(0, name.lastIndexOf(",")).trim().toUpperCase(); // It removes the name of the city
		
		return Location.valueOf(
				name, 
				locationAsJson.get("externalCode").asText(), 
				City.valueOf(locationAsJson.get("city").asText(), locationAsJson.get("cityCode").asText()));
	}

	
	public static JsonNodeToLocationFunction get() {
		return INSTANCE;
	}
	
	
}
