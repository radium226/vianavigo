package com.github.radium226.vianavigo;

import com.google.common.base.Objects;

public class Step {

	private LocationDateTime departure; 
	private Location arrival;
	private Transportation transportation;
	
	public Step(LocationDateTime departure, Transportation transportation, Location arrival) {
		super();
		
		this.departure = departure;
		this.transportation = transportation;
		this.arrival = arrival;
	}
	
	public LocationDateTime getDeparture() {
		return this.departure;
	}
	
	public Location getArrival() {
		return this.arrival;
	}
	
	public Transportation getTransportation() {
		return this.transportation; 
	}
	
	@Override
	public boolean equals(Object object) {
		boolean equality = false;
		if (object instanceof Step) {
			Step that = (Step) object;
			equality = Objects.equal(this.departure, that.departure) && Objects.equal(this.transportation, that.transportation) && Objects.equal(this.arrival, that.arrival);
		}
		return equality;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.departure, this.transportation, this.arrival);
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("departure", this.departure)
				.add("transportation", this.transportation)
				.add("arrival", this.arrival)
			.toString();
	}
	
}
