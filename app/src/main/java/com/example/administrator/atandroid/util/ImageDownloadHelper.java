package com.example.administrator.atandroid.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;
@SuppressLint("NewApi")

/**
 * 1.内存强引用缓存区：系统不会回收的缓存区，缓存区满了则不能再存，�?常存放常用数�?
 * 2.内存软引用缓存区：当系统内存空间不足时会回收软引用缓存区，�?常存放不常用数据
 * 3.图片的三级缓存原理：
 * �?）从网络加载图片后，存入SD卡缓存区，同时存入内存强引用缓存区；
 * �?）强引用存满了后，使用Android内存缓存类的�?��使用算法，将强引用中不常用的图片移入软引用，等待系统回收�?
 * �?）获取图片时先在强引用区查找，再从软引用获取，最后在SD卡获取，如果都没有找到才从网络获取�?
 */
public class ImageDownloadHelper {
	
	public Map<String, SoftReference<Bitmap>> softCaches = new LinkedHashMap<String, SoftReference<Bitmap>>();
	public LruCache<String, Bitmap> lruCache = null;

	public interface OnImageDownloadListener {
		void onImageDownload(Bitmap bitmap, String imgUrl);
	}

	public ImageDownloadHelper() {
		int memoryAmount = (int) Runtime.getRuntime().maxMemory();
		// 获取剩余内存�?分之�?��为缓�?
		int cacheSize = memoryAmount / 8;
		if (lruCache == null) {
			lruCache = new MyLruCache(cacheSize);
		}
	}

	// 异步加载图片方法
	public void myDownloadImage(Context context, String url,
			ImageView imageView, OnImageDownloadListener downloadListener) {
		Bitmap bitmap = null;
		// 先从强引用中拿数�?
		if (lruCache != null) {
			bitmap = lruCache.get(url);
		}
		if (bitmap != null && url.equals(imageView.getTag())) {
			downloadListener.onImageDownload(bitmap, url);
			imageView.setImageBitmap(bitmap);
		} else {
			SoftReference<Bitmap> softReference = softCaches.get(url);
			if (softReference != null) {
				bitmap = softReference.get();
			}
			// 从软引用中拿数据
			if (bitmap != null && url.equals(imageView.getTag())) {
				downloadListener.onImageDownload(bitmap, url);
				imageView.setImageBitmap(bitmap);
				// 添加到强引用�?
				lruCache.put(url, bitmap);
				// 从软引用集合中移�?
				softCaches.remove(url);
			} else {
				// 从文件缓存中拿数�?
				if (url != null) {
					String imageName = "";
					imageName = getImageName(url);
					String cachePath = SDCardHelper
							.getInstance().getSDCardCachePath(context);
					bitmap = SDCardHelper.getInstance()
							.loadBitmapFromSDCard(
									cachePath + File.separator + imageName);

					if (bitmap != null && url.equals(imageView.getTag())) {
						// Log.i(TAG, "==从文件缓存中找到数据" + bitmap.toString());
						imageView.setImageBitmap(bitmap);
						downloadListener.onImageDownload(bitmap, url);
						// 放入强缓�?
						lruCache.put(url, bitmap);
					} else {
						// 从网络中拿数�?
						new MyAsyncTask(context, url, imageView,
								downloadListener).execute(url);
					}
				}
			}
		}
	}

	// 异步任务�?
	class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {
		public Context context;
		public ImageView mImageView;
		public String url;
		public OnImageDownloadListener downloadListener;

		public MyAsyncTask(Context context, String url, ImageView mImageView,
				OnImageDownloadListener downloadListener) {
			this.context = context;
			this.url = url;
			this.mImageView = mImageView;
			this.downloadListener = downloadListener;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap bm = null;
			try {
				String urlString = params[0];
				// 加载网络图片，获得图片的字节数组
				byte[] data = HttpURLConnHelper.loadByteFromURL(urlString);
				bm = BitmapHelper.compressBitmap(data);
				if (bm != null) {
					String imageName = getImageName(urlString);
					boolean flag = SDCardHelper
							.getInstance().saveBitmapToSDCardPrivateCacheDir(
									bm, imageName, context);
					if (flag) {
						// 放入强缓�?
						lruCache.put(urlString, bm);
					}
					return bm;
				} 
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			// 回调设置图片
			if (downloadListener != null && result != null) {
				downloadListener.onImageDownload(result, url);
			}
		}
	}

	// 强引用缓存类
	class MyLruCache extends LruCache<String, Bitmap> {
		public MyLruCache(int maxSize) {
			super(maxSize);
		}

		@Override
		protected int sizeOf(String key, Bitmap value) {
			return value.getHeight() * value.getWidth() * 4;
		}

		// 当强引用缓存空间已满，将较少使用的图片移入软引用，软引用会在内存空间不够时被系统自动回收
		@Override
		protected void entryRemoved(boolean evicted, String key,
				Bitmap oldValue, Bitmap newValue) {
			if (evicted) {
				SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(
						oldValue);
				softCaches.put(key, softReference);
			}
		}
	}

	// SDCard工具�?
	static class SDCardHelper {
		public static SDCardHelper sdCardHelper;

		public static SDCardHelper getInstance() {
			if (sdCardHelper == null) {
				sdCardHelper = new SDCardHelper();
			}
			return sdCardHelper;
		}

		// 判断SDCard是否挂载
		public boolean isSDCardMounted() {
			return Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED);
		}

		// 获取SDCard的根目录路径
		public String getSDCardBasePath() {
			if (isSDCardMounted()) {
				return Environment.getExternalStorageDirectory()
						.getAbsolutePath();
			} else {
				return null;
			}
		}

		// 获取SDCard的完整空间大�?
		@SuppressWarnings("deprecation")
		public long getSDCardTotalSize() {
			long size = 0;
			if (isSDCardMounted()) {
				StatFs statFs = new StatFs(getSDCardBasePath());
				if (Build.VERSION.SDK_INT >= 18) {
					size = statFs.getTotalBytes();
				} else {
					size = statFs.getBlockCount() * statFs.getBlockSize();
				}
				return size / 1024 / 1024;
			} else {
				return 0;
			}
		}

		// 获取SDCard的可用空间大�?
		@SuppressWarnings("deprecation")
		public long getSDCardAvailableSize() {
			long size = 0;
			if (isSDCardMounted()) {
				StatFs statFs = new StatFs(getSDCardBasePath());
				if (Build.VERSION.SDK_INT >= 18) {
					size = statFs.getAvailableBytes();
				} else {
					size = statFs.getAvailableBlocks() * statFs.getBlockSize();
				}
				return size / 1024 / 1024;
			} else {
				return 0;
			}
		}

		// 获取SDCard的剩余空间大�?
		@SuppressWarnings("deprecation")
		public long getSDCardFreeSize() {
			long size = 0;
			if (isSDCardMounted()) {
				StatFs statFs = new StatFs(getSDCardBasePath());
				if (Build.VERSION.SDK_INT >= 18) {
					size = statFs.getFreeBytes();
				} else {
					size = statFs.getFreeBlocks() * statFs.getBlockSize();
				}
				return size / 1024 / 1024;
			} else {
				return 0;
			}
		}

		// 保存byte[]文件到SDCard的指定公有目�?
		public boolean saveFileToSDCardPublicDir(byte[] data, String type,
				String fileName) {
			if (isSDCardMounted()) {
				BufferedOutputStream bos = null;
				File file = Environment.getExternalStoragePublicDirectory(type);

				try {
					bos = new BufferedOutputStream(new FileOutputStream(
							new File(file, fileName)));
					bos.write(data);
					bos.flush();
					return true;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				} finally {
					if (bos != null) {
						try {
							bos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				return false;
			}
		}

		// 保存byte[]文件到SDCard的自定义目录
		public boolean saveFileToSDCardCustomDir(byte[] data, String dir,
				String fileName) {
			if (isSDCardMounted()) {
				BufferedOutputStream bos = null;
				File file = new File(getSDCardBasePath() + File.separator + dir);
				if (!file.exists()) {
					file.mkdirs();// 递归创建子目�?
				}
				try {
					bos = new BufferedOutputStream(new FileOutputStream(
							new File(file, fileName)));
					bos.write(data, 0, data.length);
					bos.flush();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (bos != null) {
						try {
							bos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				return true;
			} else {
				return false;
			}
		}

		// 保存byte[]文件到SDCard的指定私有Files目录
		public boolean saveFileToSDCardpublicDir(byte[] data, String type,
				String fileName, Context context) {
			if (isSDCardMounted()) {
				BufferedOutputStream bos = null;
				// 获取私有Files目录
				File file = context.getExternalFilesDir(type);
				try {
					bos = new BufferedOutputStream(new FileOutputStream(
							new File(file, fileName)));
					bos.write(data, 0, data.length);
					bos.flush();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (bos != null) {
						try {
							bos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				return true;
			} else {
				return false;
			}
		}

		// 保存byte[]文件到SDCard的私有Cache目录
		public boolean saveFileToSDCardpublicCacheDir(byte[] data,
				String fileName, Context context) {
			if (isSDCardMounted()) {
				BufferedOutputStream bos = null;
				// 获取私有的Cache缓存目录
				File file = context.getExternalCacheDir();
				// Log.i("SDCardHelper", "==" + file);
				try {
					bos = new BufferedOutputStream(new FileOutputStream(
							new File(file, fileName)));
					bos.write(data, 0, data.length);
					bos.flush();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (bos != null) {
						try {
							bos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				return true;
			} else {
				return false;
			}
		}

		// 保存bitmap图片到SDCard的私有Cache目录
		public boolean saveBitmapToSDCardPrivateCacheDir(Bitmap bitmap,
				String fileName, Context context) {
			if (isSDCardMounted()) {
				BufferedOutputStream bos = null;
				// 获取私有的Cache缓存目录
				File file = context.getExternalCacheDir();
				try {
					bos = new BufferedOutputStream(new FileOutputStream(
							new File(file, fileName)));
					if (fileName != null
							&& (fileName.contains(".png") || fileName
									.contains(".PNG"))) {
						bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
					} else {
						bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
					}
					bos.flush();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (bos != null) {
						try {
							bos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				return true;
			} else {
				return false;
			}
		}

		// 从SDCard中寻找指定目录下的文件，返回byte[]
		public byte[] loadFileFromSDCard(String filePath) {
			BufferedInputStream bis = null;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			File file = new File(filePath);
			if (file.exists()) {
				try {
					bis = new BufferedInputStream(new FileInputStream(file));
					byte[] buffer = new byte[1024 * 8];
					int c = 0;
					while ((c = (bis.read(buffer))) != -1) {
						baos.write(buffer, 0, c);
						baos.flush();
					}
					return baos.toByteArray();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (bis != null) {
						try {
							bis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (baos != null) {
						try {
							baos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			return null;
		}

		// 从SDCard中寻找指定目录下的文件，返回Bitmap
		public Bitmap loadBitmapFromSDCard(String filePath) {
			byte[] data = loadFileFromSDCard(filePath);
			if (data != null) {
				Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
				if (bm != null) {
					return bm;
				}
			}
			return null;
		}

		// 获取SDCard私有的Cache目录
		public String getSDCardCachePath(Context context) {
			return context.getExternalCacheDir().getAbsolutePath();
		}

		// 获取SDCard私有的Files目录
		public String getSDCardFilePath(Context context, String type) {
			return context.getExternalFilesDir(type).getAbsolutePath();
		}

		// 从sdcard中删除文�?
		public boolean removeFileFromSDCard(String filePath) {
			File file = new File(filePath);
			if (file.exists()) {
				try {
					file.delete();
					return true;
				} catch (Exception e) {
					return false;
				}
			} else {
				return false;
			}
		}
	}

	// 获取图片名称，默认取�?���?��"/"之后的字符串作为图片�?
	public String getImageName(String url) {
		String imageName = "";
		if (url != null) {
			imageName = url.substring(url.lastIndexOf("/") + 1);
		}
		return imageName;
	}

}
