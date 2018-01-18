package com.example.administrator.atandroid.bean;

/**
 * 购物表
 * 
 * @author Administrator
 *
 */
public class Shopping {
	private String id;// 订单号
	private int seller;//商家号
	private String user;
	private String time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSeller() {
		return seller;
	}

	public void setSeller(int seller) {
		this.seller = seller;
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