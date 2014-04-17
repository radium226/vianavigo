package com.github.radium226.vianavigo.ws;

import javax.json.JsonString;

import com.google.common.base.Function;

public class JsonStringToIntegerFunction implements Function<JsonString, Integer> {

	protected JsonStringToIntegerFunction() {
		super();
	}
	
	@Override
	public Integer apply(JsonString jsonString) {
		return Integer.valueOf(jsonString.getString());
	}
	
	public static JsonStringToIntegerFunction get() {
		return new JsonStringToIntegerFunction();
	}

}
