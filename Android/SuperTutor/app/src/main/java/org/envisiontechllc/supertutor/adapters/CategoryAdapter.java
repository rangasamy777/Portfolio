package org.envisiontechllc.supertutor.adapters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.adapters.holder.CategoryHolder;
import org.envisiontechllc.supertutor.internal.wrappers.boards.Category;

import java.util.List;

/**
 * Created by EmileBronkhorst on 22/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {

    private AppCompatActivity activity;
    private Context ctx;
    private List<Category> categories;
    private BoardAdapter adapter;
    private RecyclerView view;

    public CategoryAdapter(AppCompatActivity activity, Context ctx, RecyclerView view, List<Category> categories){
        this.activity = activity;
        this.ctx = ctx;
        this.categories = categories;
        this.view = view;
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.category_card, parent, false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, int position) {
        final Category category = categories.get(position);
        if(category != null){
            holder.setTitle(category.getTitle());
            holder.setCategoryDescription(category.getDescription());
            holder.setImage(ctx, category.getImageName());
            holder.getParentView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter = new BoardAdapter(activity, ctx, category);
                    view.setAdapter(adapter);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.categories.size();
    }
}
