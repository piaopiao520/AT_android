package com.example.administrator.atandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.atandroid.R;

import java.util.List;

import com.example.administrator.atandroid.bean.Good;

/**
 * Created by Administrator on 2017/5/22.
 */

public class adapter_around_jiesuan extends BaseAdapter {
    private Context context;
    private List<Good> list;

    public adapter_around_jiesuan(Context context) {
        this.context = context;
    }
    //更新适配器数据
    public void setDate(List<Good> list){
        this.list = list;
        notifyDataSetChanged();
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
        ViewHolder viewHolder = null;
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.activity_around_jiesuanitem, null);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
            viewHolder.around_jiesuan_content = (TextView) convertView.findViewById(R.id.around_jiesuan_title);
            viewHolder.around_jiesuan_amount = (TextView) convertView.findViewById(R.id.around_jiesuan_amount);
            viewHolder.around_jiesuan_price = (TextView) convertView.findViewById(R.id.around_jiesuan_price);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.around_jiesuan_content.setText(list.get(position).getContent());
        viewHolder.around_jiesuan_price.setText(list.get(position).getPrice()+"");
        viewHolder.around_jiesuan_amount.setText(list.get(position).getQuantity()+"");
        return convertView;
    }

    static class ViewHolder{
        public TextView around_jiesuan_content,around_jiesuan_amount,around_jiesuan_price;

    }
}
