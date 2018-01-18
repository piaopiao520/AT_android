package com.example.administrator.atandroid.bean;

import java.util.Date;

import com.example.administrator.atandroid.util.DateUtil;

/**
 * 关注用户
 * @author Administrator
 *
 */
public class Concern {
	private String user_self;
	private String user_target;
	private String time;

	public Concern() {
		super();
	}

	public Concern(String user_self, String user_target) {
		super();
		this.user_self = user_self;
		this.user_target = user_target;
		this.time = DateUtil.getFormatDate(new Date());
	}

	public String getUser_self() {
		return user_self;
	}

	public void setUser_self(String user_self) {
		this.user_self = user_self;
	}

	public String getUser_target() {
		return user_target;
	}

	public void setUser_target(String user_target) {
		this.user_target = user_target;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
