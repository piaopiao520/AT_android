package com.example.administrator.atandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.atandroid.R;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String info = "1";

    private ImageView charity, memorial, around, gohome, mine;
    private RollPagerView mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        rollviewpager();
    }

    private void rollviewpager() {
        mViewPager = (RollPagerView) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new ImageNormalAdapter());
    }

    private class ImageNormalAdapter extends StaticPagerAdapter {
        int[] imgs = new int[]{
                R.drawable.img1,
                R.drawable.img2,
                R.drawable.img3,
                R.drawable.img4,
                R.drawable.img5,
        };

        /**
         * SetScaleType(ImageView.ScaleType.CENTER);
         * 按图片的原来size居中显示，当图片长/宽超过View的长/宽，则截取图片的居中部分显示
         * <p>
         * SetScaleType(ImageView.ScaleType.CENTER_CROP);
         * 按比例扩大图片的size居中显示，使得图片长(宽)等于或大于View的长(宽)
         * <p>
         * setScaleType(ImageView.ScaleType.CENTER_INSIDE);
         * 将图片的内容完整居中显示，通过按比例缩小或原来的size使得图片长/宽等于或小于View的长/宽
         * <p>
         * setScaleType(ImageView.ScaleType.FIT_CENTER);
         * 把图片按比例扩大/缩小到View的宽度，居中显示
         */
        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);// 按比例扩大图片的size居中显示，使得图片长(宽)等于或大于View的长(宽)

            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            view.setImageResource(imgs[position]);
            return view;
        }


        public int getCount() {
            return imgs.length;
        }
    }

    private void init() {
        charity = (ImageView) findViewById(R.id.charity);
        memorial = (ImageView) findViewById(R.id.memorial);
        around = (ImageView) findViewById(R.id.around);
        gohome = (ImageView) findViewById(R.id.gohome);
        mine = (ImageView) findViewById(R.id.mine);
        charity.setOnClickListener(this);
        memorial.setOnClickListener(this);
        around.setOnClickListener(this);
        gohome.setOnClickListener(this);
        mine.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.charity: {
                Intent intent1 = new Intent();
                intent1.setClass(this, Charity.class);
                startActivity(intent1);
            }
            break;
            case R.id.memorial: {
                Intent intent2 = new Intent();
                intent2.setClass(this, Memorial.class);
                startActivity(intent2);
            }
            break;
            case R.id.around: {
                Intent intent3 = new Intent();
                intent3.setClass(this,Around.class);
                intent3.putExtra("info",info);
                startActivity(intent3);
            }
            break;
            case R.id.gohome: {
                Intent intent4 = new Intent();
                intent4.setClass(this, Gohome.class);
                startActivity(intent4);
            }
            break;
            case R.id.mine:
           if(com.example.administrator.atandroid.util.UserInfo.getIsLogin()==true){
                Intent intent1 = new Intent();
                intent1.setClass(this, Mine.class);
                startActivity(intent1);
                }else {
                    Intent intent5 = new Intent();
                    intent5.setClass(this,login.class);
                    startActivity(intent5);
                }

        }
    }
}
