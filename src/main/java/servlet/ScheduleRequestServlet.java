package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import algorithm.Course;
import algorithm.Schedule;
import algorithm.ScheduleMaker;
import database.DatabaseConnector;
import uscapi.WebScraper;

@WebServlet(
	urlPatterns = {"/ScheduleRequest"}
)

public class ScheduleRequestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String coursesraw = req.getParameter("coursesraw"); // courses seperated by ,
		String[] courses = coursesraw.split(",");
		ScheduleMaker sm = new ScheduleMaker();
		
		ExecutorService executor = Executors.newFixedThreadPool(3);
		
		ArrayList<WebScraper> webscrapers = new ArrayList<WebScraper>();
		try {
			for(int i = 0; i < courses.length ; i++) {
				String[] temppair = courses[i].split(" ");
				webscrapers.add(new WebScraper(temppair[0], temppair[1]));
				executor.execute(webscrapers.get(i));
			}
			executor.shutdown();
			while (!executor.isTerminated()) { Thread.yield(); }
			
			for(int i = 0; i < courses.length ; i++) {
				Course tempCourse;
				tempCourse = webscrapers.get(i).courses;
				sm.addCourse(tempCourse);
			}
			
			sm.generateSchedule();
		}catch (Exception e) {
			System.out.println("Couldn't find course");
			e.printStackTrace();
		}
		
		ArrayList<Schedule> genSchedules = sm.getSchedules(5); 
		ArrayList<Integer> IDlist = new ArrayList<Integer>();
		for(Schedule schedule: genSchedules){
			int ID = DatabaseConnector.setSchedule(schedule, (Integer) req.getSession().getAttribute("userID"));
			if(ID != -1) {
				IDlist.add(ID);
			}
			
		}
		PrintWriter out = resp.getWriter();
		for(int i: IDlist){
			out.println(i);
		}
		out.flush();
		out.close();
		
		
		
	}
	
}
