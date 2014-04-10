package com.github.radium226.vianavigo.ws;

import com.github.radium226.vianavigo.JSONResponseHandler;
import java.io.IOException;
import java.net.URISyntaxException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

public class TryWebService {

    public static void main(String[] arguments) throws URISyntaxException, IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet(new URIBuilder()
                    .setScheme("https")
                    .setHost("ws.vianavigo.com")
                    .setPath("/stif_web_services/rest/itinerarySearch")
                    .addParameter("enableNewCriteria", "true")
                    .addParameter("departureType", "StopArea")
                    .addParameter("departureCity", "Paris")
                    .addParameter("departure", "GARE DE L'EST, Paris")
                    .addParameter("arrival", "Richelieu Drouot")
                    .addParameter("modes", "11111")
                    .addParameter("walkSpeed", "1")
                    .addParameter("itineraryType", "5")
                    .addParameter("date", "2014-04-10T01:53")
                    .addParameter("dateType", "1")
                .build());
            
            JSONObject jsonResponse = client.execute(request, new JSONResponseHandler());
            System.out.println(jsonResponse);
            
        } finally {
            client.close();
        }
    }
    
}
//https://ws.vianavigo.com/stif_web_services/rest/itinerarySearch?enableNewCriteria=true&departureType=StopArea&departureCity=Paris&departure=GARE+DE+L%27EST%2C+Paris&arrival=Richelieu+Drouot&modes=11111&walkSpeed=1&changeDate=1&&itineraryType=5

