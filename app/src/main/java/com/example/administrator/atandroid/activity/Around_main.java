package com.example.administrator.atandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.administrator.atandroid.R;
import com.example.administrator.atandroid.adapter.adapter_around_main;
import com.example.administrator.atandroid.bean.MyConfig;
import com.example.administrator.atandroid.bean.Seller;
import com.example.administrator.atandroid.util.AsyncTaskHelper;
import com.example.administrator.atandroid.util.FastJsonTools;
import com.example.administrator.atandroid.util.StringUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/6.
 */

public class Around_main extends AppCompatActivity implements View.OnClickListener,PullToRefreshBase.OnRefreshListener2<ListView>, PullToRefreshBase.OnPullEventListener<ListView> {
    private AsyncTaskHelper taskHelper;// 网络加载对象
    private AsyncTaskHelper.OnDataDownloadListener downloadListener;// 接口回调对象
    private boolean isPullDown = false;// 是否下拉
    private boolean isPullUp = false;// 是否上拉
    private int page = 1;
    MyConfig myConfig ;
    String url;
    private TextView show_info;
    private static String aroundInfo;
    private ImageView around_back_around_iv;

    private PullToRefreshListView listView;
    List<Seller> list ;
    private adapter_around_main adapter_around_main;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_around_main);
        listView =(PullToRefreshListView) findViewById(R.id.mine_around_main_listview);
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
                list = new ArrayList<Seller>();
                // 将Json字符串转换为Map对象
                Map<String, Object> map = FastJsonTools.getMap(jsonString);
                String productList = map.get("result").toString();
                Log.e("bbbbbbbbbbbbbbbbbbbb",productList);
                // 通过FastJson映射得到集合
                list = FastJsonTools.getBeans(productList, Seller.class);
                if (!isPullUp) { // 初始化列表或者下拉刷新时
                    adapter_around_main.setDate(list);
                    if (isPullDown) {
                        listView.onRefreshComplete(); // 列表刷新完成恢复原样
                        isPullDown = false;
                    }
                } else { // 上拉加载
                    adapter_around_main.addData(list);
                    listView.onRefreshComplete();
                    isPullUp = false;
                }
                adapter_around_main.setDate(list);

            }
        };
        taskHelper.downloadData(this,url, downloadListener);
    }

    private void init() {
        aroundInfo = this.getIntent().getStringExtra("aroundInfo");
        myConfig = new MyConfig("SellerServlet","selectAllByDistance");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("longitude",116.102223);
        map.put("latitude",29.675031);
        map.put("serve",aroundInfo);

        String str = JSON.toJSONString(map);
        Log.e("ccccccccccc",str);
        try {
            url= myConfig.getURL()+StringUtil.strEncode(str);
            Log.e("-----------------------",url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        taskHelper = new AsyncTaskHelper();// 加载网络使用封装好AsyncTask的Helper类
        adapter_around_main = new adapter_around_main(this);
        listView.setAdapter(adapter_around_main);
        // 设置下拉刷新和上拉加载的模式：默认只有下拉刷新一种模式
        // 如果需要两种模式，必须要调用setMode方法设置
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        // 设置下拉刷新和上拉加载的监听器
        listView.setOnRefreshListener(this);
        listView.setOnPullEventListener(this);

        show_info = (TextView) findViewById(R.id.around_show_info);

        show_info.setText(aroundInfo);

        around_back_around_iv = (ImageView) findViewById(R.id.around_back_around_iv);
        around_back_around_iv.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.around_back_around_iv:
                Intent intent = new Intent();
                intent.setClass(this,Around.class);
                startActivity(intent);
                this.finish();
                break;
        }
    }

    @Override
    public void onPullEvent(PullToRefreshBase<ListView> refreshView, PullToRefreshBase.State state, PullToRefreshBase.Mode direction) {
        if (state.equals(PullToRefreshBase.State.PULL_TO_REFRESH)) {
            if (direction.equals(PullToRefreshBase.Mode.PULL_DOWN_TO_REFRESH)) {
                refreshView.getLoadingLayoutProxy().setPullLabel(
                        getString(R.string.pull_down_refresh));// 设置下拉刷新时文本显示
            }
            if (direction.equals(PullToRefreshBase.Mode.PULL_UP_TO_REFRESH)) {
                refreshView.getLoadingLayoutProxy().setPullLabel(
                        getString(R.string.pull_on_refresh));// 设置上拉加载时文本显示
            }
            refreshView.getLoadingLayoutProxy().setReleaseLabel(
                    getString(R.string.release_to_refresh));// 设置释放时文本显示
            refreshView.getLoadingLayoutProxy().setRefreshingLabel(
                    getString(R.string.loading)); // 设置刷新时文本显示
        }

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        page = 1;
        isPullDown = true;
        taskHelper.downloadData(this,url , downloadListener);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        page++;
        isPullUp = true;
        taskHelper.downloadData(this, url , downloadListener);

    }
}
