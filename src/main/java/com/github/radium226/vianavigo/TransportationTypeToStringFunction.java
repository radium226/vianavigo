package com.github.radium226.vianavigo;

import java.util.EnumSet;

import com.google.common.base.Function;

public class TransportationTypeToStringFunction implements Function<Transportation.Type, String> {

    private EnumSet<Transportation.Type> types;
    
    protected TransportationTypeToStringFunction(EnumSet<Transportation.Type> types) {
        super();
        
        this.types = types;
    }
    
    @Override
    public String apply(Transportation.Type type) {
        return types.contains(type) ? "1" : "0";
    }
    
    public static TransportationTypeToStringFunction forTypes(EnumSet<Transportation.Type> types) {
        return new TransportationTypeToStringFunction(types);
    }

}
