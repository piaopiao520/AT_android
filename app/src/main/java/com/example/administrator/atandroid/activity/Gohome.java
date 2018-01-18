package com.example.administrator.atandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.administrator.atandroid.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/5/6.
 */

public class Gohome extends AppCompatActivity {

    private ImageView rip;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_gohome);
        final Intent localIntent = new Intent(this, Gohome_main.class);
        Timer timer = new Timer();
        TimerTask tast = new TimerTask() {
            @Override
            public void run() {
                startActivity(localIntent);
            }
        };
        timer.schedule(tast, 1500);
    }


}
