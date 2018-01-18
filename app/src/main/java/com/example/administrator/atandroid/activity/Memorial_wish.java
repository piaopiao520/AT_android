package com.example.administrator.atandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.atandroid.R;

/**
 * Created by Administrator on 2017/5/6.
 */

public class Memorial_wish extends AppCompatActivity implements View.OnClickListener{
    private String info = "3";

    private ImageView memorial_wish_back,memorial_wish_next;
   private ImageView memorial_wish_retake,memorial_wish_around,memorial_wish_services;
    private TextView memorial_wishin;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorial_wish);
        init();
    }

    private void init() {
        memorial_wish_back = (ImageView) findViewById(R.id.memorial_wish_back);
        memorial_wish_next = (ImageView) findViewById(R.id.memorial_wish_next);
        memorial_wish_retake = (ImageView) findViewById(R.id.memorial_wish_retake);
        memorial_wish_around = (ImageView) findViewById(R.id.memorial_wish_around);
        memorial_wish_services = (ImageView) findViewById(R.id.memorial_wish_services);
        memorial_wishin = (TextView) findViewById(R.id.memorial_wishin);
        memorial_wish_back.setOnClickListener(this);
        memorial_wish_next.setOnClickListener(this);
        memorial_wish_retake.setOnClickListener(this);
        memorial_wish_around.setOnClickListener(this);
        memorial_wish_services.setOnClickListener(this);
        memorial_wishin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.memorial_wish_back: {
                Intent intent1 = new Intent();
                intent1.setClass(this, Memorial.class);
                startActivity(intent1);
            }
            break;
            case R.id.memorial_wish_next: {
                Intent intent1 = new Intent();
                intent1.setClass(this, Memorial_rip.class);
                startActivity(intent1);
            }
            break;
            case R.id.memorial_wish_retake: {
                Intent intent1 = new Intent();
                intent1.setClass(this, Memorial_wish_retake.class);
                startActivity(intent1);
            }
            break;
            case R.id.memorial_wishin: {
                Intent intent2 = new Intent();
                intent2.setClass(this, Memorial_wish_retake.class);
                startActivity(intent2);
            }
            break;
            case R.id.memorial_wish_around: {
                Intent intent3 = new Intent();
                intent3.setClass(this, Memorial_wish_around.class);
                startActivity(intent3);
            }
            break;
            case R.id.memorial_wish_services: {
                Intent intent4 = new Intent();
                intent4.setClass(this, Around.class);
                intent4.putExtra("info",info);
                startActivity(intent4);
            }
            break;
        }

    }
}
