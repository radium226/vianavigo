package com.github.radium226.vianavigo;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.damnhandy.uri.template.VarExploder;
import com.damnhandy.uri.template.VariableExpansionException;
import com.google.common.collect.ImmutableMap;

public class DateTimeVarExploder implements VarExploder {

	private Date dateTime;
	
	protected DateTimeVarExploder(Date dateTime) {
		super();
		
		this.dateTime = dateTime;
	}
	
	public static DateTimeVarExploder forDateTime(Date dateTime) {
		return new DateTimeVarExploder(dateTime);
	}

	private static String toDate(Date dateTime) {
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(dateTime);
	}
	
	private static int toDateType(Date dateTime) {
		return 1;
	}
	
	@Override
	public Map<String, Object> getNameValuePairs() throws VariableExpansionException {
		return ImmutableMap.<String, Object>builder()
				.put("date", toDate(dateTime))
				.put("dateType", toDateType(dateTime))
			.build();
	}

	@Override
	public Collection<Object> getValues() throws VariableExpansionException {
		return Arrays.asList((Object) toDate(dateTime), (Object) toDateType(dateTime));
	}
	
}
