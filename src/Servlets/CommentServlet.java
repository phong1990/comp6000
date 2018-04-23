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

/**
 * Servlet implementation class CommentServlet
 */
@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CommentServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();
		if (session != null) {
			User user = (User) session.getAttribute("user");
			if (user != null) {
				if (request.getParameter("comment") != null) {
					String comment = request.getParameter("comment");
					int submissionID = Integer
							.parseInt(request.getParameter("submissionid"));
					if (!comment.isEmpty()) {
						try {
							PostgresDB.getInstance().addACommentForSubmission(
									submissionID, user.getDBID(), comment);

							request.setAttribute("message", "New comment added!");
							request.setAttribute("submissionID", submissionID);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							request.setAttribute("message",
									"Error: can't comment right now");
							request.setAttribute("submissionID", submissionID);
						}
					} else {
						request.setAttribute("message", "Comment is empty..");
						request.setAttribute("submissionID", submissionID);
					}
					RequestDispatcher view = request
							.getRequestDispatcher("DetailPageServlet");
					view.forward(request, response);

				} else {
					out.print(
							"<h3>You are not supposed to be here, nosey little ...</h3>");
				}
			} else {

				out.print("<h3>Please login first</h3>");
				request.getRequestDispatcher("login.jsp").include(request,
						response);
			}

		} else {
			out.print("<h3>Please login first</h3>");
			request.getRequestDispatcher("login.jsp").include(request,
					response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
