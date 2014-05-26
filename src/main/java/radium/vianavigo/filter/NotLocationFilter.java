package radium.vianavigo.filter;

import radium.vianavigo.Location;

import com.damnhandy.uri.template.UriTemplate;
import com.google.common.base.Predicates;

public class NotLocationFilter extends AbstractLocationFilter {

	private LocationFilter one;
	
	public NotLocationFilter(LocationFilter one) {
		super();
		
		this.one = one;
	}
	
	@Override
	public boolean apply(Location location) {
		return Predicates.not(one).apply(location);
	}

	@Override
	public UriTemplate alterUriTemplate(UriTemplate uriTemplate) {
		return one.alterUriTemplate(uriTemplate);
	}

}
