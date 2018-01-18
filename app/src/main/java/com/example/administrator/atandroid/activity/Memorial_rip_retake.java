package com.example.administrator.atandroid.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.atandroid.R;
import com.example.administrator.atandroid.bean.Gravestone;
import com.example.administrator.atandroid.bean.MyConfig;
import com.example.administrator.atandroid.util.AsyncTaskHelper;
import com.example.administrator.atandroid.util.FastJsonTools;
import com.example.administrator.atandroid.util.ImageDownloadHelper;
import com.example.administrator.atandroid.util.StringUtil;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/23.
 */

public class Memorial_rip_retake extends AppCompatActivity implements View.OnClickListener{

    private ImageView memorial_rip_retake_back,memorial_rip_retake_iv;
    private EditText memorial_rip_retake_et;
    private Button memorial_rip_set;
    private UploadManager uploadManager;
    public static String url;
    public static String URL;
    List<Gravestone> list ;
    MyConfig myConfig;
    private static final String IMAGE_UNSPECIFIED = "image/*";
    private final int IMAGE_CODE = 0; // 这里的IMAGE_CODE是自己任意定义的

    private ImageDownloadHelper downloadHelper;// 图片加载对象
    private ImageDownloadHelper.OnImageDownloadListener imageDownloadListener;// 图片加载回调接口的对象
    private AsyncTaskHelper taskHelper;// 网络加载对象
    private AsyncTaskHelper.OnDataDownloadListener downloadListener;// 接口回调对象


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorial_ripretake);
        init();
    }

    private void init() {
        downloadHelper = new ImageDownloadHelper();

        taskHelper = new AsyncTaskHelper();// 加载网络使用封装好AsyncTask的Helper类
        memorial_rip_retake_back = (ImageView) findViewById(R.id.memorial_rip_retake_back);
        memorial_rip_set = (Button) findViewById(R.id.memorial_rip_set);
        memorial_rip_retake_back.setOnClickListener(this);
        memorial_rip_set.setOnClickListener(this);
        memorial_rip_retake_iv = (ImageView) findViewById(R.id.memorial_rip_retake_iv);
        memorial_rip_retake_et = (EditText) findViewById(R.id.memorial_rip_retake_et);
        memorial_rip_retake_iv.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.memorial_rip_retake_back:
                Intent intent = new Intent();
                intent.setClass(this,Memorial_rip.class);
                startActivity(intent);
                this.finish();
                break;
            case R.id.memorial_rip_retake_iv:
                getImage();
                break;
            case R.id.memorial_rip_set:
                setData();//传数据
                Intent intent1 = new Intent();
                intent1.setClass(this,Memorial_rip.class);
                startActivity(intent1);
                this.finish();
                break;

        }
    }

    private void setData() {
        myConfig = new MyConfig("GravestoneServlet","updateGravestone");

//获取内容
        String content = memorial_rip_retake_et.getText().toString();
        // 获取发送时间
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String releaseTime = sdf.format(date);
        // 获取图片路径
        String icon = url;
        Map<String, Object> map1 = new LinkedHashMap<String, Object>();
        map1.put("id",1);
        map1.put("content",content);
        map1.put("time",releaseTime);
        map1.put("icon",icon);
        map1.put("user","123");
        String str = JSON.toJSONString(map1);
        Log.e("ccccccccccc",str);
        try {
            URL= myConfig.getURL()+ StringUtil.strEncode(str);
            Log.e("-----------------------",URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        downloadListener = new AsyncTaskHelper.OnDataDownloadListener() {
            @Override
            public void onDataDownload(byte[] result) {
                // 将结果转换成Json字符串
                String jsonString = new String(result);
                Log.e("aaaaaaaaaaaaaa",jsonString);
                // 将Json字符串转换为Map对象
                Map<String, Object> map = new HashMap<>();
                map = FastJsonTools.getMap(jsonString);
                String result1 = map.get("result").toString();
            }
        };
        taskHelper.downloadData(this,URL, downloadListener);
    }

    private void getImage() {
        Intent getAlbum = new Intent(Intent.ACTION_PICK);

        getAlbum.setType(IMAGE_UNSPECIFIED);

        startActivityForResult(getAlbum, IMAGE_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TODO Auto-generated method stub
Log.e("sssssssssssssssss",requestCode+"");
Log.e("sssssssssssssssss",resultCode+"");
Log.e("sssssssssssssssss",IMAGE_CODE+"");
        Bitmap bm = null;

        // 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口

        ContentResolver resolver = getContentResolver();

        if (requestCode == IMAGE_CODE) {

            try {

                Uri originalUri = data.getData(); // 获得图片的uri

                bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);


                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                System.out.println(byteArray);
                memorial_rip_retake_iv.setImageBitmap(ThumbnailUtils.extractThumbnail(bm,
                        100, 100)); // 使用系统的一个工具类，参数列表为 Bitmap Width,Height
                // 这里使用压缩后显示，否则在华为手机上ImageView 没有显示
                // 显得到bitmap图片
                // imageView.setImageBitmap(bm);

                String[] proj = { MediaStore.Images.Media.DATA };

                // 好像是android多媒体数据库的封装接口，具体的看Android文档
                Cursor cursor = managedQuery(originalUri, proj, null, null,
                        null);

                // 按我个人理解 这个是获得用户选择的图片的索引值
                int column_index = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                // 将光标移至开头 ，这个很重要，不小心很容易引起越界
                cursor.moveToFirst();
                // 最后根据索引值获取图片路径
                String path = cursor.getString(column_index);
              //  tv.setText(path);
                uploadManager = new UploadManager();
                String key = null;
                // token是每个注册账号可以建立多个，用“QiNiuGenertorToken”这个项目去生成自己的token
                String token = "6WhBXBouf6QghARZBLJiPaEgV3dclqjbMHwdJ15R:jRELqCfbbhaY8R0sIVVAWtiqclo=:eyJzY29wZSI6Ind1eHVhbmJhY2tldCIsImRlYWRsaW5lIjozMjg0NTg4MTY3fQ==";

                uploadManager.put(byteArray, key, token, new UpCompletionHandler(){

                    @Override
                    public void complete(String s, ResponseInfo responseInfo,
                                         JSONObject jsonObject) {
                        // TODO Auto-generated method stub
                        System.out.println("complete");
                        if (responseInfo.statusCode == 200){
                            String headerUrl = "";
                            try {
                                headerUrl = jsonObject.getString("key");// 得到回传的头像url
                                System.out.println(headerUrl);
                                url = "http://oibc7l5du.bkt.clouddn.com/"+headerUrl;
                                Log.e("eeeeeeeeeeeeeeeeeeee",url);
                                //img_url.setText(url);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (headerUrl != null && !headerUrl.equals("")) {
                                //updateHeader(); // 修改头像
                            } else {
                                Toast.makeText(Memorial_rip_retake.this, "上传失败",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }


                }, null);

            } catch (IOException e) {
                Log.e("TAG-->Error", e.toString());

            }

            finally {
                return;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }


}
