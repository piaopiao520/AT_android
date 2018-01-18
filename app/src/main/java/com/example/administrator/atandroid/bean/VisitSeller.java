package com.example.administrator.atandroid.bean;

import java.util.Date;

import com.example.administrator.atandroid.util.DateUtil;

public class VisitSeller {
	private int id;
	private String user;
	private int sellerId;
	private String time;

	public VisitSeller(String user, int sellerId) {
		super();
		this.time = DateUtil.getFormatDate(new Date());
		this.user = user;
		this.sellerId = sellerId;
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

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
