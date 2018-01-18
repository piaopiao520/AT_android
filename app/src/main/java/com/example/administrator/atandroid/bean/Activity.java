package com.example.administrator.atandroid.bean;

public class Activity {
	private int id;
	private int heat = 0;// 活动热度;查看一次活动加一
	private int love = 0;// 爱心值
	private String organization;// 活动发起组织
	private String icon;// 活动海报
	private String address = "";
	private String title = "";
	private String content = "";
	private String time_begin = "";
	private String time_end = "";
	private String sponsor = "";// 活动发起人

	public Activity() {
		super();
	}

	public Activity(String organization, String icon, String address, String title, String content, String time_begin,
			String time_end, String sponsor) {
		super();
		this.organization = organization;
		this.icon = icon;
		this.address = address;
		this.title = title;
		this.content = content;
		this.time_begin = time_begin;
		this.time_end = time_end;
		this.sponsor = sponsor;
	}

	public int getHeat() {
		return heat;
	}

	public void setHeat(int heat) {
		this.heat = heat;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime_begin() {
		return time_begin;
	}

	public void setTime_begin(String time_begin) {
		this.time_begin = time_begin;
	}

	public String getTime_end() {
		return time_end;
	}

	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}

	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public int getLove() {
		return love;
	}

	public void setLove(int love) {
		this.love = love;
	}
}
