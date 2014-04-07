package com.github.radium226.vianavigo;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class UserLocationChooser implements LocationChooser {

	public UserLocationChooser() {
		super();
	}
	
	@Override
	public Location chooseLocation(List<Location> locations) {
		int size = locations.size();
		for (int index = 0; index < size; index++) {
			Location location = locations.get(index);
			String name = location.getName();
			System.out.println(String.format("%d. %s", index, name));
		}
		
		int index = readInteger(System.in);
		return locations.get(index);
	}
	
	protected int readInteger(InputStream inputStream) {
		Scanner scanner = new Scanner(inputStream);
		int integer = scanner.nextInt();
		return integer;
	}

}
