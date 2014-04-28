package com.mid.anime_tweet_android.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import com.mid.anime_tweet_android.socket.OnMessageHandler;
import com.mid.anime_tweet_android.socket.SocketConnector;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class MainActivity extends Activity implements OnMessageHandler {

    private static final String TAG = MainActivity.class.getName();
    Handler handler;
    private ListViewAdapter listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<String> list = new ArrayList<String>();
        listViewAdapter = new ListViewAdapter(this, list);

        // ListView
        final ListView listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(listViewAdapter);

        handler = new Handler();

        // Socket作成
        try {
            SocketConnector socket = new SocketConnector(handler);
            socket.setOnMessageHandler(this);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Failed to create socket.", e);
            return;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMessage(String message) {
        listViewAdapter.add(message);
    }
}
