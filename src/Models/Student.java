package Models;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import Utils.PostgresDB;

public class Student extends User {

	public Student(int dbid, String fn, String ln) {
		super(dbid, fn, ln);
		// TODO Auto-generated constructor stub
	}

	public void submit(String description, InputStream fis_file,
			InputStream fis_thumbnail) throws SQLException, IOException {
		PostgresDB.getInstance().addSubmission(DBID, description, fis_file,
				fis_thumbnail);
	}

}
