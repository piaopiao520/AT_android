package com.example.administrator.atandroid.bean;

/**
 * 商家
 *
 * @author Administrator
 *
 */
public class Seller {
	private int id;
	private String user;
	private String time_begin = "";
	private String time_end = "";
	private int priority = 0;// 优先值
	private String province = "";// 省
	private String city = "";// 市
	private String district = "";// 区
	private String summary = "";// 简介
	private String serve;// 服务
	private String name = "";
	private String phone = "";
	private double longitude = 0.0;// 经度
	private double latitude = 0.0;// 纬度
	private String icon = "";
	private String address = "";
	private Double distance;

	public Seller() {
		super();
	}

	public Seller(String user, String time_begin, String time_end, int priority, String province, String city,
				  String district, String summary, String serve, String name, String phone, double longitude, double latitude,
				  String icon, String address) {
		super();
		this.user = user;
		this.time_begin = time_begin;
		this.time_end = time_end;
		this.priority = priority;
		this.province = province;
		this.city = city;
		this.district = district;
		this.summary = summary;
		this.serve = serve;
		this.name = name;
		this.phone = phone;
		this.longitude = longitude;
		this.latitude = latitude;
		this.icon = icon;
		this.address = address;
	}

	public Double getDistance() {
		return distance;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getServe() {
		return serve;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public void setServe(String serve) {
		this.serve = serve;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
