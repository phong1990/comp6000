package Models;

import java.sql.SQLException;

import Utils.PostgresDB;

public class User {
	protected String firstName;
	protected String lastName;
	protected int DBID;
	public static final String TEACHER = "teacher";
	public static final String STUDENT = "student";
	public static final String ADMIN = "admin";

	public int getDBID() {
		return DBID;
	}

	public String getfirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getRole() throws SQLException {
		return PostgresDB.getInstance().getRole(DBID);
	}

	public User(int dbid, String fn, String ln) {
		// TODO Auto-generated constructor stub
		firstName = fn;
		lastName = ln;
		DBID = dbid;
	}

	public void addComment(int submissionDBID, String comment)
			throws SQLException {
		PostgresDB.getInstance().addACommentForSubmission(submissionDBID, DBID,
				comment);
	}

	public void addRating(int submissionDBID, int rating) throws SQLException {
		PostgresDB.getInstance().addRatingForSubmission(submissionDBID, DBID,
				rating);
	}

}
