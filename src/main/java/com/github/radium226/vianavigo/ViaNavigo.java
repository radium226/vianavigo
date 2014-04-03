package com.github.radium226.vianavigo;

import com.github.radium226.vianavigo.adapter.ViaNavigoAdapter;


final public class ViaNavigo {

	private ViaNavigo() {
		super();
	}
	
	public static interface Interface {
		
		public Itinerary planItinerary(ItineraryPlanner planner);
		
		public Location searchLocation(LocationSearcher searcher);
		
	}
	
	public static Itinerary planItinerary(ItineraryPlanner planner) {
		return newAdapter().planItinerary(planner);
	}
	
	public static Location searchLocation(LocationSearcher searcher) {
		return newAdapter().searchLocation(searcher);
	}
	
	private static ViaNavigoAdapter newAdapter() {
		return new ViaNavigoAdapter();
	}
	
}
