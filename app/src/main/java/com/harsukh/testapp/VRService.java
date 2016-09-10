package com.harsukh.testapp;

import android.app.IntentService;
import android.content.Intent;
import android.speech.RecognitionService;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

public class VRService extends IntentService {
    private static SpeechRecognizer speechRecognizer = null;
    private static final String TAG = "VRService";
    String[] currentList;
    private Intent mSpeechRecognizerIntent = null;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     * <p/>
     * Used to name the worker thread, important only for debugging.
     */
    public VRService() {
        super(VRService.class.getSimpleName());
        if (speechRecognizer == null) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        }
        if (mSpeechRecognizerIntent == null) {
            mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);

        }
        speechRecognizer.setRecognitionListener(new VoiceRecognition(this));
        speechRecognizer.startListening(mSpeechRecognizerIntent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        currentList = intent.getStringArrayExtra(MessageSMSListener.key_extra);
        for(int i = 0; i<currentList.length; ++i)
        {

        }
    }
}
