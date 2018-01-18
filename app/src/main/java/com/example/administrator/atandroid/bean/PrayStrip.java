package com.example.administrator.atandroid.bean;

import java.util.Date;

/**
 * 祈福条
 * @author Administrator
 *
 */
public class PrayStrip {
	private int id;
	private String user;
	private String content;//祈福内容
	private String time;

	public PrayStrip() {
		super();
	}

	public PrayStrip(String user,String content) {
		super();
		this.user = user;

		this.content = content;
		this.time = DateUtil.getFormatDate(new Date());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
