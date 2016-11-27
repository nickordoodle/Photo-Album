package nscherer30.gmail.com.androidphotoalbum11.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

	private static final long serialVersionUID = 473609096067308990L;
	private String userName;
	private List<Album> albums;
	
	public User(String userName) {
		this.userName = userName;
		albums = new ArrayList<Album>();
	}
	
	public String getUserName() {
		return userName;
	}
	
	public List<Album> getAlbums() {
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
