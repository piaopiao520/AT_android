package com.example.administrator.atandroid.bean;

public class ProductList {

	private int productId;
	private String thumbnailURl;
	private String name;
	private int saleNum;
	private int originalPrice;
	private int price;
	private int postage;
	private String storeName;
	private String city;

	public void setProductid(int productid) {
		this.productId = productid;
	}

	public int getProductid() {
		return productId;
	}

	public void setThumbnailurl(String thumbnailurl) {
		this.thumbnailURl = thumbnailurl;
	}

	public String getThumbnailurl() {
		return thumbnailURl;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setSalenum(int salenum) {
		this.saleNum = salenum;
	}

	public int getSalenum() {
		return saleNum;
	}

	public void setOriginalprice(int originalprice) {
		this.originalPrice = originalprice;
	}

	public int getOriginalprice() {
		return originalPrice;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getPrice() {
		return price;
	}

	public void setPostage(int postage) {
		this.postage = postage;
	}

	public int getPostage() {
		return postage;
	}

	public void setStorename(String storename) {
		this.storeName = storename;
	}

	public String getStorename() {
		return storeName;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}

}