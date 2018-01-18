package com.example.administrator.atandroid.bean;

import com.example.administrator.atandroid.bean.Myenum.Sex;

/**
 * 宠物
 * 
 * @author Administrator
 *
 */
public class Pet {
	private int id;
	private String user;
	private String icon;
	private Sex sex;
	private String nickname;// 昵称
	private String sign;// 签名
	private int age;
	private String kind;

	public Pet() {
		super();
	}

	public Pet(String user, String icon, Sex sex, String nickname, String sign, int age, String kind) {
		super();
		this.user = user;
		this.icon = icon;
		this.sex = sex;
		this.nickname = nickname;
		this.sign = sign;
		this.age = age;
		this.kind = kind;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

}
