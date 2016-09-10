package com.harsukh.testapp;

import android.app.IntentService;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

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
     * <p/>
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
//        speechRecognizer.setRecognitionListener(new VoiceRecognition(this));
//        speechRecognizer.startListening(mSpeechRecognizerIntent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        currentList = intent.getStringArrayExtra(MessageSMSListener.key_extra);
        current_messages = intent.getStringArrayExtra(MessageSMSListener.key_extra_2);
        Socket socketInstance = SocketObject.getInstance(MessageSMSListener.URI);
        socketInstance.connect();
        for (int i = 0; i < currentList.length; ++i) {
            //send messages
            socketInstance.emit(event, "{\ntask: " + "‘sms’,\ndata:'" + current_messages[i] + "‘}");
        }
    }
}
