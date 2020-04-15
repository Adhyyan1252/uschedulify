package uscapi;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import algorithm.Course;
import algorithm.Schedule;
import algorithm.Section;
import algorithm.TimeInterval;
import algorithm.Timer;
import database.DatabaseConnector;

public class WebScraper {
	public WebScraper()
	{
		
	}
	public static void main(String[] args) {
//		Course a = null;
//		Course b = null;
//		try {
//			a = AddCourse("CSCI", "360");
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//		try {
//			b = AddCourse("CSCI", "201");
//			//System.out.println(b.detailedInfo());
//		} catch (Exception e) { e.printStackTrace(); }
	
		Schedule returnedschedule = DatabaseConnector.retrieveSchedule(31, 11);
		System.out.println(returnedschedule.detailedInfo());
	}
	
	public static Course AddCourse(String major, String number) throws Exception {
		ArrayList<Section> sect = new ArrayList<Section>();
		String address = "https://classes.usc.edu/term-20203/course/" + major + "-" + number + "/";
		org.jsoup.nodes.Document doc;
		try {
			doc = Jsoup.connect(address).get();
			Elements table = doc.select("table[class= sections responsive]");
			Elements rows = ((Elements) table).get(0).select("tr");
			for(org.jsoup.nodes.Element row : rows) {
				if(row.select("td").size() == 10) {
					String ID = row.select("td").get(0).text();
					String type_ = row.select("td").get(2).text();
					String time = row.select("td").get(3).text();
					String days = row.select("td").get(4).text();
					ArrayList<TimeInterval> timelist = TimeHelper(time, days);
					String register = row.select("td").get(5).text();
					int[] registered =  RegisterHelper(register);
					String instructor = row.select("td").get(6).text();
					String location = row.select("td").get(7).text();
					Section input = new Section(ID, timelist, registered[0], registered[1], type_, instructor, location, major, number);
					sect.add(input);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		Course course = new Course(major, number, sect);
		return course;
	}
	
	private static int[] RegisterHelper(String reg) {
		int[] a = new int[2];
		if(reg.equalsIgnoreCase("closed")){
			a[0] = -1;
			a[1] = -1;
			return a;
		}
		String[] helper = reg.split(" ");
		a[0] = Integer.parseInt(helper[0]);
		a[1] = Integer.parseInt(helper[2]);
		if(a[0] == a[1]) {
			a[0] = -1;
			a[1] = -1;
			return a;
		}
		return a;
	}

	private static ArrayList<TimeInterval> TimeHelper(String time, String day) {
		ArrayList<Integer> days = ParseDay(day);
		ArrayList<TimeInterval> interval = new ArrayList<TimeInterval>();
		String[] helper = time.split("-");
		int[] timeend = new int[2];
		int[] timestart = new int[2];
		if(helper.length == 2) {
			timestart = ParseTime(helper[0]);
			timeend = ParseTime(helper[1]);
		}
		else {
			timestart[0] = -1;
			timestart[1] = -1;
			timeend[0] = -1;
			timeend[1] = -1;
		}
		for(int i = 0;i < days.size();i++ ) {
			Timer sta = new Timer(days.get(i), timestart[0], timestart[1]);
			Timer en = new Timer(days.get(i), timeend[0], timeend[1]);
			TimeInterval insert = new TimeInterval(sta, en);
			interval.add(insert);
		}
		return interval;
	}
	
	private static int[] ParseTime(String time){
		String checker = "";
		int[] timer = new int[2];
		String[] separate = time.split(":");
		for(int i = 0; i < separate.length; i++) {
			if(separate[i].length() > 2) {
				checker = separate[i].substring(2, 4);
				separate[i] = separate[i].substring(0, 2);
			}
		}
		if(separate.length == 2) {
			timer[0] = Integer.parseInt(separate[0]);
			if(!checker.equalsIgnoreCase("")){
				if(checker.equalsIgnoreCase("am")) {
					if(timer[0] == 12) {
						timer[0] = 0;
					}
				}
				else if(checker.equalsIgnoreCase("pm")) {
					if(timer[0] != 12) {
						timer[0] += 12;
					}
				}
			}
			else {
				if(timer[0] < 8) {
					timer[0] += 12;
				}
			}
			timer[1] = Integer.parseInt(separate[1]);
		}
		return timer;
	}
	
	private static ArrayList<Integer> ParseDay(String day){
		ArrayList<Integer> days = new ArrayList<Integer>();
		if(day.equals("MWF")) {
			days.add(0);
			days.add(2);
			days.add(4);
		}
		else if(day.equals("MTuWThF")) {
			days.add(0);
			days.add(1);
			days.add(2);
			days.add(3);
			days.add(4);
		}
		else if(day.equals("Mon, Wed")) {
			days.add(0);
			days.add(2);
			
		}
		else if(day.equals("Tue, Thu")) {
			days.add(1);
			days.add(3);
		}
		else if(day.equals("Wed, Thu")) {
			days.add(2);
			days.add(3);
		}
		else if(day.equals("Monday")) {
			days.add(0);
		}
		else if(day.equals("Tuesday")) {
			days.add(1);
		}
		else if(day.equals("Wednesday")) {
			days.add(2);
		}
		else if(day.equals("Thursday")) {
			days.add(3);
		}
		else if(day.equals("Friday")) {
			days.add(4);
		}
		else if(day.contentEquals("TBA")) {
			days.add(7);
		}
		return days;
	}
	
}
