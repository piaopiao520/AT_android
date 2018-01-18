package com.example.administrator.atandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.atandroid.R;
import com.example.administrator.atandroid.adapter.NewsAdapter;
import com.example.administrator.atandroid.bean.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class Charity_information_Activity extends AppCompatActivity implements View.OnClickListener {

    private List<News> newsList;
    private NewsAdapter adapter;
    private Handler handler;
    private ListView lv;
    public List<String> times;
    private ImageView info_back_to_charity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_information);
        info_back_to_charity = ((ImageView) findViewById(R.id.info_back_to_charity));
        info_back_to_charity.setOnClickListener(this);
        newsList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.news_lv);
        getNews();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    adapter = new NewsAdapter(Charity_information_Activity.this,newsList);
                    lv.setAdapter(adapter);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            News news = newsList.get(position);
                            Intent intent = new Intent(Charity_information_Activity.this, NewsDisplayActvivity.class);
                            intent.putExtra("news_url", news.getNewsUrl());
                            startActivity(intent);
                        }
                    });
                }
            }
        };

    }


    private void getNews() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取新闻3页的数据，网址格式为：https://voice.hupu.com/nba/第几页
                    for (int i = 1; i <= 3; i++) {

                        Document doc = Jsoup.connect("http://www.ixiupet.com/zixun/xinwen/list_2_" + Integer.toString(i) + ".html").get();
                        Elements titleLinks = doc.select("h3");    //解析来获取每条新闻的标题与链接地址
                        Elements imgeLinks = doc.select("img");//解析来获取每条新闻的简介
                        Elements timeLinks = doc.select("span.zzsj");   //解析来获取每条新闻的时间与来源
                        times = new ArrayList<String>();
                        for (int k = 0; k<=45; k = k + 3) {
                            times.add(timeLinks.get(k).text() + "    时间： " + timeLinks.get(k + 1).text()+"  阅读量：  "+timeLinks.get(k + 2).text());

                        }

                        for (int j = 0; j <=15 ; j++) {
                            String title = titleLinks.get(j).select("a").text();
                            String uri = titleLinks.get(j).select("a").attr("href");
                            String imag = imgeLinks.get(j).attr("src");
                            String time = times.get(j);
                            News news = new News(title, uri, imag, time);
                            newsList.add(news);

                        }
                    }
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onClick(View view) {
        Intent intent =new Intent();
        intent.setClass(this,Charity.class);
        startActivity(intent);
        this.finish();
    }
}



