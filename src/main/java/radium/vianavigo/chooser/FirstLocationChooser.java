package radium.vianavigo.chooser;

import java.util.List;

import radium.vianavigo.Location;

public class FirstLocationChooser implements LocationChooser {

	public FirstLocationChooser() {
		super();
	}
	
	@Override
	public Location chooseLocation(List<Location> locations) {
		Location first = locations.get(0);
		return first;
	}

}
