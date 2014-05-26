package radium.vianavigo.filter;

import radium.vianavigo.Location;

import com.damnhandy.uri.template.UriTemplate;
import com.google.common.base.Predicate;

public interface LocationFilter extends Predicate<Location> {

	public LocationFilter and(LocationFilter that);
	
	public LocationFilter or(LocationFilter that);
	
	public LocationFilter not();
	
	public UriTemplate alterUriTemplate(UriTemplate uriTemplate);

}
