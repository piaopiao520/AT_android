package com.example.administrator.atandroid.bean;

import java.util.Date;

import com.example.administrator.atandroid.util.DateUtil;

/**
 * 相册
 * 
 * @author Administrator
 *
 */
public class Album {
	private int id;
	private String user;
	private String name;
	private String time;

	public Album() {
		super();
	}

	public Album(String name) {
		super();
		this.time = DateUtil.getFormatDate(new Date());
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
