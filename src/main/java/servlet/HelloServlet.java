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

@WebServlet(
        name = "MyServlet", 
        urlPatterns = {"/login"}
    )
public class HelloServlet extends HttpServlet {

//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		doGet(req, resp);
//	}
	
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	
    	String userName = req.getParameter("lusername");
    	String passHash = req.getParameter("lpassword");
    	
    	
    	
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
			
			HttpSession session = req.getSession();
            session.setAttribute("user", rs.getInt("userID"));
			RequestDispatcher dispatcher = req.getRequestDispatcher("home.jsp");
            dispatcher.forward(req, resp);

			
    	} catch (SQLException sqle) {
			System.err.println("sqle: " + sqle.getMessage());
			
			req.setAttribute("message", "javascript/loginerror.js");
            RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
            dispatcher.forward(req, resp);
			
//			ServletOutputStream out = resp.getOutputStream();
//			byte[] fileContent = Files.readAllBytes(Paths.get("src/main/webapp/index.html"));
//	        out.write(fileContent);
//	        out.flush();
//	        out.close();			
//			resp.sendRedirect("src/main/webapp/index.html");
//			resp.sendRed
		}
    }
    
}
