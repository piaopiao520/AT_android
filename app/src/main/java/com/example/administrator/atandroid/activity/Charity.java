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

public class Charity extends AppCompatActivity implements View.OnClickListener{

    private ImageView charity_project,charity_information,charity_back_to_Main;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_charity);
        init();
    }

    private void init() {
        charity_information = (ImageView) findViewById(R.id.charity_information);
        charity_project = (ImageView) findViewById(R.id.charity_project);
        charity_back_to_Main = (ImageView) findViewById(R.id.charity_back_to_Main);
        charity_back_to_Main.setOnClickListener(this);
        charity_information.setOnClickListener(this);
        charity_project.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.charity_project:
                Intent intent1 = new Intent();
                intent1.setClass(this, Charity_project.class);
                startActivity(intent1);
            break;
            case R.id.charity_information:
                Intent intent2 = new Intent();
                intent2.setClass(this, Charity_information_Activity.class);
                startActivity(intent2);
            break;
            case R.id.charity_back_to_Main:
                Intent intent3 = new Intent();
                intent3.setClass(this,MainActivity.class);
                startActivity(intent3);
                this.finish();
                break;
        }

    }
}
