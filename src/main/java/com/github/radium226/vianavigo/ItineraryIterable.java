package com.github.radium226.vianavigo;

import java.util.Iterator;

public class ItineraryIterable implements Iterable<Itinerary> {
	
	@Override
	public Iterator<Itinerary> iterator() {
		return new ItineraryIterator();
	}

}
