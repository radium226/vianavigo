package radium.jayway;

import java.io.IOException;
import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonStructure;
import javax.json.JsonValue;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.JsonProviderFactory;

public class TryJSONPath {

	public static void main(String[] arguments) throws IOException {
		String json = Resources.asCharSource(Resources.getResource("Sample.json"), Charsets.UTF_8).read();
		JsonArray jsonArray = parseJSONArray(json);
		
		JsonValue jsonValue = jsonArray.get(0);
		JsonObject jsonObject = (JsonObject) jsonValue;
		
		JsonNumber jsonNumber = JsonPath.read(jsonValue, "$.id");
		System.out.println("foundJSONStructure = " + jsonNumber.intValue());
	}
	
	public static JsonArray parseJSONArray(String json) {
		JsonArray jsonArray = Json.createReader(new StringReader(json)).readArray();
		return jsonArray;
	}
	
}
