package com.example.administrator.atandroid.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapHelper {
	
	/**
	 * @param data：图片Bitmap对应的字节数组
	 * @param sampleSize：压缩比
	 */
	public static Bitmap compressBitmap(byte[] data) {
		Bitmap bm = null;
		// 对图片进行等比例压缩
		// 生成Bitmap之前先用图片工厂设置属性
		BitmapFactory.Options options = new BitmapFactory.Options();
		// 设置为true表示Bitmap可以为空，只是去测量图片的宽和高
		options.inJustDecodeBounds = true;
		// 不去生成Bitmap图片，只是测量宽和高
		BitmapFactory.decodeByteArray(data, 0, data.length, options);
		options.inJustDecodeBounds = false;
		// 求出图片的实际宽度和实际高度
		int width = options.outWidth;
		int height = options.outHeight;
		// 以手机屏幕的800*480分辨率为标准
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (width > height && width > ww) {// 如果图片宽度较高度大或者大于手机屏幕宽度，则按照宽度比设置缩放
			be = (int) (width / ww);
		} else if (width < height && height > hh) {// 如果图片高度较宽度大或者大于手机屏幕高度，则按照高度比设置缩放
			be = (int) (height / hh);
		}
		if (be <= 0) // 如果计算后缩放比反而小于0，说明图片分辨率较低，这时不缩放
			be = 1;
		options.inSampleSize = be;// 配置缩放比例
		bm = BitmapFactory.decodeByteArray(data, 0, data.length, options);
		return bm;
	}

}
