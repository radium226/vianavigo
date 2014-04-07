package com.github.radium226.vianavigo;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import com.damnhandy.uri.template.VarExploder;
import com.damnhandy.uri.template.VariableExpansionException;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

public class LocationVarExploder implements VarExploder {

	final protected static String DEPARTURE_PREFIX = "departure";
	final protected static String ARRIVAL_PREFIX = "arrival";
	
	private String prefix;
	private Location location;
	
	protected LocationVarExploder(String prefix, Location location) {
		super();
		
		this.prefix = prefix;
		this.location = location;
	}
	
	@Override
	public Map<String, Object> getNameValuePairs() throws VariableExpansionException {
		return ImmutableMap.<String, Object>builder()
				.put(this.prefix, this.location.getName())
				.put(this.prefix + "ExternalCode", this.location.getCode())
				.put(this.prefix + "City", this.location.getCity().getName())
				.put(this.prefix + "CityCode", this.location.getCity().getCode())
				.put(this.prefix + "Type", "StopArea")
			.build();
	}

	@Override
	public Collection<Object> getValues() throws VariableExpansionException {
		return Lists.<Object>newArrayList(this.location.getName(), this.location.getCode());
	}

	public static LocationVarExploder forDeparture(Location departure) {
		return new LocationVarExploder(DEPARTURE_PREFIX, departure);
	}
	
	public static LocationVarExploder forArrival(Location arrival) {
		return new LocationVarExploder(ARRIVAL_PREFIX, arrival);
	}
	
}
