package com.harsukh.testapp;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import com.github.nkzawa.socketio.client.Socket;

public class VRService extends IntentService {
    private static final String TAG = "VRService";
    private static final String event = "device_msg";
    String[] currentList;
    String[] current_messages;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     * <p/>
     * Used to name the worker thread, important only for debugging.
     */
    public VRService() {
        super(VRService.class.getSimpleName());
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        currentList = intent.getStringArrayExtra(MessageSMSListener.key_extra);
        current_messages = intent.getStringArrayExtra(MessageSMSListener.key_extra_2);
        Socket socketInstance = SocketObject.getInstance(MessageSMSListener.URI);
        socketInstance.connect();
        for (int i = 0; i < currentList.length; ++i) {
            socketInstance.emit(event, "{\n" +
                    "\"task\": \"sms_new_message\",\n" +
                    "\"data\": \"" + current_messages[i] + "\",\n" +
                    "\"sender\": \"" + currentList[i] + "\"\n" +
                    "}");
        }
        this.startActivity(new Intent(this, MainActivity.class));
    }

}
