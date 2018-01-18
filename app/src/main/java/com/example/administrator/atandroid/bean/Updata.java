package com.example.administrator.atandroid.bean;

import java.util.Date;

import com.example.administrator.atandroid.util.DateUtil;

/**
 * 动态更新,包括公益活动和宠物寻回
 * 
 * @author Administrator
 *
 */
public class Updata {
	private int id;
	private int target;// 更新对象,包括公益活动和宠物寻回
	private String time;
	private String content;

	public Updata() {
		super();
	}

	public Updata(int target, String content) {
		super();
		this.time = DateUtil.getFormatDate(new Date());
		this.target = target;
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
