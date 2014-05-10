package com.example.mediaplayer;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
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
	 * 待完善的问题：
	 * 1.seekbar的定位
	 * 2.来电停止播放
	 * 3.UI
	 */
	public static class PlaceholderFragment extends Fragment implements
			OnClickListener {

		private Button bn_record;
		private Button bn_play;
		private Button bn_stop_record;
		private MediaRecorder recorder;
		private Button bn_reset_record;
		private SeekBar sb;
		private Timer mTimer;
		private TimerTask mTimerTask;
		private boolean isChanging;
		private Button bn_pause_play;
		private Button bn_resume_play;
		private Button bn_stop_play;
		private MediaPlayer mediaPlayer;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			bn_record = (Button) rootView.findViewById(R.id.bn_record);
			bn_play = (Button) rootView.findViewById(R.id.bn_play);
			bn_stop_record = (Button) rootView
					.findViewById(R.id.bn_stop_record);
			bn_reset_record = (Button) rootView
					.findViewById(R.id.bn_reset_record);
			sb = (SeekBar) rootView.findViewById(R.id.sb);
			bn_pause_play = (Button) rootView.findViewById(R.id.bn_pause_play);
			bn_resume_play = (Button) rootView
					.findViewById(R.id.bn_resume_play);
			bn_stop_play = (Button) rootView.findViewById(R.id.bn_stop_play);
			bn_pause_play.setOnClickListener(this);
			bn_resume_play.setOnClickListener(this);
			bn_stop_play.setOnClickListener(this);
			bn_reset_record.setOnClickListener(this);
			bn_stop_record.setOnClickListener(this);
			bn_record.setOnClickListener(this);
			bn_play.setOnClickListener(this);
			return rootView;
		}

		@Override
		public void onClick(View v) {
			try {
				switch (v.getId()) {
				case R.id.bn_record:
					System.out.println("开始录制..");

					if (recorder == null) {
						recorder = new MediaRecorder();
						recorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 从麦克风采集声音
						recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);// 内容输出格式
						recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);// 音频编码方式
						recorder.setOutputFile(Environment
								.getExternalStorageDirectory()
								.getAbsolutePath()
								+ "/test1.3gp");
						recorder.prepare();// 预期准备
						recorder.start(); // 开始刻录
						// recorder.stop();// 停止刻录
						// recorder.reset(); // 重设
						// recorder.release(); // 刻录完成一定要释放资源
					}
					Toast.makeText(getActivity(), "开始录制", 0).show();
					break;
				case R.id.bn_reset_record:
					if (recorder != null) {
						recorder.reset();
						recorder.release();
						Toast.makeText(getActivity(), "请重新开始录制", 0).show();
						recorder = null;
					}
				case R.id.bn_stop_record:
					if (recorder != null) {
						recorder.stop();
						recorder.release();
						recorder = null;
						Toast.makeText(getActivity(), "录制成功", 0).show();
					}
					break;
				case R.id.bn_play:

					System.out.println("开始播放..");
					mediaPlayer = new MediaPlayer();
					// mediaPlayer.reset();// 重置为初始状态
					mediaPlayer.setDataSource(Environment
							.getExternalStorageDirectory().getAbsolutePath()
							+ "/test1.3gp");
					mediaPlayer.prepare();
					mediaPlayer.start();// 开始或恢复播放
					// mediaPlayer.pause();// 暂停播放
					// mediaPlayer.start();// 恢复播放
					// mediaPlayer.stop();// 停止播放
					// mediaPlayer.release();// 释放资源
					sb.setMax(mediaPlayer.getDuration());
					isChanging = false;
					// ----------定时器记录播放进度---------//
					mTimer = new Timer();
					mTimerTask = new TimerTask() {
						@Override
						public void run() {
							if (isChanging == true) {
								return;
							}
							sb.setProgress(mediaPlayer.getCurrentPosition());
						}
					};
					mTimer.schedule(mTimerTask, 0, 10);
					mediaPlayer
							.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {// 播出完毕事件
								@Override
								public void onCompletion(MediaPlayer arg0) {
									System.out.println("player complete");
									mTimerTask.cancel();
									mTimer.cancel();
									mediaPlayer.release();
									sb.setProgress(0);
									bn_play.setClickable(true);
									bn_play.setTextColor(Color.BLACK);
								}
							});
					mediaPlayer
							.setOnErrorListener(new MediaPlayer.OnErrorListener() {// 错误处理事件
								@Override
								public boolean onError(MediaPlayer player,
										int arg1, int arg2) {
									System.out.println("player error");
									mTimerTask.cancel();
									mTimer.cancel();
									mediaPlayer.release();
									sb.setProgress(0);
									bn_play.setClickable(true);
									bn_play.setTextColor(Color.BLACK);
									return false;
								}
							});
					bn_play.setClickable(false);
					bn_play.setTextColor(Color.GRAY);
					break;
				case R.id.bn_pause_play:
					if (mediaPlayer != null) {
						isChanging = true;
						mediaPlayer.pause();
					}
					break;
				case R.id.bn_resume_play:
					if (mediaPlayer != null) {
						isChanging = false;
						mediaPlayer.start();
					}
					break;
				case R.id.bn_stop_play:
					if (mediaPlayer != null) {
						mTimerTask.cancel();
						mTimer.cancel();
						sb.setProgress(0);
						mediaPlayer.stop();
						mediaPlayer.release();
						mediaPlayer = null;

						bn_play.setClickable(true);
						bn_play.setTextColor(Color.BLACK);
					}
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
