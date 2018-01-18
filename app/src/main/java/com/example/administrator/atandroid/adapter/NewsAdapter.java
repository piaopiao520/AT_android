package com.example.administrator.atandroid.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.atandroid.R;

import java.util.List;

import com.example.administrator.atandroid.bean.News;
import com.example.administrator.atandroid.util.ImageDownloadHelper;
import com.example.administrator.atandroid.util.ImageDownloadHelper.OnImageDownloadListener;

/**
 * Created by Administrator on 2017/1/21.
 */

public class NewsAdapter extends BaseAdapter {

    private List<News> newsList;
    private View view;
    private Context mContext;
    private ViewHolder viewHolder;
    private ImageDownloadHelper downloadHelper;// 图片加载对象
    private OnImageDownloadListener imageDownloadListener;// 图片加载回调接口的对象


    public NewsAdapter(Context mContext, List<News> newsList) {
        this.newsList = newsList;
        this.mContext = mContext;
        downloadHelper = new ImageDownloadHelper();
    }
    // 更新适配器的数据
    public void setData(List<News> list) {
        this.newsList = newsList;
        notifyDataSetChanged(); // 刷新适配器的数据
    }

    // 添加适配器的数据
    public void addData(List<News> newsList) {
        this.newsList.addAll(newsList);
        notifyDataSetChanged(); // 刷新适配器的数据
    }
    @Override
    public int getCount() {
        return newsList == null ? 0 : newsList.size(); // 当List为空时，适配器自动为空
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.activity_charity_information_listview,
                    null);
            viewHolder = new ViewHolder();
            viewHolder.newsTitle = (TextView) view
                    .findViewById(R.id.news_title);
            viewHolder.newsDesc = (ImageView) view.findViewById(R.id.news_desc);
            viewHolder.newsTime = (TextView) view.findViewById(R.id.news_time);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.newsTitle.setText(newsList.get(position).getNewsTitle());
        viewHolder.newsDesc.setTag(newsList.get(position).getImg_url());
        viewHolder.newsDesc.setImageResource(R.drawable.placeholder);
        viewHolder.newsTime.setText("来自 : " + newsList.get(position).getNewsTime());


        String imgUrl =newsList.get(position).getImg_url();
        imageDownloadListener = new OnImageDownloadListener() {
            @Override
            //setImageResource会根据设备分辨率进行图片大小缩放适配
            //setImageBitmap(BitmapFactory.decodeResource(res,id))大小需要手动调。
            public void onImageDownload(Bitmap bitmap, String imgUrl) {
                if (bitmap != null
                        && imgUrl.equals(viewHolder.newsDesc.getTag())) {
                    viewHolder.newsDesc.setImageBitmap(bitmap);
                    notifyDataSetChanged();
                }
            }
        };
        // 加载图片
        // @project_photoStr:图片的地址
        // @holder:显示图片的控件变量
        // @imageDownloadListener:回调加载结果的接口对象
        downloadHelper.myDownloadImage(mContext,newsList.get(position).getImg_url(), viewHolder.newsDesc,
                imageDownloadListener);
        return view;
    }

    class ViewHolder {
        TextView newsTitle;
        ImageView newsDesc;
        TextView newsTime;
    }

}
