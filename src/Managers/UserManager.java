package Managers;

public class UserManager {
	private static UserManager instance = null;

	private UserManager() {
		// TODO Auto-generated constructor stub
	}

	public static UserManager getInstance() {
		if (instance == null)
			instance = new UserManager();
		return instance;
	}
}
