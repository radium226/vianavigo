package com.github.radium226.vianavigo;

import com.google.common.base.Objects;

public class Location {
	
	private String name;
	
	protected Location(String name) {
		super();
		
		this.name = name;
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("name", this.name)
			.toString();
	}
	
	@Override
	public boolean equals(Object object) {
		boolean equality = false;
		if (object instanceof Location) {
			Location that = (Location) object;
			equality = Objects.equal(this.name, that.name);
		}
		return equality;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.name);
	}
	
	public static Location valueOf(String name) {
		return new Location(name);
	}
	
}
