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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.atandroid.R;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/5/22.
 */

public class Charity_project_hold_Activity  extends AppCompatActivity implements View.OnClickListener {
private Button charity_projectHold;
    private ImageView IV_back_charityProject,charity_project_iv;
    private static final String IMAGE_UNSPECIFIED = "image/*";
    private final int IMAGE_CODE = 0; // 这里的IMAGE_CODE是自己任意定义的
    private UploadManager uploadManager;
    public static String url;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_project_hold_);
        init();
    }

    private void init() {
        charity_projectHold = ((Button) findViewById(R.id.charity_projectHold));
        IV_back_charityProject = ((ImageView) findViewById(R.id.IV_back_charityProject));
        charity_projectHold.setOnClickListener(this);
        IV_back_charityProject.setOnClickListener(this);
        charity_project_iv = ((ImageView) findViewById(R.id.charity_project_iv));
        charity_project_iv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.IV_back_charityProject:
               Intent intent = new Intent();
               intent.setClass(this,Charity_project.class);
               this.finish();
               break;
           case R.id.charity_projectHold:
               charity_projectHold();
               break;
           case R.id.charity_project_iv:
               getImage();
               break;
       }
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
                charity_project_iv.setImageBitmap(ThumbnailUtils.extractThumbnail(bm,
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
                                Toast.makeText(Charity_project_hold_Activity.this, "上传失败",
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

    private void charity_projectHold() {

        Toast.makeText(this, "提交成功！请耐心等待审核", Toast.LENGTH_SHORT).show();
        Intent intent1 = new Intent();
        intent1.setClass(this,Charity_project.class);
        startActivity(intent1);
    }


}
