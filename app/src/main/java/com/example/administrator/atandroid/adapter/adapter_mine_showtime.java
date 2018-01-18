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

import com.example.administrator.atandroid.bean.Photo;
import com.example.administrator.atandroid.util.ImageDownloadHelper;

/**
 * Created by Administrator on 2017/5/26.
 */

public class adapter_mine_showtime extends BaseAdapter {
    private Context context;
    private List<Photo> list;
    private ImageDownloadHelper downloadHelper;// 图片加载对象
    private ImageDownloadHelper.OnImageDownloadListener imageDownloadListener;// 图片加载回调接口的对象
    public adapter_mine_showtime(Context context){
        this.context = context;
        downloadHelper = new ImageDownloadHelper();
    }

    //更新适配器数据
    public void setDate(List<Photo> list){
        this.list = list;
        notifyDataSetChanged();
    }
    // 添加适配器的数据
    public void addData(List<Photo> list) {
        this.list.addAll(list);
        notifyDataSetChanged(); // 刷新适配器的数据
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size(); // 当List为空时，适配器自动为空;
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
        ViewHolder viewHolder = null;
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.activity_mine_showtime_item, null);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
            viewHolder.mine_showtime_content = (TextView)convertView.findViewById(R.id.mine_showtime_content);
            viewHolder.mine_showtime_time = (TextView)convertView.findViewById(R.id.mine_showtime_time);
            viewHolder.mine_showtime_iv1 = (ImageView) convertView.findViewById(R.id.mine_showtime_iv1);
            viewHolder.mine_showtime_iv2 = (ImageView) convertView.findViewById(R.id.mine_showtime_iv2);
            viewHolder.mine_showtime_iv3 = (ImageView) convertView.findViewById(R.id.mine_showtime_iv3);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        setText(position, viewHolder);
        setImage(position, viewHolder);// 加载图片
        return convertView;
    }

    private void setImage(int position, final ViewHolder viewHolder) {
        final String icon = list.get(position).getUrl();
        viewHolder.mine_showtime_iv2.setTag(icon);// 将待加载的图片地址和显示图片的控件进行绑定，防止图片显示错位
        viewHolder.mine_showtime_iv2.setImageResource(R.drawable.placeholder);// 设置默认图片
        // 接口回调，将加载的bitmap图片返回
        imageDownloadListener = new ImageDownloadHelper.OnImageDownloadListener(){

            @Override
            public void onImageDownload(Bitmap bitmap, String icon) {
                if (bitmap != null && icon.equals(viewHolder.mine_showtime_iv2.getTag())) {
                    viewHolder.mine_showtime_iv2.setImageBitmap(bitmap);
                    notifyDataSetChanged();
                }
            }
        };
        // 加载图片
        // @imageUrl:图片的地址
        // @holderView.iv:显示图片的控件变量
        // @imageDownloadListener:回调加载结果的接口对象
        downloadHelper.myDownloadImage(context, icon, viewHolder.mine_showtime_iv2,
                imageDownloadListener);

        viewHolder.mine_showtime_iv1.setTag(icon);// 将待加载的图片地址和显示图片的控件进行绑定，防止图片显示错位
        viewHolder.mine_showtime_iv1.setImageResource(R.drawable.placeholder);// 设置默认图片
        // 接口回调，将加载的bitmap图片返回
        imageDownloadListener = new ImageDownloadHelper.OnImageDownloadListener(){

            @Override
            public void onImageDownload(Bitmap bitmap, String icon) {
                if (bitmap != null && icon.equals(viewHolder.mine_showtime_iv1.getTag())) {
                    viewHolder.mine_showtime_iv2.setImageBitmap(bitmap);
                    notifyDataSetChanged();
                }
            }
        };
        // 加载图片
        // @imageUrl:图片的地址
        // @holderView.iv:显示图片的控件变量
        // @imageDownloadListener:回调加载结果的接口对象
        downloadHelper.myDownloadImage(context, icon, viewHolder.mine_showtime_iv1,
                imageDownloadListener);
        viewHolder.mine_showtime_iv3.setTag(icon);// 将待加载的图片地址和显示图片的控件进行绑定，防止图片显示错位
        viewHolder.mine_showtime_iv3.setImageResource(R.drawable.placeholder);// 设置默认图片
        // 接口回调，将加载的bitmap图片返回
        imageDownloadListener = new ImageDownloadHelper.OnImageDownloadListener(){

            @Override
            public void onImageDownload(Bitmap bitmap, String icon) {
                if (bitmap != null && icon.equals(viewHolder.mine_showtime_iv3.getTag())) {
                    viewHolder.mine_showtime_iv3.setImageBitmap(bitmap);
                    notifyDataSetChanged();
                }
            }
        };
        // 加载图片
        // @imageUrl:图片的地址
        // @holderView.iv:显示图片的控件变量
        // @imageDownloadListener:回调加载结果的接口对象
        downloadHelper.myDownloadImage(context, icon, viewHolder.mine_showtime_iv3,
                imageDownloadListener);
    }

    private void setText(int position, ViewHolder viewHolder) {
        viewHolder.mine_showtime_content.setText(list.get(position).getContent());
        viewHolder.mine_showtime_time.setText(list.get(position).getTime());
    }

    static class ViewHolder{
        public ImageView mine_showtime_iv1,mine_showtime_iv2,mine_showtime_iv3;
        public TextView mine_showtime_time;
        public TextView mine_showtime_content;
    }
}
