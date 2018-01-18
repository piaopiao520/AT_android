package com.example.administrator.atandroid.bean;

import java.util.Date;

/**
 * 墓碑
 * 
 * @author Administrator
 *
 */
public class Gravestone {
	private int id;
	private String icon="";
	private String user;
	private String time;
	private String content="";

	public Gravestone() {
		super();
	}

	public Gravestone(String icon, String user, String content) {
		super();
		this.icon = icon;
		this.user = user;
		this.time = DateUtil.getFormatDate(new Date());
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
