package com.harsukh.testapp;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

/**
 * Created by harsukh on 9/10/16.
 */
public class SocketObject {

    private static Socket socket = null;

    public static Socket getInstance(String URI) {
        try {
            socket = IO.socket(URI);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return socket;
    }

}
