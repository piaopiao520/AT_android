package com.example.administrator.atandroid.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.administrator.atandroid.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.example.administrator.atandroid.bean.Activity;
import com.example.administrator.atandroid.util.AsyncTaskHelper;
import com.example.administrator.atandroid.util.AsyncTaskHelper.OnDataDownloadListener;
import com.example.administrator.atandroid.util.FastJsonTools;
import com.example.administrator.atandroid.util.ImageDownloadHelper;

/**
 * Created by Administrator on 2017/5/6.
 */

public class Charity_project_listviewitem extends AppCompatActivity implements View.OnClickListener {
    private TextView genzong, love_detail, rescueNum, project_synopsis, time_start, time_stop,locale_detail;
    private Button DonateCompassion;
    private ImageView photo_detail, charity_project_collect, tv_back_charity_project;
    private String Project_id, URL_charity_project_xiangxi;
    private OnDataDownloadListener downloadListener;
    private AsyncTaskHelper taskHelper;
    private ImageDownloadHelper downloadHelper;// 图片加载对象
    private ImageDownloadHelper.OnImageDownloadListener imageDownloadListener;// 图片加载回调接口的对象

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_project_listviewitem);
        init();

    }

    private void init() {
        downloadHelper = new ImageDownloadHelper();
        genzong = (TextView) findViewById(R.id.genzong);//项目跟踪
        locale_detail = (TextView) findViewById(R.id.tv_charity_project_locale_detail);//项目跟踪
        love_detail = (TextView) findViewById(R.id.tv_charity_project_love_detail);//爱心值
        rescueNum = (TextView) findViewById(R.id.charity_project_rescueNum);//救助量
        project_synopsis = (TextView) findViewById(R.id.charity_project_synopsis);//项目简介
        time_start = (TextView) findViewById(R.id.tv_charity_project_time_start);//开始时间
        time_stop = (TextView) findViewById(R.id.tv_charity_project_time_stop);//结束时间
        DonateCompassion = (Button) findViewById(R.id.tv_charity_project_DonateCompassion);//献出爱心
        photo_detail = (ImageView) findViewById(R.id.iv_charity_project_photo_detail);//图片海报
        charity_project_collect = (ImageView) findViewById(R.id.charity_project_collect);//项目收藏
        tv_back_charity_project = (ImageView) findViewById(R.id.tv_back_charity_project);//返回前页
        genzong.setOnClickListener(this);
        DonateCompassion.setOnClickListener(this);
        charity_project_collect.setOnClickListener(this);
        tv_back_charity_project.setOnClickListener(this);
        showCharityProject();
    }

    private void showCharityProject() {
        Bundle bundle = this.getIntent().getExtras();
        String charityproject_info = bundle.getString("charityproject_info");
        Activity charityproject= FastJsonTools.getBean(charityproject_info, Activity.class);
        love_detail.setText(charityproject.getLove()+"");
        project_synopsis.setText(charityproject.getContent());
        time_start.setText(charityproject.getTime_begin());
        time_stop.setText(charityproject.getTime_end());
        locale_detail.setText(charityproject.getOrganization());

        photo_detail.setTag(charityproject.getIcon());
        photo_detail.setImageResource(R.drawable.placeholder);


        imageDownloadListener = new ImageDownloadHelper.OnImageDownloadListener() {
            @Override
            //setImageResource会根据设备分辨率进行图片大小缩放适配
            //setImageBitmap(BitmapFactory.decodeResource(res,id))大小需要手动调。
            public void onImageDownload(Bitmap bitmap, String imgUrl) {
                if (bitmap != null
                        && imgUrl.equals(photo_detail.getTag())) {
                    photo_detail.setImageBitmap(bitmap);
                }
            }
        };
        // 加载图片
        // @project_photoStr:图片的地址
        // @holder:显示图片的控件变量
        // @imageDownloadListener:回调加载结果的接口对象
        downloadHelper.myDownloadImage(this,charityproject.getIcon(), photo_detail,
                imageDownloadListener);

    }

    private void ecanpJson() {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Project_id", Project_id);
        URL_charity_project_xiangxi = JSON.toJSONString(map);
        try {
            URL_charity_project_xiangxi = URLEncoder.encode(URL_charity_project_xiangxi, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(URL_charity_project_xiangxi);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.genzong://项目跟踪
                Intent intent1 = new Intent();
                intent1.setClass(this, Charity_project_genzong.class);
                startActivity(intent1);
            case R.id.tv_charity_project_DonateCompassion://献爱心
//                Intent intent2 = new Intent();
//                intent2.setClass(this,. class);
//                startActivity(intent2);
            case R.id.charity_project_collect://收藏公益项目
                addCharityProject();
                break;
            case R.id.tv_back_charity_project://返回公益项目listview
                Intent intent4 = new Intent();
                intent4.setClass(this, Charity_project.class);
                startActivity(intent4);
                this.finish();
            default:
        }
    }
//收藏公益项目
    private void addCharityProject() {

    }
}
