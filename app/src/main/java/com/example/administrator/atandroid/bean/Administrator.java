package com.example.administrator.atandroid.bean;

public class Administrator {
	private String id;
	private String password;

	public Administrator() {
		super();
	}

	public Administrator(String id, String password) {
		super();
		this.id = id;
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
