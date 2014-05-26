package radium.vianavigo.filter;

import radium.vianavigo.Location;

import com.damnhandy.uri.template.UriTemplate;
import com.google.common.base.Predicates;

public class AndLocationFilter extends AbstractLocationFilter {

	private LocationFilter one;
	private LocationFilter other;
	
	public AndLocationFilter(LocationFilter one, LocationFilter other) {
		super();
		
		this.one = one;
		this.other = other;
	}
	
	@Override
	public boolean apply(Location location) {
		return Predicates.and(one, other).apply(location);
	}

	@Override
	public UriTemplate alterUriTemplate(UriTemplate uriTemplate) {
		return one.alterUriTemplate(other.alterUriTemplate(uriTemplate));
	}

}
