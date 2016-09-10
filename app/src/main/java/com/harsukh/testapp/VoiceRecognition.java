package com.harsukh.testapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.ArrayList;

import hugo.weaving.DebugLog;


public class VoiceRecognition implements RecognitionListener {

    private Context context;
    private static final String TAG = "VoiceRec";
    private static final CharSequence initString = "Okay On Star";

    private static VoiceRecognition VR = null;

    private VoiceRecognition(Context context) {
        Debug.waitForDebugger();

        Log.d(TAG, "listening");
        this.context = context;
    }

    public static VoiceRecognition getInstance(Context context) {
        if (VR == null) {
            VR = new VoiceRecognition(context);
            return VR;
        } else {
            return VR;
        }
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
