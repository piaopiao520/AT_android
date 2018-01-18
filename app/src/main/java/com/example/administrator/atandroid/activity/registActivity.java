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

import java.util.HashMap;
import java.util.Map;

import com.example.administrator.atandroid.util.AsyncTaskHelper;
import com.example.administrator.atandroid.util.AsyncTaskHelper.OnDataDownloadListener;
import com.example.administrator.atandroid.util.FastJsonTools;
import com.example.administrator.atandroid.util.MyConfig;
import com.example.administrator.atandroid.util.StringUtil;

/**
 * Created by Administrator on 2017/5/17.
 */

public class registActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_rg_regist;
    public static String URL;
    private TextView tv_rg_userId, tv_rg_userPW, tv_rg_userPW2;
    private String rg_UserId, rg_UserPW, rg_UserPW2;
    private OnDataDownloadListener downloadListener;
    private AsyncTaskHelper taskHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_regist);
        init();
    }

    private void init() {
        taskHelper = new AsyncTaskHelper();
        bt_rg_regist = ((Button) findViewById(R.id.bt_rg_regist));
        tv_rg_userId = ((TextView) findViewById(R.id.tv_rg_userId));
        tv_rg_userPW = ((TextView) findViewById(R.id.tv_rg_userPW));
        tv_rg_userPW2 = ((TextView) findViewById(R.id.tv_rg_userPW2));
        bt_rg_regist.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        user_regist();

    }

    private void user_regist() {
        rg_UserId = tv_rg_userId.getText().toString();
        rg_UserPW = tv_rg_userPW.getText().toString();
        rg_UserPW2 = tv_rg_userPW2.getText().toString();
        if (rg_UserId.equals("") || rg_UserPW.equals("")
                || rg_UserPW2.equals("")) {
            Toast.makeText(this, "账号和密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!rg_UserPW.equals(rg_UserPW2)) {
            Toast.makeText(this, "两次密码不同", Toast.LENGTH_SHORT).show();
            return;
        }

        MyConfig config = new MyConfig("UserServlet", "addUser");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", rg_UserId);
        map.put("password", rg_UserPW);
        map.put("address", "");
        map.put("virtual_currency", 0);
        map.put("name", "");
        map.put("sex", "");
        map.put("icon", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1846948884,880298315&fm=26&gp=0.jpg");
        map.put("volunteer", 0);
        String str = JSON.toJSONString(map);
        try {
            URL = config.getURL() + StringUtil.strEncode(str);

        } catch (Exception e) {
            e.printStackTrace();
        }

        downloadListener = new OnDataDownloadListener() { // 访问服务器后获取返回数据

            @Override
            public void onDataDownload(byte[] result) {
                String jsonString = new String(result); // 返回的字节数组转换为字符串
                Map<String, Object> map = new HashMap<String, Object>();
                map = FastJsonTools.getMap(jsonString);
                String code = map.get("code").toString();
                String msg = map.get("msg").toString();
                if (code.equals("1")) {
                    Toast.makeText(registActivity.this, "注册成功！",
                            Toast.LENGTH_SHORT).show();

                } else if (code.equals("0")) {
                    Toast.makeText(registActivity.this, "注册失败！",
                            Toast.LENGTH_SHORT).show();
                }
            }
        };
        taskHelper.downloadData(this, URL,
                downloadListener); // 发起服务器的访问


        Log.e("zhucezzzzzzzzzzzzzzz",URL);
        Intent intent2 = new Intent(registActivity.this, login.class);
        startActivity(intent2);
        this.finish();
    }


}

