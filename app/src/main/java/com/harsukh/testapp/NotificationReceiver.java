package com.harsukh.testapp;

import android.content.Context;
import android.os.Handler;

import com.github.nkzawa.emitter.Emitter;

import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class NotificationReceiver {


    private final static ExecutorService emitter = Executors.newSingleThreadExecutor();
    private Context context;

    public NotificationReceiver(Context context) {
        this.context = context;
    }

    public void onEmitterAdded(final Emitter emitterObject) {
        emitter.submit(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
