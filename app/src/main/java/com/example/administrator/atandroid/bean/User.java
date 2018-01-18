package com.example.administrator.atandroid.bean;

public class User {
	private String id;// 用于登录
	private String password;
	private String address = "";
	private int virtual_currency = 0;// 虚拟货币
	private String name = "";
	private String sex = "";
	private String icon = "";
	private int volunteer = 0;// 公益值

	public User() {
		super();
	}

	public User(String id, String password) {
		super();
		this.id = id;
		this.password = password;
	}

	public User(String id, String password, String address, int virtual_currency, String name, String sex, String icon,
			int volunteer) {
		super();
		this.id = id;
		this.password = password;
		this.address = address;
		this.virtual_currency = virtual_currency;
		this.name = name;
		this.sex = sex;
		this.icon = icon;
		this.volunteer = volunteer;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getVirtual_currency() {
		return virtual_currency;
	}

	public void setVirtual_currency(int virtual_currency) {
		this.virtual_currency = virtual_currency;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getVolunteer() {
		return volunteer;
	}

	public void setVolunteer(int volunteer) {
		this.volunteer = volunteer;
	}

}
