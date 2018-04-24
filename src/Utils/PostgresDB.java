package Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.postgresql.largeobject.LargeObject;
import org.postgresql.largeobject.LargeObjectManager;

import Models.Comment;
import Models.Submission;
import Models.User;

public class PostgresDB {
	public static final String DBLOGIN = "postgres";
	public static final String DBPASSWORD = "phdcs2014";
	public static final String MAINDB = "cs6000";

	public static final String CREATE_TABLE_USERS = "CREATE TABLE users(" + "   ID SERIAL PRIMARY KEY NOT NULL,"
			+ "   firstname		 TEXT NOT NULL," + "   lastname          TEXT NOT NULL,"
			+ "   username          TEXT NOT NULL," + "   pashword_hash     TEXT NOT NULL,"
			+ "   role              TEXT NOT NULL" + ")";
	public static final String CREATE_TABLE_SUBMISSIONS = "CREATE TABLE submissions("
			+ "   ID SERIAL PRIMARY KEY NOT NULL," + "   author_id     INT references users(ID)," + "   file      oid,"
			+ "   description   TEXT," + "   thumbnail     oid," + "   grade         INT," + "   delete        boolean"
			+ ");";

	public static final String CREATE_TABLE_COMMENTS = "CREATE TABLE comments(" + "   ID SERIAL PRIMARY KEY NOT NULL,"
			+ "   user_id     INT references users(ID)," + "   submission_id     INT references submissions(ID),"
			+ "   text        TEXT," + "   time        BIGINT" + ")";

	public static final String CREATE_TABLE_RATINGS = "CREATE TABLE ratings(" + "   ID SERIAL PRIMARY KEY NOT NULL,"
			+ "   user_id     INT references users(ID)," + "   submission_id     INT references submissions(ID),"
			+ "   rating      INT)";

	public static final String DAYS_TABLE = "days";
	private Connection mConnect = null;

	private static PostgresDB instance = null;

	public static synchronized PostgresDB getInstance() {
		if (instance == null) {
			instance = new PostgresDB();
		}
		return instance;
	}

	private PostgresDB() {
		// TODO Auto-generated constructor stub
		String url = "jdbc:postgresql://localhost/" + MAINDB;
		List<String> tables = new ArrayList<>();
		tables.add(CREATE_TABLE_USERS);
		tables.add(CREATE_TABLE_SUBMISSIONS);
		tables.add(CREATE_TABLE_COMMENTS);
		tables.add(CREATE_TABLE_RATINGS);
		try {
			tryCreatingDatabase(MAINDB, DBLOGIN, DBPASSWORD);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException ex) {
				Logger.getLogger(PostgresDB.class.getName()).log(Level.SEVERE, null, ex);
			}
			mConnect = DriverManager.getConnection(url, DBLOGIN, DBPASSWORD);
			tryCreatingTables(tables);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void tryCreatingTables(List<String> tables) {
		for (String sqlCreateTable : tables) {
			try {
				Statement statement = mConnect.createStatement();
				statement.execute(sqlCreateTable);
				System.out.println(sqlCreateTable);
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
	}

	public void tryCreatingDatabase(String database, String user, String password) throws ClassNotFoundException {
		Connection connection = null;
		Statement statement = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost/", user, password);
			statement = connection.createStatement();
			String sql = "CREATE DATABASE " + database;
			statement.executeUpdate(sql);
			System.out.println("Database created!");
			statement.close();
		} catch (SQLException sqlException) {
			if (sqlException.getErrorCode() == 1007) {
				// Database already exists error
				// System.out.println(sqlException.getMessage());
			} else {
				// Some other problems, e.g. Server down, no permission, etc
				// sqlException.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// you need to close all three to make sure
	public void close() throws SQLException {
		if (mConnect != null) {
			mConnect.close();
		}
	}

	// return false if user name exist
	public boolean registerUser(String fname, String lname, String username, String password_hash) throws SQLException {
		// check if this username already exists
		final String queryCheck = "SELECT count(*) from users WHERE username= ?";
		PreparedStatement ps = mConnect.prepareStatement(queryCheck);
		ps.setString(1, username);
		final ResultSet resultSet = ps.executeQuery();
		boolean exists = false;
		if (resultSet.next()) {
			if (resultSet.getInt(1) > 0) {
				exists = true;
			}
		}
		ps.close();
		resultSet.close();
		if (exists) {
			return false;
		}
		final String insertUser = "INSERT INTO users VALUES (default,?,?,?,?,'student')";
		ps = mConnect.prepareStatement(insertUser);
		ps.setString(1, fname);
		ps.setString(2, lname);
		ps.setString(3, username);
		ps.setString(4, password_hash);
		ps.executeUpdate();
		ps.close();
		return true;
	}

	// null means not correct username or password
	public User login(String username, String password) throws SQLException {
		final String queryLogin = "SELECT ID,firstname,lastname,role,pashword_hash from users WHERE username= ? ";
		PreparedStatement ps = mConnect.prepareStatement(queryLogin);
		ps.setString(1, username);
		final ResultSet resultSet = ps.executeQuery();
		User user = null;
		if (resultSet.next()) {
			String fname = resultSet.getString("firstname");
			String lname = resultSet.getString("lastname");
			int dbid = resultSet.getInt("ID");
			String hash = resultSet.getString("pashword_hash");
			PasswordAuthentication passAu = new PasswordAuthentication();
			if (passAu.authenticate(password, hash))
				user = new User(dbid, fname, lname);
		}
		ps.close();
		resultSet.close();
		return user;
	}

	public void changeRole(int DBID, String role) throws SQLException {
		final String changeRole = "UPDATE users SET role = ? WHERE ID = ?";
		PreparedStatement ps = mConnect.prepareStatement(changeRole);
		ps.setString(1, role);
		ps.setInt(2, DBID);
		ps.executeUpdate();
		ps.close();
	}

	// null if wrong dbID
	public String getRole(int DBID) throws SQLException {
		final String queryLogin = "SELECT role from users WHERE ID= ?";
		PreparedStatement ps = mConnect.prepareStatement(queryLogin);
		ps.setInt(1, DBID);
		final ResultSet resultSet = ps.executeQuery();
		String role = null;
		if (resultSet.next()) {
			role = resultSet.getString("role");
		}
		ps.close();
		resultSet.close();
		return role;
	}

	public int addSubmission(int author_id, String description, InputStream fis_file, InputStream fis_thumbnail)
			throws SQLException, IOException {
		mConnect.setAutoCommit(false);
		final String createNewSubmission = "INSERT INTO submissions VALUES (default,?,?,?,?,0,false) RETURNING ID";
		PreparedStatement ps = mConnect.prepareStatement(createNewSubmission);
		ps.setInt(1, author_id);
		ps.setLong(2, getOID(fis_file));
		ps.setString(3, description);
		ps.setLong(4, getOID(fis_thumbnail));
		ResultSet rs = ps.executeQuery();
		int ID = 0;
		if (rs.next()) {
			ID = rs.getInt(1);
		} else {
			throw new SQLException("Creating user failed, no ID obtained.");
		}
		ps.close();
		// Finally, commit the transaction.
		mConnect.commit();
		mConnect.setAutoCommit(true);
		return ID;
	}

	private long getOID(InputStream fis_file) throws SQLException, IOException {
		// Get the Large Object Manager to perform operations with
		LargeObjectManager lobj = ((org.postgresql.PGConnection) mConnect).getLargeObjectAPI();

		// Create a new large object
		long oid_file = lobj.createLO(LargeObjectManager.READ | LargeObjectManager.WRITE);

		// Open the large object for writing
		LargeObject obj = lobj.open(oid_file, LargeObjectManager.WRITE);
		// Copy the data from the file to the large object
		byte buf[] = new byte[2048];
		int s, tl = 0;
		while ((s = fis_file.read(buf, 0, 2048)) > 0) {
			obj.write(buf, 0, s);
			tl += s;
		}

		// Close the large object
		obj.close();
		return oid_file;
	}

	// nothing means deleted or wrong id
	public Submission getSingleSubmission(int submission_id) throws SQLException {

		final String querrySubmission = "SELECT * from submissions WHERE ID = ? AND delete = false";
		PreparedStatement ps = mConnect.prepareStatement(querrySubmission);
		ps.setInt(1, submission_id);
		final ResultSet resultSet = ps.executeQuery();
		Submission submission = null;
		if (resultSet.next()) {
			String description = resultSet.getString("description");
			int author_id = resultSet.getInt("author_id");
			long file_oid = resultSet.getLong("file");
			long thumbnail_oid = resultSet.getLong("thumbnail");
			submission = new Submission(submission_id, description, author_id, file_oid, thumbnail_oid);
		}
		ps.close();
		resultSet.close();
		return submission;
	}

	public byte[] retrievingOIDFile(long oid, int DBID) throws SQLException {
		mConnect.setAutoCommit(false);
		// Get the Large Object Manager to perform operations with
		LargeObjectManager lobj = ((org.postgresql.PGConnection) mConnect).getLargeObjectAPI();

		// Open the large object for reading
		LargeObject obj = lobj.open(oid, LargeObjectManager.READ);

		// Read the data
		byte buf[] = new byte[obj.size()];
		obj.read(buf, 0, obj.size());
		// Close the object
		obj.close();
		return buf;
	}

	public void deleteSubmission(int DBID) throws SQLException {
		final String deleteSubmission = "UPDATE submissions SET delete = true WHERE ID = ?";
		PreparedStatement ps = mConnect.prepareStatement(deleteSubmission);
		ps.setInt(1, DBID);
		ps.executeUpdate();
		ps.close();
	}

	public List<Submission> getAllSubmissions() throws SQLException {
		final String querrySubmission = "SELECT * from submissions WHERE delete = false";
		PreparedStatement ps = mConnect.prepareStatement(querrySubmission);
		final ResultSet resultSet = ps.executeQuery();
		List<Submission> submissionList = new ArrayList<>();
		while (resultSet.next()) {
			String description = resultSet.getString("description");
			int author_id = resultSet.getInt("author_id");
			long file_oid = resultSet.getLong("file");
			long thumbnail_oid = resultSet.getLong("thumbnail");
			int DBID = resultSet.getInt("ID");
			Submission submission = new Submission(DBID, description, author_id, file_oid, thumbnail_oid);
			submissionList.add(submission);
		}
		ps.close();
		resultSet.close();
		return submissionList;
	}

	public int countSubmissionForAnAuthor(int authorDBID) throws SQLException {
		final String querrySubmission = "SELECT * from submissions WHERE delete = false AND author_id = ?";
		PreparedStatement ps = mConnect.prepareStatement(querrySubmission);
		ps.setInt(1, authorDBID);
		final ResultSet resultSet = ps.executeQuery();
		int count = 0;
		while (resultSet.next()) {
			count++;
		}
		ps.close();
		resultSet.close();
		return count;
	}

	public void changeGradeForSubmission(int DBID, int grade) throws SQLException {
		final String gradeSubmission = "UPDATE submissions SET grade = ? WHERE ID = ?";
		PreparedStatement ps = mConnect.prepareStatement(gradeSubmission);
		ps.setInt(1, grade);
		ps.setInt(2, DBID);
		ps.executeUpdate();
		ps.close();
	}

	// return -1 if deleted or wrong dbid
	public int getGradeForSubmission(int DBID) throws SQLException {
		final String querrySubmission = "SELECT * from submissions WHERE ID = ? AND delete = false";
		PreparedStatement ps = mConnect.prepareStatement(querrySubmission);
		ps.setInt(1, DBID);
		final ResultSet resultSet = ps.executeQuery();
		int grade = -1;
		if (resultSet.next()) {
			grade = resultSet.getInt("grade");
		}
		ps.close();
		resultSet.close();
		return grade;
	}

	public void addACommentForSubmission(int submissionDBID, int userDBID, String comment) throws SQLException {
		final String createNewComment = "INSERT INTO comments VALUES (default,?,?,?,?)";
		PreparedStatement ps = mConnect.prepareStatement(createNewComment);
		ps.setInt(1, userDBID);
		ps.setInt(2, submissionDBID);
		ps.setString(3, comment);
		ps.setLong(4, System.currentTimeMillis());
		ps.executeUpdate();
		ps.close();
	}

	public List<Comment> getCommentsForASubmission(int submissionDBID) throws SQLException {
		final String querrySubmission = "SELECT u.firstname,c.text,c.time from comments c, users u"
				+ " WHERE c.submission_id = ? AND c.user_id=u.ID";
		PreparedStatement ps = mConnect.prepareStatement(querrySubmission);
		ps.setInt(1, submissionDBID);
		final ResultSet resultSet = ps.executeQuery();
		List<Comment> commentList = new ArrayList<>();
		while (resultSet.next()) {
			String text = resultSet.getString("text");
			long time = resultSet.getLong("time");
			String userFirstName = resultSet.getString("firstname");
			Comment comment = new Comment(text, time, userFirstName);
			commentList.add(comment);
		}
		ps.close();
		resultSet.close();
		return commentList;
	}

	public int checkCurrentRatingForASubmission(int submissionDBID, int userDBID) throws SQLException {
		final String queryCheck = "SELECT rating from ratings WHERE user_id= ? AND submission_id=?";
		PreparedStatement ps = mConnect.prepareStatement(queryCheck);
		ps.setInt(1, userDBID);
		ps.setInt(2, submissionDBID);
		final ResultSet resultSet = ps.executeQuery();
		int rating = -1;
		if (resultSet.next()) {
			rating = resultSet.getInt("rating");
		}
		ps.close();
		resultSet.close();
		return rating;
	}

	public void addRatingForSubmission(int submissionDBID, int userDBID, int rating) throws SQLException {
		// check if rating exist
		final String queryCheck = "SELECT ID from ratings WHERE user_id= ? AND submission_id=?";
		PreparedStatement ps = mConnect.prepareStatement(queryCheck);
		ps.setInt(1, userDBID);
		ps.setInt(2, submissionDBID);
		final ResultSet resultSet = ps.executeQuery();
		int DBID = -1;
		if (resultSet.next()) {
			DBID = resultSet.getInt("ID");
		}
		ps.close();
		resultSet.close();
		if (DBID == -1) {
			// add new rating
			final String addNewRating = "INSERT INTO ratings VALUES (default,?,?,?)";
			ps = mConnect.prepareStatement(addNewRating);
			ps.setInt(1, userDBID);
			ps.setInt(2, submissionDBID);
			ps.setInt(3, rating);
			ps.executeUpdate();
			ps.close();
		} else {
			// update previous rating
			final String updateRating = "UPDATE ratings SET rating = ? WHERE ID = ?";
			ps = mConnect.prepareStatement(updateRating);
			ps.setInt(1, rating);
			ps.setInt(2, DBID);
			ps.executeUpdate();
			ps.close();
		}
	}

	public float getAVGRatingForASubmission(int submissionDBID) throws SQLException {
		final String querryRating = "SELECT rating from ratings" + " WHERE submission_id = ?";
		PreparedStatement ps = mConnect.prepareStatement(querryRating);
		ps.setInt(1, submissionDBID);
		final ResultSet resultSet = ps.executeQuery();
		float avgRating = 0;
		int count = 0;
		while (resultSet.next()) {
			int rating = resultSet.getInt("rating");
			avgRating += rating;
			count++;
		}
		ps.close();
		resultSet.close();
		if (count != 0)
			return avgRating / count;
		else
			return avgRating;
	}

	public float getAVGRatingForAUser(int userDBID) throws SQLException {
		final String querrySubmission = "SELECT r.rating from ratings r,submissions s"
				+ " WHERE r.submission_id = s.ID AND s.author_id = ?";
		PreparedStatement ps = mConnect.prepareStatement(querrySubmission);
		ps.setInt(1, userDBID);
		final ResultSet resultSet = ps.executeQuery();
		float avgRating = 0;
		int count = 0;
		while (resultSet.next()) {
			int rating = resultSet.getInt("rating");
			avgRating += rating;
			count++;
		}
		ps.close();
		resultSet.close();
		if (count != 0)
			return avgRating / count;
		else
			return avgRating;
	}

	public float getAVGGradeForAUser(int userDBID) throws SQLException {
		final String querrySubmission = "SELECT grade from submissions" + " WHERE author_id=?";
		PreparedStatement ps = mConnect.prepareStatement(querrySubmission);
		ps.setInt(1, userDBID);
		final ResultSet resultSet = ps.executeQuery();
		float avgGrade = 0;
		int count = 0;
		while (resultSet.next()) {
			int rating = resultSet.getInt("grade");
			avgGrade += rating;
			count++;
		}
		ps.close();
		resultSet.close();
		if (count != 0)
			return avgGrade / count;
		else
			return avgGrade;
	}

	public int getNumberOfSubmission(int userDBID) throws SQLException {
		final String querrySubmission = "SELECT count(*) from submissions" + " WHERE author_id=?";
		PreparedStatement ps = mConnect.prepareStatement(querrySubmission);
		ps.setInt(1, userDBID);
		final ResultSet resultSet = ps.executeQuery();
		int count = 0;
		if (resultSet.next()) {
			count = resultSet.getInt(1);
		}
		ps.close();
		resultSet.close();
		return count;
	}
}
