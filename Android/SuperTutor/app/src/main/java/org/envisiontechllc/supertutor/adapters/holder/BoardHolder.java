package org.envisiontechllc.supertutor.adapters.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.envisiontechllc.supertutor.R;

/**
 * Created by EmileBronkhorst on 23/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class BoardHolder extends RecyclerView.ViewHolder {

    private TextView boardTitle, boardReplies;
    private Button viewBoard;

    public BoardHolder(View itemView) {
        super(itemView);

        boardTitle = (TextView) itemView.findViewById(R.id.board_title);
        boardReplies = (TextView)itemView.findViewById(R.id.board_replies);
        viewBoard = (Button)itemView.findViewById(R.id.board_view_button);
    }

    public void setBoardTitle(String title){
        this.boardTitle.setText(title);
    }

    public void setBoardReplies(int replies){
        this.boardReplies.setText("Replies: " + replies);
    }

    public void setClickListener(Button.OnClickListener listener){
        this.viewBoard.setOnClickListener(listener);
    }
}
