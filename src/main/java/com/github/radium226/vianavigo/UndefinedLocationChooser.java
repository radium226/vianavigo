package com.github.radium226.vianavigo;

import java.util.List;

public class UndefinedLocationChooser implements LocationChooser {

	public UndefinedLocationChooser() {
		super();
	}
	
	@Override
	public Location chooseLocation(List<Location> location) {
		throw new UnableToChooseLocationException();
	}

}
