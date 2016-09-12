package com.harsukh.testapp;

import java.util.ArrayList;

/**
 * Created by harsukh on 9/12/16.
 */
public interface ISpeechObserver {
    void setText(ArrayList<String> matches);

    void emitData(ArrayList<String> matches);

    void endOfSpeech();

    void restart();
}
