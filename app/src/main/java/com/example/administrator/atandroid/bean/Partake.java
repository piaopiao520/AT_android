package com.example.administrator.atandroid.bean;

import java.util.Date;

import com.example.administrator.atandroid.util.DateUtil;

/**
 * 参加活动
 * 
 * @author Administrator
 *
 */
public class Partake {
	private String user;
	private int activity;
	private String time;

	public Partake() {
		super();
	}

	public Partake(String user, int activity) {
		super();
		this.user = user;
		this.activity = activity;
		this.time = DateUtil.getFormatDate(new Date());
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getActivity() {
		return activity;
	}

	public void setActivity(int activity) {
		this.activity = activity;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
