package Models;

import java.util.Comparator;

public class Comment implements Comparator<Comment> {
	private long time;
	private String text;
	private String fname;

	public Comment(String text, long time, String userFirstName) {
		// TODO Auto-generated constructor stub
		this.text = text;
		this.time = time;
		fname = userFirstName;
	}

	public long getTime() {
		return time;
	}

	public String getText() {
		return text;
	}

	public String getFname() {
		return fname;
	}

	@Override
	public int compare(Comment o1, Comment o2) {
		// TODO Auto-generated method stub
		return Long.compare(o1.time, o2.time);
	}
}
