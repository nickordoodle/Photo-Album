package nscherer30.gmail.com.androidphotoalbum11.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Users implements Serializable {

	private static final long serialVersionUID = 473609096067308990L;
	static String filepath = "src" + File.separator + "userList.dat";
	private List<User> users;
	
	public Users() {
		users = new ArrayList<User>();
	}
	
	public List<User> getList() {
		return users;
	}
	
	public User getUser(String userName) {
		if(users == null) return null;
		for(int i=0; i<users.size(); i++) {
			if(users.get(i).getUserName().equals(userName)) {
				return users.get(i);
			}
		}
		return null;
	}
	
	public void addUser(User user) {
		users.add(user);
	}
	
	public void removeUser(User user) {
		users.remove(user);
	}
	
	public boolean hasUser(String userName) {
		if(users == null) return false;
		for(int i=0; i<users.size(); i++) {
			if(users.get(i).getUserName().equals(userName)) {
				return true;
			}
		}
		return false;
	}
	
	public static Users read() {
		Users u = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath));
			u = (Users) ois.readObject();
			ois.close();
		} catch (IOException e) {

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return u;
	}

	public static void write(Users u) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath));
			oos.writeObject(u);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
