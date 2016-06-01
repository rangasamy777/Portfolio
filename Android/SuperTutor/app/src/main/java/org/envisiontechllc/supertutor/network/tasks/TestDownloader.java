package org.envisiontechllc.supertutor.network.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.envisiontechllc.supertutor.adapters.pagers.QuizPager;
import org.envisiontechllc.supertutor.internal.testing.Quiz;
import org.envisiontechllc.supertutor.internal.utils.Utilities;
import org.envisiontechllc.supertutor.internal.wrappers.Subject;
import org.envisiontechllc.supertutor.internal.wrappers.TestQuestion;
import org.envisiontechllc.supertutor.network.Network;
import org.envisiontechllc.supertutor.subactivities.TestActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by EmileBronkhorst on 27/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class TestDownloader extends AsyncTask<Subject, Integer, List<TestQuestion>> {


    private Context ctx;
    private List<TestQuestion> questions;
    private ProgressDialog dialog;

    public TestDownloader(Context ctx){
        this.ctx = ctx;
        this.questions = new ArrayList<>();
    }

    @Override
    public void onPreExecute(){
        questions = new ArrayList<>();
        dialog = new ProgressDialog(ctx);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Downloading test");
        dialog.setMessage("Please wait...");

        dialog.show();
    }

    @Override
    protected List<TestQuestion> doInBackground(Subject... params) {

        try {
            String tempUrl = String.format(Network.NETWORK_URL, "files/downloadTest/" + Utilities.encodeParam(params[0].getSubjectName()));
            URL url = new URL(tempUrl);

            HttpURLConnection conn = Network.getConnection(url);
            if(conn != null){
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String input = "";

                while((input = br.readLine()) != null){
                    String iParams[] = input.split("-");

                    try {
                        int number = Integer.parseInt(iParams[0]), correctIndex = Integer.parseInt(iParams[6]);
                        String question = iParams[1];

                        TestQuestion newQuestion = new TestQuestion(number, question, correctIndex);

                        for(int i = 2; i < iParams.length - 1; i++){
                            String answer = iParams[i];
                            newQuestion.addAnswer(answer);
                        }

                        Log.d("QUESTION", newQuestion.getQuestion() + "|Answers: " + Arrays.toString(newQuestion.getAnswers().toArray()) + "|Index: " + newQuestion.getCorrectIndex());

                        questions.add(newQuestion);
                    }catch(Exception ex){ex.printStackTrace();}
                }
            }
        }catch(IOException ex){}

        return questions;
    }

    @Override
    public void onPostExecute(List<TestQuestion> response){
        dialog.dismiss();

        if(response.size() > 0){
            Quiz.getInstance().setQuestions(response);

            Intent quizIntent = new Intent(ctx, TestActivity.class);
            ctx.startActivity(quizIntent);
        } else {
            Utilities.showToast(ctx, "Failed to download test", Toast.LENGTH_SHORT);
        }
    }
}
