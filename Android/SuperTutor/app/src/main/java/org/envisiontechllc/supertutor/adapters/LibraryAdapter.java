package org.envisiontechllc.supertutor.adapters;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.data.SQLManager;
import org.envisiontechllc.supertutor.internal.utils.Utilities;
import org.envisiontechllc.supertutor.internal.wrappers.Subject;
import org.envisiontechllc.supertutor.network.tasks.FileDownloader;
import org.envisiontechllc.supertutor.settings.AppContext;

import java.util.List;

/**
 * Created by EmileBronkhorst on 18/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class LibraryAdapter extends ArrayAdapter<Subject> implements Button.OnClickListener {

    private final int CAPTION_SIZE = 12;

    private Context ctx;
    private List<Subject> subjects;

    private SQLManager dbManager;
    private Dialog downloadDialog;

    public LibraryAdapter(Context context, int resource, List<Subject> subjects) {
        super(context, resource, subjects);
        this.subjects = subjects;
        this.ctx = context;
        this.dbManager = new SQLManager(context);
    }

    @Override
    public int getCount() {
        return subjects.size();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view == null){
            view = LayoutInflater.from(ctx).inflate(R.layout.search_result, parent, false);

            final Subject subject = subjects.get(position);

            TextView title = (TextView)view.findViewById(R.id.search_title);
            TextView description = (TextView)view.findViewById(R.id.search_description);
            TextView size = (TextView)view.findViewById(R.id.search_contentSize);

            title.setText(subject.getSubjectName());
            description.setText(subject.getDescription());

            if(!dbManager.isDownloaded(subject)){
                size.setText(Utilities.formatFileSize(subject.getContentSize()));
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(subject);
                    }
                });
            } else {
                size.setText("Downloaded");
                size.setTextSize(CAPTION_SIZE);
            }
        }

        return view;
    }

    private void showDialog(final Subject subject){
        downloadDialog = new Dialog(ctx);
        downloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        downloadDialog.setContentView(R.layout.download_dialog);

        Button cancel = (Button)downloadDialog.findViewById(R.id.dialog_cancel);
        Button download = (Button) downloadDialog.findViewById(R.id.dialog_download);
        TextView title = (TextView)downloadDialog.findViewById(R.id.dialog_title);
        TextView desc = (TextView)downloadDialog.findViewById(R.id.dialog_description);

        if(title != null && desc != null && cancel != null && download != null){
            title.setText(subject.getSubjectName());
            desc.setText(subject.getDescription());
            download.setText("Download \n(" + Utilities.formatFileSize(subject.getContentSize()) + ")");
            download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    downloadDialog.dismiss();
                    new FileDownloader(ctx, subject).execute(AppContext.getContext().getCurrentUser());
                }
            });
            cancel.setOnClickListener(this);
        }

        downloadDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.dialog_cancel:
                downloadDialog.dismiss();
                break;
        }
    }
}
