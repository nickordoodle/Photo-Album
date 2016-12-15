package edu.rutgers.scarletmail.kp605.photoalbumandroidapp11.model;


import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.path;

public class User implements Serializable {

    private static final long serialVersionUID = 473609096067308990L;
    private String userName;
    private ArrayList<Album> albums;

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

    public static User read(String path) {
        User u = null;
        try {
            Log.d("File", "READ FILE");
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
            u = (User) ois.readObject();
            ois.close();
        } catch (IOException e) {

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return u;
    }

    public static void write(User u, String path) {
            try {
                File f = new File(path);
                if(f.exists() && !f.isDirectory()) {
                    Log.d("File", "WROTE FILE");
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
                    oos.writeObject(u);
                    oos.close();
                } else {
                    Log.d("File", "MADE FILE");
                    File file = new File(path);
                    file.createNewFile();
                }
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
