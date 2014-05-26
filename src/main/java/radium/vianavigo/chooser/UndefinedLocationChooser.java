package radium.vianavigo.chooser;

import java.util.List;

import radium.vianavigo.Location;
import radium.vianavigo.UnableToChooseLocationException;

public class UndefinedLocationChooser implements LocationChooser {

	public UndefinedLocationChooser() {
		super();
	}
	
	@Override
	public Location chooseLocation(List<Location> location) {
		throw new UnableToChooseLocationException();
	}

}
