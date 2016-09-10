package com.harsukh.testapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;


public class MessageSMSListener extends BroadcastReceiver {
    public static final String key_extra = "numbers";
    public static final String key_extra_2 = "msgs";
    public static final String URI = "http://10.251.2.10:1337";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String[] msg_body;
        String[] msg_from;
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            msg_from = new String[pdus.length];
            msg_body = new String[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                msg_body[i] = msgs[i].getMessageBody();
                msg_from[i] = getContactName(context, msgs[i].getOriginatingAddress());
                Toast.makeText(context, msg_body + " : " + msg_from, Toast.LENGTH_SHORT).show();
            }
            Intent intent1 = new Intent(context.getApplicationContext(), VRService.class);
//            intent.putExtra(key_extra, msg_from);
//            intent.putExtra(key_extra_2, msg_body);
            VRService.getMessages(msg_from, msg_body);
            context.startService(intent1);
        } else {
            Log.d("message", "bundle is null");
        }
    }

    private String getContactName(Context context, String number) {
        return null;
    }
}
