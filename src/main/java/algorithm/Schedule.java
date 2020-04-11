package algorithm;

import java.util.ArrayList;

//A Schedule is a list of sections

public class Schedule {
	public int scheduleID;
	public ArrayList<Section> sections;
	
	//returns all the sections in the schedule which have at least one timing on this day
	public ArrayList<Section> getByDay(int day){
		ArrayList<Section> ret = new ArrayList<Section>();
		for(Section s : sections) {
			if(s.isOnDay(day) != null) ret.add(s);
		}
		return ret;
	}
	
	//returns a String with information about each section in the schedule
	public String detailedInfo() {
		String ret = "SCHEDULE ID: " + scheduleID + "\n";
		for(Section s : sections) {
			ret += s.toString() + "\n";
		}
		return ret;
	}
	
}
