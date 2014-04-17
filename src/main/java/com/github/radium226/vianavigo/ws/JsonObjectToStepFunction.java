package com.github.radium226.vianavigo.ws;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.json.JsonObject;
import javax.json.JsonString;

import com.github.radium226.vianavigo.City;
import com.github.radium226.vianavigo.Location;
import com.github.radium226.vianavigo.LocationDateTime;
import com.github.radium226.vianavigo.Step;
import com.github.radium226.vianavigo.Transportation;
import com.google.common.base.Function;

public class JsonObjectToStepFunction implements Function<JsonObject, Step> {

	protected JsonObjectToStepFunction() {
		super();
	}
	
	@Override
	public Step apply(JsonObject jsonObject) {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		
		System.out.println("jsonObject = " + jsonObject);
		
		LocationDateTime departure = null;
		try {
			departure = LocationDateTime.valueOf(
					Location.valueOf(jsonObject.getString("nom"), jsonObject.getString("externalCode"), 
							City.valueOf(jsonObject.getString("commune"), null)), 
					dateFormat.parse(jsonObject.getString("depart")));
			
			String transportationTypeAsString = jsonObject.getString("mode");
			Transportation.Type transportationType = null; 
			if (transportationTypeAsString.equals("Metro")) {
				transportationType = Transportation.Type.METRO;
			} else if (transportationTypeAsString.equals("Marche")) {
				transportationType = Transportation.Type.WALK;
			} else if (transportationTypeAsString.equals("Bus")) {
				transportationType = Transportation.Type.BUS;
			} else throw new RuntimeException("Unknow transportation type (" + transportationTypeAsString + ")");
			
			JsonString transportationNameAsJson = jsonObject.getJsonString("ligneCode");
			System.out.println("transportationNameAsJson = " + transportationNameAsJson);
			Location direction = Location.valueOf(jsonObject.getString("dir"), null, null);
			Transportation transportation = transportationNameAsJson == null ? new Transportation(transportationType, direction) : new Transportation(transportationType, transportationNameAsJson.getString(), direction);
			return new Step(departure, transportation, null);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}	
	}
	
	public static JsonObjectToStepFunction get() {
		return new JsonObjectToStepFunction();
	}

}
