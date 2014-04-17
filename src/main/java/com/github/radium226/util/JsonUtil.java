package com.github.radium226.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.json.Json;
import javax.json.JsonStructure;
import javax.json.JsonValue;

import org.apache.http.HttpEntity;

import com.jayway.jsonpath.JsonPath;

public class JsonUtil {

	public static JsonStructure parseText(String text) {
		StringReader jsonReader = new StringReader(text);
		JsonStructure jsonStructure = Json.createReader(jsonReader).read();
		return jsonStructure;
	}
	
	public static JsonStructure parseInputStream(InputStream inputStream) {
		JsonStructure jsonStructure = Json.createReader(inputStream).read();
		return jsonStructure;
	}
	
	public static JsonStructure parseReader(Reader reader) {
		JsonStructure jsonStructure = Json.createReader(reader).read();
		return jsonStructure;
	}
	
	public static JsonStructure parseHttpEntity(HttpEntity httpEntity) throws IllegalStateException, IOException {
		InputStream content = httpEntity.getContent();
		JsonStructure jsonStructure = parseInputStream(content);
		return jsonStructure;
	}
	
	public static String formatJson(JsonStructure JsonStructure) {
		StringWriter writer = new StringWriter();
		Json.createWriter(writer).write(JsonStructure);
		String text = writer.toString();
		return text;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T executeJsonPathQuery(JsonStructure jsonStructure, String path) {
		String text = formatJson(jsonStructure);
		return (T) parseText(JsonPath.read(text, path).toString());
	}
	
	public static <T> T query(JsonStructure jsonStructure, String path) {
		return executeJsonPathQuery(jsonStructure, path); 
	}
	
}
