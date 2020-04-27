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
import java.util.ArrayList;

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
        name = "SavedServlet", 
        urlPatterns = {"/SavedServlet"}
    )
public class SavedServlet extends HttpServlet {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
    		int userID = (Integer) req.getSession().getAttribute("userID");
    		ArrayList<Integer> arr = DatabaseConnector.retrieveSavedSchedules(userID);
    		PrintWriter out = resp.getWriter();
    		System.out.println(arr.size());
    		for (Integer i: arr) {
    			out.println(i);
    		}
    		out.flush();
    		System.out.println("REACHED END");
    		out.close();
    }
    
}
