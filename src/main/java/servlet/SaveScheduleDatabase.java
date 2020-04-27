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
        name = "SaveScheduleDatabase", 
        urlPatterns = {"/SaveScheduleDatabase"}
    )
public class SaveScheduleDatabase extends HttpServlet {
	
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
    		String sid = req.getParameter("id");
    		System.out.println("FLAG: " + sid);
    		int scheduleID = -1;
    		if (sid != null) { scheduleID = Integer.parseInt(sid); }
    		int userID = (Integer) req.getSession().getAttribute("userID");
    		String response = DatabaseConnector.saveToDatabase(scheduleID, userID);
    		PrintWriter out = resp.getWriter();
    		out.println(response);
    		out.flush();
    		out.close();
    }
    
}
