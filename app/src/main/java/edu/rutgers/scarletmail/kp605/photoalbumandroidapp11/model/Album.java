package edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.model;

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