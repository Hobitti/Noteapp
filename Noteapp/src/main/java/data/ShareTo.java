package data;

public class ShareTo {
	private int shareToID;
	private int shareID;
	private int userID;
	
	public ShareTo() {
		
	}

	public ShareTo(int shareToID, int shareID, int userID) {
		super();
		this.shareToID = shareToID;
		this.shareID = shareID;
		this.userID = userID;
	}

	public int getShareToID() {
		return shareToID;
	}

	public void setShareToID(int shareToID) {
		this.shareToID = shareToID;
	}

	public int getShareID() {
		return shareID;
	}

	public void setShareID(int shareID) {
		this.shareID = shareID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	
}
