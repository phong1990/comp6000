package Servlets;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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
 * Servlet implementation class download
 */
@WebServlet("/download")
public class download extends HttpServlet {
    // size of byte buffer to send file
    private static final int BUFFER_SIZE = 4096;  
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public download() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if (session != null) {
			User user = (User) session.getAttribute("user");
			if (user != null) {
				if (request.getParameter("submissionid") != null) {
					int submissionID = Integer.parseInt(request.getParameter("submissionid"));

					try {

						PostgresDB db = PostgresDB.getInstance();
						Submission sub = db.getSingleSubmission(submissionID);
						// start downloading
						byte[] data;
						data = PostgresDB.getInstance().retrievingOIDFile(sub.getFileOID(), submissionID);
						InputStream inputStream = new ByteArrayInputStream(data);
						int fileLength = inputStream.available();
						System.out.println("fileLength = " + fileLength);
						ServletContext context = getServletContext();
						// sets MIME type for the file download
						String mimeType = context.getMimeType(sub.getFileName());
						if (mimeType == null) {
							mimeType = "application/octet-stream";
						}
						// set content properties and header attributes for the response
						response.setContentType(mimeType);
						response.setContentLength(fileLength);
						String headerKey = "Content-Disposition";
						String headerValue = String.format("attachment; filename=\"%s\"", sub.getFileName());
						response.setHeader(headerKey, headerValue);
						// writes the file to the client
						OutputStream outStream = response.getOutputStream();
						byte[] buffer = new byte[BUFFER_SIZE];
						int bytesRead = -1;
						while ((bytesRead = inputStream.read(buffer)) != -1) {
							outStream.write(buffer, 0, bytesRead);
						}
						inputStream.close();
						outStream.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block

						request.setAttribute("message", Util.addH3ToText("Error: file is not accessible right now!"));
						request.setAttribute("submissionID", submissionID);
						RequestDispatcher view = request.getRequestDispatcher("DetailPageServlet");
						view.forward(request, response);
					}
				} else {
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
