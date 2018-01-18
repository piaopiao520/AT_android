package com.example.administrator.atandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.atandroid.R;

/**
 * Created by Administrator on 2017/5/6.
 */

public class Mine extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout showtime,around;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_mine);
        init();

    }

    private void init() {
        showtime = (LinearLayout) findViewById(R.id.mine_showtime);
        showtime.setOnClickListener(this);
        around = (LinearLayout) findViewById(R.id.mine_around);
        around.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_showtime: {
                Intent intent1 = new Intent();
                intent1.setClass(this, Mine_showtime.class);
                startActivity(intent1);
            }
            break;
            case R.id.mine_around: {
                Intent intent1 = new Intent();
                intent1.setClass(this, Mine_around.class);
                startActivity(intent1);
            }
            break;
        }
    }
}
