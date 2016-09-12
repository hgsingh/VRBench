package com.harsukh.testapp;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

/**
 * Created by harsukh on 9/12/16.
 */
public class SpeechRecognizerWrapper {
    private static Intent mSpeechRecognizerIntent = null;
    private static SpeechRecognizer speechRecognizer = null;
    private static VoiceRecognition voiceRecognition;


    public SpeechRecognizerWrapper() {
    }

    public void initializeSpeechService(Context context, ISpeechObserver speechObserver) {
        if (speechRecognizer == null) {
            voiceRecognition = new VoiceRecognition();
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
            speechRecognizer.setRecognitionListener(voiceRecognition);
        }
        if (mSpeechRecognizerIntent == null) {
            mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);

        }
        voiceRecognition.addObserver(speechObserver);
        startOfSpeech(context);
    }

    // stops the service
    public void stopListeningSpeechService() {
        if (speechRecognizer != null) {
            speechRecognizer.stopListening();
            speechRecognizer.cancel();
            speechRecognizer.destroy();
        }
        speechRecognizer = null;
    }

    public void endOfSpeech() {
        speechRecognizer.stopListening();
    }

    public void startOfSpeech(Context context) {
        AudioManager amanager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        amanager.setStreamMute(AudioManager.STREAM_MUSIC, true);
        speechRecognizer.startListening(mSpeechRecognizerIntent);
    }
}
