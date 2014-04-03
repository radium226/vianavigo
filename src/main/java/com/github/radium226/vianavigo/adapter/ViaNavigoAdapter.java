package com.github.radium226.vianavigo.adapter;

import java.util.EnumSet;
import java.util.List;

import com.github.radium226.vianavigo.AbstractViaNavigoInterface;
import com.github.radium226.vianavigo.Itinerary;
import com.github.radium226.vianavigo.Location;
import com.github.radium226.vianavigo.Mode;

public class ViaNavigoAdapter extends AbstractViaNavigoInterface {

	public ViaNavigoAdapter() {
		super();
	}
	
	@Override
	protected Itinerary planItinerary(Location departure, Location arrival, EnumSet<Mode> modes) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected List<Location> searchLocation(String locationName) {
		// TODO Auto-generated method stub
		return null;
	}

}
