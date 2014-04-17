package com.github.radium226.vianavigo.ws;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;

import com.damnhandy.uri.template.VarExploder;
import com.damnhandy.uri.template.VariableExpansionException;
import com.github.radium226.vianavigo.Transportation;
import com.github.radium226.vianavigo.TransportationTypeToStringFunction;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableMap;

public class TransportationTypeVarExploder implements VarExploder {

	private EnumSet<Transportation.Type> types;
	
	protected TransportationTypeVarExploder(EnumSet<Transportation.Type> types) {
		super();
		
		this.types = types;
	}
	
	@Override
	public Map<String, Object> getNameValuePairs() throws VariableExpansionException {
		return ImmutableMap.<String, Object>builder()
				.put("modes", toModes(types))
			.build();
	}

	@Override
	public Collection<Object> getValues() throws VariableExpansionException {
		return Arrays.asList((Object) toModes(types));
	}
	
	private static String toModes(EnumSet<Transportation.Type> types) {
		return Joiner.on("").join(Collections2.transform(EnumSet.range(Transportation.Type.TRAIN, Transportation.Type.BUS), TransportationTypeToStringFunction.forTypes(types)));
	}
	
	public static TransportationTypeVarExploder forTypes(Transportation.Type firstType, Transportation.Type... otherTypes) {
		return forTypes(EnumSet.of(firstType, otherTypes));
	}
	
	public static TransportationTypeVarExploder forTypes(EnumSet<Transportation.Type> types) {
		return new TransportationTypeVarExploder(types);
	}

}
