package radium.vianavigo.filter;

import radium.vianavigo.Location;

import com.damnhandy.uri.template.UriTemplate;

public abstract class AbstractLocationFilter implements LocationFilter {

	public AbstractLocationFilter() {
		super();
	}
	
	@Override
	public abstract boolean apply(Location location);

	@Override
	public LocationFilter and(LocationFilter that) {
		return new AndLocationFilter(this, that);
	}

	@Override
	public LocationFilter or(LocationFilter that) {
		return new OrLocationFilter(this, that);
	}

	@Override
	public LocationFilter not() {
		return new NotLocationFilter(this);
	}

	@Override
	public abstract UriTemplate alterUriTemplate(UriTemplate uriTemplate);

}
