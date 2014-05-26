package radium.vianavigo.filter;

import radium.vianavigo.Location;

import com.damnhandy.uri.template.UriTemplate;

public class ByCodeLocationFilter extends AbstractLocationFilter {

	private String code;
	
	private ByCodeLocationFilter(String code) {
		super();
		
		this.code = code;
	}
	
	public static ByCodeLocationFilter forCode(String code) {
		return new ByCodeLocationFilter(code);
	}

	@Override
	public boolean apply(Location location) {
		return location.getCode().equals(code);
	}

	@Override
	public UriTemplate alterUriTemplate(UriTemplate uriTemplate) {
		return uriTemplate;
	}
	
	
	
}
