package com.example.administrator.atandroid.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.atandroid.R;

import java.util.ArrayList;
import java.util.List;

import com.example.administrator.atandroid.bean.PetBack;
import com.example.administrator.atandroid.util.FastJsonTools;
import com.example.administrator.atandroid.util.ImageDownloadHelper;

/**
 * Created by Administrator on 2017/5/6.
 */

public class Gohome_listviewitem extends AppCompatActivity implements View.OnClickListener{

    private Button gohome_phone;
    private String phonemunber;
    private ImageView gohome_listviewitem_back;
    private ImageView gohome_listviewitem_iv,gohome_listviewitem_iv1,gohome_listviewitem_iv2,gohome_listviewitem_iv3;
    private TextView gohome_listviewitem_name,gohome_listviewitem_time,gohome_listviewitem_describe,gohome_lisviewitem_kind,gohome_lisviewitem_sex,gohome_lisviewitem_age,gohome_lisviewitem_address,gohome_listviewitem_money;
    PetBack petback;
    private List<ImageView> iv = new ArrayList<>();
    int k=0;
    private ImageDownloadHelper downloadHelper;// 图片加载对象
    private ImageDownloadHelper.OnImageDownloadListener imageDownloadListener;// 图片加载回调接口的对象
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_gohome_listviewitem);
        init();
        showData();
    }

    private void showData() {
        Bundle bundle = this.getIntent().getExtras();
        String gohome = bundle.getString("gohome");
        petback = FastJsonTools.getBean(gohome, PetBack.class);
        gohome_listviewitem_name.setText(petback.getName());
        gohome_listviewitem_time.setText(petback.getTime());
        gohome_listviewitem_describe.setText(petback.getDescribe());
        gohome_lisviewitem_kind.setText(petback.getKind());
        gohome_lisviewitem_sex.setText(petback.getSex());
        gohome_lisviewitem_age.setText(petback.getAge());
        gohome_lisviewitem_address.setText(petback.getAddress());
        gohome_listviewitem_money.setText(petback.getMoney()+"");
        phonemunber =petback.getPhone();
        String icon = petback.getIcon();
        gohome_listviewitem_iv.setTag(icon);
        imageDownloadListener = new ImageDownloadHelper.OnImageDownloadListener() {

            @Override
            public void onImageDownload(Bitmap bitmap, String imgUrl) {
                if (bitmap != null) {
                    gohome_listviewitem_iv.setImageBitmap(bitmap);
                }

            }
        };
        downloadHelper.myDownloadImage(this, icon, gohome_listviewitem_iv, imageDownloadListener);

        String image = petback.getImage();
        String[] strs = image.split(",");
        for (int i = 0; i < strs.length; i++) {
            iv.get(i).setTag(strs[k]);
            imageDownloadListener = new ImageDownloadHelper.OnImageDownloadListener() {

                @Override
                public void onImageDownload(Bitmap bitmap, String imgUrl) {
                    if (bitmap != null) {
                        iv.get(k).setImageBitmap(bitmap);

                    }

                }
            };

            downloadHelper.myDownloadImage(this, strs[k], iv.get(k), imageDownloadListener);
            k++;
        }
    }

    private void init() {
        downloadHelper = new ImageDownloadHelper();
        gohome_listviewitem_back = (ImageView)findViewById(R.id.gohome_listviewitem_back);
        gohome_listviewitem_back.setOnClickListener(this);
        gohome_listviewitem_iv = (ImageView)findViewById(R.id.gohome_listviewitem_iv);
        gohome_listviewitem_iv1 = (ImageView)findViewById(R.id.gohome_listviewitem_iv1);
        gohome_listviewitem_iv2 = (ImageView)findViewById(R.id.gohome_listviewitem_iv2);
        gohome_listviewitem_iv3 = (ImageView)findViewById(R.id.gohome_listviewitem_iv3);

        iv.add(gohome_listviewitem_iv1);
        iv.add(gohome_listviewitem_iv2);
        iv.add(gohome_listviewitem_iv3);
        gohome_listviewitem_name = (TextView) findViewById(R.id.gohome_listviewitem_name);
        gohome_listviewitem_time = (TextView) findViewById(R.id.gohome_listviewitem_time);
        gohome_listviewitem_describe = (TextView) findViewById(R.id.gohome_listviewitem_describe);
        gohome_lisviewitem_kind = (TextView) findViewById(R.id.gohome_lisviewitem_kind);
        gohome_lisviewitem_sex = (TextView) findViewById(R.id.gohome_lisviewitem_sex);
        gohome_lisviewitem_age = (TextView) findViewById(R.id.gohome_lisviewitem_age);
        gohome_lisviewitem_address = (TextView) findViewById(R.id.gohome_lisviewitem_address);
        gohome_listviewitem_money = (TextView) findViewById(R.id.gohome_listviewitem_money);
        gohome_phone = (Button) findViewById(R.id.gohome_phone);
        gohome_phone.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gohome_listviewitem_back:
                Intent intent1 = new Intent();
                intent1.setClass(this, Gohome_main.class);
                startActivity(intent1);
                this.finish();
                break;
            case R.id.gohome_phone:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phonemunber));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
               //this.finish();
                break;
        }
    }
}
