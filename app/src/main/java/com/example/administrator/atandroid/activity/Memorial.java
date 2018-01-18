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

public class Memorial extends AppCompatActivity implements View.OnClickListener{

    private ImageView rip,wish,memorial_back;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_memorial);
        init();
    }

    private void init() {
        rip = (ImageView) findViewById(R.id.rip);
        wish = (ImageView) findViewById(R.id.wish);
        memorial_back = (ImageView) findViewById(R.id.memorial_back);
        rip.setOnClickListener(this);
        wish.setOnClickListener(this);
        memorial_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rip: {
                Intent intent1 = new Intent();
                intent1.setClass(this, Memorial_rip.class);
                startActivity(intent1);
                this.finish();
            }
            break;
            case R.id.wish: {
                Intent intent1 = new Intent();
                intent1.setClass(this, Memorial_wish.class);
                startActivity(intent1);
                this.finish();
            }
            break;
            case R.id.memorial_back: {
                Intent intent1 = new Intent();
                intent1.setClass(this,MainActivity.class);
                startActivity(intent1);
                this.finish();
            }
            break;
        }
    }
}
