package radium.vianavigo;

import radium.vianavigo.filter.ByCodeLocationFilter;
import radium.vianavigo.filter.ByNameLocationFilter;
import radium.vianavigo.filter.LocationFilter;

public class LocationFilters {

	private LocationFilters() {
		super();
	}
	
	public static LocationFilter name(String name) {
		return ByNameLocationFilter.forName(name);
	}
	
	public static LocationFilter code(String code) {
		return ByCodeLocationFilter.forCode(code);
	}
	
}
