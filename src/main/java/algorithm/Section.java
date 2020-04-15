package algorithm;

import java.util.ArrayList;

//A section is a single listed option such as a Lecture, Discussion, Lab or Quiz 

public class Section {

	public String sectionID;
	public ArrayList<TimeInterval> timing;
	
	public int currentRegistered, maxRegistered;
	
	public String majorname;
	public String classname; //reference to unit
	public String type;
	public String instructor, location;
	public Section(String SID, ArrayList<TimeInterval> time, int cr, int mr, String t, String in, String lo, String mn, String cn) {
		sectionID = SID;
		timing = time;
		currentRegistered = cr;
		maxRegistered = mr;
		type = t;
		instructor = in;
		location = lo;
		majorname = mn;
		classname = cn;
	}
	
	public Section() {
		classname = "";
	}

	public String toString() {
		return classname.toString() + ": " + type + ":" + sectionID;
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
		if(currentRegistered == -1 && maxRegistered == -1) {
			return false;
		}
		else {
			return currentRegistered < maxRegistered;
		}
	}
	
	public void print() {
		if(currentRegistered == -1 && maxRegistered == -1) {
			System.out.println(sectionID + " " + type + " " + "closed" + " " + instructor + " " + location);
		}
		else {
			System.out.println(sectionID + " " + type + " " + currentRegistered + " " + maxRegistered + " " + instructor + " " + location);
		}
		for(int i = 0; i < timing.size(); i++) {
			timing.get(i).start.print();
			timing.get(i).end.print();
		}
	}
}
