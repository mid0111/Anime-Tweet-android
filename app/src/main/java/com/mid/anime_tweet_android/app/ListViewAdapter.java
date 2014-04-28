package com.mid.anime_tweet_android.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mid on 14/04/27.
 */
public class ListViewAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> values;

    public ListViewAdapter(Context context, ArrayList<String> values) {
        super(context, R.layout.item_tweet, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public void add(String value) {
        super.add(value);

        // TODO メモリを消費しないように直近の30個だけリストを保持する

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_tweet, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.textView);

        textView.setText(values.get(position));

        return rowView;
    }
}
