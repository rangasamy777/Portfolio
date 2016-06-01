package org.envisiontechllc.supertutor.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.adapters.holder.BoardHolder;
import org.envisiontechllc.supertutor.internal.library.DiscussionBoard;
import org.envisiontechllc.supertutor.internal.wrappers.boards.Category;
import org.envisiontechllc.supertutor.internal.wrappers.boards.XBoard;
import org.envisiontechllc.supertutor.subactivities.fragments.ChatFragment;

/**
 * Created by EmileBronkhorst on 23/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class BoardAdapter extends RecyclerView.Adapter<BoardHolder> {

    private AppCompatActivity activity;
    private Context ctx;
    private Category category;

    public BoardAdapter(AppCompatActivity activity, Context ctx, Category category){
        this.activity = activity;
        this.ctx = ctx;
        this.category = category;
    }

    @Override
    public BoardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BoardHolder(LayoutInflater.from(ctx).inflate(R.layout.board_row, parent, false));
    }

    @Override
    public void onBindViewHolder(BoardHolder holder, int position) {
        final XBoard board = category.getBoards().get(position);
        if(board != null){
            holder.setBoardTitle(board.getBoardTitle());
            holder.setBoardReplies(board.getReplies().size());
            holder.setClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChatFragment chatFragment = new ChatFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("boardName", board.getBoardTitle());
                    chatFragment.setArguments(bundle);

                    DiscussionBoard.getInstance().setBoard(board);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.includeLayout, chatFragment).commit();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return category.getBoards().size();
    }
}
