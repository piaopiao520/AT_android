package com.example.administrator.atandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.atandroid.R;

/**
 * Created by Administrator on 2017/5/6.
 */

public class Gohome_sent extends AppCompatActivity implements View.OnClickListener{

    private ImageView gohome_sent_back;
    private Button gohome_sentup;
    private Button gohome_sent_lingyang,gohome_sent_xunhui,gohome_sent_tuoguan;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_gohome_sent);
        init();

    }

    private void init() {
        gohome_sentup = (Button) findViewById(R.id.gohome_sentup);
        gohome_sent_back = (ImageView) findViewById(R.id.gohome_sent_back);
        gohome_sentup.setOnClickListener(this);
        gohome_sent_back.setOnClickListener(this);

        gohome_sent_lingyang = (Button) findViewById(R.id.gohome_sent_lingyang);
        gohome_sent_xunhui = (Button) findViewById(R.id.gohome_sent_xunhui);
        gohome_sent_tuoguan = (Button) findViewById(R.id.gohome_sent_tuoguan);
        gohome_sent_lingyang.setOnClickListener(this);
        gohome_sent_xunhui.setOnClickListener(this);
        gohome_sent_tuoguan.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gohome_sentup:
                Intent intent = new Intent();
                intent.setClass(this, Gohome_sentup.class);
                startActivity(intent);
                this.finish();
            break;
            case R.id.gohome_sent_back:
                Intent intent1 = new Intent();
                intent1.setClass(this, Gohome_main.class);
                startActivity(intent1);
                this.finish();
                break;
            case R.id.gohome_sent_lingyang:
                gohome_sent_lingyang.setBackgroundResource(R.color.colorPrimary);
                gohome_sent_lingyang.setTextColor(getResources().getColor(R.color.white));
                gohome_sent_xunhui.setBackgroundResource(R.drawable.login_bg);
                gohome_sent_xunhui.setTextColor(getResources().getColor(R.color.black));
                gohome_sent_tuoguan.setBackgroundResource(R.drawable.login_bg);
                gohome_sent_tuoguan.setTextColor(getResources().getColor(R.color.black));
                break;
            case R.id.gohome_sent_xunhui:
                gohome_sent_xunhui.setBackgroundResource(R.color.buttonRed);
                gohome_sent_xunhui.setTextColor(getResources().getColor(R.color.white));
                gohome_sent_tuoguan.setBackgroundResource(R.drawable.login_bg);
                gohome_sent_tuoguan.setTextColor(getResources().getColor(R.color.black));
                gohome_sent_lingyang.setBackgroundResource(R.drawable.login_bg);
                gohome_sent_lingyang.setTextColor(getResources().getColor(R.color.black));
                break;
            case R.id.gohome_sent_tuoguan:
                gohome_sent_tuoguan.setBackgroundResource(R.color.green);
                gohome_sent_xunhui.setTextColor(getResources().getColor(R.color.white));
                gohome_sent_tuoguan.setBackgroundResource(R.drawable.login_bg);
                gohome_sent_tuoguan.setTextColor(getResources().getColor(R.color.black));
                gohome_sent_lingyang.setBackgroundResource(R.drawable.login_bg);
                gohome_sent_lingyang.setTextColor(getResources().getColor(R.color.black));
                break;
        }
    }
}
