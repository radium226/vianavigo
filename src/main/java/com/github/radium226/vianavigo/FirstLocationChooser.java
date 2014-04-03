package com.github.radium226.vianavigo;

import com.github.radium226.vianavigo.Location;
import com.github.radium226.vianavigo.LocationChooser;
import java.util.List;

public class FirstLocationChooser implements LocationChooser {

	public FirstLocationChooser() {
		super();
	}
	
	@Override
	public Location chooseLocation(List<Location> locations) {
		Location first = locations.get(0);
		return first;
	}

}
