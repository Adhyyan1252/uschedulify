package servlet;

import java.io.IOException;
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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@WebServlet(
        name = "MyServlet", 
        urlPatterns = {"/login"}
    )
public class LoginServlet extends HttpServlet {
	
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	
    	String userName = req.getParameter("lusername");
    	String stringToEncrypt = req.getParameter("lpassword");
    	String passHash = "";
    	MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(stringToEncrypt.getBytes());
	    	String temp = new String(messageDigest.digest());
	    	for(int i = 0; i < temp.length() && i < 10; i++) {
	    		passHash += (char)(temp.charAt(i)%26 + 'a');
	    	}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
    	System.out.println(passHash);
    	if(userName == null) {
    		resp.sendRedirect("/");
    		return;
    	}
    	
    	
    	try {
	    	Connection conn = null; // Establish connection to database
			ResultSet rs = null; // What comes back from a select statement
			PreparedStatement ps = null;
			
			conn = DriverManager.getConnection("jdbc:mysql://us-cdbr-iron-east-01.cleardb.net:3306/heroku_15f10f75c7431e6?user=b5a203584b9d69&password=205de108&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC"); // URI - Uniform Resource Identifier
			ps = conn.prepareStatement("SELECT * FROM auth WHERE userName = ?");
			ps.setString(1, userName); // set first variable in prepared statement
			rs = ps.executeQuery();
			rs.next();
			
			if(!passHash.equals(rs.getString("passHash"))) {
				throw new SQLException();
			}
			
			int userID = rs.getInt("userID");
			
			HttpSession session = req.getSession();
			System.out.println("setting userID to " + userID);
            session.setAttribute("userID", userID);
            session.setAttribute("userName", userName);
			RequestDispatcher dispatcher = req.getRequestDispatcher("/home");
            dispatcher.forward(req, resp);

			
    	} catch (SQLException sqle) {
			System.err.println("sqle: " + sqle.getMessage());
			
			req.setAttribute("message", "javascript/loginerror.js");
            RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
            dispatcher.forward(req, resp);
			
		}
    }
    
}
