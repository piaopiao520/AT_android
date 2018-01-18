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
import com.example.administrator.atandroid.activity.Around_listviewitem;
import com.example.administrator.atandroid.R;

import java.util.List;

import com.example.administrator.atandroid.bean.Seller;
import com.example.administrator.atandroid.util.ImageDownloadHelper;

/**
 * Created by Administrator on 2017/5/22.
 */

public class adapter_around_main extends BaseAdapter {

    private Context context;
    private List<Seller> list;
    private ImageDownloadHelper downloadHelper;// 图片加载对象
    private ImageDownloadHelper.OnImageDownloadListener imageDownloadListener;// 图片加载回调接口的对象



    public adapter_around_main(Context context) {
        this.context = context;
        downloadHelper = new ImageDownloadHelper();

    }
    //更新适配器数据
    public void setDate(List<Seller> list){
        this.list = list;
        notifyDataSetChanged();
    }
    // 添加适配器的数据
    public void addData(List<Seller> list) {
        this.list.addAll(list);
        notifyDataSetChanged(); // 刷新适配器的数据
    }
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.activity_around_listview,null);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
            viewHolder.around_listview_ll = (LinearLayout) convertView.findViewById(R.id.around_listview_ll);
            viewHolder.around_listview_iv = (ImageView) convertView.findViewById(R.id.around_listview_iv);
            viewHolder.around_listview_title = (TextView)convertView.findViewById(R.id.around_listview_title);
            viewHolder.around_listview_content = (TextView)convertView.findViewById(R.id.around_listview_content);
            viewHolder.around_listview_address = (TextView)convertView.findViewById(R.id.around_listview_address);
            viewHolder.around_listview_distance = (TextView)convertView.findViewById(R.id.around_listview_distance);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        setText(position, viewHolder);
        setImage(position, viewHolder);// 加载图片
        final Seller around_list = list.get(position);
        viewHolder.around_listview_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, Around_listviewitem.class);
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
        final String icon = list.get(position).getIcon();
        viewHolder.around_listview_iv.setTag(icon);// 将待加载的图片地址和显示图片的控件进行绑定，防止图片显示错位
        viewHolder.around_listview_iv.setImageResource(R.drawable.placeholder);// 设置默认图片
        // 接口回调，将加载的bitmap图片返回
        imageDownloadListener = new ImageDownloadHelper.OnImageDownloadListener() {

            @Override
            public void onImageDownload(Bitmap bitmap, String icon) {
                if (bitmap != null && icon.equals(viewHolder.around_listview_iv.getTag())) {
                    viewHolder.around_listview_iv.setImageBitmap(bitmap);
                    notifyDataSetChanged();
                }
            }
            };
        // 加载图片
        // @imageUrl:图片的地址
        // @holderView.iv:显示图片的控件变量
        // @imageDownloadListener:回调加载结果的接口对象
        downloadHelper.myDownloadImage(context, icon, viewHolder.around_listview_iv,
                imageDownloadListener);

    }


    private void setText(int position, ViewHolder viewHolder) {
        viewHolder.around_listview_title.setText(list.get(position).getName());
        viewHolder.around_listview_content.setText(list.get(position).getSummary());
        viewHolder.around_listview_address.setText(list.get(position).getAddress());
        viewHolder.around_listview_distance.setText(list.get(position).getDistance() + "");
    }

    static class ViewHolder {
        public LinearLayout around_listview_ll;
        public ImageView around_listview_iv;
        public TextView around_listview_title;
        public TextView around_listview_content;
        public TextView around_listview_address;
        public TextView around_listview_distance;
    }
}
