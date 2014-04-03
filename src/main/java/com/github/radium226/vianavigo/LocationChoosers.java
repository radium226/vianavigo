package com.github.radium226.vianavigo;

public class LocationChoosers {

	public static LocationChooser undefined() {
		return new UndefinedLocationChooser();
	}
	
	public static LocationChooser first() {
		return new FirstLocationChooser();
	}
	
}
