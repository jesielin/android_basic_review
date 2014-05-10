package com.example.soundpool;

import com.example.soundpool.R.raw;

import android.app.Activity;
import android.app.Fragment;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
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
	public static class PlaceholderFragment extends Fragment implements OnClickListener {

		private Button bn_play;
		private Button bn_stop;
		private SoundPool pool;
		private int sourceid;
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			//指定声音池的最大音频流数目为10，声音品质为5
			pool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
			sourceid = pool.load(getActivity(),raw.gun1, 0);//载入音频流，返回在池中的id
			bn_play = (Button) rootView.findViewById(R.id.bn_play);
			bn_stop = (Button) rootView.findViewById(R.id.bn_stop);
			bn_play.setOnClickListener(this);
			bn_stop.setOnClickListener(this);

			return rootView;
		}

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.bn_play:
				Toast.makeText(getActivity(), "正在播放", 0).show();
				//播放音频，第二个参数为左声道音量;第三个参数为右声道音量;第四个参数为优先级；第五个参数为循环次数，0不循环，-1循环;第六个参数为速率，速率最低0.5最高为2，1代表正常速度
				pool.play(sourceid, 1, 1, 0, -1, 1);

				break;
			case R.id.bn_stop:
				Toast.makeText(getActivity(), "已停止", 0).show();
				pool.stop(sourceid);
				break;
			}
		}
	}

}
