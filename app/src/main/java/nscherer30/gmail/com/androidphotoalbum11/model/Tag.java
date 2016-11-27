package nscherer30.gmail.com.androidphotoalbum11.model;

import java.io.Serializable;

public class Tag implements Serializable {

	private static final long serialVersionUID = 473609096067308990L;
	private String type;
	private String value;
	
	public Tag(String type, String value) {
		this.type = type;
		this.value = value;
	}
	
	public String getType() {
		return type;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString() {
		return type + ": " + value;
	}
}
