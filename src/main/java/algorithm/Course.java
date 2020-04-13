package algorithm;

import java.util.ArrayList;

//I wanted to call this a "Class" but not allowed

//A Unit is for eg. CS201. 

public class Course {

	public String major, number;
	public ArrayList<Section> sections;
	public Course(String maj, String num, ArrayList<Section> sect) {
		major = maj;
		number = num;
		sections = sect;
	}
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
