package com.example.administrator.atandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.administrator.atandroid.activity.Charity_project_listviewitem;
import com.example.administrator.atandroid.R;

import java.util.List;

import com.example.administrator.atandroid.bean.Activity;
import com.example.administrator.atandroid.util.ImageDownloadHelper;
import com.example.administrator.atandroid.util.ImageDownloadHelper.OnImageDownloadListener;

/**
 * Created by Administrator on 2017/5/17.
 */

public class charityProjectAdapter extends BaseAdapter {
    List<Activity> list;
    Context context;
    private ImageDownloadHelper downloadHelper;// 图片加载对象
    private OnImageDownloadListener imageDownloadListener;// 图片加载回调接口的对象

    public charityProjectAdapter(Context context) {
        this.context = context;
        downloadHelper = new ImageDownloadHelper();
    }
    // 更新适配器的数据
    public void setData(List<Activity> list) {
        this.list = list;
        notifyDataSetChanged(); // 刷新适配器的数据
    }

    // 添加适配器的数据
    public void addData(List<Activity> list) {
        this.list.addAll(list);
        notifyDataSetChanged(); // 刷新适配器的数据
    }

    // 配置适配器的行数量
    @Override

    public int getCount() {
        return list == null ? 0 : list.size(); // 当List为空时，适配器自动为空
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
        final ViewHolder holder = new ViewHolder();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.activity_charity_project_listview, null);
        if (convertView != null) {
            holder.project_photo = ((ImageView) convertView.findViewById(R.id.iv_charity_project_photo));
            holder.project_title = ((TextView) convertView.findViewById(R.id.tv_charity_project_title));
            holder.project_locale = ((TextView) convertView.findViewById(R.id.tv_charity_project_locale));
            holder.project_love = ((TextView) convertView.findViewById(R.id.tv_charity_project_love));
            holder.ll_list = ((LinearLayout) convertView.findViewById(R.id.ll_list));

            final Activity charityproject = list.get(position);


            holder.project_title.setText(charityproject.getTitle());
            holder.project_locale.setText(charityproject.getOrganization());
            holder.project_love.setText(charityproject.getLove()+"");

            holder.project_photo.setTag(charityproject.getIcon());
            holder.project_photo.setImageResource(R.drawable.placeholder);

            String imgUrl =charityproject.getIcon();

            holder.ll_list.setOnClickListener(new View.OnClickListener() {
                                                      public void onClick(View arg0) {

                                                          Intent intent = new Intent();
                                                          intent.setClass(context,Charity_project_listviewitem.class);
                                                          Bundle bundle = new Bundle();
                                                          String charityproject_info = JSONObject.toJSONString(charityproject);
                                                          bundle.putCharSequence("charityproject_info", charityproject_info);
                                                          intent.putExtras(bundle);
                                                          context.startActivity(intent);
                                                      }
                                                  });
            imageDownloadListener = new OnImageDownloadListener() {
                @Override
                //setImageResource会根据设备分辨率进行图片大小缩放适配
               //setImageBitmap(BitmapFactory.decodeResource(res,id))大小需要手动调。
                public void onImageDownload(Bitmap bitmap, String imgUrl) {
                    if (bitmap != null
                            && imgUrl.equals(holder.project_photo.getTag())) {
                        holder.project_photo.setImageBitmap(bitmap);
                        notifyDataSetChanged();
                    }
                }
            };
            // 加载图片
            // @project_photoStr:图片的地址
            // @holder:显示图片的控件变量
            // @imageDownloadListener:回调加载结果的接口对象
            downloadHelper.myDownloadImage(context, charityproject.getIcon(), holder.project_photo,
                    imageDownloadListener);
        }
        return convertView;
    }
    private class ViewHolder {
        LinearLayout ll_list;
        ImageView project_photo;
        TextView project_title;
        TextView project_locale;//组织机构
        TextView project_love;
//        private int id;
//        private int heat = 0;// 活动热度;查看一次活动加一
//        private String organization;// 活动发起组织
//        private String icon;// 活动海报
//        private String address = "";
//        private String title = "";
//        private String content = "";
//        private String time_begin = "";
//        private String time_end = "";
//        private String sponsor = "";// 活动发起人
    }
}
