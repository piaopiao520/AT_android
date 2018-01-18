package com.example.administrator.atandroid.bean;

import java.util.Date;

import com.example.administrator.atandroid.util.DateUtil;

/**
 * 回复，可以是帖子，人和宠物寻回
 * 
 * @author Administrator
 *
 */
public class Answer {
	private int id;
	private int postId = 0;// 回复对象，帖
	private int petBackId = 0;// 回复对象，寻回
	private String userId = "";// 回复对象，人
	private String user = "";// 回复人
	private String content = "";
	private String time = "";

	public Answer() {
		super();
	}

	public Answer(int postId, int petBackId, String userId, String user, String content) {
		super();
		this.postId = postId;
		this.petBackId = petBackId;
		this.userId = userId;
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

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getPetBackId() {
		return petBackId;
	}

	public void setPetBackId(int petBackId) {
		this.petBackId = petBackId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
