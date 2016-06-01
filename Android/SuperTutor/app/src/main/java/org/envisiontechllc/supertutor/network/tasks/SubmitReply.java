package org.envisiontechllc.supertutor.network.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.envisiontechllc.supertutor.internal.library.DiscussionBoard;
import org.envisiontechllc.supertutor.internal.utils.Utilities;
import org.envisiontechllc.supertutor.internal.wrappers.boards.Reply;
import org.envisiontechllc.supertutor.network.Network;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by EmileBronkhorst on 23/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class SubmitReply extends AsyncTask<Reply, Void, Integer> {

    private Context ctx;
    private DiscussionBoard board;

    public SubmitReply(Context ctx){
        this.ctx = ctx;
        board = DiscussionBoard.getInstance();
    }

    @Override
    protected Integer doInBackground(Reply... params) {

        try {
            if(board != null){
                Reply reply = params[0];

                String boardTitle = Utilities.encodeParam(board.getBoard().getBoardTitle()),
                        username = Utilities.encodeParam(board.getCurrentUser().getUsername()),
                        content = Utilities.encodeParam(reply.getContent());

                String tempUrl = String.format(Network.NETWORK_URL, "boards/addReply/" + boardTitle + "/" + username + "/" + content);

                URL url = new URL(tempUrl);

                HttpURLConnection conn = Network.getConnection(url);
                if(conn != null){
                    String pageData = Network.getPageData(conn.getInputStream());
                    JSONObject ob = new JSONObject(pageData);

                    if(ob != null){
                        int responseCode = ob.getInt("code");
                        return responseCode;
                    }
                }
            }
        }catch(Exception ex){}

        return -1;
    }

    @Override
    public void onPostExecute(Integer code){
        switch(code){
            case -1:case 201:
                Utilities.showToast(ctx, "Error submitting reply", Toast.LENGTH_SHORT);
                break;
        }
    }
}
