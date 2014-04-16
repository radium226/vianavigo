package com.github.radium226.json.jayway;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jayway.jsonpath.InvalidJsonException;
import com.jayway.jsonpath.spi.JsonProvider;
import com.jayway.jsonpath.spi.Mode;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;

public class JavaxJsonProvider implements JsonProvider {

    @Override
    public Mode getMode() {
        return Mode.STRICT;
    }

    @Override
    public Object parse(String json) throws InvalidJsonException {
        return parse(new StringReader(json));
    }

    @Override
    public Object parse(Reader jsonReader) throws InvalidJsonException {
        try {
            return Json.createReader(jsonReader).readObject();
        } catch (JsonException e) {
            throw new InvalidJsonException(e);
        }
    }

    @Override
    public Object parse(InputStream jsonStream) throws InvalidJsonException {
        try {
            return Json.createReader(jsonStream).readObject();
        } catch (JsonException e) {
            throw new InvalidJsonException(e);
        }
    }

    @Override
    public String toJson(Object object) {
        JsonObject jsonObject = (JsonObject) object;
        StringWriter writer = new StringWriter();
        Json.createWriter(writer).writeObject(jsonObject);
        return writer.toString();
    }

    @Override
    public Map<String, Object> createMap() {
        return Maps.newHashMap();
    }

    @Override
    public List<Object> createList() {
        return Lists.newArrayList();
    }

    @Override
    public Object clone(Object object) {
        JsonObject jsonObject = (JsonObject) object;
        JsonArray
        
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.
    }

    @Override
    public boolean isContainer(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isList(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object> toList(Object list) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, Object> toMap(Object map) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getMapValue(Object map, String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isMap(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
