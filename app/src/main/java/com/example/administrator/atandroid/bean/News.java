package com.example.administrator.atandroid.bean;

/**
 * Created by Administrator on 2017/1/21.
 */

public class News {
    private String newsTitle;   //新闻标题
    private String newsUrl;     //新闻链接地址
    private String img_url;        //图片
    private String newsTime;    //新闻时间与来源

    public News(String newsTitle, String newsUrl, String img_url, String newsTime) {
        this.newsTitle = newsTitle;
        this.newsUrl = newsUrl;
        this.img_url = img_url;
        this.newsTime = newsTime;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(String newsTime) {
        this.newsTime = newsTime;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }
}
