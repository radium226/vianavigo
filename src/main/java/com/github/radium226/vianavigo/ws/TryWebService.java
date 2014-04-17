package com.github.radium226.vianavigo.ws;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import org.apache.http.impl.client.CloseableHttpClient;

import com.damnhandy.uri.template.MalformedUriTemplateException;
import com.damnhandy.uri.template.VariableExpansionException;
import com.github.radium226.util.HttpUtil;
import com.github.radium226.vianavigo.Itinerary;
import com.github.radium226.vianavigo.Location;
import com.github.radium226.vianavigo.LocationDateTime;
import com.github.radium226.vianavigo.Proxy;
import com.github.radium226.vianavigo.Step;
import com.github.radium226.vianavigo.Transportation;

public class TryWebService {

    public static void main(String[] arguments) throws URISyntaxException, IOException, MalformedUriTemplateException, VariableExpansionException {
        CloseableHttpClient client = newHttpClient();
        try {
        	Location departure = LocationSearcher.searchLocationsByName("Porte de Charenton").usingHttpClient(client).get(0);
        	System.out.println("departure = " + departure);
        	
        	Location arrival = LocationSearcher.searchLocationsByName("Jules Joffrin").usingHttpClient(client).get(0);
        	System.out.println("arrival = " + arrival);
        	
        	List<Itinerary> itineraries = ItinerarySearcher.searchItineraries(LocationDateTime.valueOf(departure, new Date()), arrival, EnumSet.of(Transportation.Type.METRO)).usingHttpClient(client);
        	for (Itinerary itinerary : itineraries) {
        		System.out.println("=============");
        		for (Step step : itinerary.getSteps()) {
        			System.out.println("step = " + step);
        		}
        	}
        } finally {
            client.close();
        }
    }
    
    public static CloseableHttpClient newHttpClient() {
    	CloseableHttpClient client = HttpUtil.newClientWithProxy(Proxy.HOST, Proxy.PORT, Proxy.USER, Proxy.PASSWORD);
        return client;
    }
    
    
    
}
//https://ws.vianavigo.com/stif_web_services/rest/itinerarySearch?enableNewCriteria=true&departureType=StopArea&departureCity=Paris&departure=GARE+DE+L%27EST%2C+Paris&arrival=Richelieu+Drouot&modes=11111&walkSpeed=1&changeDate=1&&itineraryType=5

