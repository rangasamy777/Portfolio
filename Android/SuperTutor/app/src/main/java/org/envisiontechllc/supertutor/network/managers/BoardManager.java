package org.envisiontechllc.supertutor.network.managers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.envisiontechllc.supertutor.adapters.CategoryAdapter;
import org.envisiontechllc.supertutor.internal.wrappers.boards.Category;
import org.envisiontechllc.supertutor.internal.wrappers.boards.XBoard;
import org.envisiontechllc.supertutor.internal.wrappers.boards.Reply;
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
 * Created by EmileBronkhorst on 22/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class BoardManager extends AsyncTask<Void, Integer, List<Category>> {

    private AppCompatActivity activity;
    private Context ctx;
    private List<Category> list;
    private ProgressDialog dialog;
    private RecyclerView cardView;

    public BoardManager(AppCompatActivity activity, Context ctx, RecyclerView cardView){
        this.activity = activity;
        this.ctx = ctx;
        this.cardView = cardView;
        this.list = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        dialog = new ProgressDialog(ctx, ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Downloading");
        dialog.setMessage("Downloading categories...");
        dialog.show();
    }

    @Override
    protected List<Category> doInBackground(Void... params) {

        try {
            String tempUrl = String.format(Network.NETWORK_URL, "boards/getCategories");
            URL url = new URL(tempUrl);

            HttpURLConnection conn = Network.getConnection(url);
            if(conn != null){
                String dataString = Network.getPageData(conn.getInputStream());
                JSONArray catArray = new JSONArray(dataString);

                if(catArray != null && catArray.length() > 0){
                    for(int i = 0; i < catArray.length(); i++){
                        JSONObject catObj = catArray.getJSONObject(i);
                        if(catObj != null){
                            String title = catObj.getString("title");
                            String description = catObj.getString("description");
                            String imageName = catObj.getString("imageName");

                            Category newCat = new Category(title, description, imageName);
                            if(newCat != null){
                                JSONArray boardArray = catObj.getJSONArray("boards");
                                if(boardArray != null){
                                    for(int j = 0; j < boardArray.length(); j++){
                                        JSONObject boardObj = boardArray.getJSONObject(j);

                                        String boardTitle = boardObj.getString("boardTitle");
                                        JSONArray replies = boardObj.getJSONArray("replies");

                                        XBoard board = new XBoard(boardTitle);

                                        if(replies != null){
                                            for(int k = 0; k < replies.length(); k++){
                                                JSONObject replyObj = replies.getJSONObject(k);
                                                if(replyObj != null){
                                                    String timestamp = replyObj.getString("timestamp");
                                                    String content = replyObj.getString("content");
                                                    String authorName = replyObj.getString("authorName");

                                                    board.addReply(new Reply(authorName, timestamp, content));
                                                }
                                            }
                                            newCat.addBoard(board);
                                        }
                                    }
                                }
                            }
                            list.add(newCat);
                            publishProgress(i, catArray.length());
                        }
                    }
                }
            }
        }catch(IOException | JSONException ex){}

        return list;
    }

    @Override
    protected void onPostExecute(List<Category> categories) {
        super.onPostExecute(categories);

        CategoryAdapter adapter = new CategoryAdapter(activity, ctx, cardView, categories);
        if(cardView != null && adapter != null){
            cardView.setLayoutManager(new LinearLayoutManager(ctx));
            cardView.setItemAnimator(new DefaultItemAnimator()) ;
            cardView.setAdapter(adapter);
        }

        dialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        dialog.setProgress(values[0]);
        dialog.setMax(values[1]);
        dialog.setIndeterminate(false);
    }
}
