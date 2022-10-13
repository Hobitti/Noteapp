package data;

public class Share {
	private int shareID;
	private int distributorID;
	private int noteID;
	
	public Share() {
		
	}
	
	public Share(int shareID, int distributorID, int noteID) {
		this.shareID = shareID;
		this.distributorID = distributorID;
		this.noteID = noteID;
	}

	public int getShareID() {
		return shareID;
	}

	public void setShareID(int shareID) {
		this.shareID = shareID;
	}

	public int getDistributorID() {
		return distributorID;
	}

	public void setDistributorID(int distributorID) {
		this.distributorID = distributorID;
	}

	public int getNoteID() {
		return noteID;
	}

	public void setNoteID(int noteID) {
		this.noteID = noteID;
	}
	
}
