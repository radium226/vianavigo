package com.github.radium226.vianavigo;

import static com.github.radium226.vianavigo.ItineraryPlanner.from;
import static com.github.radium226.vianavigo.Mode.*;
import static com.github.radium226.vianavigo.LocationSearcher.named;

public class Try {

	public static void main(String[] arguments) {
//		Itinerary itinerary = ViaNavigo.planItinerary(from("Jules Joffrin").to("Ch�telet").by(METRO));
//		for (Step step : itinerary.getSteps()) {
//			System.out.println(step);
//		}
		
		Location departure = ViaNavigo.searchLocation(named("test"));
		
		Itinerary itinerary = ViaNavigo.planItinerary(from(departure).to("Jules Joffrin"));
		for (Step step : itinerary.getSteps()) {
			
		}
	}
	
}
