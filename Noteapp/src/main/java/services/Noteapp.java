package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import conn.Connections;
import data.*;
import dao.Dao;

@Path("noteappservice")
public class Noteapp {
	
	@POST
	@Path("/checkuserlogin")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean checkUserLogin(User u) {
		return Dao.checkUserLogin(u.getUsername(), u.getPassword());
	}
	
	@POST
	@Path("/registeruser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean registerUser(User u) {
		return Dao.addNewUser(u.getUsername(), u.getPassword());
	}
	
	@POST
	@Path("/getusernotes")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<Note> getUserNotes(User u) {
		return Dao.getUserNotes(u.getUserID());
	}
	
	@POST
	@Path("/getnotessharedtouser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<Note> getNotesSharedToUser(User u) {
		return Dao.getNotesSharedToUser(u.getUserID());
	}
	
	@POST
	@Path("/getpublicnotes")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<Note> getPublicNotes(User u) {
		return Dao.getPublicNotes(u.getUserID());
	}
	
	@POST
	@Path("/getusers")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ArrayList<User> getUsers(User u) {
		return Dao.getUsers(u.getUserID());
	}
	
	@POST
	@Path("/getnote")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Note getNote(Note n) {
		return Dao.getNote(n.getNoteID());
	}
	
	@POST
	@Path("/addnote")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean addNote(Note n) {
		return Dao.addNewNote(n.getTitle(), n.getContent(), n.getDate(), n.isNotePublic(), n.getUserID());
	}
}
