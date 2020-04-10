package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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
			Statement st = null; // How we run an SQL statement
			ResultSet rs = null; // What comes back from a select statement
			
			conn = DriverManager.getConnection("jdbc:mysql://us-cdbr-iron-east-01.cleardb.net:3306/heroku_15f10f75c7431e6?user=b5a203584b9d69&password=205de108&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC"); // URI - Uniform Resource Identifier
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM auth WHERE userName ='" + userName + "'");
			rs.next();
			
			if(!passHash.equals(rs.getString("passHash"))) {
				throw new SQLException();
			}

			ServletOutputStream out = resp.getOutputStream();
	        out.write("Authorized login".getBytes());
	        out.flush();
	        out.close();

			
    	} catch (SQLException sqle) {
			System.err.println("sqle: " + sqle.getMessage());
			
			req.setAttribute("message", "login failed");
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
    	
    	
        ServletOutputStream out = resp.getOutputStream();
        out.write("hello heroku".getBytes());
        out.flush();
        out.close();
    }
    
}
