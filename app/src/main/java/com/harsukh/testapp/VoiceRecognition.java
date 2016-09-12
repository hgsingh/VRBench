package com.harsukh.testapp;


import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;



import java.util.ArrayList;
import java.util.LinkedList;

import hugo.weaving.DebugLog;


public class VoiceRecognition implements RecognitionListener {

    private static final String TAG = "VoiceRec";
    private static final CharSequence initString = "Okay On Star";
    private LinkedList<ISpeechObserver> speechObservers;

    public VoiceRecognition() {
        Log.d(TAG, "listening");
        speechObservers = new LinkedList<>();
    }


    @DebugLog
    @Override
    public void onReadyForSpeech(Bundle bundle) {

    }

    @DebugLog
    @Override
    public void onBeginningOfSpeech() {


    }

    @DebugLog
    @Override
    public void onRmsChanged(float v) {


    }

    @DebugLog
    @Override
    public void onBufferReceived(byte[] bytes) {

    }

    @Override
    public void onEndOfSpeech() {
        if (speechObservers.size() > 0) {
            for (ISpeechObserver speechObserver : speechObservers) {
                speechObserver.endOfSpeech();
            }
        }
    }

    @DebugLog
    @Override
    public void onError(int i) {
        if (speechObservers.size() > 0) {
            for (ISpeechObserver speechObserver : speechObservers) {
                speechObserver.restart();
            }
        }
    }

    @DebugLog
    @Override
    public void onResults(Bundle bundle) {
        ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        for (String command : matches) {
            System.out.println(command);
        }
        if (matches != null && speechObservers.size() > 0) {
            for (ISpeechObserver speechObserver : speechObservers) {
                speechObserver.emitData(matches);
                speechObserver.setText(matches);
            }
        }
    }


    @DebugLog
    @Override
    public void onPartialResults(Bundle bundle) {

    }

    @Override
    public void onEvent(int i, Bundle bundle) {
        //dosomething here
    }

    public void addObserver(ISpeechObserver speechObserver) {
        speechObservers.add(speechObserver);
    }
}
