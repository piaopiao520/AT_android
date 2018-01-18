package com.example.administrator.atandroid.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.atandroid.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.administrator.atandroid.adapter.adapter_around_jiesuan;
import com.example.administrator.atandroid.bean.Good;
import com.example.administrator.atandroid.util.AsyncTaskHelper;
import com.example.administrator.atandroid.util.FastJsonTools;

/**
 * Created by Administrator on 2017/5/22.
 */

public class Around_jiesuan extends AppCompatActivity {

    private ListView listView;
    String url;
    List<Good> list;
    private static float sum=0;
    private adapter_around_jiesuan adapter_around_jiesuan;
    private TextView around_jiesuan_name,around_jiesuan_price;
    private EditText price_et;
    private AsyncTaskHelper taskHelper;// 网络加载对象
    private AsyncTaskHelper.OnDataDownloadListener downloadListener;// 接口回调对象
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_around_jiesuan);
        listView = (ListView) findViewById(R.id.around_jiesuan_listview);
        init();

    }

    private void init() {
        taskHelper = new AsyncTaskHelper();// 加载网络使用封装好AsyncTask的Helper类
        adapter_around_jiesuan = new adapter_around_jiesuan(this);
        listView.setAdapter(adapter_around_jiesuan);
        Bundle bundle = this.getIntent().getExtras();
        String name = bundle.getString("name");
        url = bundle.getString("url");
        around_jiesuan_name = (TextView) findViewById(R.id.around_jiesuan_name);
        around_jiesuan_name.setText(name);
        around_jiesuan_price = (TextView) findViewById(R.id.around_jiesuan_price);
        price_et = (EditText) findViewById(R.id.price_et);
        showListview();

//        for(int i=0;i<3;i++){
//            sum +=list.get(i).getPrice() * list.get(i).getQuantity();
//        }
//        around_jiesuan_price.setText(sum + "");
    }
    private void showListview() {
        // 实例化接口对象，同时获取回调的结果
        downloadListener = new AsyncTaskHelper.OnDataDownloadListener() {
            @Override
            public void onDataDownload(byte[] result) {
                // 将结果转换成Json字符串
                String jsonString = new String(result);
                Log.e("aaaaaaaaaaaaaa",jsonString);
                list = new ArrayList<Good>();
                // 将Json字符串转换为Map对象
                Map<String, Object> map = FastJsonTools.getMap(jsonString);
                String productList = map.get("result").toString();
                Log.e("bbbbbbbbbbbbbbbbbbbb",productList);
                // 通过FastJson映射得到集合
                list = FastJsonTools.getBeans(productList, Good.class);
                adapter_around_jiesuan.setDate(list);
            }
        };
        taskHelper.downloadData(this,url, downloadListener);
    }
}
