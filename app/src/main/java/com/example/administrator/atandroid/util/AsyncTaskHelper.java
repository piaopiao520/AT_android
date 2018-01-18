package com.example.administrator.atandroid.util;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class AsyncTaskHelper {
	public interface OnDataDownloadListener {
		void onDataDownload(byte[] result);
	}

	public void downloadData(Context context, String url,
			OnDataDownloadListener downloadListener) {
		new MyTask(downloadListener, context).execute(url);
	}

	private class MyTask extends AsyncTask<String, Void, byte[]> {
		private OnDataDownloadListener downloadListener;
		private Context context;

		public MyTask(OnDataDownloadListener downloadListener, Context context) {
			this.downloadListener = downloadListener;
			this.context = context;
		}

		@Override
		protected byte[] doInBackground(String... params) {
			byte[] jsonData = HttpURLConnHelper.loadByteFromURL(params[0]);
			return jsonData;
		}

		@Override
		protected void onPostExecute(byte[] result) {
			super.onPostExecute(result);
			if (result != null) {
				// 通过回调接口来传递数�?				
				downloadListener.onDataDownload(result);
			} else {
				Toast.makeText(context, "加载超时，请检查网络连接", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}
}
