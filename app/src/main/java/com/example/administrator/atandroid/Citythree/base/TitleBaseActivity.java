package com.example.administrator.atandroid.Citythree.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.atandroid.R;


public abstract class TitleBaseActivity extends BaseActivity {

    //back arrow
    private View mArrowBack;
    //title
    private TextView mTxtTitle;

    private View.OnClickListener mDefaultClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_base);

        //back arrow
        mArrowBack = findViewById(R.id.arrow_title);
        mArrowBack.setOnClickListener(mDefaultClickListener);
        //title
        mTxtTitle = (TextView) findViewById(R.id.text_title);
        //container
        ViewGroup container = (ViewGroup) findViewById(R.id.container_base);
        container.addView(getLayoutInflater().inflate(getContentResId(), null));
    }

    public void setCusTitle(String title) {
        mTxtTitle.setText(title);
    }

    public void setCusTitle(int resId) {
        mTxtTitle.setText(resId);
    }

    public void setOnArrowClickListener(View.OnClickListener listener) {
        mArrowBack.setOnClickListener(listener);
    }

    public void hideArrow() {
        if (mArrowBack.getVisibility() == View.VISIBLE) mArrowBack.setVisibility(View.GONE);
    }

    public abstract int getContentResId();
}
