package algorithm;

import java.util.ArrayList;

//Class which is used to make a schedule given the classes and rules
//Make an instance of this class and add the classes to the array

public class ScheduleMaker {
	
	private ArrayList<Unit> units;
	
	public ScheduleMaker() {
		
	}
	
	public void addUnit(Unit u) {
		units.add(u);
	}
	
	//returns a schedule based on the classes added
	public Schedule generateClass() {
		return null;
	}
	
}
