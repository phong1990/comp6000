package Models;

import java.sql.SQLException;

import Utils.PostgresDB;

public class Submission {
	private int DBID;
	private int authorID;
	private long fileOID;
	private long thumbnailOID;
	private String description;

	public long getFileOID() {
		return fileOID;
	}

	public long getThumbnailOID() {
		return thumbnailOID;
	}

	public Submission(int submission_id, String description, int author_id,
			long file_oid, long thumbnail_oid) {
		// TODO Auto-generated constructor stub
		DBID = submission_id;
		this.description = description;
		authorID = author_id;
		fileOID = file_oid;
		thumbnailOID = thumbnail_oid;
	}

	public int getGrade() throws SQLException {
		return PostgresDB.getInstance().getGradeForSubmission(DBID);
	}

	public int getDBID() {
		return DBID;
	}

	public String getDescription() {
		return description;
	}

	public void delete() throws SQLException {
		PostgresDB.getInstance().deleteSubmission(DBID);
	}
}
