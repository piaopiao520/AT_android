package com.example.administrator.atandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.atandroid.R;

/**
 * Created by Administrator on 2017/5/6.
 */

public class Charity_project_genzong extends AppCompatActivity implements View.OnClickListener {
    private ImageView rip,gengzong_back_to_xiangqing;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_project_genzong);
        init();

    }

    private void init() {
        gengzong_back_to_xiangqing = ((ImageView) findViewById(R.id.gengzong_back_to_xiangqing));
        gengzong_back_to_xiangqing.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.setClass(this,Charity_information_listviewitem.class);
        this.finish();
    }
}
