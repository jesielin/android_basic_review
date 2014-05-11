package com.example.bar;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
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
	public static class PlaceholderFragment extends Fragment {
		private SeekBar seekBar;
		private TextView tv_progress;
		private ProgressBar pb;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);

			seekBar = (SeekBar) rootView.findViewById(R.id.seekBar);
			tv_progress = (TextView) rootView.findViewById(R.id.tv_progress);
			pb = (ProgressBar) rootView.findViewById(R.id.downloadbar);
			pb.setMax(100);
			seekBar.setMax(100);// 设置最大刻度
			seekBar.setProgress(30);// 设置当前刻度
			pb.setProgress(30);
			seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromTouch) {
					Log.v("onProgressChanged()", String.valueOf(progress)
							+ ", " + String.valueOf(fromTouch));
					tv_progress.setText("追踪到当前位置："+progress);
					pb.setProgress(progress);
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {// 开始拖动
					Log.v("onStartTrackingTouch()",
							String.valueOf(seekBar.getProgress()));
					tv_progress.setText("开始喽~~");
					
				}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {// 结束拖动
					Log.v("onStopTrackingTouch()",
							String.valueOf(seekBar.getProgress()));
					tv_progress.setText("搞定！停留在："+seekBar.getProgress());
				}
			});

			return rootView;
		}
	}

}
