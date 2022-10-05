package data;

public class Note {
	private int noteID;
	private String title;
	private String content;
	private String date;
	private boolean notePublic;
	private int userID;
	
	public Note() {
		
	}
	
	public Note(int noteID, String title, String content, String date, boolean notePublic, int userID) {
		super();
		this.noteID = noteID;
		this.title = title;
		this.content = content;
		this.date = date;
		this.notePublic = notePublic;
		this.userID = userID;
	}

	public int getNoteID() {
		return noteID;
	}

	public void setNoteID(int noteID) {
		this.noteID = noteID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public boolean isNotePublic() {
		return notePublic;
	}

	public void setNotePublic(boolean notePublic) {
		this.notePublic = notePublic;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	
}
