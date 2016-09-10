package com.harsukh.testapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harsukh on 9/9/16.
 */
public class MessageSMSListener extends BroadcastReceiver {
    public static final String key_extra = "numbers";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String[] msg_body;
            String[] msg_from;
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                msgs = new SmsMessage[pdus.length];
                msg_from = new String[pdus.length];
                for (int i = 0; i < msgs.length; i++) {
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    msg_from[i] = msgs[i].getOriginatingAddress();
                    String msgBody = msgs[i].getMessageBody();
                    Toast.makeText(context, msgBody + " : " + msg_from, Toast.LENGTH_SHORT).show();
                }
                Intent intent1 = new Intent(context.getApplicationContext(), VRService.class);
                intent.putExtra("numbers",  msg_from);
                context.startService(intent1);
            }
        }
    }
}
