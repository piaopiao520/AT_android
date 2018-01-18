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
import com.example.administrator.atandroid.activity.Gohome_listviewitem;
import com.example.administrator.atandroid.R;

import java.util.List;

import com.example.administrator.atandroid.bean.PetBack;
import com.example.administrator.atandroid.util.ImageDownloadHelper;

/**
 * Created by Administrator on 2017/5/22.
 */

public class adapter_gohome_main extends BaseAdapter {

    private Context context;
    private List<PetBack> list;
    private ImageDownloadHelper downloadHelper;// 图片加载对象
    private ImageDownloadHelper.OnImageDownloadListener imageDownloadListener;// 图片加载回调接口的对象



    public adapter_gohome_main(Context context) {
        this.context = context;
        downloadHelper = new ImageDownloadHelper();

    }
    //更新适配器数据
    public void setData(List<PetBack> list){
        this.list = list;
        notifyDataSetChanged();
    }
    // 添加适配器的数据
    public void addData(List<PetBack> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.activity_gohome_listview,null);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
            viewHolder.gohome_listview_ll = (LinearLayout) convertView.findViewById(R.id.gohome_listview_ll);
            viewHolder.gohome_listview_pet_iv = (ImageView) convertView.findViewById(R.id.gohome_listview_pet_iv);
            viewHolder.gohome_listview_tag_iv = (ImageView) convertView.findViewById(R.id.gohome_listview_tag_iv);
            viewHolder.gohome_listview_sex_iv = (ImageView) convertView.findViewById(R.id.gohome_listview_sex_iv);
            viewHolder.gohome_listview_name = (TextView) convertView.findViewById(R.id.gohome_listview_name);
            viewHolder.gohome_listview_age = (TextView) convertView.findViewById(R.id.gohome_listview_age);
            viewHolder.gohome_listview_content = (TextView) convertView.findViewById(R.id.gohome_listview_content);
            viewHolder.gohome_listview_address = (TextView) convertView.findViewById(R.id.gohome_listview_address);
            viewHolder.gohome_listview_time = (TextView) convertView.findViewById(R.id.gohome_listview_time);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        setText(position, viewHolder);
        setImage(position, viewHolder);// 加载图片
        switch (list.get(position).getSex().toString()){
            case "女":
                viewHolder.gohome_listview_sex_iv.setImageResource(R.drawable.woman);
                break;
            case "男":
                viewHolder.gohome_listview_sex_iv.setImageResource(R.drawable.man);
                break;
        }
        switch (list.get(position).getState().toString()){
            case "待领养":
                viewHolder.gohome_listview_tag_iv.setImageResource(R.drawable.lingyang);
                break;
            case "待托管":
                viewHolder.gohome_listview_tag_iv.setImageResource(R.drawable.tuoguan);
                break;
            case "待寄养":
                viewHolder.gohome_listview_tag_iv.setImageResource(R.drawable.jiyang);
                break;
        }
        final PetBack around_list = list.get(position);
        viewHolder.gohome_listview_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, Gohome_listviewitem.class);
                Bundle bundle = new Bundle();
                String gohome = JSONObject.toJSONString(around_list);
                bundle.putCharSequence("gohome",gohome);
                intent.putExtras(bundle);
                // intent.putExtra("id",around_list);
                context.startActivity(intent);

            }
        });
        return convertView;
    }

    private void setImage(int position, final ViewHolder viewHolder) {
        final String icon = list.get(position).getIcon();
        viewHolder.gohome_listview_pet_iv.setTag(icon);// 将待加载的图片地址和显示图片的控件进行绑定，防止图片显示错位
        viewHolder.gohome_listview_pet_iv.setImageResource(R.drawable.placeholder);// 设置默认图片
        // 接口回调，将加载的bitmap图片返回
        imageDownloadListener = new ImageDownloadHelper.OnImageDownloadListener() {

            @Override
            public void onImageDownload(Bitmap bitmap, String icon) {
                if (bitmap != null && icon.equals(viewHolder.gohome_listview_pet_iv.getTag())) {
                    viewHolder.gohome_listview_pet_iv.setImageBitmap(bitmap);
                    notifyDataSetChanged();
                }
            }
        };
        // 加载图片
        // @imageUrl:图片的地址
        // @holderView.iv:显示图片的控件变量
        // @imageDownloadListener:回调加载结果的接口对象
        downloadHelper.myDownloadImage(context, icon, viewHolder.gohome_listview_pet_iv,
                imageDownloadListener);


    }

    private void setText(int position, ViewHolder viewHolder) {
        viewHolder.gohome_listview_name.setText(list.get(position).getKind());
        viewHolder.gohome_listview_age.setText(list.get(position).getAge());
        viewHolder.gohome_listview_content.setText(list.get(position).getDescribe());
        viewHolder.gohome_listview_time.setText(list.get(position).getTime());
        viewHolder.gohome_listview_address.setText(list.get(position).getAddress());
    }

    static class ViewHolder{
        public LinearLayout gohome_listview_ll;
        public ImageView gohome_listview_pet_iv,gohome_listview_tag_iv,gohome_listview_sex_iv;
        public TextView gohome_listview_name,gohome_listview_age,gohome_listview_content,gohome_listview_address,gohome_listview_time;    }
}
