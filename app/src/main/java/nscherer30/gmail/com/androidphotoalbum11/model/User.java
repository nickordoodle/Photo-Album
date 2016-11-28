package nscherer30.gmail.com.androidphotoalbum11.model;

import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

	private static final long serialVersionUID = 473609096067308990L;
	private String userName;
	private ArrayList<Album> albums;
	static String filepath = "C:\\Users\\Nick\\AndroidStudioProjects\\androidphotoalbum11\\app\\src\\main\\java\\nscherer30\\gmail\\com\\androidphotoalbum11\\userList.dat";

	public User(String userName) {
		this.userName = userName;
		albums = new ArrayList<Album>();
	}
	
	public String getUserName() {
		return userName;
	}
	
	public ArrayList<Album> getAlbums() {
		return albums;
	}
	
	public Album getAlbum(String albumName) {
		for(int i=0; i < albums.size(); i++) {
			if(albums.get(i).getName().equals(albumName)) {
				return albums.get(i);
			}
		}
		return null;
	}
	
	public boolean hasAlbum(String albumName) {
		for(int i=0; i < albums.size(); i++) {
			if(albums.get(i).equals(albumName)) {
				return true;
			}
		}
		return false;
	}

	public static User read() {
		User u = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath));
			u = (User) ois.readObject();
			ois.close();
		} catch (IOException e) {

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return u;
	}

	public static void write(User u) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath));
			oos.writeObject(u);
			oos.close();
			Log.d("write", "updated " + u.getUserName() + "user to path " + filepath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void addAlbum(Album album) {
		albums.add(album);
	}
	
	public void removeAlbum(Album album) {
		albums.remove(album);
	}
	
	public String toString() {
		return userName;
	}
	
}
