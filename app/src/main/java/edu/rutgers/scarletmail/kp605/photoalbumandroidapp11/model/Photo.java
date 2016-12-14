package edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Photo implements Serializable {

    private static final long serialVersionUID = 473609096067308990L;
    private String caption;
    private Calendar date;
    private String imgPath;
    private List<Tag> tags;


    public Photo(String caption, String imgPath) {
        this.caption = caption;
        this.imgPath = imgPath;
        date = Calendar.getInstance();
        date.set(Calendar.MILLISECOND, 0);
        tags = new ArrayList<Tag>();

    }

    public String getCaption() {
        return caption;
    }

    public Calendar getDate() {
        return date;
    }

    public String getDateString() {
        String[] temp = date.getTime().toString().split(" ");
        return temp[1] + " " + temp[2] + " " + temp[5];
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
    }

    public String toString() {
        return caption;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getTagsString() {
        String tagString = "";
        for(Tag tag : tags) {
            tagString += tag.toString() + ", ";
        }
        return tagString == "" ? "none" : tagString;
    }

	/*
	public boolean inRange(LocalDate start, LocalDate end) {
		LocalDate photoDate = date.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		if(photoDate.isAfter(start) && photoDate.isBefore(end)) {
			return true;
		} else if(photoDate.equals(start)) {
			return true;
		} else if(photoDate.equals(end)) {
			return true;
		} else {
			return false;
		}
	}*/

    public boolean hasTag(String tag) {
        tag = tag.trim();
        String[] types = tag.split(":");
        String[] values = tag.split(" ");
        String type = "", value = "";
        if(types.length == 2) {
            type = types[0];
        }
        if(values.length == 2) {
            value = types[1];
        }
        for(Tag t : tags) {
            if(type.trim().equals(t.getType()) && value.trim().equals(t.getValue())) {
                return true;
            }
        }
        return false;
    }


}
