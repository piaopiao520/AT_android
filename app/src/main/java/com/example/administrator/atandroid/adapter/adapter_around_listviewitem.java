package com.example.administrator.atandroid.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.atandroid.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.administrator.atandroid.bean.Good;
import com.example.administrator.atandroid.bean.MyConfig;
import com.example.administrator.atandroid.util.AsyncTaskHelper;
import com.example.administrator.atandroid.util.FastJsonTools;
import com.example.administrator.atandroid.util.ImageDownloadHelper;
import com.example.administrator.atandroid.util.StringUtil;

/**
 * Created by Administrator on 2017/5/22.
 */

public class adapter_around_listviewitem extends BaseAdapter{
    private Context context;
    private List<Good> list;
    private AsyncTaskHelper taskHelper;// 网络加载对象
    private AsyncTaskHelper.OnDataDownloadListener downloadListener;// 接口回调对象
    private ImageDownloadHelper downloadHelper;// 图片加载对象
    private ImageDownloadHelper.OnImageDownloadListener imageDownloadListener;// 图片加载回调接口的对象

    public adapter_around_listviewitem(Context context) {
        this.context = context;
        downloadHelper = new ImageDownloadHelper();
        taskHelper = new AsyncTaskHelper();// 加载网络使用封装好AsyncTask的Helper类

    }
    //更新适配器数据
    public void setDate(List<Good> list){
        this.list = list;
        notifyDataSetChanged();
    }
    // 添加适配器的数据
    public void addData(List<Good> list) {
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
        ViewHolder viewHolder = null;
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.activity_around_listviewitem_two, null);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
            viewHolder.around_listviewitem_iv = (ImageView) convertView.findViewById(R.id.around_listviewitem_iv);
            viewHolder.around_listviewitem_content = (TextView) convertView.findViewById(R.id.around_listviewitem_context);
            viewHolder.around_listviewitem_price = (TextView) convertView.findViewById(R.id.around_listviewitem_price);
            viewHolder.around_listviewitem_sub_iv = (ImageView) convertView.findViewById(R.id.around_listviewitem_sub_iv);
            viewHolder.around_listviewitem_amount = (TextView) convertView.findViewById(R.id.around_listviewitem_amount);
            viewHolder.around_listviewitem_quantity = (TextView) convertView.findViewById(R.id.around_listviewitem_quantity);
            viewHolder.around_listviewitem_add_iv = (ImageView) convertView.findViewById(R.id.around_listviewitem_add_iv);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        setText(position, viewHolder);
        setImage(position, viewHolder);// 加载图片
        final ViewHolder finalViewHolder = viewHolder;

        viewHolder.around_listviewitem_add_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             int num=Integer.parseInt(finalViewHolder.around_listviewitem_amount.getText().toString());
                num++;
                Good good = list.get(position);
                good.setQuantity(good.getQuantity()-1);
                Map map = new HashMap();
                map.put("id",good.getId());
                map.put("price",good.getPrice());
                map.put("quantity",good.getQuantity());
                map.put("icon",good.getIcon());
                map.put("content",good.getContent());
                map.put("seller",good.getSeller());
                String str = JSON.toJSONString(map);
                try {
                    Log.e("------updateGood",str);
                    String URL = new MyConfig("GoodServlet","updateGood").getURL() + StringUtil.strEncode(str);
                    downloadListener = new AsyncTaskHelper.OnDataDownloadListener() {
                        @Override
                        public void onDataDownload(byte[] result) {
                            // 将结果转换成Json字符串
                            String jsonString = new String(result);
                            Log.e("aaaaaaaaaaaaaa",jsonString);
                            // 将Json字符串转换为Map对象
                            Map<String, Object> map = FastJsonTools.getMap(jsonString);
                            String msg = map.get("msg").toString();
                            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
                            Log.e("bbbbbbbbbbbbbbbbbbbb",msg);
                        }
                    };
                    taskHelper.downloadData(context,URL, downloadListener);
                    Log.e("------updateGood",URL);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finalViewHolder.around_listviewitem_amount.setText(num+"");

            }
        });
        viewHolder.around_listviewitem_sub_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num=Integer.parseInt(finalViewHolder.around_listviewitem_amount.getText().toString());
                if(num > 0) {
                    num--;
                    Good good = list.get(position);
                    good.setQuantity(good.getQuantity()+1);
                    Map map = new HashMap();
                    map.put("id",good.getId());
                    map.put("price",good.getPrice());
                    map.put("quantity",good.getQuantity());
                    map.put("icon",good.getIcon());
                    map.put("content",good.getContent());
                    map.put("seller",good.getSeller());
                    String str = JSON.toJSONString(map);
                    try {
                        Log.e("------updateGood",str);
                        String URL = new MyConfig("GoodServlet","updateGood").getURL() + StringUtil.strEncode(str);
                        downloadListener = new AsyncTaskHelper.OnDataDownloadListener() {
                            @Override
                            public void onDataDownload(byte[] result) {
                                // 将结果转换成Json字符串
                                String jsonString = new String(result);
                                Log.e("aaaaaaaaaaaaaa",jsonString);
                                // 将Json字符串转换为Map对象
                                Map<String, Object> map = FastJsonTools.getMap(jsonString);
                                String msg = map.get("msg").toString();
                                Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
                                Log.e("bbbbbbbbbbbbbbbbbbbb",msg);
                            }
                        };
                        taskHelper.downloadData(context,URL, downloadListener);
                        Log.e("------updateGood",URL);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finalViewHolder.around_listviewitem_amount.setText(num+"");


                }
                finalViewHolder.around_listviewitem_amount.setText(num+"");
            }
        });
        int num = Integer.parseInt(finalViewHolder.around_listviewitem_amount.getText().toString());
        if(num != 0){
            String price = viewHolder.around_listviewitem_price.getText().toString();
            String amount = viewHolder.around_listviewitem_amount.getText().toString();
        }
        return convertView;

    }

    private void setImage(int position, final ViewHolder viewHolder) {
        final String icon = list.get(position).getIcon();
        viewHolder.around_listviewitem_iv.setTag(icon);// 将待加载的图片地址和显示图片的控件进行绑定，防止图片显示错位
        viewHolder.around_listviewitem_iv.setImageResource(R.drawable.placeholder);// 设置默认图片
        // 接口回调，将加载的bitmap图片返回
        imageDownloadListener = new ImageDownloadHelper.OnImageDownloadListener(){

            @Override
            public void onImageDownload(Bitmap bitmap, String icon) {
                if (bitmap != null && icon.equals(viewHolder.around_listviewitem_iv.getTag())) {
                    viewHolder.around_listviewitem_iv.setImageBitmap(bitmap);
                    notifyDataSetChanged();
                }
            }
        };
        // 加载图片
        // @imageUrl:图片的地址
        // @holderView.iv:显示图片的控件变量
        // @imageDownloadListener:回调加载结果的接口对象
        downloadHelper.myDownloadImage(context, icon, viewHolder.around_listviewitem_iv,
                imageDownloadListener);
    }


    private void setText(int position, ViewHolder viewHolder) {//从数据库只提取商品的描述和价格
        viewHolder.around_listviewitem_content.setText(list.get(position).getContent());
        viewHolder.around_listviewitem_price.setText(list.get(position).getPrice() + "");
        viewHolder.around_listviewitem_quantity.setText(list.get(position).getQuantity() + "");
    }

    static class ViewHolder{
        public ImageView around_listviewitem_iv;
        public TextView around_listviewitem_content;
        public TextView around_listviewitem_price;
        public ImageView around_listviewitem_sub_iv;
        public TextView around_listviewitem_quantity;
        public TextView around_listviewitem_amount;
        public ImageView around_listviewitem_add_iv;
    }
}
