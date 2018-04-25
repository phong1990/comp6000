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

import Models.Submission;
import Models.User;
import Utils.PostgresDB;
import Utils.Util;

/**
 * Servlet implementation class FileDownloadServlet
 */
@WebServlet("/FileDownloadServlet")
public class FileDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FileDownloadServlet() {
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
				if (request.getParameter("submissionid") != null) {
					int submissionID = Integer
							.parseInt(request.getParameter("submissionid"));
					try {
						PostgresDB db = PostgresDB.getInstance();
						Submission sub = db.getSingleSubmission(submissionID);
						// start downloading
						byte[] data;
						data = PostgresDB.getInstance().retrievingOIDFile(
								sub.getFileOID(), submissionID);
						response.setHeader("Content-length",
								Integer.toString(data.length));
						response.setHeader("Content-Disposition",
								"attachment; filename=xyz.bin");
						response.getOutputStream().write(data, 0, data.length);
						response.getOutputStream().flush();
						
						request.setAttribute("message", "");
						request.setAttribute("submissionID", submissionID);
						RequestDispatcher view = request
								.getRequestDispatcher("DetailPageServlet");
						view.forward(request, response);
					} catch (SQLException e) {
						// TODO Auto-generated catch block

						request.setAttribute("message", Util.addH3ToText(
								"Error: file is not accessible right now!"));
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
				request.getRequestDispatcher("login.jsp").include(request,
						response);
			}

		} else {
			String message = "Please login first!";
			request.setAttribute("message", Util.addH3ToText(message));
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
