package com.github.radium226.vianavigo;

import com.google.common.base.Objects;
import com.google.common.base.Optional;

public class Transportation {

	public static enum Type {
		TRAIN, METRO, TRAMWAY, RER, BUS, WALK
	}
	
	private Type type;
	private Optional<String> name;
	private Location direction;
	
	protected Transportation(Transportation.Type type, Optional<String> name, Location direction) {
		super();
		
		this.type = type; 
		this.name = name;
		this.direction = direction;
	}
	
	public Transportation(Transportation.Type type, Location direction) {
		this(type, Optional.<String>absent(), direction);
	}
	
	public Transportation(Transportation.Type type, String name, Location direction) {
		this(type, Optional.<String>of(name), direction);
	}
	
	public Optional<String> getName() {
		return this.name; 
	}
	
	public Transportation.Type getType() {
		return this.type; 
	}
	
	public Location getDirection() {
		return direction;
	}
	
	@Override
	public boolean equals(Object object) {
		boolean equality = false;
		if (object instanceof Transportation) {
			Transportation that = (Transportation) object;
			equality = Objects.equal(this.type, that.type) && Objects.equal(this.name, that.name) && Objects.equal(this.direction, that.direction);
		}
		return equality;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.type, this.name, this.direction);
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("type", this.type)
				.add("name", this.name)
				.add("name", this.direction)
			.toString();
	}
	
}
