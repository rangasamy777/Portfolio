package org.envisiontechllc.supertutor.adapters.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.network.tasks.ImageDownloader;

/**
 * Created by EmileBronkhorst on 22/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class CategoryHolder extends RecyclerView.ViewHolder {

    private View parentView;
    private LinearLayout viewBackground;
    private ImageView categoryImage;
    private TextView categoryTitle, categoryDescription;

    public CategoryHolder(View view) {
        super(view);
        this.parentView = view;

        categoryTitle = (TextView)view.findViewById(R.id.category_title);
        categoryDescription = (TextView)view.findViewById(R.id.category_description);
        categoryImage = (ImageView)view.findViewById(R.id.category_image);
        viewBackground = (LinearLayout)view.findViewById(R.id.category_placeholder);
        viewBackground.setAlpha(0.7f);
    }

    public void setTitle(String title){
        this.categoryTitle.setText(title);
    }

    public void setCategoryDescription(String description){
        this.categoryDescription.setText(description);
    }

    public void setImage(Context ctx, String name){
        new ImageDownloader(ctx, categoryImage).execute(name);
    }

    public View getParentView(){
        return this.parentView;
    }
}
