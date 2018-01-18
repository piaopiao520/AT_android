package com.example.administrator.atandroid.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.example.administrator.atandroid.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.administrator.atandroid.bean.Gravestone;
import com.example.administrator.atandroid.bean.MyConfig;
import com.example.administrator.atandroid.util.AsyncTaskHelper;
import com.example.administrator.atandroid.util.FastJsonTools;
import com.example.administrator.atandroid.util.ImageDownloadHelper;
import com.example.administrator.atandroid.util.StringUtil;

/**
 * Created by Administrator on 2017/5/23.
 */

public class Memorial_rip_around extends AppCompatActivity implements View.OnClickListener{

    private ImageView memorial_rip_around_back,rip_next;
    private EditText rip_ed;
    private ImageView rip_iv;
    List<Gravestone> list ;
    MyConfig myConfig;
    String url;
    Gravestone gravestone;
    private static int i=1;
    private ImageDownloadHelper downloadHelper;// 图片加载对象
    private ImageDownloadHelper.OnImageDownloadListener imageDownloadListener;// 图片加载回调接口的对象
    private AsyncTaskHelper taskHelper;// 网络加载对象
    private AsyncTaskHelper.OnDataDownloadListener downloadListener;// 接口回调对象

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorial_riparound);
        init();
    }

    private void init() {

        taskHelper = new AsyncTaskHelper();// 加载网络使用封装好AsyncTask的Helper类

        memorial_rip_around_back = (ImageView) findViewById(R.id.memorial_rip_around_back);
        memorial_rip_around_back.setOnClickListener(this);
        rip_next = (ImageView) findViewById(R.id.rip_next);
        rip_next.setOnClickListener(this);
        downloadHelper = new ImageDownloadHelper();
        rip_ed = (EditText) findViewById(R.id.rip_ed);
        rip_iv = (ImageView) findViewById(R.id.rip_iv);
        showData();
    }

    private void showData() {
        myConfig = new MyConfig("GravestoneServlet","selectGravestone");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id",i);

        String str = JSON.toJSONString(map);
        Log.e("ccccccccccc",str);
        try {
            url= myConfig.getURL()+ StringUtil.strEncode(str);
            Log.e("-----------------------",url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 实例化接口对象，同时获取回调的结果
        downloadListener = new AsyncTaskHelper.OnDataDownloadListener() {
            @Override
            public void onDataDownload(byte[] result) {
                // 将结果转换成Json字符串
                String jsonString = new String(result);
                Log.e("aaaaaaaaaaaaaa",jsonString);
                list = new ArrayList<Gravestone>();
                // 将Json字符串转换为Map对象
                Map<String, Object> map = FastJsonTools.getMap(jsonString);
                String result1 = map.get("result").toString();
                Log.e("bbbbbbbbbbbbbbbbbbbb",result1);
                gravestone=FastJsonTools.getBean(result1,Gravestone.class);
                show();
            }


        };
        taskHelper.downloadData(this,url, downloadListener);

    }
    private void show() {
        rip_ed.setText(gravestone.getContent());

        imageDownloadListener = new ImageDownloadHelper.OnImageDownloadListener() {

            @Override
            public void onImageDownload(Bitmap bitmap, String imgUrl) {
                if(bitmap != null) {
                    rip_iv.setImageBitmap(bitmap);
                }

            }
        };
        downloadHelper.myDownloadImage(this, gravestone.getIcon(), rip_iv, imageDownloadListener);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.memorial_rip_around_back:
                Intent intent = new Intent();
                intent.setClass(this, Memorial_rip.class);
                startActivity(intent);
                this.finish();
                break;
            case R.id.rip_next:
                i++;
                showData();
                break;
        }
    }
}
