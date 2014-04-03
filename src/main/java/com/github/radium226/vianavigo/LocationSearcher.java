package com.github.radium226.vianavigo;

import com.google.common.base.Optional;

public class LocationSearcher {

	private String name;
	private Optional<LocationChooser> chooser;
	
	private LocationSearcher(String name) {
		super();
		
		this.name = name;
	}
	
	public static LocationSearcher named(String name) {
		LocationSearcher searcher = new LocationSearcher(name);
		return searcher;
	}
	
	public String getName() {
		return name;
	}
	
	public Optional<LocationChooser> getChooser() {
		return chooser;
	}
	
	public LocationSearcher chooseWith(LocationChooser chooser) {
		this.chooser = Optional.<LocationChooser>of(chooser);
		return this;
	}
	
}
