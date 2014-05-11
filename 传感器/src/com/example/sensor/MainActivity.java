package com.example.sensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView accelerometer;
	private TextView orientation;
	private SensorManager sensorManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		// ��ȡ��Ӧ��������
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelerometer = (TextView) findViewById(R.id.accelerometer);
		orientation = (TextView) findViewById(R.id.orientation);
	}

	@Override
	protected void onResume() {
		Sensor sensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);// ��ȡ�������ٶȴ�����
		sensorManager.registerListener(listener, sensor,
				SensorManager.SENSOR_DELAY_GAME);
		Sensor sensor1 = sensorManager
				.getDefaultSensor(Sensor.TYPE_ORIENTATION);// ��ȡ���򴫸���
		sensorManager.registerListener(listener, sensor1,
				SensorManager.SENSOR_DELAY_GAME);
		super.onResume();
	}

	@Override
	protected void onPause() {
		sensorManager.unregisterListener(listener);// ע�����д���������
		super.onPause();
	}

	private SensorEventListener listener = new SensorEventListener() {
		@Override
		public void onSensorChanged(SensorEvent event) {// ����������ֵ�����仯
			float x = event.values[SensorManager.DATA_X];
			float y = event.values[SensorManager.DATA_Y];
			float z = event.values[SensorManager.DATA_Z];
			switch (event.sensor.getType()) {
			case Sensor.TYPE_ACCELEROMETER:
				accelerometer.setText("Accelerometer Sensor: " + x + ", " + y
						+ ", " + z);
				break;

			case Sensor.TYPE_ORIENTATION:
				/*
				 * x��ֵ��ʾ��λ��0������North����90������East����180�����ϣ�South����270��������West��
				 * ���xֵ��������4��ֵ֮һ�������ֻ���ˮƽ���ã��ֻ��Ķ�����׼�ķ�����Ǹ�ֵ����ķ���
				 * 
				 * yֵ��ʾ��б�ȣ����ֻ�����ĳ̶ȡ����ֻ�����X����бʱ��ֵ�����仯��yֵ��ȡֵ��Χ��-180��yֵ ��180��
				 * ���轫�ֻ���Ļ����ˮƽ���������ϣ���ʱ�����������ȫˮƽ�ģ�yֵӦ����0�����ں����������Ǿ���ˮƽ�ģ�
				 * ��ˣ���ֵ�ܿ��ܲ�Ϊ0
				 * ����һ�㶼��-5��5֮���ĳ��ֵ������ʱ���ֻ�������ʼ̧��ֱ�����ֻ���X����ת180�ȣ���Ļ����ˮƽ���������ϣ���
				 * �������ת�����У�yֵ����0��-180֮��仯��Ҳ����˵�����ֻ�����̧��ʱ��y��ֵ���𽥱�С��
				 * ֱ������-180��������ֻ��ײ���ʼ̧��ֱ�����ֻ���X����ת180�ȣ���ʱyֵ����0��180֮��仯��
				 * Ҳ����yֵ��������ֱ������180����������yֵ��zֵ���������ӵ��������б�ȡ�
				 * 
				 * zֵ��ʾ�ֻ�����Y��Ĺ����Ƕȡ���ʾ�ֻ�����Y��Ĺ����Ƕȡ�ȡֵ��Χ��-90��zֵ��90��
				 * ���轫�ֻ���Ļ����ˮƽ���������ϣ���ʱ���������ƽ�ģ�zֵӦΪ0�����ֻ������̧��ʱ��
				 * zֵ�𽥱�С��ֱ���ֻ���ֱ��������ã���ʱzֵ��-90�����ֻ��Ҳ���̧��ʱ��zֵ������
				 * ֱ���ֻ���ֱ��������ã���ʱzֵ��90���ڴ�ֱλ��ʱ�������һ����������zֵ�������-90��90֮��仯��
				 */
				orientation.setText("Orientation Sensor: " + x + ", " + y
						+ ", " + z);
				break;
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {// ���������ľ��ȱ仯ʱ
		}
	};
}
