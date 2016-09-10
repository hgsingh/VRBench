package com.harsukh.testapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.speech.RecognitionListener;
import android.speech.RecognitionService;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.ArrayList;

import hugo.weaving.DebugLog;

/**
 * Created by harsukh on 9/9/16.
 */
public class VoiceRecognition implements RecognitionListener {

    private Context context;
    private static final String TAG = "VoiceRec";
    private static final CharSequence initString = "Okay On Star";

    public VoiceRecognition(Context context) {
        Log.d(TAG, "listening");
        this.context = context;
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
        context.stopService(new Intent(context, VRService.class));
    }

    @DebugLog
    @Override
    public void onError(int i) {

    }

    @DebugLog
    @Override
    public void onResults(Bundle bundle) {
        ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        for (String command : matches) {
            System.out.println(command);
            if (command.contains(initString)) {
                context.startActivity(new Intent(context, MainActivity.class));
                break;
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
}
