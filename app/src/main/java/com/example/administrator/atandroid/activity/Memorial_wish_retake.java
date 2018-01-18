package com.example.administrator.atandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.example.administrator.atandroid.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.example.administrator.atandroid.bean.MyConfig;
import com.example.administrator.atandroid.bean.PrayStrip;
import com.example.administrator.atandroid.util.AsyncTaskHelper;
import com.example.administrator.atandroid.util.FastJsonTools;
import com.example.administrator.atandroid.util.ImageDownloadHelper;
import com.example.administrator.atandroid.util.StringUtil;

/**
 * Created by Administrator on 2017/5/6.
 */

public class Memorial_wish_retake extends AppCompatActivity implements View.OnClickListener{

    MyConfig myConfig;
    public static String URL;
    PrayStrip prayStrip;
    private LinearLayout wish_retake;
    private ImageView memorial_wish_retake_back,wish_retake_iv;
    private EditText wish_retake_et;
    private Button memorial_wish_retake_set;
    private ImageView red,yellow,blue;

    List<PrayStrip> list;
    private ImageDownloadHelper downloadHelper;// 图片加载对象
    private ImageDownloadHelper.OnImageDownloadListener imageDownloadListener;// 图片加载回调接口的对象
    private AsyncTaskHelper taskHelper;// 网络加载对象
    private AsyncTaskHelper.OnDataDownloadListener downloadListener;// 接口回调对象
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_memorial_wishretake);
        init();
    }

    private void init() {
        downloadHelper = new ImageDownloadHelper();
        taskHelper = new AsyncTaskHelper();// 加载网络使用封装好AsyncTask的Helper类

        wish_retake = (LinearLayout)findViewById(R.id.wish_retake);
        wish_retake_iv = (ImageView) findViewById(R.id.wish_retake_iv);
        wish_retake_et = (EditText) findViewById(R.id.wish_retake_et);
        memorial_wish_retake_back = (ImageView) findViewById(R.id.memorial_wish_retake_back);
        memorial_wish_retake_set = (Button) findViewById(R.id.memorial_wish_retake_set);
        memorial_wish_retake_back.setOnClickListener(this);
        memorial_wish_retake_set.setOnClickListener(this);

        red = (ImageView) findViewById(R.id.memorial_wish_red);
        yellow = (ImageView) findViewById(R.id.memorial_wish_yellow);
        blue = (ImageView) findViewById(R.id.memorial_wish_blue);
        red.setOnClickListener(this);
        yellow.setOnClickListener(this);
        blue.setOnClickListener(this);
    }

    private void showData() {
        myConfig = new MyConfig("PrayStripServlet","updatePrayStrip");

//获取内容
        String content = wish_retake_et.getText().toString();
        // 获取发送时间
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String releaseTime = sdf.format(date);

        Map<String, Object> map1 = new LinkedHashMap<String, Object>();
        map1.put("id",1);
        map1.put("content",content);
        map1.put("time",releaseTime);
        map1.put("user","123");
        String str = JSON.toJSONString(map1);
        Log.e("ccccccccccc",str);
        try {
            URL= myConfig.getURL()+ StringUtil.strEncode(str);
            Log.e("-----------------------",URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            }
        };
        taskHelper.downloadData(this,URL, downloadListener);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.memorial_wish_retake_back:
                Intent intent = new Intent();
                intent.setClass(this,Memorial_wish.class);
                startActivity(intent);
                this.finish();
                break;
            case R.id.memorial_wish_retake_set:
                showData();
                Intent intent1 = new Intent();
                intent1.setClass(this,Memorial_wish.class);
                startActivity(intent1);
                this.finish();
                break;
            case R.id.memorial_wish_red:
                memorial_wish_retake_set.setBackgroundResource(R.drawable.anniu);
                wish_retake.setBackgroundDrawable(getResources().getDrawable(R.drawable.redb));
                break;
            case R.id.memorial_wish_yellow:
                memorial_wish_retake_set.setBackgroundResource(R.drawable.yellowbtl);
                wish_retake.setBackgroundDrawable(getResources().getDrawable(R.drawable.yellowb));
                break;
            case R.id.memorial_wish_blue:
                memorial_wish_retake_set.setBackgroundResource(R.drawable.bluebtl);
                wish_retake.setBackgroundDrawable(getResources().getDrawable(R.drawable.blueb));
                break;
        }
    }
}
