package com.harsukh.testapp;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ISpeechObserver {

    SpeechRecognizerWrapper speech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        speech = new SpeechRecognizerWrapper();
        speech.initializeSpeechService(this, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        speech.stopListeningSpeechService();
    }


    public void endOfSpeech() {
        speech.endOfSpeech();
    }

    @Override
    public void restart() {
        speech.endOfSpeech();
        speech.startOfSpeech(this);
    }

    @Override
    public void setText(ArrayList<String> matches) {
        StringBuilder listString = new StringBuilder();
        for (String s : matches) {
            listString.append(s + " ");
        }
        EditText editText = (EditText) findViewById(R.id.edit);
        editText.append(listString.toString());
        speech.startOfSpeech(this);
    }

    @Override
    public void emitData(ArrayList<String> matches) {
        com.github.nkzawa.socketio.client.Socket socket = SocketObject.getInstance(MessageSMSListener.URI);
        StringBuilder listString = new StringBuilder();
        for (String s : matches) {
            listString.append(s + " ");
        }
        socket.emit("device_msg", "{\n" +
                "\"task\": \"vr_command\",\n" +
                "\"data\": \"" + listString.toString() + "\"\n" +
                "}");
    }
}
