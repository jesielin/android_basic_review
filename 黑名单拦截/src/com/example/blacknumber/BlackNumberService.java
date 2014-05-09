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
				System.out.println("空闲状态..");
				break;
			case TelephonyManager.CALL_STATE_RINGING:
				System.out.println("响铃状态..这里来做电话拦截");
				//拦截电话
				endCall();
				//观察呼叫记录数据库内容的变化,并删除记录
				Uri uri = Uri.parse("content://call_log/calls");
				getContentResolver().registerContentObserver(uri, true, new CallLogObserver( incomingNumber,new Handler()));
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				System.out.println("接听状态..");
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
			System.out.println("数据库的内容变化了，产生了呼叫记录");
			getContentResolver().unregisterContentObserver(this);
			deleteCallLog(incomingNumber);
			super.onChange(selfChange);
		}
		
	}
	/**
	 * 利用内容提供者删除呼叫记录
	 * @param incomingNumber
	 */
	public void deleteCallLog(String incomingNumber) {
		ContentResolver  resolver = getContentResolver();
		//呼叫记录uri的路径
		Uri uri = Uri.parse("content://call_log/calls");
		resolver.delete(uri, "number=?", new String[]{incomingNumber});
	}
	private void endCall(){
		try {
			//由于系统隐藏掉了相关API，所以用反射实现
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
