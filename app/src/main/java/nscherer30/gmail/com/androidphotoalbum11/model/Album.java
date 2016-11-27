package nscherer30.gmail.com.androidphotoalbum11.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Album implements Serializable {

	private static final long serialVersionUID = 473609096067308990L;
	private String name;
	private List<Photo> photos;
	
	public Album(String name) {
		this.name = name;
		photos = new ArrayList<Photo>();
	}
	
	public Album(String name, List<Photo> photos) {
		this.name = name;
		this.photos = photos;
	}

	public String getName() {
		return name;
	}
	
	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void addPhoto(Photo photo) {
		photos.add(photo);
	}
	
	public void removePhoto(Photo photo) {
		photos.remove(photo);
	}
	
	public int getAmountOfPhotos() {
		return photos.size();
	}
	
	public Photo getOldestPhoto() {
		if(photos.size() > 0) {
			Photo oldestPhoto = photos.get(0);
			for(int i=1; i < photos.size(); i++) {
				Photo temp = photos.get(i);
				if(temp.getDate().compareTo(oldestPhoto.getDate()) < 0 ) {
					oldestPhoto = temp;
				}
			}
			return oldestPhoto;
		} else {
			return null;
		}
	}
	
	public Photo getNewestPhoto() {
		if(photos.size() > 0) {
			Photo newestPhoto = photos.get(0);
			for(int i=1; i < photos.size(); i++) {
				Photo temp = photos.get(i);
				if(temp.getDate().compareTo(newestPhoto.getDate()) > 0 ) {
					newestPhoto = temp;
				}
			}
			return newestPhoto;
		} else {
			return null;
		}
	}
	
	public String getRange() {
		Photo oldest = getOldestPhoto();
		Photo newest = getNewestPhoto();

		String oldestDate = (oldest == null ? "N/A" : oldest.getDateString());
		String newestDate = (newest == null ? "N/A" : newest.getDateString());
		
		return oldestDate + " - " + newestDate;
	}
	
	public int indexOf(Photo photo) {
		if(photo == null) return -1;
		for(Photo p : photos) {
			if(p.getCaption().equals(photo.getCaption()) 
					&& p.getDate().equals(photo.getDate())) {
				return photos.indexOf(p);
			}
		}
		return -1;
	}
	
	public Photo getPhoto(int index) {
		return photos.get(index);
	}
	
	public Photo getNextPhoto(int photoIndex) {
		if(photoIndex + 1 == photos.size()) {
			return null;
		}
		return photos.get(photoIndex + 1);
	}
	
	public Photo getPrevPhoto(int photoIndex) {
		if(photoIndex - 1 < 0) {
			return null;
		}
		return photos.get(photoIndex - 1);
	}
	
	public String toString() {
		return name;
	}
	
}
