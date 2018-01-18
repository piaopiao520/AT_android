package com.example.administrator.atandroid.bean;

import java.util.Date;

import com.example.administrator.atandroid.util.DateUtil;

/**
 * 扫墓
 * 
 * @author Administrator
 *
 */
public class VisitGrave {
	private int id;
	private String user;
	private int gravestone_id;
	private String time;

	public VisitGrave() {
		super();
	}

	public VisitGrave(String user, int gravestone_id) {
		super();
		this.user = user;
		this.gravestone_id = gravestone_id;
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

	public int getGravestone_id() {
		return gravestone_id;
	}

	public void setGravestone_id(int gravestone_id) {
		this.gravestone_id = gravestone_id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
