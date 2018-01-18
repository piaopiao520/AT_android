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

public class Around extends AppCompatActivity implements View.OnClickListener{

    private ImageView petshop,IV_back_around,petFuneral,petConsign,petHospital,petBoard,petBeauty,merchant_entering;
    private String aroundInfo;
    private static String info;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_around);
        init();
    }

    private void init() {
        info = this.getIntent().getStringExtra("info");
        petshop = (ImageView) findViewById(R.id.around_petshop);
        IV_back_around = (ImageView) findViewById(R.id.IV_back_around);
        petFuneral = (ImageView) findViewById(R.id.around_petFuneral);
        petConsign = (ImageView) findViewById(R.id.around_petConsign);
        petHospital = (ImageView) findViewById(R.id.around_petHospital);
        petBoard = (ImageView) findViewById(R.id.around_petBoard);
        petBeauty = (ImageView) findViewById(R.id.around_petBeauty);
        merchant_entering = (ImageView) findViewById(R.id.merchant_entering);
        petshop.setOnClickListener(this);
        IV_back_around.setOnClickListener(this);
        petFuneral.setOnClickListener(this);
        petConsign.setOnClickListener(this);
        petHospital.setOnClickListener(this);
        petBoard.setOnClickListener(this);
        petBeauty.setOnClickListener(this);
        merchant_entering.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.around_petshop:
                Intent intent1 = new Intent();
               aroundInfo = "商店";
                intent1.setClass(this, Around_main.class);
                intent1.putExtra("aroundInfo",aroundInfo);
                startActivity(intent1);
                this.finish();
                break;
            case R.id.around_petFuneral:
                Intent intent2 = new Intent();
                aroundInfo = "殡葬";
                intent2.setClass(this, Around_main.class);
                intent2.putExtra("aroundInfo",aroundInfo);
                startActivity(intent2);
                this.finish();
                break;
            case R.id.around_petConsign:
                Intent intent3 = new Intent();
                aroundInfo = "托运";
                intent3.setClass(this, Around_main.class);
                intent3.putExtra("aroundInfo",aroundInfo);
                startActivity(intent3);
                this.finish();
                break;
            case R.id.around_petHospital:
                Intent intent4 = new Intent();
                aroundInfo = "医院";
                intent4.setClass(this, Around_main.class);
                intent4.putExtra("aroundInfo",aroundInfo);
                startActivity(intent4);
                this.finish();
                break;
            case R.id.around_petBoard:
                Intent intent5 = new Intent();
                aroundInfo = "寄养";
                intent5.setClass(this, Around_main.class);
                intent5.putExtra("aroundInfo",aroundInfo);
                startActivity(intent5);
                this.finish();
                break;
            case R.id.around_petBeauty:
                Intent intent6 = new Intent();
                aroundInfo = "美容";
                intent6.setClass(this, Around_main.class);
                intent6.putExtra("aroundInfo",aroundInfo);
                startActivity(intent6);
                this.finish();
                break;
            case R.id.IV_back_around:
                Intent intent7 = new Intent();
                switch (info){
                    case "1":
                        intent7.setClass(this, MainActivity.class);
                        startActivity(intent7);
                        this.finish();
                        break;
                    case "2":
                        intent7.setClass(this, Memorial_rip.class);
                        startActivity(intent7);
                        this.finish();
                        break;
                    case "3":
                        intent7.setClass(this, Memorial_wish.class);
                        startActivity(intent7);
                        this.finish();
                        break;
                    default:
                        intent7.setClass(this, MainActivity.class);
                        startActivity(intent7);
                        this.finish();
                        break;
                }
                break;
            case R.id.merchant_entering:
                Intent intent8 = new Intent();
                intent8.setClass(this, Around_enter.class);
                startActivity(intent8);
                this.finish();
                break;

        }

    }
}
