package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import algorithm.Course;
import algorithm.Schedule;
import algorithm.ScheduleMaker;
import uscapi.WebScraper;

@WebServlet(
	name ="MyServlet2",
	urlPatterns = {"/ScheduleRequest"}
)

public class ScheduleRequestServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String coursesraw = req.getParameter("coursesraw"); // courses seperated by ,
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
		ArrayList<Schedule> genSchedules = sm.getSchedules(5); // hardcoded to 5
		
		PrintWriter out = resp.getWriter();
		out.println(genSchedules.get(0).detailedInfo());
		out.flush();
		out.close();
		
		
		
	}
	
}
