package com.github.radium226.vianavigo.ws;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.json.JsonObject;
import javax.json.JsonStructure;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import com.damnhandy.uri.template.MalformedUriTemplateException;
import com.damnhandy.uri.template.UriTemplate;
import com.damnhandy.uri.template.VariableExpansionException;
import com.github.radium226.util.JsonUtil;
import com.github.radium226.vianavigo.City;
import com.github.radium226.vianavigo.JsonResponseHandler;
import com.github.radium226.vianavigo.Location;
import com.google.common.collect.Lists;

public class LocationSearcher {
	
	private Location example;
	
	protected LocationSearcher(Location example) {
		super();
		
		this.example = example;
	}
	
	public static LocationSearcher searchLocationsByName(String name) {
		Location example = Location.valueOf(name, null, null);
		return searchLocationsByExample(example);
	}
	
	protected static LocationSearcher searchLocationsByExample(Location example) {
		return new LocationSearcher(example);
	}
	
	public List<Location> usingHttpClient(CloseableHttpClient client) throws MalformedUriTemplateException, VariableExpansionException, ClientProtocolException, IOException {
		UriTemplate uriTemplate = UriTemplate.fromTemplate("https://ws.vianavigo.com/stif_web_services/rest/searchPoints/{name}");
		String name = example.getName();
		City city = example.getCity();
		if (city != null) {
			name += ", " + city.getName();
		}
		URI uri = URI.create(uriTemplate.set("name", name).expand());
		JsonStructure responseAsJson = client.execute(new HttpGet(uri), new JsonResponseHandler());
		List<JsonObject> locationsAsJson = JsonUtil.query(responseAsJson, "$.list[?(@.type == 'StopArea')]");
		List<Location> locations = Lists.transform(locationsAsJson, JsonObjectToLocationFunction.get());
		return locations; 
	}
	
}
