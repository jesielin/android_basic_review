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
		// 获取感应器管理器
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelerometer = (TextView) findViewById(R.id.accelerometer);
		orientation = (TextView) findViewById(R.id.orientation);
	}

	@Override
	protected void onResume() {
		Sensor sensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);// 获取重力加速度传感器
		sensorManager.registerListener(listener, sensor,
				SensorManager.SENSOR_DELAY_GAME);
		Sensor sensor1 = sensorManager
				.getDefaultSensor(Sensor.TYPE_ORIENTATION);// 获取方向传感器
		sensorManager.registerListener(listener, sensor1,
				SensorManager.SENSOR_DELAY_GAME);
		super.onResume();
	}

	@Override
	protected void onPause() {
		sensorManager.unregisterListener(listener);// 注消所有传感器监听
		super.onPause();
	}

	private SensorEventListener listener = new SensorEventListener() {
		@Override
		public void onSensorChanged(SensorEvent event) {// 当传感器的值发生变化
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
				 * x该值表示方位，0代表北（North）；90代表东（East）；180代表南（South）；270代表西（West）
				 * 如果x值正好是这4个值之一，并且手机是水平放置，手机的顶部对准的方向就是该值代表的方向。
				 * 
				 * y值表示倾斜度，或手机翘起的程度。当手机绕着X轴倾斜时该值发生变化。y值的取值范围是-180≤y值 ≤180。
				 * 假设将手机屏幕朝上水平放在桌子上，这时如果桌子是完全水平的，y值应该是0（由于很少有桌子是绝对水平的，
				 * 因此，该值很可能不为0
				 * ，但一般都是-5和5之间的某个值）。这时从手机顶部开始抬起，直到将手机沿X轴旋转180度（屏幕向下水平放在桌面上）。
				 * 在这个旋转过程中，y值会在0到-180之间变化，也就是说，从手机顶部抬起时，y的值会逐渐变小，
				 * 直到等于-180。如果从手机底部开始抬起，直到将手机沿X轴旋转180度，这时y值会在0到180之间变化。
				 * 也就是y值会逐渐增大，直到等于180。可以利用y值和z值来测量桌子等物体的倾斜度。
				 * 
				 * z值表示手机沿着Y轴的滚动角度。表示手机沿着Y轴的滚动角度。取值范围是-90≤z值≤90。
				 * 假设将手机屏幕朝上水平放在桌面上，这时如果桌面是平的，z值应为0。将手机左侧逐渐抬起时，
				 * z值逐渐变小，直到手机垂直于桌面放置，这时z值是-90。将手机右侧逐渐抬起时，z值逐渐增大，
				 * 直到手机垂直于桌面放置，这时z值是90。在垂直位置时继续向右或向左滚动，z值会继续在-90至90之间变化。
				 */
				orientation.setText("Orientation Sensor: " + x + ", " + y
						+ ", " + z);
				break;
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {// 当传感器的精度变化时
		}
	};
}
