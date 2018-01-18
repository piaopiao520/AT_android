package com.example.administrator.atandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.atandroid.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.administrator.atandroid.adapter.adapter_gohome_main;
import com.example.administrator.atandroid.bean.MyConfig;
import com.example.administrator.atandroid.bean.PetBack;
import com.example.administrator.atandroid.util.FastJsonTools;

/**
 * Created by Administrator on 2017/5/6.
 */

public class Gohome_main extends AppCompatActivity implements View.OnClickListener{

    private ListView listView;
    List<PetBack> list ;
    private adapter_gohome_main adapter_gohome_main;
    private ImageView gohome_sent,gohome_main_back;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gohome_main);
        init();
        parseJson();

    }

    private void parseJson() {
        String jsonString = MyConfig.petback;
        // 解析Json，将商品列表获取存入list集合
        list = new ArrayList<PetBack>();
        // 将Json字符串转换为Map对象
        Map<String,Object> map = FastJsonTools.getMap(jsonString);
        String petback = map.get("PetBack").toString();
        Log.e("aaaaaaaaaaaaaaaaaaa",petback);
        list = FastJsonTools.getBeans(petback,PetBack.class);
        adapter_gohome_main.setData(list); // 更新适配器的数据
    }

    private void init() {
        listView =(ListView) findViewById(R.id.gohome_main_listview);

        gohome_sent = (ImageView) findViewById(R.id.gohome_sent);
        gohome_main_back = (ImageView) findViewById(R.id.gohome_main_back);
        gohome_sent.setOnClickListener(this);
        gohome_main_back.setOnClickListener(this);
        adapter_gohome_main = new adapter_gohome_main(this);
        listView.setAdapter(adapter_gohome_main);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gohome_main_back:
                Intent intent = new Intent();
                intent.setClass(this, MainActivity.class);
                startActivity(intent);
                this.finish();
                break;
            case R.id.gohome_sent:
                Intent intent1 = new Intent();
                intent1.setClass(this, Gohome_sent.class);
                startActivity(intent1);
                this.finish();
            break;
        }
    }
}
