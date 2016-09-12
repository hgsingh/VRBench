package com.harsukh.testapp;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.github.nkzawa.engineio.client.Socket;

public class MainActivity extends AppCompatActivity {

    Intent mSpeechRecognizerIntent = null;
    private static SpeechRecognizer speechRecognizer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startListeningActivity();
    }

    public void startListeningActivity() {
        if (speechRecognizer == null) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        }
        if (mSpeechRecognizerIntent == null) {
            mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);

        }
        speechRecognizer.setRecognitionListener(new VoiceRecognition(this.getApplicationContext(), SocketObject.getInstance(MessageSMSListener.URI), this));
        speechRecognizer.startListening(mSpeechRecognizerIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopListeningActivity();
    }

    // stops the service
    public void stopListeningActivity() {
        if (speechRecognizer != null) {
            speechRecognizer.stopListening();
            speechRecognizer.cancel();
            speechRecognizer.destroy();
        }
        speechRecognizer = null;
    }

    public void restart() {
        speechRecognizer.startListening(mSpeechRecognizerIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void endOfSpeech() {
        speechRecognizer.stopListening();
    }

    public void setText(String string) {
        EditText editText = (EditText) findViewById(R.id.edit);
        editText.append(string);
    }
}
