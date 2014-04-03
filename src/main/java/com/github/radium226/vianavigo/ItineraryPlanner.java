package com.github.radium226.vianavigo;

import java.util.EnumSet;

import com.github.radium226.commons.Either;

public class ItineraryPlanner {

	private Either<String, Location> departure;
	private Either<String, Location> arrival;
	private EnumSet<Mode> modes;
	
	private ItineraryPlanner(Either<String, Location> departure) {
		super();
		
		this.departure = departure;
	}
	
	public static ItineraryPlanner from(Location departure) {
		return new ItineraryPlanner(Either.<String, Location>right(departure));
	}
	
	public static ItineraryPlanner from(String departure) {
		return new ItineraryPlanner(Either.<String, Location>left(departure));
	}
	
	public ItineraryPlanner to(Location arrival) {
		this.arrival = Either.<String, Location>right(arrival);
		return this;
	}
	
	public ItineraryPlanner to(String arrival) {
		this.arrival = Either.<String, Location>left(arrival);
		return this;
	}
	
	public ItineraryPlanner by(Mode firstMode, Mode... otherModes) {
		this.modes = EnumSet.of(firstMode, otherModes);
		return this;
	}
	
	public Either<String, Location> getDeparture() {
		return departure;
	}
	
	public Location getDeparture(SearchLocationFunction function) {
		return getDeparture().transformToRight(function);
	}
	
	public Either<String, Location> getArrival() {
		return arrival;
	}
	
	public EnumSet<Mode> getModes() {
		return modes;
	}
	
}
