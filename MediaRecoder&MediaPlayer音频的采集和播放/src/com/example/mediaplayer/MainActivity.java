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
	 * �����Ƶ����⣺
	 * 1.seekbar�Ķ�λ
	 * 2.����ֹͣ����
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
					System.out.println("��ʼ¼��..");

					if (recorder == null) {
						recorder = new MediaRecorder();
						recorder.setAudioSource(MediaRecorder.AudioSource.MIC);// ����˷�ɼ�����
						recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);// ���������ʽ
						recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);// ��Ƶ���뷽ʽ
						recorder.setOutputFile(Environment
								.getExternalStorageDirectory()
								.getAbsolutePath()
								+ "/test1.3gp");
						recorder.prepare();// Ԥ��׼��
						recorder.start(); // ��ʼ��¼
						// recorder.stop();// ֹͣ��¼
						// recorder.reset(); // ����
						// recorder.release(); // ��¼���һ��Ҫ�ͷ���Դ
					}
					Toast.makeText(getActivity(), "��ʼ¼��", 0).show();
					break;
				case R.id.bn_reset_record:
					if (recorder != null) {
						recorder.reset();
						recorder.release();
						Toast.makeText(getActivity(), "�����¿�ʼ¼��", 0).show();
						recorder = null;
					}
				case R.id.bn_stop_record:
					if (recorder != null) {
						recorder.stop();
						recorder.release();
						recorder = null;
						Toast.makeText(getActivity(), "¼�Ƴɹ�", 0).show();
					}
					break;
				case R.id.bn_play:

					System.out.println("��ʼ����..");
					mediaPlayer = new MediaPlayer();
					// mediaPlayer.reset();// ����Ϊ��ʼ״̬
					mediaPlayer.setDataSource(Environment
							.getExternalStorageDirectory().getAbsolutePath()
							+ "/test1.3gp");
					mediaPlayer.prepare();
					mediaPlayer.start();// ��ʼ��ָ�����
					// mediaPlayer.pause();// ��ͣ����
					// mediaPlayer.start();// �ָ�����
					// mediaPlayer.stop();// ֹͣ����
					// mediaPlayer.release();// �ͷ���Դ
					sb.setMax(mediaPlayer.getDuration());
					isChanging = false;
					// ----------��ʱ����¼���Ž���---------//
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
							.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {// ��������¼�
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
							.setOnErrorListener(new MediaPlayer.OnErrorListener() {// �������¼�
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
