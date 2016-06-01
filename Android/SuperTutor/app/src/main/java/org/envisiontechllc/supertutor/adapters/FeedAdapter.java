package org.envisiontechllc.supertutor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.internal.wrappers.ProfileFeed;

import java.util.List;

/**
 * Created by EmileBronkhorst on 27/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class FeedAdapter extends ArrayAdapter<ProfileFeed> {

    private Context ctx;
    private List<ProfileFeed> list;

    public FeedAdapter(Context context, int resource, List<ProfileFeed> objects) {
        super(context, resource, objects);
        this.ctx = context;
        this.list = objects;
    }

    @Override
    public int getCount() {
        return (list.size() > 0 ? list.size() : 1);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view == null){
            if(list.size() > 0){
                view = LayoutInflater.from(ctx).inflate(R.layout.profile_feed_row, parent, false);

                ProfileFeed feed = list.get(position);
                if(feed != null){
                    TextView subjectName = (TextView)view.findViewById(R.id.profileFeed_subjectTitle);
                    TextView subjectCategory = (TextView)view.findViewById(R.id.profileFeed_category);
                    TextView timestamp = (TextView)view.findViewById(R.id.profileFeed_timestamp);

                    subjectName.setText(feed.getSubjectname());
                    subjectCategory.setText(feed.getCategoryname());
                    timestamp.setText("Downloaded: " + feed.getTimestamp());
                }
            } else {
                view = LayoutInflater.from(ctx).inflate(R.layout.list_warning, parent, false);

                TextView message = (TextView)view.findViewById(R.id.listWarningMessage);

                message.setText("Download topics for them to appear in your feed!");
            }
        }

        return view;
    }
}
