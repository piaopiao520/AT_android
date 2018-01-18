package com.example.administrator.atandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.atandroid.R;
import com.example.administrator.atandroid.bean.User;
import com.example.administrator.atandroid.util.AsyncTaskHelper;
import com.example.administrator.atandroid.util.AsyncTaskHelper.OnDataDownloadListener;
import com.example.administrator.atandroid.util.FastJsonTools;
import com.example.administrator.atandroid.util.MyConfig;
import com.example.administrator.atandroid.util.StringUtil;
import com.example.administrator.atandroid.util.UserInfo;

import java.util.HashMap;
import java.util.Map;



public class login extends AppCompatActivity implements View.OnClickListener {
    public static String URL;
    public TextView tv_UserId, tv_UserPW;
    public Button bt_login, bt_regist;
    private String UserId, UserPW;
    private OnDataDownloadListener downloadListener;
    private AsyncTaskHelper taskHelper;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        taskHelper = new AsyncTaskHelper();
        tv_UserId = (TextView) findViewById(R.id.tv_UserId);
        tv_UserPW = (TextView) findViewById(R.id.tv_UserPW);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_regist = (Button) findViewById(R.id.bt_regist);
        bt_login.setOnClickListener(this);
        bt_regist.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                user_login();

                break;
            case R.id.bt_regist:
                user_regist();
                this.finish();
                break;
        }
    }

    private void user_regist() {
        Intent intent2 = new Intent(this, registActivity.class);
        intent2.setClass(this,registActivity.class);
        startActivity(intent2);
    }

    private void user_login() {
        UserId = tv_UserId.getText().toString();
        UserPW = tv_UserPW.getText().toString();
        if (UserPW.equals("") || UserId.equals("")) {
            Toast.makeText(this, "账号密码不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        MyConfig config = new MyConfig("UserServlet", "login");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", UserId);
        map.put("password", UserPW);
        map.put("address", "");
        map.put("virtual_currency", 0);
        map.put("name", "");
        map.put("sex", "");
        map.put("icon", "");
        map.put("volunteer", 0);
        String str = JSON.toJSONString(map);
        try {
            URL = config.getURL() + StringUtil.strEncode(str);
            Log.e("----------------------.",URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        downloadListener = new OnDataDownloadListener() {
            @Override

            public void onDataDownload(byte[] result) {
                String jsonString = new String(result); // 返回的字节数组转换为字符串
                Map<String, Object> map = new HashMap<String, Object>();
                map = FastJsonTools.getMap(jsonString);
                String code = map.get("code").toString();
                String msg = map.get("msg").toString();
                if (code.equals("1")) {
                    Toast.makeText(login.this, "登录成功！",
                            Toast.LENGTH_SHORT).show();
                   String Result =map.get("result").toString();
                    User user = FastJsonTools.getBean(Result,User.class);
                    UserInfo.setId(user.getId());
                    UserInfo.setPassword(user.getPassword());
                    UserInfo.setIsLogin(true);
//                    //通过SharedPreferences 将用户信息封装在util.UserInfo里面
//                    SharedPreferences pref = login.this.getSharedPreferences("com.example.administrator.atandroid.util.UserInfo", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = pref.edit();
//                    editor.putBoolean("IsLogin", true);
//                    editor.commit();


                } else if (code.equals("0")) {
                    Toast.makeText(login.this, "登录失败！",
                            Toast.LENGTH_SHORT).show();
                }
            }
        };
        taskHelper.downloadData(this,URL,downloadListener);
        Intent intent = new Intent(login.this, MainActivity.class);
        startActivity(intent);
        this.finish();

    }


}
