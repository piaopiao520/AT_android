package com.example.administrator.atandroid.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class CompressUtils {

	public static byte[] compressImage(byte[] data, String imageType) {
		while(data.length / 1024 > 300) { // 判断如果压缩后图片大�?00k进行按比例压�?
			Bitmap bitmap = compressBitmap(data);
			data = CommentUtil.BitmapToBytes(bitmap,imageType);
		}
		return data;
	}

	/**
	 * 
	 * @param data
	 *            ：图片Bitmap对应的字节数�?
	 * @param sampleSize
	 *            ：压缩比
	 * @return
	 */
	private static Bitmap compressBitmap(byte[] data) {
		Bitmap bm;
		// 对图片进行等比例压缩
		// 生成Bitmap之前先用图片工厂做一些配�?
		BitmapFactory.Options options = new BitmapFactory.Options();
		// 设置为true表示Bitmap可以为空，只是去测量图片的宽和高
		options.inJustDecodeBounds = true;
		// 不去生成Bitmap图片，只是测量宽和高
		BitmapFactory.decodeByteArray(data, 0, data.length, options);
		options.inJustDecodeBounds = false;
		int w = options.outWidth;
		int h = options.outHeight;
		// 现在主流手机比较多是800*480分辨率，�?��高和宽我们设置为
		float hh = 800f;// 这里设置高度�?00f
		float ww = 480f;// 这里设置宽度�?80f
		// 缩放比�?由于是固定比例缩放，只用高或者宽其中�?��数据进行计算即可
		int sampleSize = 1;// sampleSize=1表示不缩�?
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩�?
			sampleSize = (int) (options.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据高度固定大小缩�?
			sampleSize = (int) (options.outHeight / hh);
		}
		if (sampleSize <= 1)
			sampleSize = 2; //默认压缩�??
		// 设置压缩比例，比如设置为4表示�?M --> 1M
		options.inSampleSize = sampleSize;
		// inJustDecodeBounds已经设置为false，此时会生成Bitmap
		bm = BitmapFactory.decodeByteArray(data, 0, data.length, options);
		return bm;
	}
	
}
