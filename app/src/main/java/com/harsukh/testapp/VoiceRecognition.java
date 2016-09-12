package com.harsukh.testapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;


import com.github.nkzawa.socketio.client.Socket;

import java.util.ArrayList;

import hugo.weaving.DebugLog;


public class VoiceRecognition implements RecognitionListener {

    private Context context;
    private static final String TAG = "VoiceRec";
    private static final CharSequence initString = "Okay On Star";
    private Socket socket;
    private MainActivity activity;


    public VoiceRecognition(Context applicationContext, Socket instance, MainActivity activity) {
        Log.d(TAG, "listening");
        this.context = applicationContext;
        this.socket = instance;
        this.activity = activity;
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
        activity.endOfSpeech();
    }

    @DebugLog
    @Override
    public void onError(int i) {
        activity.stopListeningActivity();
        activity.startListeningActivity();
    }

    @DebugLog
    @Override
    public void onResults(Bundle bundle) {
        ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        for (String command : matches) {
            System.out.println(command);
        }
        sendString(matches);
    }

    private void sendString(ArrayList<String> matches) {
        StringBuilder listString = new StringBuilder();
        for (String s : matches) {
            listString.append(s + " ");
        }
        socket.connect();
        socket.emit("device_msg", "{\n" +
                "\"task\": \"vr_command\",\n" +
                "\"data\": \"" + listString.toString() + "\"\n" +
                "}");
        activity.setText(listString.toString());
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
