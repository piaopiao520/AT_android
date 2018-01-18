package com.example.administrator.atandroid.bean;

import java.util.Date;

import com.example.administrator.atandroid.util.DateUtil;

/**
 * 帖
 * 
 * @author Administrator
 *
 */
public class Post {
	private int id;
	private String title;// 标题
	private String content;// 内容
	private String user;// 发表人
	private String time;
	private String picture;// 图片

	public Post() {
		super();
	}

	public Post(String title, String content, String user, String picture) {
		super();
		this.title = title;
		this.content = content;
		this.user = user;
		this.time = DateUtil.getFormatDate(new Date());
		this.picture = picture;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

}
