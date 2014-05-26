package radium;

import static radium.vianavigo.LocationFilters.name;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import org.apache.http.impl.client.CloseableHttpClient;

import radium.util.HttpUtil;
import radium.vianavigo.Itinerary;
import radium.vianavigo.ItinerarySearcher;
import radium.vianavigo.Location;
import radium.vianavigo.LocationDateTime;
import radium.vianavigo.LocationSearcher;
import radium.vianavigo.Step;
import radium.vianavigo.Transportation;
import radium.vianavigo.http.Proxy;

import com.damnhandy.uri.template.MalformedUriTemplateException;
import com.damnhandy.uri.template.VariableExpansionException;

public class TryWebService {

    public static void main(String[] arguments) throws URISyntaxException, IOException, MalformedUriTemplateException, VariableExpansionException {
        CloseableHttpClient client = newHttpClient();
        try {
        	Location departure = LocationSearcher.searchLocationsBy(name("Noisy le grand")).usingHttpClient(client).get(0);
        	System.out.println("departure = " + departure);
        	
        	Location arrival = LocationSearcher.searchLocationsBy(name("Jules Joffrin")).usingHttpClient(client).get(0);
        	System.out.println("arrival = " + arrival);
        	
        	List<Itinerary> itineraries = ItinerarySearcher.searchItineraries(LocationDateTime.valueOf(departure, new Date()), arrival, Transportation.Type.all()).usingHttpClient(client);
        	for (Itinerary itinerary : itineraries) {
        		System.out.println("-----");
        		for (Step step : itinerary.getSteps()) {
        			Location stepDeparture = step.getDeparture().getLocation();
        			Location stepArrival = step.getArrival();
        			System.out.println(" - " + stepDeparture.getName() + " -==[ " + step.getTransportation().getType() + " (" + step.getTransportation().getName().or("n/a") + " vers " + step.getTransportation().getDirection() + " ]==> " + stepArrival.getName());
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
