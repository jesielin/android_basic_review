package com.example.data_listen_sms;

import android.app.Activity;
import android.app.Fragment;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			getActivity().getContentResolver().registerContentObserver(
					Uri.parse("content://sms"), true,
					new SmsObserver(new Handler()));
		}

		private final class SmsObserver extends ContentObserver {
			public SmsObserver(Handler handler) {
				super(handler);
			}

			public void onChange(boolean selfChange) {// 查询发送箱中的短信(处于正在发送状态的短信放在发送箱)
				Cursor cursor = getActivity().getContentResolver().query(
						Uri.parse("content://sms/outbox"), null, null, null,
						null);
				while (cursor.moveToNext()) {
					StringBuilder sb = new StringBuilder();
					sb.append("_id=").append(
							cursor.getInt(cursor.getColumnIndex("_id")));
					sb.append(",address=").append(
							cursor.getString(cursor.getColumnIndex("address")));
					sb.append(";body=").append(
							cursor.getString(cursor.getColumnIndex("body")));
					sb.append(";time=").append(
							cursor.getLong(cursor.getColumnIndex("date")));
					Log.i("ReceiveSendSMS", sb.toString());
				}
			}
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);

			return rootView;
		}
	}

}
