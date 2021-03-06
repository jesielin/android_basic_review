package com.example.notification;

import android.app.Activity;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

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
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			
			Button send = (Button) rootView.findViewById(R.id.send);
			send.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					NotificationManager nm = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
					Intent openIntent = new Intent(getActivity(),FullscreenActivity.class);
					PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), 0, openIntent, 0);
					int icon = R.drawable.preferences_desktop_notification;
					Notification n = new Notification(icon,"通知来了",System.currentTimeMillis());
					n.flags |= Notification.FLAG_AUTO_CANCEL;//自动消失
					n.defaults = Notification.DEFAULT_SOUND;//声音默认
					n.setLatestEventInfo(getActivity(), "通知标题", "通知正文", contentIntent);
					nm.notify(0,n);
				}
			});
			
			return rootView;
		}
	}

}
