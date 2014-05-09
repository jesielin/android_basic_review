package com.example.blacknumber;

import android.app.Activity;
import android.app.Fragment;
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

		private Intent intent;
		private Button bn_start;
		private Button bn_stop;

		public PlaceholderFragment() {
			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			bn_start = (Button) rootView.findViewById(R.id.bn_start);
			bn_stop = (Button) rootView.findViewById(R.id.bn_stop);
			bn_start.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					intent = new Intent(getActivity(),BlackNumberService.class);
					getActivity().startService(intent);
				}
			});
			bn_stop.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(intent != null){
						getActivity().stopService(intent);
						intent = null;
					}
				}
			});
			return rootView;
		}
	}

}
