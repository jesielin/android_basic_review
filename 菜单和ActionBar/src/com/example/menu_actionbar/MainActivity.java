package com.example.menu_actionbar;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {
	private static final int MENU_ADD = Menu.FIRST;
	private static final int MENU_UPDATE = Menu.FIRST + 1;
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
		//getMenuInflater().inflate(R.menu.main, menu);
		menu.add(Menu.NONE, MENU_ADD, Menu.NONE, "Ìí¼Ó");  
		menu.add(Menu.NONE, MENU_UPDATE, Menu.NONE, "¸üÐÂ");

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		  case MENU_ADD:
		       Toast.makeText(this, item.getTitle(), 0).show();
		       return true;
		  case MENU_UPDATE:
			  Toast.makeText(this, item.getTitle(), 0).show();
		       return true;
		  default:
	              return super.onOptionsItemSelected(item);
		  }

	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		  case MENU_ADD:
		       Toast.makeText(this, item.getTitle()+"__menu", 0).show();
		       return true;
		  case MENU_UPDATE:
			  Toast.makeText(this, item.getTitle()+"__menu", 0).show();
		       return true;
		  default:
	              return super.onMenuItemSelected(featureId, item);
		  }
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
			return rootView;
		}
	}

}
