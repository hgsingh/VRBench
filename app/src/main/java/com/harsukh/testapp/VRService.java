package com.harsukh.testapp;

import android.app.IntentService;
import android.content.Intent;
import android.os.Debug;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import com.github.nkzawa.socketio.client.Socket;

public class VRService extends IntentService {
    private static SpeechRecognizer speechRecognizer = null;
    private static final String TAG = "VRService";
    private static final String event = "device_msg";
    static String[] currentList;
    static String[] current_messages;
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
//        speechRecognizer.setRecognitionListener(new VoiceRecognition(this));
//        speechRecognizer.startListening(mSpeechRecognizerIntent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Socket socketInstance = SocketObject.getInstance(MessageSMSListener.URI);
        socketInstance.connect();
        for(int i = 0; i< currentList.length; ++i)
        {
            socketInstance.emit(event, "{\n" +
                    "    'task': 'sms_new_message',\n" +
                    "    'data': '" + current_messages[i]+"',\n" +
                    "    'sender': '" + currentList[i]+"'\n" +
                    "}");
        }
    }

    public static void getMessages(String[] names, String[] messageBody) {
        currentList = names;
        current_messages = messageBody;
    }
}
