package com.github.radium226.vianavigo;

import java.util.Date;

import com.google.common.base.Objects;

public class LocationDateTime {

	private Date dateTime; 
	private Location location;
	
	public LocationDateTime(Location location, Date dateTime) {
		super();
		
		this.location = location;
		this.dateTime = dateTime;
	}
	
	public Date getDateTime() {
		return this.dateTime;
	}
	
	public Location getLocation() {
		return this.location; 
	}
	
	@Override
	public boolean equals(Object object) {
		boolean equality = false;
		if (object instanceof LocationDateTime) {
			LocationDateTime that = (LocationDateTime) object;
			equality = Objects.equal(this.location, that.location) && Objects.equal(this.dateTime, that.dateTime);
		}
		return equality;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.location, this.dateTime);
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("location", this.location)
				.add("dateTime", this.dateTime)
			.toString();
	}
	
}
