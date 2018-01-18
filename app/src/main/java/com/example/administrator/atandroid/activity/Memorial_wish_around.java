package com.example.administrator.atandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.administrator.atandroid.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.administrator.atandroid.bean.MyConfig;
import com.example.administrator.atandroid.bean.PrayStrip;
import com.example.administrator.atandroid.util.AsyncTaskHelper;
import com.example.administrator.atandroid.util.FastJsonTools;
import com.example.administrator.atandroid.util.ImageDownloadHelper;
import com.example.administrator.atandroid.util.StringUtil;

/**
 * Created by Administrator on 2017/5/23.
 */

public class Memorial_wish_around extends AppCompatActivity implements View.OnClickListener{

    private ImageView memorial_wish_around_back,wishi_around_iv,wishi_around_next;
    private TextView wishi_around_tx;
    List<PrayStrip> list ;
    MyConfig myConfig;
    String url;
    PrayStrip prayStrip;
    private static int i=1;
    private ImageDownloadHelper downloadHelper;// 图片加载对象
    private ImageDownloadHelper.OnImageDownloadListener imageDownloadListener;// 图片加载回调接口的对象
    private AsyncTaskHelper taskHelper;// 网络加载对象
    private AsyncTaskHelper.OnDataDownloadListener downloadListener;// 接口回调对象
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorial_wisharound);
        init();
    }

    private void init() {

        taskHelper = new AsyncTaskHelper();// 加载网络使用封装好AsyncTask的Helper类
        memorial_wish_around_back = (ImageView) findViewById(R.id.memorial_wish_around_back);
        memorial_wish_around_back.setOnClickListener(this);
        wishi_around_next = (ImageView) findViewById(R.id.wish_around_next);
        wishi_around_next.setOnClickListener(this);
        downloadHelper = new ImageDownloadHelper();
        wishi_around_tx = (TextView) findViewById(R.id.wishi_around_tx);
        wishi_around_iv = (ImageView) findViewById(R.id.wish_around_iv);
        showData();
    }

    private void showData() {
        myConfig = new MyConfig("PrayStripServlet","selectPrayStrip");
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
                list = new ArrayList<PrayStrip>();
                // 将Json字符串转换为Map对象
                Map<String, Object> map = FastJsonTools.getMap(jsonString);
                String result1 = map.get("result").toString();
                Log.e("bbbbbbbbbbbbbbbbbbbb",result1);
                prayStrip=FastJsonTools.getBean(result1,PrayStrip.class);
                wishi_around_tx.setText(prayStrip.getContent()+"");

            }


        };
        taskHelper.downloadData(this,url, downloadListener);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.memorial_wish_around_back:
                Intent intent = new Intent();
                intent.setClass(this, Memorial_wish.class);
                startActivity(intent);
                this.finish();
                break;
            case R.id.wish_around_next:
                i++;
                showData();
                break;
        }
    }
}
