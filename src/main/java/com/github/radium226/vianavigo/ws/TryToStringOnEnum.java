package com.github.radium226.vianavigo.ws;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import java.util.EnumSet;

public class TryToStringOnEnum {

    public static enum Type {
        
        TRAIN, PLANE, BOAT
        
    }
    
    public static class TypeToStringFunction implements Function<Type, String> {

        private static EnumSet<Type> selectedTypes;
        
        protected TypeToStringFunction(EnumSet<Type> selectedTypes) {
            super();
            
            this.selectedTypes = selectedTypes;
        }
        
        @Override
        public String apply(Type type) {
            return selectedTypes.contains(type) ? "1" : "0";
        }
        
        public static TypeToStringFunction forSelectedTypes(EnumSet<Type> selectedTypes) {
            return new TypeToStringFunction(selectedTypes);
        }
        
    }
    
    public static void main(String[] arguments) {
        
        System.out.println(toString(EnumSet.of(Type.TRAIN, Type.BOAT)));
        System.out.println(toString(EnumSet.of(Type.TRAIN)));
        System.out.println(toString(EnumSet.of(Type.BOAT, Type.PLANE)));
    }
    
    public static String toString(EnumSet<Type> types) {
        return Joiner.on("").join(Collections2.transform(EnumSet.allOf(Type.class), TypeToStringFunction.forSelectedTypes(types)));
    }
    
}
