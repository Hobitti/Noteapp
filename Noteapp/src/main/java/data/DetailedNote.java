package data;

import java.util.ArrayList;

public class DetailedNote {
	private Note note;
	private Share share;
	private ArrayList<User> sharedTo;
	
	public DetailedNote() {
		
	}

	public DetailedNote(Note note, Share share, ArrayList<User> sharedTo) {
		super();
		this.note = note;
		this.share = share;
		this.sharedTo = sharedTo;
	}

	public Note getNote() {
		return note;
	}

	public void setNote(Note note) {
		this.note = note;
	}

	public Share getShare() {
		return share;
	}

	public void setShare(Share share) {
		this.share = share;
	}

	public ArrayList<User> getSharedTo() {
		return sharedTo;
	}

	public void setSharedTo(ArrayList<User> sharedTo) {
		this.sharedTo = sharedTo;
	}
}
