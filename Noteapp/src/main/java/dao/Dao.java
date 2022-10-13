package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import data.*;
import conn.Connections;

public class Dao {
	
	public static User checkUserLogin(String username, String password) {
		User user = new User();
		Connection conn = null;
		
		try {
			conn = Connections.getDevConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return user;
		}
		
		try {
			String sql = "SELECT userID, username FROM users WHERE username = ? AND password = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet result = pstmt.executeQuery();

			if (result.next()) {
				user.setUserID(result.getInt(1));
				user.setUsername(result.getString(2));
				return user;
			} else {
				return user;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return user;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean addNewUser(String username, String password) {
		Connection conn = null;
		
		try {
			conn = Connections.getDevConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		try {
			String sql = "INSERT INTO users (username, password) VALUES (?, ?);";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			int resultCount = pstmt.executeUpdate();
			
			if (resultCount > 0) {
				return true;
			} else {
				return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static ArrayList<Note> getUserNotes(int userID) {
		ArrayList<Note> user_notes = new ArrayList<>();
		Connection conn = null;
		
		try {
			conn = Connections.getDevConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return user_notes;
		}
		
		try {
			String sql = "SELECT * FROM notes WHERE userID = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userID);
			ResultSet result = pstmt.executeQuery();
			
			while(result.next()) {
				Note current_note = new Note();
				current_note.setNoteID(result.getInt(1));
				current_note.setTitle(result.getString(2));
				current_note.setContent(result.getString(3));
				current_note.setDate(result.getString(4));
				current_note.setNotePublic(result.getBoolean(5));
				current_note.setUserID(result.getInt(6));
				user_notes.add(current_note);
			}
			
			return user_notes;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return user_notes;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static ArrayList<User> getUserNoteSharedUsers(int userID, int noteID) {
		ArrayList<User> user_note_shared_users = new ArrayList<>();
		Connection conn = null;
		
		try {
			conn = Connections.getDevConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return user_note_shared_users;
		}
		
		try {
			String sql = "SELECT users.userID, users.username FROM shares INNER JOIN sharedTo ON shares.shareID = sharedTo.shareID INNER JOIN users ON sharedTo.userID = users.userID WHERE shares.distributorID = ? AND shares.noteID = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userID);
			pstmt.setInt(2, noteID);
			ResultSet result = pstmt.executeQuery();
			
			while(result.next()) {
				User current_user = new User();
				current_user.setUserID(result.getInt(1));
				current_user.setUsername(result.getString(2));
				user_note_shared_users.add(current_user);
			}
			
			return user_note_shared_users;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return user_note_shared_users;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static DetailedNote getNoteDetails(int noteID, int userID) {
		DetailedNote detailed_note = new DetailedNote();
		Connection conn = null;
		
		try {
			conn = Connections.getDevConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return detailed_note;
		}
		
		try {
			Note note = new Note();
			note = getNote(noteID);
			
			Share share = new Share();
			share = getShare(userID, noteID);
			
			ArrayList<User> shared_to = new ArrayList<>();
			shared_to = getUserNoteSharedUsers(userID, noteID);
			
			detailed_note.setNote(note);
			detailed_note.setShare(share);
			detailed_note.setSharedTo(shared_to);
			
			return detailed_note;
			
		} catch (Exception e) {
			e.printStackTrace();
			return detailed_note;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Share getShare(int userID, int noteID) {
		Share share = new Share();
		Connection conn = null;
		
		try {
			conn = Connections.getDevConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return share;
		}
		
		try {
			String sql = "SELECT * FROM shares WHERE distributorID = ? AND noteID = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userID);
			pstmt.setInt(2, noteID);
			ResultSet result = pstmt.executeQuery();
			
			if(result.next()) {
				share.setShareID(result.getInt(1));
				share.setDistributorID(result.getInt(2));
				share.setNoteID(result.getInt(3));
			}
			
			return share;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return share;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static ArrayList<Note> getNotesSharedToUser(int userID) {
		ArrayList<Note> notes_shared_to_user = new ArrayList<>();
		Connection conn = null;
		
		try {
			conn = Connections.getDevConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return notes_shared_to_user;
		}
		
		try {
			String sql = "SELECT notes.* FROM shares JOIN sharedTo ON shares.shareID = sharedTo.shareID JOIN notes ON shares.NoteID = notes.NoteID WHERE sharedTo.userID = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userID);
			ResultSet result = pstmt.executeQuery();
			
			while(result.next()) {
				Note current_note = new Note();
				current_note.setNoteID(result.getInt(1));
				current_note.setTitle(result.getString(2));
				current_note.setContent(result.getString(3));
				current_note.setDate(result.getString(4));
				current_note.setNotePublic(result.getBoolean(5));
				current_note.setUserID(result.getInt(6));
				notes_shared_to_user.add(current_note);
			}
			
			return notes_shared_to_user;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return notes_shared_to_user;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static ArrayList<Note> getPublicNotes(int userID) {
		ArrayList<Note> public_notes = new ArrayList<>();
		Connection conn = null;
		
		try {
			conn = Connections.getDevConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return public_notes;
		}
		
		try {
			String sql = "SELECT * FROM notes WHERE NOT userID = ? AND public = 1;";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userID);
			ResultSet result = pstmt.executeQuery();
			
			while(result.next()) {
				Note current_note = new Note();
				current_note.setNoteID(result.getInt(1));
				current_note.setTitle(result.getString(2));
				current_note.setContent(result.getString(3));
				current_note.setDate(result.getString(4));
				current_note.setNotePublic(result.getBoolean(5));
				current_note.setUserID(result.getInt(6));
				public_notes.add(current_note);
			}
			
			return public_notes;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return public_notes;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static ArrayList<User> getUsers(int userID) {
		ArrayList<User> users = new ArrayList<>();
		Connection conn = null;
		
		try {
			conn = Connections.getDevConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return users;
		}
		
		try {
			String sql = "SELECT userID, username FROM users WHERE NOT userID = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userID);
			ResultSet result = pstmt.executeQuery();
			
			while(result.next()) {
				User current_user = new User();
				current_user.setUserID(result.getInt(1));
				current_user.setUsername(result.getString(2));
				users.add(current_user);
			}
			
			return users;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return users;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static ArrayList<User> getUsersBySearch(String search) {
		ArrayList<User> users = new ArrayList<>();
		Connection conn = null;
		
		try {
			conn = Connections.getDevConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return users;
		}
		
		try {
			String sql = "SELECT userID, username FROM users WHERE username LIKE ?;";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + search + "%");
			ResultSet result = pstmt.executeQuery();
			
			while(result.next()) {
				User current_user = new User();
				current_user.setUserID(result.getInt(1));
				current_user.setUsername(result.getString(2));
				users.add(current_user);
			}
			
			return users;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return users;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Note getNote(int noteID) {
		Connection conn = null;
		Note note = new Note();
		
		try {
			conn = Connections.getDevConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return note;
		}
		
		try {
			String sql = "SELECT * FROM notes WHERE noteID = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, noteID);
			ResultSet result = pstmt.executeQuery();

			if (result.next()) {
				note.setNoteID(result.getInt(1));
				note.setTitle(result.getString(2));
				note.setContent(result.getString(3));
				note.setDate(result.getString(4));
				note.setNotePublic(result.getBoolean(5));
				note.setUserID(result.getInt(6));
			}
			
			return note;

		} catch (SQLException e) {
			e.printStackTrace();
			return note;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean addNewNote(String title, String content, String date, boolean notePublic, int userID) {
		Connection conn = null;
		
		try {
			conn = Connections.getDevConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		try {
			String sql = "INSERT INTO notes (title, content, date, public, userID) VALUES (?, ?, ?, ?, ?);";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, date);
			pstmt.setBoolean(4, notePublic);
			pstmt.setInt(5, userID);
			int resultCount = pstmt.executeUpdate();
			
			if (resultCount > 0) {
				return true;
			} else {
				return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Share shareUserNote(int userID, int noteID) {
		Connection conn = null;
		Share share = new Share();
		
		try {
			conn = Connections.getDevConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return share;
		}
		
		try {
			String sql = "INSERT INTO shares (distributorID, noteID) VALUES (?, ?);";
			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, userID);
			pstmt.setInt(2, noteID);
			int resultCount = pstmt.executeUpdate();
			
			if (resultCount > 0) {
				ResultSet generatedKeys = pstmt.getGeneratedKeys();
				if(generatedKeys.next()) {
					share.setShareID(generatedKeys.getInt(1));
				}
				return share;
			} else {
				return share;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return share;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean shareToUser(int shareID, int userID) {
		Connection conn = null;
		
		try {
			conn = Connections.getDevConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		try {
			String sql = "INSERT INTO sharedTo (shareID, userID) VALUES (?, ?);";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, shareID);
			pstmt.setInt(2, userID);
			int resultCount = pstmt.executeUpdate();
			
			if (resultCount > 0) {
				return true;
			} else {
				return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Note updateNote(int noteID, String title, String content, String date, boolean notePublic, int userID) {
		Connection conn = null;
		Note note = new Note();
		
		try {
			conn = Connections.getDevConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return note;
		}
		
		try {
			String sql = "UPDATE notes SET title = ?, content = ?, date = ?, public = ? WHERE noteID = ? AND userID = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, date);
			pstmt.setBoolean(4, notePublic);
			pstmt.setInt(5, noteID);
			pstmt.setInt(6, userID);
			int resultCount = pstmt.executeUpdate();
			
			if (resultCount > 0) {
				note.setNoteID(noteID);
				note.setTitle(title);
				note.setContent(content);
				note.setDate(date);
				note.setNotePublic(notePublic);
				note.setUserID(userID);
				return note;
			} else {
				return note;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return note;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean deleteShareTo(int shareID, int userID) {
		Connection conn = null;
		
		try {
			conn = Connections.getDevConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		try {
			String sql = "DELETE FROM sharedTo WHERE shareID = ? AND userID = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, shareID);
			pstmt.setInt(2, userID);
			int resultCount = pstmt.executeUpdate();
			
			if (resultCount > 0) {
				return true;
			} else {
				return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean deleteShare(int userID, int noteID) {
		Connection conn = null;
		
		try {
			conn = Connections.getDevConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		try {
			String sql = "DELETE FROM shares WHERE distributorID = ? AND noteID = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userID);
			pstmt.setInt(2, noteID);
			int resultCount = pstmt.executeUpdate();
			
			if (resultCount > 0) {
				return true;
			} else {
				return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean deleteNote(int noteID, int userID) {
		Connection conn = null;
		
		try {
			conn = Connections.getDevConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		try {
			String sql = "DELETE FROM notes WHERE noteID = ? AND userID = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, noteID);
			pstmt.setInt(2, userID);
			int resultCount = pstmt.executeUpdate();
			
			if (resultCount > 0) {
				return true;
			} else {
				return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}