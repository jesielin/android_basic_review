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
					 * ��ȡSIM����IMSI�� SIM��Ψһ��ʶ��IMSI �����ƶ��û�ʶ���루IMSI��International
					 * Mobile Subscriber Identification Number���������ƶ��û��ı�־��
					 * ������SIM����
					 * �������������ƶ��û�����Ч��Ϣ��IMSI��MCC��MNC��MSIN��ɣ�����MCCΪ�ƶ����Һ��룬��3λ������ɣ�
					 * Ψһ��ʶ���ƶ��ͻ������Ĺ��ң��ҹ�Ϊ460��MNCΪ����id����2λ������ɣ�
					 * ����ʶ���ƶ��ͻ����������ƶ����磬�й��ƶ�Ϊ00
					 * ���й���ͨΪ01,�й�����Ϊ03��MSINΪ�ƶ��ͻ�ʶ���룬���õȳ�11λ���ֹ��ɡ�
					 * Ψһ��ʶ�����GSM�ƶ�ͨ�������ƶ��ͻ�������Ҫ�������ƶ�������ͨ��ֻ��ȡ��SIM���е�MNC�ֶμ���
					 */
					String imsi = telManager.getSubscriberId();
					if (imsi != null) {
						if (imsi.startsWith("46000")
								|| imsi.startsWith("46002")) {// ��Ϊ�ƶ�������46000�µ�IMSI�Ѿ����꣬����������һ��46002��ţ�134/159�Ŷ�ʹ���˴˱��
							// �й��ƶ�
							tv.setText("�й��ƶ�");
						} else if (imsi.startsWith("46001")) {
							// �й���ͨ
							tv.setText("�й���ͨ");
						} else if (imsi.startsWith("46003")) {
							// �й�����
							tv.setText("�й�����");
						}
					}
				}
			});
			bn1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// �ڶ��ַ���
					TelephonyManager telManager = (TelephonyManager) getActivity()
							.getSystemService(Context.TELEPHONY_SERVICE);
					String operator = telManager.getSimOperator();
					if (operator != null) {
						if (operator.equals("46000")
								|| operator.equals("46002")) {
							// �й��ƶ�
							tv.setText("�й��ƶ�");
						} else if (operator.equals("46001")) {
							// �й���ͨ
							tv.setText("�й���ͨ");
						} else if (operator.equals("46003")) {
							// �й�����
							tv.setText("�й�����");
						}
					}
				}
			});

			return rootView;
		}
	}

}
