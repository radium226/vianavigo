package radium.vianavigo.filter;

import radium.vianavigo.Location;

import com.damnhandy.uri.template.UriTemplate;

public class ByNameLocationFilter extends AbstractLocationFilter {

	private String name;
	
	private ByNameLocationFilter(String name) {
		super();
		
		this.name = name;
	}
	
	@Override
	public boolean apply(Location location) {
		return true;
	}

	@Override
	public UriTemplate alterUriTemplate(UriTemplate uriTemplate) {
		return uriTemplate.set("name", name);
	}
	
	public static ByNameLocationFilter forName(String name) {
		return new ByNameLocationFilter(name);
	}

}
