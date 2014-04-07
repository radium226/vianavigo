package com.github.radium226.vianavigo;

import com.google.common.base.Objects;
import com.google.common.base.Optional;

public class Transportation {

	public static enum Type {
		WALK, TRAIN, RER, BUS, METRO, TRAMWAY
	}
	
	private Type type;
	private Optional<String> name;
	
	protected Transportation(Transportation.Type type, Optional<String> name) {
		super();
		
		this.type = type; 
		this.name = name;
	}
	
	public Transportation(Transportation.Type type) {
		this(type, Optional.<String>absent());
	}
	
	public Transportation(Transportation.Type type, String name) {
		this(type, Optional.<String>of(name));
	}
	
	public Optional<String> getName() {
		return this.name; 
	}
	
	public Transportation.Type getType() {
		return this.type; 
	}
	
	@Override
	public boolean equals(Object object) {
		boolean equality = false;
		if (object instanceof Transportation) {
			Transportation that = (Transportation) object;
			equality = Objects.equal(this.type, that.type) && Objects.equal(this.name, that.name);
		}
		return equality;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.type, this.name);
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("type", this.type)
				.add("name", this.name)
			.toString();
	}
	
}
