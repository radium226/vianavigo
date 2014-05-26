package radium.vianavigo.function;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Function;

public class JsonNodeToIntegerFunction implements Function<JsonNode, Integer> {

	public static JsonNodeToIntegerFunction INSTANCE =  new JsonNodeToIntegerFunction();
	
	protected JsonNodeToIntegerFunction() {
		super();
	}
	
	@Override
	public Integer apply(JsonNode jsonNode) {
		return jsonNode.asInt();
	}
	
	public static JsonNodeToIntegerFunction get() {
		return INSTANCE;
	}

}
