package radium.vianavigo.http;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;

import radium.vianavigo.Transportation;

import com.damnhandy.uri.template.VarExploder;
import com.damnhandy.uri.template.VariableExpansionException;
import com.google.common.base.Strings;
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
		int modesAsInteger = 0x00000;
		for (Transportation.Type type : types) {
			modesAsInteger = modesAsInteger | type.getMode();
		}
		String modes = Strings.padStart(Integer.toHexString(modesAsInteger), 5, '0');
		return modes;
	}
	
	public static TransportationTypeVarExploder forTypes(Transportation.Type firstType, Transportation.Type... otherTypes) {
		return forTypes(EnumSet.of(firstType, otherTypes));
	}
	
	public static TransportationTypeVarExploder forTypes(EnumSet<Transportation.Type> types) {
		return new TransportationTypeVarExploder(types);
	}

}
