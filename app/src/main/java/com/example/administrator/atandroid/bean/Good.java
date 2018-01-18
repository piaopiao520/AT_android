package com.example.administrator.atandroid.bean;

/**
 * 商品
 * 
 * @author Administrator
 *
 */
public class Good {
	private int id;
	private float price;// 价格
	private int quantity;// 数量
	private String icon = "";// 图片
	private String content = "";// 商品描述
	private int seller;

	public int getSeller() {
		return seller;
	}

	public void setSeller(int seller) {
		this.seller = seller;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Good(int price, int quantity, String icon, String content) {
		super();
		this.price = price;
		this.quantity = quantity;
		this.icon = icon;
		this.content = content;
	}

	public Good() {
		super();
	}

}
