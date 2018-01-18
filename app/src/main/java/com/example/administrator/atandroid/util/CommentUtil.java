package com.example.administrator.atandroid.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@TargetApi(19)
@SuppressLint({ "SimpleDateFormat", "NewApi" })
public class CommentUtil {

	/**
	 * Bitmapת��Ϊ�ֽ�����
	 * @param bitmap
	 * @param imageType
	 * @return
	 */
	public static byte[] BitmapToBytes(Bitmap bitmap, String imageType) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if(imageType.equals("image/jpeg")){
			bitmap.compress(CompressFormat.JPEG, 100, baos);
		}else if(imageType.equals("image/png")){
			bitmap.compress(CompressFormat.PNG, 100, baos);
		}
		return baos.toByteArray();
	}

	/**
	 * ʹ�õ�ǰʱ���ƴ��һ��Ψһ���ļ���
	 * 
	 * @param //format
	 * @return
	 */
	public static String getTempFileName() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSS");
		String fileName = format.format(new Timestamp(System
				.currentTimeMillis()));
		return fileName;
	}

	/**
	 * ����ͼƬ�洢·������ȡ��ǰʱ���ΪͼƬΨһ����
	 * 
	 * @return
	 */
	public static String getImageKey(String imageType) {
		String key = "";
		if (imageType.equals("image/jpeg")) {
			key = "image/" + CommentUtil.getTempFileName() + ".jpg";
		} else if (imageType.equals("image/png")) {
			key = "image/" + CommentUtil.getTempFileName() + ".png";
		}
		return key;
	}

	/**
	 * ��ȡͼƬ��������Ϣ
	 * 
	 * @param bytes
	 *            2~8 byte at beginning of the image file
	 * @return image mimetype or null if the file is not image
	 */
	public static String getImageType(byte[] bytes) {
		if (isJPEG(bytes)) {
			return "image/jpeg";
		}
		if (isPNG(bytes)) {
			return "image/png";
		}
		return null;
	}

	private static boolean isJPEG(byte[] b) {
		if (b.length < 2) {
			return b[0] == (byte) 0xFF;
		}else {
			return (b[0] == (byte) 0xFF) && (b[1] == (byte) 0xD8);
		}
	}

	private static boolean isPNG(byte[] b) {
		if (b.length < 8) {
			return false;
		}
		return (b[0] == (byte) 137 && b[1] == (byte) 80 && b[2] == (byte) 78
				&& b[3] == (byte) 71 && b[4] == (byte) 13 && b[5] == (byte) 10
				&& b[6] == (byte) 26 && b[7] == (byte) 10);
	}
	
	/**
	 * ��ȡ�ֻ�ͼƬ����ת�Ƕ�
	 * @param path
	 * @return
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * �����ֻ�ͼƬ����ת�Ƕ�
	 * @param angle
	 * @param data
	 * @param imageType
	 * @return
	 */
	public static byte[] rotaingImageView(int angle, byte[] data, String imageType) {
		Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
		// ��תͼƬ ����
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		// �����µ�ͼƬ
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		if (bitmap != resizedBitmap) {
			bitmap.recycle();
		}
		return CommentUtil.BitmapToBytes(resizedBitmap, imageType);
	}
	
	//ͨ�õĴ�uri�л�ȡ·���ķ���, ��������˵����2��shceme
	public static String getPath(final Context context, final Uri uri) {

	   final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
	   // DocumentProvider
	   if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
	       // ExternalStorageProvider
	       if (isExternalStorageDocument(uri)) {
	           final String docId = DocumentsContract.getDocumentId(uri);
	           final String[] split = docId.split(":");
	           final String type = split[0];

	           if ("primary".equalsIgnoreCase(type)) {
	               return Environment.getExternalStorageDirectory() + "/" + split[1];
	           }
	       }
	       // DownloadsProvider
	       else if (isDownloadsDocument(uri)) {

	           final String id = DocumentsContract.getDocumentId(uri);
	           final Uri contentUri = ContentUris.withAppendedId(
	                   Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

	           return getDataColumn(context, contentUri, null, null);
	       }
	       // MediaProvider
	       else if (isMediaDocument(uri)) {
	           final String docId = DocumentsContract.getDocumentId(uri);
	           final String[] split = docId.split(":");
	           final String type = split[0];

	           Uri contentUri = null;
	           if ("image".equals(type)) {
	               contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
	           } else if ("video".equals(type)) {
	               contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
	           } else if ("audio".equals(type)) {
	               contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	           }

	           final String selection = "_id=?";
	           final String[] selectionArgs = new String[]{
	                   split[1]
	           };
	           return getDataColumn(context, contentUri, selection, selectionArgs);
	       }
	   }
	   // MediaStore (and general)
	   else if ("content".equalsIgnoreCase(uri.getScheme())) {

	       // Return the remote address
	       if (isGooglePhotosUri(uri))
	           return uri.getLastPathSegment();

	       return getDataColumn(context, uri, null, null);
	   }
	   // File
	   else if ("file".equalsIgnoreCase(uri.getScheme())) {
	       return uri.getPath();
	   }
	   return "";
	}
	
	public static long getId(Context context, Uri uri) {
	    final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
	    if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
	        if (isMediaDocument(uri)) {
	            final String docId = DocumentsContract.getDocumentId(uri);
	            final String[] split = docId.split(":");
	            final String type = split[0];

	            Uri contentUri = null;
	            if ("image".equals(type)) {
	                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
	            } else if ("video".equals(type)) {
	                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
	            } else if ("audio".equals(type)) {
	                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	            }

	            final String selection = "_id=?";
	            final String[] selectionArgs = new String[]{
	                    split[1]
	            };

	            return getIdColumn(context, contentUri, selection, selectionArgs);
	        }
	    } else {
	        return getIdColumn(context, uri, null, null);
	    }
	    return 0;
	}

	public static String getDataColumn(Context context, Uri uri, String selection,
	                                   String[] selectionArgs) {

	    Cursor cursor = null;
	    final String column = "_data";
	    final String[] projection = {
	            column
	    };

	    try {
	        cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
	                null);
	        if (cursor != null && cursor.moveToFirst()) {
	            final int index = cursor.getColumnIndexOrThrow(column);
	            return cursor.getString(index);
	        }
	    } finally {
	        if (cursor != null)
	            cursor.close();
	    }
	    return "";
	}
	public static long getIdColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
	    Cursor cursor = null;
	    final String column = "_id";
	    final String[] projection = {column};

	    try {
	        cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
	        if (cursor != null && cursor.moveToFirst()) {
	            final int index = cursor.getColumnIndexOrThrow(column);
	            return cursor.getLong(index);
	        }
	    } finally {
	        if (cursor != null) {
	            cursor.close();
	        }
	    }
	    return 0;
	}
	public static boolean isExternalStorageDocument(Uri uri) {
	    return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}
	public static boolean isDownloadsDocument(Uri uri) {
	    return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}
	public static boolean isMediaDocument(Uri uri) {
	    return "com.android.providers.media.documents".equals(uri.getAuthority());
	}
	public static boolean isGooglePhotosUri(Uri uri) {
	    return "com.google.android.apps.photos.content".equals(uri.getAuthority());
	}
}
