package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Models.User;
import Utils.PasswordAuthentication;
import Utils.PostgresDB;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
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
		String username = request.getParameter("username");
		String fname = request.getParameter("firstname");
		String lname = request.getParameter("lastname");
		String password = request.getParameter("password");
		PostgresDB db = PostgresDB.getInstance();
		try {
			// store the hash, not the password
			PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
			if (db.registerUser(fname, lname, username,
					passwordAuthentication.hash(password), User.STUDENT)) {
				request.setAttribute("message", "registered!");
				request.getRequestDispatcher("login.jsp").include(request,
						response);
			} else {
				request.setAttribute("message",
						"Sorry, that username has been taken!");
				request.getRequestDispatcher("register.jsp").include(request,
						response);
			}
		} catch (SQLException ex) {
			Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE,
					null, ex);
			request.setAttribute("message",
					"Register unsucessful. Database is down!");
			request.getRequestDispatcher("register.jsp").include(request,
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
