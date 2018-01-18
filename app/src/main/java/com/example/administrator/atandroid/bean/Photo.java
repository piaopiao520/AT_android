package com.example.administrator.atandroid.bean;

public class Photo {
	private String url="";
	private String content="";//照片描述
	private String time;
	private int album_id;

	public Photo() {
		super();
	}

	public Photo(String url, String content, String time, int album_id) {
		super();
		this.url = url;
		this.content = content;
		this.time = time;
		this.album_id = album_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public int getAlbum_id() {
		return album_id;
	}

	public void setAlbum_id(int album_id) {
		this.album_id = album_id;
	}

}
