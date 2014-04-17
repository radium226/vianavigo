package com.github.radium226.vianavigo.ws;

import javax.json.JsonObject;

import com.github.radium226.vianavigo.City;
import com.github.radium226.vianavigo.Location;
import com.google.common.base.Function;

public class JsonObjectToLocationFunction implements Function<JsonObject, Location> {

	private static JsonObjectToLocationFunction INSTANCE = new JsonObjectToLocationFunction();
	
	@Override
	public Location apply(JsonObject jsonObject) {
		return Location.valueOf(jsonObject.getString("name"), jsonObject.getString("externalCode"), City.valueOf(jsonObject.getString("city"), jsonObject.getString("cityCode")));
	}

	
	public static JsonObjectToLocationFunction get() {
		return INSTANCE;
	}
	
	
}
