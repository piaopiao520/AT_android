package com.example.administrator.atandroid.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.administrator.atandroid.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.administrator.atandroid.adapter.adapter_around_listviewitem;
import com.example.administrator.atandroid.bean.Good;
import com.example.administrator.atandroid.bean.MyConfig;
import com.example.administrator.atandroid.bean.Seller;
import com.example.administrator.atandroid.util.AsyncTaskHelper;
import com.example.administrator.atandroid.util.FastJsonTools;
import com.example.administrator.atandroid.util.ImageDownloadHelper;
import com.example.administrator.atandroid.util.StringUtil;

/**
 * Created by Administrator on 2017/5/6.
 */

public class Around_listviewitem extends AppCompatActivity implements View.OnClickListener {

    private Button around_jiesuan_bt;
    private ListView listView;
    List<Good> list;
    String url;
    Seller seller;
    MyConfig myConfig ;
    private adapter_around_listviewitem adapter_around_listviewitem;
    private ImageDownloadHelper downloadHelper;// 图片加载对象
    private ImageDownloadHelper.OnImageDownloadListener imageDownloadListener;// 图片加载回调接口的对象
    private AsyncTaskHelper taskHelper;// 网络加载对象
    private AsyncTaskHelper.OnDataDownloadListener downloadListener;// 接口回调对象

    private TextView tx1,tx2,tx3,tx4,tx5,tx6;
    private List<TextView> tx = new ArrayList<>();
    private int k=0;
    private ImageView around_listviewitem_back,around_listviewitem_icon;
    private TextView around_listviewitem_name,around_listviewitem_address,around_listviewitem_summary,around_listviewitem_time_begin,around_listviewitem_time_end;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_around_listviewitem);
        listView =(ListView) findViewById(R.id.around_listviewitem_list);
        init();

    }



    private void init() {
        downloadHelper = new ImageDownloadHelper();
        taskHelper = new AsyncTaskHelper();// 加载网络使用封装好AsyncTask的Helper类
        adapter_around_listviewitem = new adapter_around_listviewitem(this);
        listView.setAdapter(adapter_around_listviewitem);

        around_listviewitem_icon = (ImageView) findViewById(R.id.around_listviewitem_icon);
        around_listviewitem_name = (TextView) findViewById(R.id.around_listviewitem_name);
        around_listviewitem_address = (TextView) findViewById(R.id.around_listviewitem_address);
        around_listviewitem_summary = (TextView) findViewById(R.id.around_listviewitem_summary);
        around_listviewitem_time_begin = (TextView) findViewById(R.id.around_listviewitem_time_begin);
        around_listviewitem_time_end = (TextView) findViewById(R.id.around_listviewitem_time_end);

        tx1 = (TextView) findViewById(R.id.tx1);
        tx2 = (TextView) findViewById(R.id.tx2);
        tx3 = (TextView) findViewById(R.id.tx3);
        tx4 = (TextView) findViewById(R.id.tx4);
        tx5 = (TextView) findViewById(R.id.tx5);
        tx6 = (TextView) findViewById(R.id.tx6);
        tx.add(tx1);
        tx.add(tx2);
        tx.add(tx3);
        tx.add(tx4);
        tx.add(tx5);
        tx.add(tx6);

        showData();
        showListview();
        around_jiesuan_bt = (Button) findViewById(R.id.around_jiesuan_bt);
        around_listviewitem_back = (ImageView) findViewById(R.id.around_listviewitem_back);
        around_jiesuan_bt.setOnClickListener(this);
        around_listviewitem_back.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.around_jiesuan_bt:
                Intent intent = new Intent();
                intent.setClass(this,Around_jiesuan.class);
                Bundle bundle = new Bundle();
                bundle.putString("url",url);
                bundle.putString("name",seller.getName());
                intent.putExtras(bundle);
                startActivity(intent);
                this.finish();
                break;
            case R.id.around_listviewitem_back:
                Intent intent1 = new Intent();
                intent1.setClass(this,Around_main.class);
                startActivity(intent1);
                this.finish();
                break;
        }
    }


    private void showData() {
        Bundle bundle = this.getIntent().getExtras();
        String around = bundle.getString("around");
       seller = FastJsonTools.getBean(around, Seller.class);
        around_listviewitem_name.setText(seller.getName());
        around_listviewitem_address.setText(seller.getAddress());
        around_listviewitem_summary.setText(seller.getSummary());
        around_listviewitem_time_begin.setText(seller.getTime_begin());
        around_listviewitem_time_end.setText(seller.getTime_end());
        String icon = seller.getIcon();
        around_listviewitem_icon.setTag(icon);
        around_listviewitem_icon.setImageResource(R.drawable.placeholder);
        imageDownloadListener = new ImageDownloadHelper.OnImageDownloadListener() {

            @Override
            public void onImageDownload(Bitmap bitmap, String imgUrl) {
                if (bitmap != null) {
                    around_listviewitem_icon.setImageBitmap(bitmap);
                }

            }
        };
        downloadHelper.myDownloadImage(this, icon, around_listviewitem_icon, imageDownloadListener);

        String serves = seller.getServe().toString();
        String[] strs = serves.split(",");
        for (int i = 0; i < strs.length; i++) {
            tx.get(i).setText(strs[k++].toString());
        }


        myConfig = new MyConfig("GoodServlet", "selectGoodBySeller");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("seller", seller.getId());
        String str = JSON.toJSONString(map);
         Log.e("ccccccccccc",str);
        try {
            url = myConfig.getURL() + StringUtil.strEncode(str);
              Log.e("-----------------------",url);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                   adapter_around_listviewitem.setDate(list);
            }
        };
        taskHelper.downloadData(this,url, downloadListener);
    }
}
