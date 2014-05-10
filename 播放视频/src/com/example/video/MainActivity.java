package com.example.video;

import java.io.IOException;

import android.app.Activity;
import android.app.Fragment;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends Activity {
	private static final String TAG = "VideoActivity";
	private EditText filenameText;
	private SurfaceView surfaceView;
	private MediaPlayer mediaPlayer;
	private String filename;// ��ǰ�����ļ�������
	private int position;// ��¼����λ��

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);

		this.mediaPlayer = new MediaPlayer();
		this.surfaceView = (SurfaceView) this.findViewById(R.id.surfaceView);
		ImageButton playButton = (ImageButton) this.findViewById(R.id.play);
		ImageButton pauseButton = (ImageButton) this.findViewById(R.id.pause);
		ImageButton resetButton = (ImageButton) this.findViewById(R.id.reset);
		ImageButton stopButton = (ImageButton) this.findViewById(R.id.stop);

		ButtonClickListener listener = new ButtonClickListener();
		playButton.setOnClickListener(listener);
		pauseButton.setOnClickListener(listener);
		resetButton.setOnClickListener(listener);
		stopButton.setOnClickListener(listener);

		/* ��������Surface��ά���Լ��Ļ����������ǵȴ���Ļ����Ⱦ���潫�������͵��û���ǰ */
		this.surfaceView.getHolder().setType(
				SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		this.surfaceView.getHolder().setFixedSize(176, 144);// ���÷ֱ���
		this.surfaceView.getHolder().setKeepScreenOn(true);
		this.surfaceView.getHolder().addCallback(new SurfaceListener());
	}

	private class ButtonClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			try {
				switch (v.getId()) {
				case R.id.play:// ���Բ��Ű�ť
					play();
					break;

				case R.id.pause:// ������ͣ��ť
					if (mediaPlayer.isPlaying()) {
						mediaPlayer.pause();
					} else {
						mediaPlayer.start();
					}
					break;

				case R.id.reset:// �������²��Ű�ť
					if (!mediaPlayer.isPlaying())
						play();
					mediaPlayer.seekTo(0);
					break;

				case R.id.stop:// ����ֹͣ��ť
					if (mediaPlayer.isPlaying())
						mediaPlayer.stop();
					break;
				}
			} catch (Exception e) {
				Log.e(TAG, e.toString());
			}
		}
	}

	/**
	 * ������Ƶ
	 */
	private void play() throws IOException {
		mediaPlayer.reset();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mediaPlayer.setDataSource(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Jason Mraz-I Won't Give Up MV 1080p.MP4");// ������Ҫ���ŵ���Ƶ
		mediaPlayer.setDisplay(surfaceView.getHolder());// ����Ƶ���������SurfaceView
		mediaPlayer.prepare();
		mediaPlayer.start();
	}

	private class SurfaceListener implements SurfaceHolder.Callback {
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {// ������onResume()�󱻵���
			Log.i(TAG, "surfaceCreated()");
			if (position > 0) {
				try {
					play();
					mediaPlayer.seekTo(position);
					position = 0;
				} catch (Exception e) {
					Log.e(TAG, e.toString());
				}
			}
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			Log.i(TAG, "surfaceDestroyed()");
		}
	}

	@Override
	protected void onPause() {// ������Activity���򿪣�ֹͣ����
		if (mediaPlayer.isPlaying()) {
			position = mediaPlayer.getCurrentPosition();// �õ�����λ��
			mediaPlayer.stop();
		}
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		if (mediaPlayer.isPlaying())
			mediaPlayer.stop();
		mediaPlayer.release();
		super.onDestroy();
	}
}
