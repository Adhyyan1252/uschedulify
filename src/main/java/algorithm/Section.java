package algorithm;

import java.util.ArrayList;

//A section is a single listed option such as a Lecture, Discussion, Lab or Quiz 

public class Section {

	public String sectionID;
	public ArrayList<TimeInterval> timing;
	
	public int currentRegistered, maxRegistered;
	
	public Course course; //reference to unit
	public String type;
	public String instructor, location;
	public String classname;
	
	public String toString() {
		return course.toString() + ": " + type + ":" + sectionID;
	}
	
	//returns a TimeInterval if there is a timing on that day
	//if there is more than one, it will return any
	//otherwise return null;
	public TimeInterval isOnDay(int day) {
		for(TimeInterval ti : timing) {
			if(ti.getDay() == day) return ti;
		}
		return null;
	}
	
	public boolean isOpen() {
		return currentRegistered < maxRegistered;
	}
}
