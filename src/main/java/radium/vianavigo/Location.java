package radium.vianavigo;

import com.google.common.base.Objects;

public class Location {
	
	private String name;
	private String code;
	private City city;
	
	protected Location(String name, String code, City city) {
		super();
		
		this.name = name;
		this.code = code;
		this.city = city;
	}
	
	public String getName() {
		return this.name; 
	}
	
	public String getCode() {
		return code;
	}
	
	
	
	public City getCity() {
		return city;
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("name", this.name)
				.add("code", this.code)
				.add("city", this.city)
			.toString();
	}
	
	@Override
	public boolean equals(Object object) {
		boolean equality = false;
		if (object instanceof Location) {
			Location that = (Location) object;
			equality = Objects.equal(this.name, that.name) && Objects.equal(this.code, that.code) && Objects.equal(this.city, that.city);
		}
		return equality;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.name, this.code, this.city);
	}
	
	public static Location valueOf(String name, String code, City city) {
		return new Location(name, code, city);
	}
	
}
