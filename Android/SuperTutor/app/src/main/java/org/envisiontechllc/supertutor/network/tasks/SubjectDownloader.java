package org.envisiontechllc.supertutor.network.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.adapters.LibraryAdapter;
import org.envisiontechllc.supertutor.internal.wrappers.Subject;
import org.envisiontechllc.supertutor.internal.wrappers.Topic;
import org.envisiontechllc.supertutor.network.Network;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by EmileBronkhorst on 18/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class SubjectDownloader extends AsyncTask<Void, Void, List<Subject>> {

    private Context ctx;
    private ProgressDialog dialog;
    private List<Subject> subjects;
    private ListView list;
    private LibraryAdapter adapter;

    public SubjectDownloader(Context ctx, List<Subject> subjects, ListView list){
        this.ctx = ctx;
        this.subjects = subjects;
        this.list = list;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        dialog = new ProgressDialog(ctx, ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Downloading Subject LibraryFragment...");
        dialog.setMessage("Downloading, please wait...");
        dialog.show();
    }

    @Override
    protected List<Subject> doInBackground(Void... params) {

        try {
            String tempUrl = String.format(Network.NETWORK_URL, "files/getSubjects");
            URL url = new URL(tempUrl);

            HttpURLConnection conn = Network.getConnection(url);
            if(conn != null && conn.getResponseCode() == Network.RESPONSE_OK){
                String jsonData = Network.getPageData(conn.getInputStream());
                JSONArray subjectArray = new JSONArray(jsonData);

                if(subjectArray != null){
                    for(int i = 0; i < subjectArray.length(); i++){
                        JSONObject subjectData = subjectArray.getJSONObject(i);
                        if(subjectData != null){
                            String subjectName = subjectData.getString("subjectName");
                            String description = subjectData.getString("description");
                            long contentSize = subjectData.getLong("contentSize");

                            Subject subject = new Subject(subjectName, description, contentSize);

                            JSONArray topicArray = subjectData.getJSONArray("topics");
                            if(topicArray != null){
                                for(int j = 0; j < topicArray.length(); j++){
                                    JSONObject topicData = topicArray.getJSONObject(j);
                                    if(topicData != null){
                                        String topicName = topicData.getString("topicName");
                                        String topicDescription = topicData.getString("topicTag");
                                        String fileName = topicData.getString("fileName");

                                        Topic tempTopic = new Topic(topicName, topicDescription, fileName);
                                        subject.addTopic(tempTopic);
                                    }
                                }
                            }
                            Log.d("DOWNLOAD", subject.getSubjectName() + "|" + subject.getTopics().size());
                            this.subjects.add(subject);
                        }
                    }
                }
            }
        }catch(Exception ex){}

        return subjects;
    }

    @Override
    protected void onPostExecute(List<Subject> subjects) {
        super.onPostExecute(subjects);

        if(subjects.size() >= 0){
            adapter = new LibraryAdapter(ctx, R.layout.search_result, subjects);
            if(adapter != null){
                list.setAdapter(adapter);
            }
        }

        dialog.dismiss();
    }
}
