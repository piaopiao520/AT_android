package com.example.administrator.atandroid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.example.administrator.atandroid.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.administrator.atandroid.adapter.adapter_mine_around;
import com.example.administrator.atandroid.bean.MyConfig;
import com.example.administrator.atandroid.bean.Shopping;
import com.example.administrator.atandroid.util.AsyncTaskHelper;
import com.example.administrator.atandroid.util.FastJsonTools;
import com.example.administrator.atandroid.util.StringUtil;

/**
 * Created by Administrator on 2017/5/6.
 */

public class Mine_around extends AppCompatActivity{
    private AsyncTaskHelper taskHelper;// 网络加载对象
    private AsyncTaskHelper.OnDataDownloadListener downloadListener;// 接口回调对象
    private Button mine_around_yonghu,mine_around_shangjia;
    MyConfig myConfig ;
    String url;
    List<Shopping> list ;
    private static String aroundInfo;
    private adapter_mine_around adapter_mine_around;
    private PullToRefreshListView listView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_mine_around);
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
                list = new ArrayList<Shopping>();
                // 将Json字符串转换为Map对象
                Map<String, Object> map = FastJsonTools.getMap(jsonString);
                String productList = map.get("result").toString();
                Log.e("bbbbbbbbbbbbbbbbbbbb",productList);
                // 通过FastJson映射得到集合
                list = FastJsonTools.getBeans(productList, Shopping.class);
                adapter_mine_around.setDate(list);

            }
        };
        taskHelper.downloadData(this,url, downloadListener);
    }
    private void init() {
        listView = (PullToRefreshListView) findViewById(R.id.mine_around_list);
        myConfig = new MyConfig("SellerServlet","selectAllByDistance");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("serve",aroundInfo);

        String str = JSON.toJSONString(map);
        Log.e("ccccccccccc",str);
        try {
            url= myConfig.getURL()+ StringUtil.strEncode(str);
            Log.e("-----------------------",url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        taskHelper = new AsyncTaskHelper();// 加载网络使用封装好AsyncTask的Helper类
        adapter_mine_around = new adapter_mine_around(this);
        listView.setAdapter(adapter_mine_around);
    }
                //1.根据登录的用户获取用户开的商店
                //2.根据返回的商店list获取每个商店的id
                //3.根据每个商店的id去Shopping表中查询



}
