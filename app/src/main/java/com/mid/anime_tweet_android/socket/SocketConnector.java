package com.mid.anime_tweet_android.socket;

import android.os.Handler;
import android.util.Log;
import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;

/**
 * Created by mid on 14/04/27.
 */
public class SocketConnector {

    private static final String TAG = SocketConnector.class.getClass().getName();
    public static final String SOCKET_SERVER = "http://192.168.0.6:3000/";
    OnMessageHandler messageHandler;

    public void setOnMessageHandler(OnMessageHandler handler) {
        this.messageHandler = handler;
    }

    public SocketConnector(final Handler handler) throws MalformedURLException {
        SocketIO socket = new SocketIO(SOCKET_SERVER);
        socket.connect(new IOCallback() {
            @Override
            public void onConnect() {
                Log.i(TAG, "Connection established");
            }

            @Override
            public void on(String event, IOAcknowledge ack, Object... args) {
                JSONObject json = (JSONObject) args[0];
                final String message;
                try {
                    message = json.getString("message");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            messageHandler.onMessage(message);
                        }
                    });
                } catch (JSONException e) {
                    Log.e(TAG, "Failed to parse message", e);
                }
                Log.i(TAG, "Server triggered event '" + event + "'");
            }

            @Override
            public void onDisconnect() {
                Log.i(TAG, "Connection terminated.");
            }

            @Override
            public void onError(SocketIOException e) {
                Log.e(TAG, "Socket error occured.", e);
            }

            @Override
            public void onMessage(String s, IOAcknowledge ioAcknowledge) {
                Log.e(TAG, "OnMessage: " + s);
            }

            @Override
            public void onMessage(JSONObject jsonObject, IOAcknowledge ioAcknowledge) {
                Log.e(TAG, "OnMessage: " + jsonObject.toString());
            }

        });

    }
}
