package Models;


public class Comment implements Comparable<Comment> {
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
	public int compareTo(Comment arg0) {
		// TODO Auto-generated method stub
		return Long.compare(this.time, arg0.time);
	}
}
