package com.example.drop_outgoingcall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OutgoingcallReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		//�õ��Ⲧ����
		String number = getResultData();
		System.out.println("number:"+number);
		if("15555211111".equals(number)){
			//abortBroadcast();
			setResultData(null);
		}

	}

}
