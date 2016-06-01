package org.envisiontechllc.supertutor.network.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.adapters.FeedAdapter;
import org.envisiontechllc.supertutor.internal.utils.Utilities;
import org.envisiontechllc.supertutor.internal.wrappers.ProfileFeed;
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
 * Created by EmileBronkhorst on 27/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class FeedDownloader extends AsyncTask<String, Void, Integer> {

    private Context ctx;
    private ListView list;
    private List<ProfileFeed> feed;

    public FeedDownloader(Context ctx, ListView list){
        this.ctx = ctx;
        this.feed = new ArrayList<>();
        this.list = list;
    }


    @Override
    protected Integer doInBackground(String... params) {

        try {
            String username = Utilities.encodeParam(params[0]);
            String tempUrl = String.format(Network.API_URL, "followers/getFeed/" + username);

            URL url = new URL(tempUrl);
            HttpURLConnection conn = Network.getConnection(url);
            if(conn != null){
                String pageData = Network.getPageData(conn.getInputStream());
                JSONArray feedArray = new JSONArray(pageData);

                if(feedArray != null){
                    for(int i = 0; i < feedArray.length(); i++){
                        JSONObject feedOb = feedArray.getJSONObject(i);
                        if(feedOb != null){
                            String subjectName = feedOb.getString("subjectname");
                            String category = feedOb.getString("categoryname");
                            String timeStamp = feedOb.getString("timestamp");

                            feed.add(new ProfileFeed(subjectName, category, timeStamp));
                        }
                    }
                    return 200;
                }
            }

        }catch(IOException | JSONException ex){}

        return -1;
    }

    @Override
    public void onPostExecute(Integer response){
        FeedAdapter adapter = new FeedAdapter(ctx, R.layout.profile_feed_row, feed);
        if(adapter != null && list != null){
            list.setAdapter(adapter);
        }
    }
}
