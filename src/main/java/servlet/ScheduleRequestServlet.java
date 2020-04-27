package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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
		
		System.out.println("entering schedule request servlet");
		String coursesraw = req.getParameter("coursesraw"); // courses seperated by ,
		
		System.out.println("courses raw is: " + coursesraw);

		String[] courses = coursesraw.split(",");

		ScheduleMaker sm = new ScheduleMaker();

		for(int i = 0; i < courses.length ; i++) {
			String[] temppair = courses[i].split(" ");
			Course tempCourse;
			try {
				tempCourse = WebScraper.getCourse(temppair[0], temppair[1]);
				sm.addCourse(tempCourse);
			} catch (Exception e) {
				System.out.println("Couldn't find course");
				e.printStackTrace();
			}
		}
		
		
		sm.generateSchedule();
		ArrayList<Schedule> genSchedules = sm.getSchedules(2); 
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
