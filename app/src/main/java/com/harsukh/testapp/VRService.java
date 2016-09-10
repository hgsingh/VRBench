package com.harsukh.testapp;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.Socket;

public class VRService extends IntentService {
    private static SpeechRecognizer speechRecognizer = null;
    private static final String TAG = "VRService";
    private static final String event = "device_msg";
    String[] currentList;
    String[] current_messages;
    private Intent mSpeechRecognizerIntent = null;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     * <p>
     * Used to name the worker thread, important only for debugging.
     */
    public VRService() {
        super(VRService.class.getSimpleName());
//        if (speechRecognizer == null) {
//            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
//        }
//        if (mSpeechRecognizerIntent == null) {
//            mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
//
//        }
//        try {
//            speechRecognizer.setRecognitionListener(VoiceRecognition.getInstance(this));
//            speechRecognizer.startListening(mSpeechRecognizerIntent);
//        } catch (Exception e) {
//            Log.e(TAG, "Problem setting the listener", e);
//        }
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Socket socketInstance = SocketObject.getInstance(MessageSMSListener.URI);
        socketInstance.connect();
        for (int i = 0; i < currentList.length; ++i) {
            socketInstance.emit(event, "{\n" +
                    "    'task': 'sms_new_message',\n" +
                    "    'data': '" + current_messages[i] + "',\n" +
                    "    'sender': '" + currentList[i] + "'\n" +
                    "}");
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Bundle bundle = intent.getBundleExtra(MessageSMSListener.bundle_name);
        int numbers_length = bundle.getStringArray(MessageSMSListener.key_extra).length;
        currentList = new String[numbers_length];
        int messages_length = bundle.getStringArray(MessageSMSListener.key_extra_2).length;
        current_messages = new String[messages_length];
        for (int i = 0; i < numbers_length; ++i) {
            currentList[i] = intent.getStringArrayExtra(MessageSMSListener.key_extra)[i];
        }
        for (int i = 0; i < messages_length; ++i) {
            currentList[i] = intent.getStringArrayExtra(MessageSMSListener.key_extra_2)[i];
        }
    }
}
