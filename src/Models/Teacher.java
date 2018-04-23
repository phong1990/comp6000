package Models;

import java.sql.SQLException;

import Utils.PostgresDB;

public class Teacher extends User {

	public Teacher(int dbid, String fn, String ln) {
		super(dbid, fn, ln);
		// TODO Auto-generated constructor stub
	}

	public void grade(int grade, int submission_id) throws SQLException {
		PostgresDB.getInstance().changeGradeForSubmission(submission_id, grade);
	}
	
}
