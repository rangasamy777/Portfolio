package org.envisiontechllc.supertutor.internal.library;

import org.envisiontechllc.supertutor.internal.wrappers.User;
import org.envisiontechllc.supertutor.internal.wrappers.boards.Reply;
import org.envisiontechllc.supertutor.internal.wrappers.boards.XBoard;
import org.envisiontechllc.supertutor.settings.AppContext;

import java.util.List;

/**
 * Created by EmileBronkhorst on 23/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class DiscussionBoard {

    private static DiscussionBoard instance;
    private AppContext context;
    private XBoard board;

    private DiscussionBoard(){
        this.context = AppContext.getContext();
    }

    public void setBoard(XBoard board){
        this.board = board;
    }

    public void addReply(Reply reply){
        this.board.getReplies().add(reply);
    }

    public List<Reply> getReplies(){
        return this.board.getReplies();
    }

    public XBoard getBoard(){
        return this.board;
    }

    public User getCurrentUser(){
        return this.context.getCurrentUser();
    }

    public static DiscussionBoard getInstance(){
        if(instance == null){
            instance = new DiscussionBoard();
        }
        return instance;
    }
}
