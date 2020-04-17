package algorithm;

import java.util.ArrayList;

//A Schedule is a list of sections

public class Schedule {
	public int userID;
	public ArrayList<Section> sections = new ArrayList<Section>();
	
		
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
		String ret = "";
		for(Section s : sections) {
			ret += s.toString() + "\n";
		}
		return ret;
	}
	
	public boolean allClassesOpen() {
		for(Section s: sections) {
			if(s.isOpen() == false) return false;
		}
		return true;
	}
	
	
	//statistics used for sorting the schedules
	public int daysOff = 0;
	public double stddev = 0;
		
	public void computeStatistics() {	
		int[] times = new int[6];
		int sum = 0;
		for(int d = 1; d <= 5; d++) {
			int count = 0;
			int time = 0;
			
			Timer startTime = new Timer(d, 24, 0);
			Timer endTime = new Timer(d, 0,  0);
			for(Section s : sections) {
				if(s.type.equals("Quiz")) continue;
				for(TimeInterval t : s.timing) {
					if(t.getDay() != d) continue;
					
					count++;
					time += (t.end.convertMin() - t.start.convertMin());
					if(t.end.convertMin() > endTime.convertMin()) {
						endTime = t.end;
					}
					
					if(t.start.convertMin() > startTime.convertMin()) {
						startTime = t.start;
					}
					
				}
			}
			
			times[d] = time;
			sum += time;
		}
		
		for(int i = 1; i < times.length; i++) {
			daysOff += (times[i] == 0)?1:0;
		}
		double mean = sum/(5.00 - daysOff);
		for(int i = 1; i < times.length; i++) {
			if(times[i] > 0) stddev += (times[i] - mean)*(times[i] - mean);
		}
		stddev = sum/(times.length - daysOff - 1);
	}
		
	
	
}
