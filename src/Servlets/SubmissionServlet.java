package Servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import Models.User;
import Utils.PostgresDB;
import Utils.Util;

/**
 * Servlet implementation class SubmissionServlet
 */
@WebServlet("/SubmissionServlet")
@MultipartConfig(maxFileSize = 16177215) // upload file's size up to 16MB
public class SubmissionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SubmissionServlet() {
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
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		/* TODO output your page here. You may use following sample code. */

		HttpSession session = request.getSession(false);
		if (session != null) {
			User user = (User) session.getAttribute("user");
			if (user != null) {
				String description = request.getParameter("description");
				InputStream inputStream_file = null; // input stream of the
														// upload file
				InputStream inputStream_thumbnail = null; // input stream of the
															// upload thumbnail
				// obtains the upload file part in this multipart request
				Part thumbnailPart = request.getPart("thumbnail");
				Part filePart = request.getPart("file");
				try {
					if (!thumbnailPart.getSubmittedFileName().isEmpty()
							&& !filePart.getSubmittedFileName().isEmpty()) {
						// prints out some information for debugging
						// System.out.println(filePart.getName());
						// System.out.println(filePart.getSize());
						// System.out.println(filePart.getContentType());

						// obtains input stream of the upload file
						inputStream_thumbnail = thumbnailPart.getInputStream();
						inputStream_file = filePart.getInputStream();
						int submissionID = PostgresDB.getInstance()
								.addSubmission(user.getDBID(), description,
										inputStream_file,
										inputStream_thumbnail);

						String message = "";
						if (submissionID > 0) {
							message = "File uploaded and saved into database";
							System.out.println(message);
							request.setAttribute("submissionID", submissionID);
							request.setAttribute("message", message);
							RequestDispatcher rd = request
									.getRequestDispatcher("DetailPageServlet");
							rd.forward(request, response);
						} else {
							message = "Something is wrong!";
							request.setAttribute("message",
									Util.addH3ToText(message));
							request.getRequestDispatcher("Submission.jsp")
									.include(request, response);
						}

					} else {
						String message = "Need to choose both thumbnail and file";
						request.setAttribute("message",
								Util.addH3ToText(message));
						request.getRequestDispatcher("Submission.jsp")
								.include(request, response);
					}
				} catch (SQLException e) {
					String message = "Something is wrong within the DB!";
					request.setAttribute("message", Util.addH3ToText(message));
					request.getRequestDispatcher("Submission.jsp")
							.include(request, response);
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

}
