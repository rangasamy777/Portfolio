package org.envisiontechllc.supertutor.network.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.adapters.FollowerAdapter;
import org.envisiontechllc.supertutor.internal.utils.Utilities;
import org.envisiontechllc.supertutor.internal.wrappers.FollowerStat;
import org.envisiontechllc.supertutor.internal.wrappers.User;
import org.envisiontechllc.supertutor.network.Network;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by EmileBronkhorst on 26/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class FollowerDownloader extends AsyncTask<User, Void, List<FollowerStat>> {

    private Context ctx;
    private ListView followerList;
    private FollowerAdapter adapter;
    private List<FollowerStat> stats;

    public FollowerDownloader(Context ctx, ListView followerList){
        this.ctx = ctx;
        this.stats = new ArrayList<>();
        this.followerList = followerList;
    }

    @Override
    protected List<FollowerStat> doInBackground(User... params) {

        try {
            User user = params[0];
            if(user != null){
                String tempUrl = String.format(Network.API_URL, "followers/getFollowers/" + Utilities.encodeParam(user.getUsername()));
                URL url = new URL(tempUrl);

                HttpURLConnection conn = Network.getConnection(url);
                if(conn != null){
                    String pageData = Network.getPageData(conn.getInputStream());
                    if(pageData != null){
                        JSONArray obArray = new JSONArray(pageData);
                        for(int i = 0; i < obArray.length(); i++){
                            JSONObject userStat = obArray.getJSONObject(i);
                            if(userStat != null){
                                String username = userStat.getString("username");
                                String status = userStat.getString("status");
                                String imageBytes = userStat.getString("imageBytes");

                                stats.add(new FollowerStat(username, status, imageBytes));
                            }
                        }
                    }
                }

            }
        }catch(IOException | JSONException ex){}

        return stats;
    }

    @Override
    public void onPostExecute(List<FollowerStat> stats){
        adapter = new FollowerAdapter(ctx, R.layout.follower_row, stats);
        followerList.setAdapter(adapter);
    }
}
