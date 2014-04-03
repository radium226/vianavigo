package com.github.radium226.vianavigo;

import java.util.EnumSet;
import java.util.List;


public abstract class AbstractViaNavigoInterface implements ViaNavigo.Interface {
	
	public AbstractViaNavigoInterface() {
		super();
	}
	
	public Itinerary planItinerary(ItineraryPlanner planner) {
		Location departure = planner.getDeparture().transformToRight(SearchLocationFunction.with(this));
		Location arrival = planner.getArrival().transformToRight(SearchLocationFunction.with(this));
		EnumSet<Mode> modes = planner.getModes();
		
		Itinerary itinerary = planItinerary(departure, arrival, modes);
		return itinerary;
	}
	
	public Location searchLocation(LocationSearcher searcher) {
		String name = searcher.getName();
		LocationChooser chooser = searcher.getChooser().or(LocationChoosers.undefined());
		
		Location location = null; 
		List<Location> locations = searchLocation(name);
		if (locations.size() > 1) {
			location = chooser.chooseLocation(locations);
		} else if (locations.size() == 0) {
			throw new NoLocationFoundException();
		} else if (locations.size() == 1) {
			location = locations.get(0);
		}
		
		return location;
	}
	
	protected abstract Itinerary planItinerary(Location departure, Location arrival, EnumSet<Mode> modes);
	
	protected abstract List<Location> searchLocation(String locationName);
}
