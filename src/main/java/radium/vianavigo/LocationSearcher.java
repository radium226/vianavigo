package radium.vianavigo;

import static radium.json.Json.path;
import static radium.json.Json.select;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import radium.vianavigo.filter.LocationFilter;
import radium.vianavigo.function.JsonNodeToLocationFunction;
import radium.vianavigo.http.JsonResponseHandler;

import com.damnhandy.uri.template.MalformedUriTemplateException;
import com.damnhandy.uri.template.UriTemplate;
import com.damnhandy.uri.template.VariableExpansionException;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

public class LocationSearcher {
	
	/*private Location example;*/
	
	private LocationFilter filter;
	
	/*protected LocationSearcher(Location example) {
		super();
		
		this.example = example;
	}*/
	
	protected LocationSearcher(LocationFilter filter) {
		super();
		
		this.filter = filter;
	}
	
	/*public static LocationSearcher searchLocationsByName(String name) {
		Location example = Location.valueOf(name, null, null);
		return searchLocationsByExample(example);
	}*/
	
	public static LocationSearcher searchLocationsBy(LocationFilter filter) {
		return new LocationSearcher(filter);
	}
	
	/*protected static LocationSearcher searchLocationsByExample(Location example) {
		return new LocationSearcher(example);
	}*/
	
	public List<Location> usingHttpClient(CloseableHttpClient client) throws MalformedUriTemplateException, VariableExpansionException, ClientProtocolException, IOException {
		UriTemplate uriTemplate = UriTemplate.fromTemplate("https://ws.vianavigo.com/stif_web_services/rest/searchPoints/{name}");
		
		uriTemplate = filter.alterUriTemplate(uriTemplate);
		URI uri = URI.create(uriTemplate.expand());
		JsonNode responseAsJson = client.execute(new HttpGet(uri), new JsonResponseHandler());
		List<JsonNode> locationsAsJson = select(path("$.list[]")).on(responseAsJson).asNodeList(new Predicate<JsonNode>() {

			@Override
			public boolean apply(JsonNode node) {
				return node.get("type").asText().equals("StopArea");
			}
			
		});
		List<Location> locations = Lists.newArrayList(Collections2.<Location>filter(Lists.transform(locationsAsJson, JsonNodeToLocationFunction.get()), filter));
		return locations; 
	}
	
}
