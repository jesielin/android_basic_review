package com.example.close;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
	public static class PlaceholderFragment extends Fragment implements
			OnClickListener {

		private EditText et;
		private Button bn;
		private ActivityManager manager;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			et = (EditText) rootView.findViewById(R.id.et);
			bn = (Button) rootView.findViewById(R.id.bn);
			Button bn1 = (Button) rootView.findViewById(R.id.bn1);
			Button bn2 = (Button) rootView.findViewById(R.id.bn2);
			Button bn3 = (Button) rootView.findViewById(R.id.bn3);
			bn1.setOnClickListener(this);
			bn2.setOnClickListener(this);
			bn3.setOnClickListener(this);
			bn.setOnClickListener(this);
			manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
			return rootView;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.bn:
				Toast.makeText(getActivity(), "干掉别人", 0).show();
				manager.killBackgroundProcesses(et.getText().toString());
				break;
			case R.id.bn1:
				Toast.makeText(getActivity(), "干掉自己1", 0).show();
				android.os.Process.killProcess(android.os.Process.myPid());
				break;
			case R.id.bn2:
				Toast.makeText(getActivity(), "干掉自己2", 0).show();
				System.exit(0);
				break;
			case R.id.bn3:
				Toast.makeText(getActivity(), "干掉自己3", 0).show();
				manager.restartPackage(getActivity().getPackageName());
				break;
			}
		}
	}

}
