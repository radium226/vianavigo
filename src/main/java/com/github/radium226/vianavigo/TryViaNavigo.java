package com.github.radium226.vianavigo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import org.w3c.dom.Document;

import com.github.radium226.vianavigo.Location;

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
			
			planItinerary(client, "Ch�telet Les Halles, Paris", "Jules Joffrin, Paris", new Date());
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
		
		HttpGet request = new HttpGet(uri);
		JSONObject rootAsJSON = client.execute(host, request, new JSONResponseHandler());
		
		List<Location> locations = Lists.newArrayList();
		JSONArray listAsJSON = rootAsJSON.getJSONArray("list");
		for (int index = 0; index < listAsJSON.length(); index++) {
			JSONObject locationAsJSON = listAsJSON.getJSONObject(index);
			String typeAsString = locationAsJSON.getString("type");
			if (typeAsString.equals("StopArea")) {
				String nameAsString = locationAsJSON.getString("name");
				Location location = Location.valueOf(nameAsString);
				locations.add(location);
				
			}
		}
		return locations; 
	}
	
	public static void planItinerary(HttpClient client, String departure, String arrival, Date dateTime) throws VariableExpansionException, MalformedUriTemplateException, ClientProtocolException, IOException, TransformerException {
		HttpHost host = new HttpHost("www.vianavigo.com");
		String uriTemplate = "/stif_web_carto/comp/itinerary/search.html{?departure,arrival,submitSearchItinerary,date,dateFormat,hour,min}";
		String dateFormat = "dd/MM/yyyy";
		URI uri = URI.create(UriTemplate.fromTemplate(uriTemplate)
				.set("departure", departure)
				.set("arrival", arrival)
				.set("dateFormat", dateFormat)
				.set("date", new SimpleDateFormat(dateFormat).format(dateTime))
				.set("hour", new SimpleDateFormat("HH").format(dateTime))
				.set("min", new SimpleDateFormat("mm").format(dateTime))
				.set("submitSearchItinerary", "")
			.expand());
		
		HttpGet request = new HttpGet(uri);
		System.out.println(uri);
		Document document = client.execute(host, request, new HTMLResponseHandler());
		printDocument(document, System.out);
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
	
	public static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException {
	    TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer transformer = tf.newTransformer();
	    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

	    transformer.transform(new DOMSource(doc), 
	         new StreamResult(new OutputStreamWriter(out, "UTF-8")));
	}
	
}
