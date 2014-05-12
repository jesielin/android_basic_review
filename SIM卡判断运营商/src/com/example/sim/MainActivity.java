package com.example.sim;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

		private TextView tv;
		private Button bn;
		private Button bn1;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			tv = (TextView) rootView.findViewById(R.id.tv);
			bn = (Button) rootView.findViewById(R.id.bn);
			bn1 = (Button) rootView.findViewById(R.id.bn1);
			bn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					TelephonyManager telManager = (TelephonyManager) getActivity()
							.getSystemService(Context.TELEPHONY_SERVICE);
					/**
					 * 获取SIM卡的IMSI码 SIM卡唯一标识：IMSI 国际移动用户识别码（IMSI：International
					 * Mobile Subscriber Identification Number）是区别移动用户的标志，
					 * 储存在SIM卡中
					 * ，可用于区别移动用户的有效信息。IMSI由MCC、MNC、MSIN组成，其中MCC为移动国家号码，由3位数字组成，
					 * 唯一地识别移动客户所属的国家，我国为460；MNC为网络id，由2位数字组成，
					 * 用于识别移动客户所归属的移动网络，中国移动为00
					 * ，中国联通为01,中国电信为03；MSIN为移动客户识别码，采用等长11位数字构成。
					 * 唯一地识别国内GSM移动通信网中移动客户。所以要区分是移动还是联通，只需取得SIM卡中的MNC字段即可
					 */
					String imsi = telManager.getSubscriberId();
					if (imsi != null) {
						if (imsi.startsWith("46000")
								|| imsi.startsWith("46002")) {// 因为移动网络编号46000下的IMSI已经用完，所以虚拟了一个46002编号，134/159号段使用了此编号
							// 中国移动
							tv.setText("中国移动");
						} else if (imsi.startsWith("46001")) {
							// 中国联通
							tv.setText("中国联通");
						} else if (imsi.startsWith("46003")) {
							// 中国电信
							tv.setText("中国电信");
						}
					}
				}
			});
			bn1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// 第二种方法
					TelephonyManager telManager = (TelephonyManager) getActivity()
							.getSystemService(Context.TELEPHONY_SERVICE);
					String operator = telManager.getSimOperator();
					if (operator != null) {
						if (operator.equals("46000")
								|| operator.equals("46002")) {
							// 中国移动
							tv.setText("中国移动");
						} else if (operator.equals("46001")) {
							// 中国联通
							tv.setText("中国联通");
						} else if (operator.equals("46003")) {
							// 中国电信
							tv.setText("中国电信");
						}
					}
				}
			});

			return rootView;
		}
	}

}
