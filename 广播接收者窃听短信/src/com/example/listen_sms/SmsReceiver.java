package com.example.listen_sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {
	
	private static final String TAG = "SmsReceiver";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Object[] pdus = (Object[]) intent.getExtras().get("pdus");
		for (Object object : pdus) {
			SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) object);
			String body = smsMessage.getMessageBody();
			String sender = smsMessage.getOriginatingAddress();
			long timestampMillis = smsMessage.getTimestampMillis();
			Log.i(TAG, "--------------------------");
			Log.i(TAG, "time:"+timestampMillis);
			Log.i(TAG, "sender:"+sender);
			Log.i(TAG, "body:"+body);
			
			if("1111".equals(sender)){
				abortBroadcast();
			}
			
		}
	}

}
