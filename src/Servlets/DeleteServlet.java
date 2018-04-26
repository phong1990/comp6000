package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Models.User;
import Utils.PostgresDB;
import Utils.Util;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();
		if (session != null) {
			User user = (User) session.getAttribute("user");
			if (user != null) {
				if (request.getParameter("submissionid") != null) {
					int submissionID = Integer
							.parseInt(request.getParameter("submissionid"));
					try {
						PostgresDB.getInstance().deleteSubmission(submissionID);;

						request.setAttribute("message", "Deleted");
						request.setAttribute("submissionID", submissionID);
						request.getRequestDispatcher("gridview.jsp").include(request, response);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						request.setAttribute("message", Util.addH3ToText("Error: can't rate right now"));
						request.setAttribute("submissionID", submissionID);
						RequestDispatcher view = request
								.getRequestDispatcher("DetailPageServlet");
						view.forward(request, response);
					}

				} else {
					out.print(
							"<h3>You are not supposed to be here, nosey little ...</h3>");
				}
			} else {

				String message = "Please login first!";
				request.setAttribute("message", Util.addH3ToText(message));
				request.getRequestDispatcher("login.jsp").include(request, response);
			}

		} else {
			String message = "Please login first!";
			request.setAttribute("message", Util.addH3ToText(message));
			request.getRequestDispatcher("login.jsp").include(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
