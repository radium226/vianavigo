package com.github.radium226.vianavigo;

import com.google.common.base.Objects;

public class City {

	private String name;
	private String code;
	
	protected City(String name, String code) {
		super();
		this.name = name; 
		this.code = code;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public boolean equals(Object object) {
		boolean equality = false;
		if (object instanceof City) {
			City that = (City) object;
			equality = Objects.equal(this.name, that.name) && Objects.equal(this.code, that.code);
		}
		return equality;
	}
	
	public int hashCode() {
		return Objects.hashCode(this.name, this.code);
	}
	
	public String toString() {
		return Objects.toStringHelper(this)
				.add("name", this.name)
				.add("code", this.code)
			.toString();
	}
	
	public static City valueOf(String name, String code) {
		return new City(name, code);
	}
	
}
