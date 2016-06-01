package org.envisiontechllc.supertutor.adapters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.internal.wrappers.boards.Reply;

import java.util.List;

/**
 * Created by EmileBronkhorst on 23/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class ChatAdapter extends ArrayAdapter<Reply> implements View.OnClickListener {

    private AppCompatActivity activity;
    private TextView author, content, timestamp;
    private List<Reply> replies;

    public ChatAdapter(AppCompatActivity activity, Context context, int resource, List<Reply> replies) {
        super(context, resource, replies);
        this.activity = activity;
        this.replies = replies;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){

            boolean odd = (position % 2 > 0 ? false : true);

            convertView = LayoutInflater.from(getContext()).inflate((odd ? R.layout.chat_row_even : R.layout.chat_row_odd), parent, false);

            Reply reply = replies.get(position);
            if(reply != null){
                author = (TextView)convertView.findViewById(R.id.chat_even_author);
                content = (TextView)convertView.findViewById(R.id.chat_even_content);
                timestamp = (TextView)convertView.findViewById(R.id.chat_even_timestamp);

                author.setText(reply.getAuthorName());
                content.setText(reply.getContent());
                timestamp.setText(reply.getTimestamp());

                convertView.setOnClickListener(this);
            }
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return replies.size();
    }

    @Override
    public void onClick(View v) {

    }
}
