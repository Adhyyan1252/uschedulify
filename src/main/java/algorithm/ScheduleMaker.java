package algorithm;

import java.util.ArrayList;
import java.util.Comparator;

import uscapi.WebScraper;

//Class which is used to make a schedule given the classes and rules
//Make an instance of this class and add courses
//then call generateClass
//then sort the schedules
//and the call getSchedule

public class ScheduleMaker {
	
	public static void main(String[] args) {
		ScheduleMaker sm = new ScheduleMaker();
		
		try {
			sm.addCourse(WebScraper.getCourse("CSCI", "102"));
			//sm.addCourse(WebScraper.getCourse("CSCI", "109"));
			sm.addCourse(WebScraper.getCourse("CSCI", "201"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		int gen = sm.generateSchedule();
		System.out.println(gen);
		sm.sortClasses();
		ArrayList<Schedule> schedules = sm.getSchedules(5);
		for(Schedule s : schedules)
			System.out.println(s.detailedInfo());
	}
	
	
	private ArrayList<Course> courses;
	private ArrayList<Schedule> schedules; 
	private ArrayList<Section> stack;
	
	public ScheduleMaker() {
		courses = new ArrayList<Course>();
		schedules = new ArrayList<Schedule>();
		stack = new ArrayList<Section>();
	}
	
	public void addCourse(Course u) {
		courses.add(u);
	}
	
	//generates as many schedules as it can
	//returns number of schedules generated
	public int generateSchedule() {
		stack = new ArrayList<Section>();
		schedules.clear();
		brute(courses.size() - 1, "Lecture", -1);
		return schedules.size();
	}
	
	//recursive function to compute all the different schedules
	//function will try all possible combinations by adding it to the stack
	//once a full schedule is made it will append it to schedules
	
	//classIndx is the class which we are currently considering
	//type is Lecture, Lab, Discussion, Quiz
	//lectureID is the last lecture index which we added
	
	//first select a lecture
	//then select a discussion
	//then select a lab
	//then select a quiz
	private void brute(int classIndx, String type, int lectureIndx) {
		//System.out.println("BRUTE: " + classIndx + " " + type + " " + lectureIndx +  " " + stack.size());
		if(classIndx == -1) { //reached the end
			Schedule curSchedule = new Schedule();
			for(Section curSection : stack) {
				curSchedule.sections.add(curSection);
			}
			schedules.add(curSchedule);
			return;
		}
		
		
		ArrayList<Section> s = courses.get(classIndx).sections;
		if(type.equals("Lecture")) {
			
			boolean found = false;
			for(int i = 0; i < s.size(); i++) {
				if(s.get(i).type.equals(type)) {
					found = true;
					if(validate(s.get(i))) {
						stack.add(s.get(i));
						brute(classIndx, "Discussion", i);
						stack.remove(stack.size() - 1);
					}
				}
			}
			if(!found) brute(classIndx-1, "Lecture", -1);
			
		}else if(type.equals("Discussion")) {
			boolean found = false;
			for(int i = lectureIndx + 1; i < s.size(); i++) {
				if(s.get(i).type.equals(type)) {
					found = true;
					if(validate(s.get(i))) {
						stack.add(s.get(i));
						brute(classIndx, "Lab", i);
						stack.remove(stack.size()-1);
					}
				}else if(s.get(i).type.contentEquals("Lecture")) {
					if(found) return;
				}
			}
			if(!found) {
				brute(classIndx, "Lab", lectureIndx);
			}
		}else if(type.equals("Lab")) {
			boolean found = false;
			for(int i = 0; i < s.size(); i++) {
				
				if(s.get(i).type.equals("Lab")) {
					found = true;
					if(validate(s.get(i))) {
						stack.add(s.get(i));
						brute(classIndx, "Quiz", i);
						stack.remove(stack.size() - 1);
					}
				}
			}
			if(!found) brute(classIndx, "Quiz", -1);
		}else if(type.equals("Quiz")){
			boolean found = false;
			for(int i = 0; i < s.size(); i++) {
				if(s.get(i).type.equals(type)) {
					found = true;
					if(validate(s.get(i))) {
						stack.add(s.get(i));
						brute(classIndx-1, "Lecture", 0);
						stack.remove(stack.size() - 1);
					}
				}
			}
			if(!found) brute(classIndx-1, "Lecture", 0);
		}
	}
	
	//check whether we can add section s to the stack without contradicting any other section
	//also makes sure that there is space left in this section
	private boolean validate(Section s) {
		if(!s.isOpen()) return false;
		for(Section a : stack) {
			if(Section.isIntersecting(a, s)) {
				return false;
			}
		}
		return true;
	}
	

	//returns upto max different schedules
	public ArrayList<Schedule> getSchedules(int max){
		ArrayList<Schedule> ret = new ArrayList<Schedule>();
		for(int i = 0; i < max && i < schedules.size(); i++)
			ret.add(schedules.get(i));
		return  ret;
	}
	

	
	public void sortClasses() {
		for(Schedule s : schedules) {
			s.computeStatistics();
		}
		schedules.sort(new SortByDaysOff());
		
	}
	
	static class SortByDaysOff implements Comparator<Schedule> 
	{ 
	    public int compare(Schedule a, Schedule b) 
	    { 
	        return b.daysOff - a.daysOff;
	    } 
	}
	
	static class SortByStdDev implements Comparator<Schedule> 
	{ 
	    public int compare(Schedule a, Schedule b) 
	    { 
	        if(a.daysOff == b.daysOff) return (int)(a.stddev - a.stddev);
	        else return b.daysOff - a.daysOff;
	    } 
	}
	
	

}