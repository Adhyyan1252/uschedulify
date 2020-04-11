package algorithm;

import java.util.ArrayList;

//I wanted to call this a "Class" but not allowed

//A Unit is for eg. CS201. 

public class Unit {

	public String major, number;
	public ArrayList<Section> sections;
	
	public String toString() {
		return major + number;
	}
	
	public String detailedInfo() {
		String ret = toString() + "\n";
		for(Section s : sections) {
			ret += s.toString() + "\n";
		}
		return ret;
	}
}
