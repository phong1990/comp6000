package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;

import Models.Comment;
import Models.Submission;
import Models.User;
import Utils.PostgresDB;
import Utils.Util;

/**
 * Servlet implementation class DetailPageServlet
 */
@WebServlet("/DetailPageServlet")
public class DetailPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DetailPageServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		/* TODO output your page here. You may use following sample code. */

		HttpSession session = request.getSession(false);
		if (session != null) {
			User user = (User) session.getAttribute("user");
			if (user != null) {
				try {
					String message = request.getParameter("message");
					if (message == null)
						message = "";
					int submissionID = Integer
							.parseInt(request.getParameter("submissionID"));
					PostgresDB db = PostgresDB.getInstance();
					Submission sub = db.getSingleSubmission(submissionID);
					List<Submission> sublist = db.getAllSubmissions();
					String image = Util.getImageForBrowser(submissionID, db, sub);
					float avgrating = (float) Util.round(
							db.getAVGRatingForASubmission(submissionID), 1);
					int user_id = user.getDBID();
					String dropdownList = getRatingDrpdList(submissionID, db,
							user_id);

					String teacherForm = getTeacherForm(submissionID, db, user);
					List<Comment> commentList = db
							.getCommentsForASubmission(submissionID);
					// set the attributes
					request.setAttribute("subid", submissionID);
					request.setAttribute("message", message);
					request.setAttribute("commentList", commentList);
					request.setAttribute("teacherForm", teacherForm);
					request.setAttribute("dropdownList", dropdownList);
					request.setAttribute("thumbnail", image);
					request.setAttribute("description", sub.getDescription());
					request.setAttribute("avgrating", avgrating);
					RequestDispatcher view = request
							.getRequestDispatcher("detailPage.jsp");
					view.forward(request, response);
				} catch (SQLException e) {
					out.print(
							"<h3>Something is wrong with this submission!</h3>");
					RequestDispatcher view = request
							.getRequestDispatcher("gridview.jsp");
					view.forward(request, response);
				} catch (NumberFormatException ne) {
					// TODO: handle exception
					out.print("<h3>You need to choose a submission first</h3>");
					RequestDispatcher view = request
							.getRequestDispatcher("gridview.jsp");
					view.forward(request, response);
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


	private String getTeacherForm(int submissionID, PostgresDB db, User user)
			throws SQLException {
		String role = user.getRole();
		StringBuilder teacherForm = new StringBuilder();
		if (role.equals(User.TEACHER)) {
			int grade = db.getGradeForSubmission(submissionID);
			teacherForm.append("<table style=\"width: 60%\"><tr>");
			teacherForm.append(
					"			<form method=\"post\" action=\"GraderServlet\">");
			teacherForm
					.append("				<td>Grade:<input type=\"hidden\" name=\"submissionid\" value=\"")
					.append(submissionID).append("\" /></td>");
			teacherForm
					.append("				<td><input type=\"text\" name=\"grade\" size=\"15\" value=\"")
					.append(grade).append("\" />/100</td>");
			teacherForm.append(
					"				<td><input type=\"submit\" value=\"Change Grade\"></td>");
			teacherForm.append("			</form>" + "		</tr></table>");
		}
		return teacherForm.toString();
	}

	private String getRatingDrpdList(int submissionID, PostgresDB db,
			int user_id) throws SQLException {
		int rating = db.checkCurrentRatingForASubmission(submissionID, user_id);
		StringBuilder dropdownList = new StringBuilder();
		dropdownList.append(
				"<select name=\"drplistRating\">  onchange=\"this.form.submit()\"");
		if (rating == -1) {
			dropdownList.append("<option value=\"-1\">-</option>");
			for (int i = 1; i < 6; i++) {
				dropdownList.append("<option value=\"").append(i).append("\">")
						.append(i).append(" star(s)</option>");
			}
		} else {
			dropdownList.append("<option value=\"").append(rating).append("\">")
					.append(rating).append(" star(s)</option>");
			for (int i = 1; i < 6; i++) {
				if (i != rating)
					dropdownList.append("<option value=\"").append(i)
							.append("\">").append(i)
							.append(" star(s)</option>");
			}
		}
		dropdownList.append("</select>");
		return dropdownList.toString();
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
