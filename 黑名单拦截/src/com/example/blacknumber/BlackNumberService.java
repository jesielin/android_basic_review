package com.example.blacknumber;

import java.lang.reflect.Method;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;

public class BlackNumberService extends Service {

	private TelephonyManager tm;
	private MyPhoneStateListener listener;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	private class MyPhoneStateListener extends PhoneStateListener{
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			switch(state){
			case TelephonyManager.CALL_STATE_IDLE:
				System.out.println("����״̬..");
				break;
			case TelephonyManager.CALL_STATE_RINGING:
				System.out.println("����״̬..���������绰����");
				//���ص绰
				endCall();
				//�۲���м�¼���ݿ����ݵı仯,��ɾ����¼
				Uri uri = Uri.parse("content://call_log/calls");
				getContentResolver().registerContentObserver(uri, true, new CallLogObserver( incomingNumber,new Handler()));
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				System.out.println("����״̬..");
				break;
			}
		}
	}
	
	private class CallLogObserver extends ContentObserver{
		private String incomingNumber;

		public CallLogObserver(String incomingNumber,Handler handler) {
			super(handler);
			this.incomingNumber = incomingNumber;
		}

		@Override
		public void onChange(boolean selfChange) {
			System.out.println("���ݿ�����ݱ仯�ˣ������˺��м�¼");
			getContentResolver().unregisterContentObserver(this);
			deleteCallLog(incomingNumber);
			super.onChange(selfChange);
		}
		
	}
	/**
	 * ���������ṩ��ɾ�����м�¼
	 * @param incomingNumber
	 */
	public void deleteCallLog(String incomingNumber) {
		ContentResolver  resolver = getContentResolver();
		//���м�¼uri��·��
		Uri uri = Uri.parse("content://call_log/calls");
		resolver.delete(uri, "number=?", new String[]{incomingNumber});
	}
	private void endCall(){
		try {
			//����ϵͳ���ص������API�������÷���ʵ��
			Class<?> smClazz = this.getClassLoader().loadClass("android.os.ServiceManager");
			Method getServiceMethod = smClazz.getMethod("getService", String.class);
			IBinder ibinder = (IBinder) getServiceMethod.invoke(smClazz.newInstance(),Context.TELEPHONY_SERVICE);
			ITelephony telephony = ITelephony.Stub.asInterface(ibinder);
			telephony.endCall();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		listener = new MyPhoneStateListener();
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
	}
	
	@Override
	public void onDestroy() {
		if(listener != null && tm != null){
			tm.listen(listener, PhoneStateListener.LISTEN_NONE);
		}
		super.onDestroy();
	}

}
