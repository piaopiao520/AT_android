package com.example.administrator.atandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.atandroid.activity.Around_jiesuan;
import com.example.administrator.atandroid.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.administrator.atandroid.bean.MyConfig;
import com.example.administrator.atandroid.bean.Seller;
import com.example.administrator.atandroid.bean.Shopping;
import com.example.administrator.atandroid.util.AsyncTaskHelper;
import com.example.administrator.atandroid.util.FastJsonTools;
import com.example.administrator.atandroid.util.ImageDownloadHelper;
import com.example.administrator.atandroid.util.StringUtil;

/**
 * Created by Administrator on 2017/6/2.
 */

public class adapter_mine_around extends BaseAdapter {
    Seller seller;
    MyConfig myConfig ;
    String url;
    Context context;
    private List<Shopping> list;
    private List<Seller> listdate;
    private ImageDownloadHelper downloadHelper;// 图片加载对象
    private ImageDownloadHelper.OnImageDownloadListener imageDownloadListener;// 图片加载回调接口的对象
    private AsyncTaskHelper taskHelper;// 网络加载对象
    private AsyncTaskHelper.OnDataDownloadListener downloadListener;// 接口回调对象

    public adapter_mine_around(Context context) {
        this.context = context;
        downloadHelper = new ImageDownloadHelper();
        taskHelper = new AsyncTaskHelper();// 加载网络使用封装好AsyncTask的Helper类

    }
    //更新适配器数据
    public void setDate(List<Shopping> list){
        this.list = list;
        notifyDataSetChanged();
    }
    // 添加适配器的数据
    public void addData(List<Shopping> list) {
        this.list.addAll(list);
        notifyDataSetChanged(); // 刷新适配器的数据
    }
    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ViewHolder viewHolder ;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.activity_mine_around_listitem,null);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
            viewHolder.mine_around_listview_ll = (LinearLayout) convertView.findViewById(R.id.mine_around_listview_ll);
            viewHolder.mine_around_listview_iv = (ImageView) convertView.findViewById(R.id.mine_around_listview_iv);
            viewHolder.mine_around_listview_title = (TextView)convertView.findViewById(R.id.mine_around_listview_title);
            viewHolder.mine_around_listview_line = (TextView)convertView.findViewById(R.id.mine_around_listview_line);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        getSeller(position);//获取商家的url
        showListview(position);
        setText(position, viewHolder);
        setImage(position, viewHolder);// 加载图片
        final Shopping around_list = list.get(position);
        viewHolder.mine_around_listview_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, Around_jiesuan.class);
                Bundle bundle = new Bundle();
                String around = JSONObject.toJSONString(around_list);
                bundle.putCharSequence("around",around);
                intent.putExtras(bundle);
                // intent.putExtra("id",around_list);
                context.startActivity(intent);

            }
        });
        return convertView;
    }

    private void setImage(int position, final ViewHolder viewHolder) {
        final String icon = listdate.get(position).getIcon();
        viewHolder.mine_around_listview_iv.setTag(icon);// 将待加载的图片地址和显示图片的控件进行绑定，防止图片显示错位
        viewHolder.mine_around_listview_iv.setImageResource(R.drawable.placeholder);// 设置默认图片
        // 接口回调，将加载的bitmap图片返回
        imageDownloadListener = new ImageDownloadHelper.OnImageDownloadListener() {

            @Override
            public void onImageDownload(Bitmap bitmap, String icon) {
                if (bitmap != null && icon.equals(viewHolder.mine_around_listview_iv.getTag())) {
                    viewHolder.mine_around_listview_iv.setImageBitmap(bitmap);
                    notifyDataSetChanged();
                }
            }
        };
        // 加载图片
        // @imageUrl:图片的地址
        // @holderView.iv:显示图片的控件变量
        // @imageDownloadListener:回调加载结果的接口对象
        downloadHelper.myDownloadImage(context, icon, viewHolder.mine_around_listview_iv,
                imageDownloadListener);

    }

    private void showListview(int position) {
        // 实例化接口对象，同时获取回调的结果

        downloadListener = new AsyncTaskHelper.OnDataDownloadListener() {
            @Override
            public void onDataDownload(byte[] result) {
                // 将结果转换成Json字符串
                String jsonString = new String(result);
                Log.e("aaaaaaaaaaaaaa",jsonString);
                //String jsonString = MyConfig.around_main;
                listdate = new ArrayList<Seller>();
                // 将Json字符串转换为Map对象
                Map<String, Object> map = FastJsonTools.getMap(jsonString);
                String productList = map.get("result").toString();
                Log.e("bbbbbbbbbbbbbbbbbbbb",productList);
                // 通过FastJson映射得到集合
                listdate = FastJsonTools.getBeans(productList, Seller.class);
            }
        };
        taskHelper.downloadData(context,url, downloadListener);

    }

    private void getSeller(int position) {
        myConfig = new MyConfig("GoodServlet", "selectGoodBySeller");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("seller", list.get(position).getSeller());
        String str = JSON.toJSONString(map);
        Log.e("ccccccccccc",str);
        try {
            url = myConfig.getURL() + StringUtil.strEncode(str);
            Log.e("-----------------------",url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setText(int position, ViewHolder viewHolder) {
        viewHolder.mine_around_listview_line.setText(list.get(position).getId());
        viewHolder.mine_around_listview_title.setText(listdate.get(position).getName());
    }

    static class ViewHolder {
        public LinearLayout mine_around_listview_ll;
        public ImageView mine_around_listview_iv;
        public TextView mine_around_listview_title;
        public TextView mine_around_listview_line;
    }
}
