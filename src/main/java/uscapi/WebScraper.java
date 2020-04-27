package uscapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import algorithm.Course;
import algorithm.Schedule;
import algorithm.Section;
import algorithm.TimeInterval;
import algorithm.Timer;
import database.DatabaseConnector;

public class WebScraper extends Thread {
	String major;
	String number;
	Course courses;
	public WebScraper(String maj, String num)
	{
		major = maj;
		number = num;
	}
	
	public static void main(String[] args) {
		/*Course a = null;
		Course b = null;
		try {
			a = getCourse("CSCI", "104");
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		try {
			//b = AddCourse("CSCI", "201");
			//System.out.println(b.detailedInfo());
		} catch (Exception e) { e.printStackTrace(); }
	
		//Schedule returnedschedule = DatabaseConnector.retrieveSchedule(31, 11);
		//System.out.println(returnedschedule.detailedInfo());
		System.out.println(a.detailedInfo());
		//System.out.println(b.detailedInfo());*/
		
		
		/*ExecutorService executor = Executors.newFixedThreadPool(3);
		executor.execute(new WebScraper("CSCI", "201"));
		executor.execute(new WebScraper("CSCI", "103"));
		executor.execute(new WebScraper("CSCI", "270"));
		executor.shutdown();*/
	}
	
	public Course getCourse(String major, String number) throws Exception {
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
		System.out.println(course.detailedInfo());
		return course;
	}
	
	private int[] RegisterHelper(String reg) {
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

	private ArrayList<TimeInterval> TimeHelper(String time, String day) {
		//System.out.println("PARSING " + time);
		ArrayList<TimeInterval> interval = new ArrayList<TimeInterval>();
		Timer start = new Timer(), end = new Timer();
		
		try {
			String amOrPm = time.substring(time.length() - 2);
			String[] helper = time.substring(0, time.length() - 2).split("-");
			start.hour = Integer.parseInt(helper[0].split(":")[0]);
			start.min = Integer.parseInt(helper[0].split(":")[1]);
			
			end.hour = Integer.parseInt(helper[1].split(":")[0]);
			end.min = Integer.parseInt(helper[1].split(":")[1]);
			
			
			if(amOrPm.equals("am")){
				//dont do anything
			}else if(amOrPm.equals("pm")){
				if(end.hour != 12) {
					end.hour += 12;
				}
				if(start.hour != 12 && end.hour != 12 && start.hour <= end.hour) {
					start.hour += 12;
				}
			}else {
				throw new Exception("not am or pm");
			}
			
		}catch (Exception e) {
			start.hour = -1;
			start.min = -1;
			end.hour = -1;
			end.min = -1;
		}
		
		if(start.hour != -1 && !day.equalsIgnoreCase("tba")) {
			ArrayList<Integer> days = ParseDay(day);
			for(int i = 0;i < days.size();i++ ) {	
				Timer sta = new Timer(days.get(i), start.hour, start.min);
				Timer en = new Timer(days.get(i), end.hour, end.min);
				TimeInterval insert = new TimeInterval(sta, en);
				interval.add(insert);
			}
		}
		//System.out.println("RETURNING " + interval);
		return interval;
	}
	
	
	private ArrayList<Integer> ParseDay(String day){
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
	
	public void run() {
		try {
			courses = getCourse(major, number);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
