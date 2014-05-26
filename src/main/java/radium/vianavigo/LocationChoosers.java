package radium.vianavigo;

import radium.vianavigo.chooser.FirstLocationChooser;
import radium.vianavigo.chooser.LocationChooser;
import radium.vianavigo.chooser.UndefinedLocationChooser;
import radium.vianavigo.chooser.UserLocationChooser;

public class LocationChoosers {

	public static LocationChooser undefined() {
		return new UndefinedLocationChooser();
	}
	
	public static LocationChooser first() {
		return new FirstLocationChooser();
	}
	
	public static LocationChooser ask() {
		return new UserLocationChooser();
	}
	
}
