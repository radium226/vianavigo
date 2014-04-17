package com.github.radium226.vianavigo.ws;

import java.util.List;

import javax.json.JsonObject;

import com.github.radium226.util.JsonUtil;
import com.github.radium226.vianavigo.Itinerary;
import com.github.radium226.vianavigo.Step;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class JsonObjectToItineraryFunction implements Function<JsonObject, Itinerary> {

	protected JsonObjectToItineraryFunction() {
		super();
	}
	
	@Override
	public Itinerary apply(JsonObject jsonObject) {
		List<JsonObject> stepsAsJson = JsonUtil.query(jsonObject, "$.etapes[*]");
		List<Step> steps = Lists.transform(stepsAsJson, JsonObjectToStepFunction.get());
		Itinerary itinerary = Itinerary.valueOf(steps);
		return itinerary;
	}

	public static JsonObjectToItineraryFunction get() {
		return new JsonObjectToItineraryFunction();
	}
	
}
