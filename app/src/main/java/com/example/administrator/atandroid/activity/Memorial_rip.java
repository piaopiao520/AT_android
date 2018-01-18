package com.example.administrator.atandroid.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.administrator.atandroid.R;

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
 * Created by Administrator on 2017/5/6.
 */

public class Memorial_rip extends AppCompatActivity implements View.OnClickListener{
    private String info = "3";
    List<Gravestone> list ;
    MyConfig myConfig;
    public static String URL;
    Gravestone gravestone;
    private ImageView memorial_rip_iv;
    private TextView memorial_rip_content;
    private ImageView memorial_rip_back,memorial_rip_next,memorial_rip_retake,memorial_rip_around,memorial_rip_serives;

    private ImageDownloadHelper downloadHelper;// 图片加载对象
    private ImageDownloadHelper.OnImageDownloadListener imageDownloadListener;// 图片加载回调接口的对象
    private AsyncTaskHelper taskHelper;// 网络加载对象
    private AsyncTaskHelper.OnDataDownloadListener downloadListener;// 接口回调对象

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorial_rip);
        init();

    }

    private void init() {
        taskHelper = new AsyncTaskHelper();// 加载网络使用封装好AsyncTask的Helper类
        memorial_rip_back = (ImageView)findViewById(R.id.memorial_rip_back);
        memorial_rip_next = (ImageView)findViewById(R.id.memorial_rip_next);
        memorial_rip_retake = (ImageView) findViewById(R.id.memorial_rip_retake);
        memorial_rip_around = (ImageView) findViewById(R.id.memorial_rip_around);
        memorial_rip_serives = (ImageView) findViewById(R.id.memorial_rip_serives);
        memorial_rip_back.setOnClickListener(this);
        memorial_rip_next.setOnClickListener(this);
        memorial_rip_retake.setOnClickListener(this);
        memorial_rip_around.setOnClickListener(this);
        memorial_rip_serives.setOnClickListener(this);
        downloadHelper = new ImageDownloadHelper();
        memorial_rip_iv = (ImageView) findViewById(R.id.memorial_rip_iv);
        memorial_rip_content = (TextView)findViewById(R.id.memorial_rip_content);
// 实例化接口对象，同时获取回调的结果
        myConfig = new MyConfig("GravestoneServlet","selectGravestone");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id",1);
        String str = JSON.toJSONString(map);
        Log.e("ccccccccccc",str);
        try {
            URL= myConfig.getURL()+ StringUtil.strEncode(str);
            Log.e("-----------------------",URL);
        downloadListener = new AsyncTaskHelper.OnDataDownloadListener() {
            @Override
            public void onDataDownload(byte[] result) {
                // 将结果转换成Json字符串
                String jsonString = new String(result);
                Log.e("aaaaaaaaaaaaaa",jsonString);
                // 将Json字符串转换为Map对象
                Map<String, Object> map = new HashMap<>();
                map = FastJsonTools.getMap(jsonString);
                String result1 = map.get("result").toString();
                String code1 = map.get("code").toString();
                Log.e("bbbbbbbbbbbbbbbbbbbb",result1);
                gravestone=FastJsonTools.getBean(result1,Gravestone.class);
                showData();
            }
        };
        taskHelper.downloadData(this,URL, downloadListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
       }

    private void showData() {
        memorial_rip_content.setText(gravestone.getContent()+"");

        imageDownloadListener = new ImageDownloadHelper.OnImageDownloadListener() {

            @Override
            public void onImageDownload(Bitmap bitmap, String imgUrl) {
                if(bitmap != null) {
                    memorial_rip_iv.setImageBitmap(bitmap);
                }

            }
        };
        downloadHelper.myDownloadImage(this, gravestone.getIcon(), memorial_rip_iv, imageDownloadListener);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.memorial_rip_retake:
                Intent intent = new Intent();
                intent.setClass(this,Memorial_rip_retake.class);
                startActivity(intent);
                this.finish();
                break;
            case R.id.memorial_rip_around:
                Intent intent1 = new Intent();
                intent1.setClass(this,Memorial_rip_around.class);
                startActivity(intent1);
                this.finish();
                break;
            case R.id.memorial_rip_serives:
                Intent intent2 = new Intent();
                intent2.setClass(this,Around.class);
                intent2.putExtra("info",info);
                startActivity(intent2);
                this.finish();
                break;
            case R.id.memorial_rip_back:
                Intent intent4 = new Intent();
                intent4.setClass(this,Memorial.class);
                startActivity(intent4);
                this.finish();
                break;
            case R.id.memorial_rip_next:
                Intent intent5 = new Intent();
                intent5.setClass(this,Memorial_wish.class);
                startActivity(intent5);
                this.finish();
                break;
        }
    }
}
