package com.github.radium226.vianavigo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.damnhandy.uri.template.MalformedUriTemplateException;
import com.damnhandy.uri.template.UriTemplate;
import com.damnhandy.uri.template.VariableExpansionException;
import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;

public class TryViaNavigo {

	final public static String VIA_NAVIGO = "http://www.vianavigo.com";
	
	final public static String SEARCH_HOST = "www.google.com";
	
	final public static String SEARCH_LOCATIONS_URI_TEMPLATE = "/search{?output,q}";
	final public static String PLAN_ITINERARY_URI_TEMPLATE = "/stif_web_carto/comp/itinerary/search.html?";
	
	public static void main(String[] arguments) throws Throwable {
		CloseableHttpClient client = newHttpClient();
		try {
			//search(httpClient, "42");
			//List<Location> locations = searchLocations(client, "ATLAS");
			//System.out.println(locations);
			
			planItinerary(client, "CHATELET LES HALLES", "Jules Joffrin, Paris", new Date(), new UserLocationChooser());
		} finally {
			IOUtil.closeQuietly(client);
		}
	}
	
	public static List<Location> searchLocations(HttpClient client, String name) throws VariableExpansionException, MalformedUriTemplateException, ClientProtocolException, IOException {
		HttpHost host = new HttpHost("www.vianavigo.com");
		String uriTemplate = "/stif_web_carto/rest/searchPoints/{name}";
		URI uri = URI.create(UriTemplate.fromTemplate(uriTemplate)
				.set("name", name)
			.expand());
		System.out.println("uri = " + uri);
		HttpGet request = new HttpGet(uri);
		JSONObject rootAsJSON = client.execute(host, request, new JSONResponseHandler());
		
		List<Location> locations = Lists.newArrayList();
		JSONArray listAsJSON = rootAsJSON.getJSONArray("list");
		for (int index = 0; index < listAsJSON.length(); index++) {
			JSONObject locationAsJSON = listAsJSON.getJSONObject(index);
			String typeAsString = locationAsJSON.getString("type");
			if (typeAsString.equals("StopArea")) {
				String nameAsString = locationAsJSON.getString("name");
				String codeAsString = locationAsJSON.getString("externalCode");
				
				String cityNameAsString = locationAsJSON.getString("city");
				String cityCodeAsString = locationAsJSON.getString("cityCode");
				
				Location location = Location.valueOf(nameAsString, codeAsString, City.valueOf(cityNameAsString, cityCodeAsString));
				locations.add(location);
				
			}
		}
		return locations; 
	}
	
	public static void planItinerary(HttpClient client, String departureName, String arrivalName, Date dateTime, LocationChooser locationChooser) throws VariableExpansionException, MalformedUriTemplateException, ClientProtocolException, IOException, TransformerException, ParseException {
		HttpHost host = new HttpHost("www.vianavigo.com");
		String uriTemplate = "/stif_web_carto/comp/itinerary/search.html{?criteria,departure*,arrival*,submitSearchItinerary,date,dateFormat,hour,min}";
		String dateFormat = "dd/MM/yyyy";
		
		Location departure = locationChooser.chooseLocation(searchLocations(client, departureName));
		Location arrival = locationChooser.chooseLocation(searchLocations(client, arrivalName));
		System.out.println(" --> departure = " + departure);
		System.out.println(" --> arrival = " + arrival);
		
		
		URI uri = URI.create(UriTemplate.fromTemplate(uriTemplate)
				.set("departure", LocationVarExploder.forDeparture(departure))
				.set("arrival", LocationVarExploder.forArrival(arrival))
				.set("dateFormat", dateFormat)
				.set("date", new SimpleDateFormat(dateFormat).format(dateTime))
				.set("hour", new SimpleDateFormat("HH").format(dateTime))
				.set("min", new SimpleDateFormat("mm").format(dateTime))
				.set("criteria", 1)
				.set("submitSearchItinerary", "")
			.expand());
		
		HttpGet request = new HttpGet(uri);
		System.out.println(uri);
		Document document = client.execute(host, request, new HTMLResponseHandler());
		scrapSteps(document);
	}	
	
	// https://www.google.fr/search?output=search&q=42
	public static void search(CloseableHttpClient httpClient, String search) {
		try {
			HttpHost host = new HttpHost(SEARCH_HOST);
			HttpGet request = new HttpGet(newSearchURI(search));
			CloseableHttpResponse response = httpClient.execute(host, request);

			String text = null; 
			try {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				
				try {
					text = new String(ByteStreams.toByteArray(content));
				} finally {
					content.close();
				}
				
			} finally {
				response.close();
			}
			
			System.out.println(text);
			
		} catch (ClientProtocolException e) {
			throw new RuntimeException(e);
		} catch (MalformedUriTemplateException e) {
			throw new RuntimeException(e);
		} catch (VariableExpansionException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static URI newSearchURI(String search) throws VariableExpansionException, MalformedUriTemplateException {
		String uri = UriTemplate.fromTemplate(SEARCH_LOCATIONS_URI_TEMPLATE)
				.set("output", "search")
				.set("q", search)
			.expand();
		
		return URI.create(uri);
	}
	
	public static HttpRoutePlanner newProxyRoutePlanner() {
		HttpHost proxyHost = newProxyHost();
		DefaultProxyRoutePlanner proxyRoutePlanner = new DefaultProxyRoutePlanner(proxyHost);
		return proxyRoutePlanner;
	}
	
	public static Credentials newProxyCredentials() {
		return new UsernamePasswordCredentials(Proxy.USER, Proxy.PASSWORD);
	}
	
	public static CloseableHttpClient newHttpClient() {
		HttpRoutePlanner proxyRoutePlanner = newProxyRoutePlanner();
		
		CloseableHttpClient httpClient = HttpClientBuilder.create()
				.setRoutePlanner(proxyRoutePlanner)
				.setDefaultCredentialsProvider(newProxyCredentialsProvider())
			.build();
		
		return httpClient;
	}
	
	public static CredentialsProvider newProxyCredentialsProvider() {
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(new AuthScope(newProxyHost()), newProxyCredentials());
		return credentialsProvider;
	}
	
	public static HttpHost newProxyHost() {
		return new HttpHost(Proxy.HOST, Proxy.PORT);
	}
	
	public static void scrapSteps(Document document) throws ParseException {
		String query = "html body div#contentItinerary.includeContent div div#scrollResultTable table.mainTableResult > tbody > tr";
		Elements elements = document.select(query);
		
		String locationAsString = null; 
		String dateTimeAsString = null; 
		String transportationTypeAsString = null; 
		String transportationNameAsString = null; 
		Pattern pattern = Pattern.compile("^((Départ|Arrivée) : )?(.+)$");
		List<Step> steps = Lists.newArrayList(); 
		Location location = null; 
		Date dateTime = null; 
		LocationDateTime departure = null, arrival = null;  
		Transportation transportation = null; 
		for (int index = 0; index < elements.size(); index++) {
			transportationNameAsString = null; // Reset
			
			Element element = elements.get(index);
			System.out.println("----- " + index + " -----");
			switch (index % 2) {
			case 0:
				dateTimeAsString = element.select("> td:eq(0)").text();
				System.out.println("dateTimeAsString = " + dateTimeAsString);
				
				locationAsString = element.select("> td.place > div[class~=connection_(departure|arrival)] > p > strong").text();
				Matcher matcher = pattern.matcher(locationAsString);
				if (matcher.matches()) {
					locationAsString = matcher.group(3);
				}
				System.out.println("locationAsString = " + locationAsString);
				
				break;
			case 1:
				Elements childElements = element.select("> td.pictoList > *");
				Element firstChildElement = childElements.get(0);
				String nodeName = firstChildElement.nodeName();
				if (nodeName.equals("img")) {
					if (!firstChildElement.attr("alt").equals("Marche")) throw new IllegalStateException();
					transportationTypeAsString = firstChildElement.attr("alt");
					
				} else if (nodeName.equals("table")) {
					// Metro
					Elements imageElements = firstChildElement.select("> tbody > tr > td:eq(0) > img");
					transportationTypeAsString = imageElements.get(0).attr("alt");
					transportationNameAsString = imageElements.get(1).attr("alt");
					
					System.out.println("subway = " + transportationTypeAsString + " " + transportationNameAsString);
				} else {
					throw new IllegalStateException();
				}
				break;
			}
			
			switch (index % 3) {
			case 0:
				System.out.println("We... ");
				// Departure
				location = Location.valueOf(locationAsString, "", null);
				dateTime = new SimpleDateFormat("HH:mm").parse(dateTimeAsString);
				departure = new LocationDateTime(location, dateTime);
				break;
			case 1:
				System.out.println("...Are...");
				// Transportation
				transportation = transportationNameAsString != null ? new Transportation(parseTransportationType(transportationTypeAsString), transportationNameAsString) : new Transportation(parseTransportationType(transportationTypeAsString));
				break;
			case 2:
				System.out.println("...Here! ");
				// Arrival
				location = Location.valueOf(locationAsString, "", null);
				dateTime = new SimpleDateFormat("HH:mm").parse(dateTimeAsString);
				arrival = new LocationDateTime(location, dateTime);
				
				Step step = new Step(departure, transportation, arrival);
				steps.add(step);				
				break;
			}
		}
		
		System.out.println(steps);
	}
	
	public static Transportation.Type parseTransportationType(String alt) {
		Transportation.Type transportationType = null; 
		if (alt.equals("RER")) {
			transportationType = Transportation.Type.RER;
		} else if (alt.equals("Metro")) {
			transportationType = Transportation.Type.METRO;
		} else if (alt.equals("Marche")) {
			transportationType = Transportation.Type.WALK;
		}
		return transportationType;
	}
	
}
