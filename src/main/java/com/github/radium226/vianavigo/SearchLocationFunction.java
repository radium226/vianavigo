package com.github.radium226.vianavigo;

import com.google.common.base.Function;
import static com.github.radium226.vianavigo.LocationSearcher.named;

public class SearchLocationFunction implements Function<String, Location> {

	private ViaNavigo.Interface viaNavigo;
	
	private SearchLocationFunction(ViaNavigo.Interface viaNavigo) {
		super();
		
		this.viaNavigo = viaNavigo;
	}
	
	@Override
	public Location apply(String location) {
		return viaNavigo.searchLocation(named(location));
	}

	public static SearchLocationFunction with(ViaNavigo.Interface viaNavigo) {
		return new SearchLocationFunction(viaNavigo);
	}
	
}
