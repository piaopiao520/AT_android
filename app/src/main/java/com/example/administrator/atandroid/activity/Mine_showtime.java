package com.example.administrator.atandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.example.administrator.atandroid.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.administrator.atandroid.adapter.adapter_mine_showtime;
import com.example.administrator.atandroid.bean.MyConfig;
import com.example.administrator.atandroid.bean.Photo;
import com.example.administrator.atandroid.util.AsyncTaskHelper;
import com.example.administrator.atandroid.util.FastJsonTools;
import com.example.administrator.atandroid.util.StringUtil;

/**
 * Created by Administrator on 2017/5/6.
 */

public class Mine_showtime extends AppCompatActivity implements View.OnClickListener {
    private AsyncTaskHelper taskHelper;// 网络加载对象
    private AsyncTaskHelper.OnDataDownloadListener downloadListener;// 接口回调对象
    private boolean isPullDown = false;// 是否下拉
    private boolean isPullUp = false;// 是否上拉
    private int page = 1;
    private ListView listView;
    List<Photo> list ;
    String url;
    MyConfig myConfig ;
    private static String aroundInfo;
    private adapter_mine_showtime adapter_mine_showtime;

    private ImageView mine_showtime_back;
    private LinearLayout mine_showtime_jilu;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_showtime);
        listView = (ListView) findViewById(R.id.mine_showtime_listview);
        init();
        showListview();
    }

    private void showListview() {
        // 实例化接口对象，同时获取回调的结果
        downloadListener = new AsyncTaskHelper.OnDataDownloadListener() {
            @Override
            public void onDataDownload(byte[] result) {
                // 将结果转换成Json字符串
                String jsonString = new String(result);
                Log.e("aaaaaaaaaaaaaa",jsonString);
                list = new ArrayList<Photo>();
                // 将Json字符串转换为Map对象
                Map<String, Object> map = FastJsonTools.getMap(jsonString);
                String productList = map.get("result").toString();
                Log.e("bbbbbbbbbbbbbbbbbbbb",productList);
                // 通过FastJson映射得到集合
                list = FastJsonTools.getBeans(productList, Photo.class);
                adapter_mine_showtime.setDate(list);
            }
        };
        taskHelper.downloadData(this,url, downloadListener);
    }

    private void init() {
        aroundInfo = this.getIntent().getStringExtra("aroundInfo");
        myConfig = new MyConfig("PhotoServlet","selectAllPhoto");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("album_id",123);
      // map.put("url","https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=680230261,3715142299&fm=23&gp=0.jpg");

        String str = JSON.toJSONString(map);
        Log.e("ccccccccccc",str);
        try {
            url= myConfig.getURL()+ StringUtil.strEncode(str);
            Log.e("-----------------------",url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        taskHelper = new AsyncTaskHelper();// 加载网络使用封装好AsyncTask的Helper类
        adapter_mine_showtime = new adapter_mine_showtime(this);
        listView.setAdapter(adapter_mine_showtime);

        mine_showtime_back = (ImageView) findViewById(R.id.mine_showtime_back);
        mine_showtime_back.setOnClickListener(this);
        mine_showtime_jilu = (LinearLayout) findViewById(R.id.mine_showtime_jilu);
        mine_showtime_jilu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mine_showtime_back:
                Intent intent = new Intent();
                intent.setClass(this,Mine.class);
                startActivity(intent);
                this.finish();
                break;
            case R.id.mine_showtime_jilu:
                Intent intent1 = new Intent();
                intent1.setClass(this,Mine_showtime_jilu.class);
                startActivity(intent1);
                this.finish();
                break;
        }
    }
}
