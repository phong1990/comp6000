package Models;

import java.sql.SQLException;

import Utils.PostgresDB;

public class Admin extends User{

	public Admin(int dbid, String fn, String ln) {
		super(dbid, fn, ln);
		// TODO Auto-generated constructor stub
	}
	public void changeRole(User user, String role) throws SQLException{
		PostgresDB.getInstance().changeRole(DBID, role);
	}

	public int getNumberOfSubmission(int authorDBID) throws SQLException {
		return PostgresDB.getInstance().countSubmissionForAnAuthor(authorDBID);
	}

	public float getAverageRating(int authorDBID) throws SQLException {
		return PostgresDB.getInstance().getAVGRatingForAUser(authorDBID);
	}

	public float getAverageGrade(int authorDBID) throws SQLException {
		return PostgresDB.getInstance().getAVGGradeForAUser(authorDBID);
	}
	
	public int countSubmissionForAnAuthor(int authorDBID) throws SQLException{
		return PostgresDB.getInstance().countSubmissionForAnAuthor(authorDBID);
	}
}
