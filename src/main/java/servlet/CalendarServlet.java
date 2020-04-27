package servlet;
import algorithm.Schedule;
import algorithm.Section;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import database.DatabaseConnector;

@WebServlet(
        name = "CalendarServlet", 
        urlPatterns = {"/CalendarServlet"}
    )
public class CalendarServlet extends HttpServlet {
	
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    		int ID = Integer.parseInt((String)req.getParameter("ID"));
    		int userID = -1;
    		Schedule currsch= DatabaseConnector.retrieveSchedule(ID, userID);
    		Gson gson = new Gson();
    		PrintWriter out = resp.getWriter();
    		out.println(gson.toJson(currsch));
    		System.out.println();
    		System.out.println(currsch.detailedInfo());
    		for (Section section: currsch.sections) {
    			System.out.println(section.toString());
    		}
    		System.out.println();
    		out.flush();
    		out.close();
    		
    }
    
}
