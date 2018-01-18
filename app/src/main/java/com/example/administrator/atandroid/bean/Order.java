package com.example.administrator.atandroid.bean;

import java.util.Date;

import com.example.administrator.atandroid.util.DateUtil;

/**
 * 订单
 * 
 * @author Administrator
 *
 */
public class Order {
	private long id;// 订单号
	private String user;
	private String time;

	public Order() {
		super();
	}

	public Order(String user) {
		super();
		this.time = DateUtil.getFormatDate(new Date());
		this.id = System.currentTimeMillis();
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
