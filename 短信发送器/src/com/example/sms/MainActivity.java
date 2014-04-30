package com.example.sms;

import java.util.ArrayList;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

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
		
		@ViewInject(R.id.et_number)
		EditText et_number;
		@ViewInject(R.id.et_content)
		EditText et_content;
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			
			ViewUtils.inject(this, rootView);
			
			return rootView;
		}
		@OnClick(R.id.bn)
		public void send(View view){
			String number = et_number.getText().toString();
			String content = et_content.getText().toString();
			if(TextUtils.isEmpty(number) || TextUtils.isEmpty(content)){
				Toast.makeText(getActivity(), "号码或者短信内容不能为空", 0).show();
				return;
			}
			SmsManager sm = SmsManager.getDefault();
			
			//如果短信内容超过70个字符，将分批发送
			ArrayList<String> smss = sm.divideMessage(content);
			for (String sms : smss) {
				sm.sendTextMessage(number, null,sms , null, null);
			}
		}
	}

}
