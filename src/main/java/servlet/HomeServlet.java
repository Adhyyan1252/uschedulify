package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(
	name ="MyServlet2",
	urlPatterns = {"/home"}
)

public class HomeServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		Integer userID = (Integer) session.getAttribute("userID");
		
		if(userID == null) {
			resp.sendRedirect("/");
			return;
		}
		
		String userName = (String) session.getAttribute("userName");
		
		System.out.println("Visiting homepage");
		
		req.setAttribute("userName", userName);
		RequestDispatcher dispatcher = req.getRequestDispatcher("home.jsp");
        dispatcher.forward(req, resp);

		
	}
	
}
