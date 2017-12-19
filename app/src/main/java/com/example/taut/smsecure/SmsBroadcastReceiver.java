package com.example.taut.smsecure;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.view.Gravity;
import android.widget.Toast;


public class SmsBroadcastReceiver extends BroadcastReceiver {

     public SmsBroadcastReceiver(){}


    public static final String SMS_BUNDLE = "pdus";

    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {


            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";
            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);
                String address = smsMessage.getOriginatingAddress();
                smsMessageStr += "SMS From: " + address ;
            }
            Toast t = Toast.makeText(context, smsMessageStr, Toast.LENGTH_SHORT);
            t.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
            t.show();
            //this will update the UI with message
            InboxActivity inst = InboxActivity.instance();
            if (inst != null)
                inst.refreshSmsInbox();
        }
    }
}
