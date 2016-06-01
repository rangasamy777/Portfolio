package org.envisiontechllc.supertutor.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.internal.utils.Utilities;
import org.envisiontechllc.supertutor.internal.wrappers.FollowerStat;
import org.envisiontechllc.supertutor.subactivities.dialogs.ProfileDialog;

import java.util.List;

/**
 * Created by EmileBronkhorst on 26/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class FollowerAdapter extends ArrayAdapter<FollowerStat> {

    private Context ctx;
    private List<FollowerStat> list;

    public FollowerAdapter(Context context, int resource, List<FollowerStat> objects) {
        super(context, resource, objects);
        this.ctx = context;
        this.list = objects;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view == null){
            if(list.size() > 0){
                view = LayoutInflater.from(ctx).inflate(R.layout.follower_row, parent, false);

                final FollowerStat stat = list.get(position);
                if(stat != null){
                    TextView username = (TextView)view.findViewById(R.id.follower_profile_username);
                    TextView status = (TextView)view.findViewById(R.id.follower_profile_status);
                    ImageView profileImg = (ImageView)view.findViewById(R.id.follower_profile_image);

                    username.setText(stat.getUsername());
                    status.setText(stat.getStatus());
                    if(stat.getImageBytes() != null && !stat.getImageBytes().equalsIgnoreCase("null") && !stat.getImageBytes().isEmpty()){
                        profileImg.setImageBitmap(Utilities.decodeImageFromStream(stat.getImageBytes()));
                    } else {
                        profileImg.setImageDrawable(ctx.getResources().getDrawable(R.drawable.default_image, ctx.getTheme()));
                    }

                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle args = new Bundle();
                            args.putString("username", stat.getUsername());

                            new ProfileDialog(ctx, args).show();
                        }
                    });
                }
            } else {
                view = LayoutInflater.from(ctx).inflate(R.layout.list_warning, parent, false);

                TextView message = (TextView)view.findViewById(R.id.listWarningMessage);
                message.setText("Add friends to view their profiles!");

                ImageView icon = (ImageView)view.findViewById(R.id.warningIcon);
                icon.setImageDrawable(ctx.getResources().getDrawable(R.mipmap.users, ctx.getTheme()));
            }
        }
        return view;
    }

    @Override
    public int getCount() {
        return (list.size() > 0 ? list.size() : 1);
    }
}
