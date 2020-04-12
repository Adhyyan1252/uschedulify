package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
        name = "NotMyServlet", 
        urlPatterns = {"/signup"}
    )
public class SignupServlet extends HttpServlet {
	
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	
    	String userName = req.getParameter("susername");
    	String passHash = req.getParameter("spassword");
    	String confirmPass = req.getParameter("scpassword");
    	
    	
    	
    	try {
    		
    		if(!passHash.equals(confirmPass)) {
    			throw new Exception("javascript/confirmerror.js");
    		}

	    	Connection conn = null; // Establish connection to database
			PreparedStatement ps = null;
			
			conn = DriverManager.getConnection("jdbc:mysql://us-cdbr-iron-east-01.cleardb.net:3306/heroku_15f10f75c7431e6?user=b5a203584b9d69&password=205de108&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC"); // URI - Uniform Resource Identifier
			
			ps = conn.prepareStatement("INSERT INTO auth (userName,passHash) VALUES (?,?);");
			ps.setString(1, userName); // set first variable in prepared statement
			ps.setString(2, passHash);
			int addedrows = ps.executeUpdate();
			System.out.println(addedrows);
			resp.sendRedirect("/");
			
    	} catch (SQLException sqle) {
    		System.err.println("sqle: " + sqle.getMessage());
			req.setAttribute("message", "javascript/usertakenerror.js");
            RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
            dispatcher.forward(req, resp);
    	} catch (Exception e) {
			System.err.println("e: " + e.getMessage());
			req.setAttribute("message", e.getMessage());
            RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
            dispatcher.forward(req, resp);
		}
    }
    
}