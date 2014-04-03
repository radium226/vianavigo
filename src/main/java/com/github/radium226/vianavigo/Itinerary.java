package com.github.radium226.vianavigo;

import java.util.List;

public class Itinerary {

	private Location departure;
	private Location arrival;
	private List<Step> steps;
	
	public Location getDeparture() {
		return departure;
	}
	
	public Location getArrival() {
		return arrival;
	}
	
	public List<Step> getSteps() {
		return steps;
	}
	
}
