package org.envisiontechllc.supertutor.subactivities.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.adapters.ChatAdapter;
import org.envisiontechllc.supertutor.internal.library.*;
import org.envisiontechllc.supertutor.internal.utils.Utilities;
import org.envisiontechllc.supertutor.internal.wrappers.boards.Reply;
import org.envisiontechllc.supertutor.network.Network;
import org.envisiontechllc.supertutor.network.tasks.SubmitReply;

/**
 * Created by EmileBronkhorst on 23/04/16.
 * Copyright 2016 Envision Tech LLC
 */
public class ChatFragment extends Fragment {

    private AppCompatActivity activity;
    private Context ctx;

    private DiscussionBoard board;
    private ChatAdapter adapter;
    private ListView chatList;
    private Button postReply;
    private EditText chatMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstance){
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.chat_fragment, parent, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        initFragment(context);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            activity.getSupportActionBar().setTitle(bundle.getString("boardName"));

            board = DiscussionBoard.getInstance();
            adapter = new ChatAdapter(activity, view.getContext(), R.layout.chat_row_even, board.getReplies());

            chatList = (ListView)view.findViewById(R.id.chat_view);
            chatList.setAdapter(adapter);
            chatList.setSelection(adapter.getCount() - 1);

            chatMessage = (EditText)view.findViewById(R.id.chat_textInput);

            postReply = (Button)view.findViewById(R.id.chat_send);
            postReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleMessage();
                }
            });
        }
    }

    private void initFragment(Context ctx){
        this.ctx = ctx;
        activity = (AppCompatActivity)getActivity();
    }

    private void handleMessage(){
        try {
            String msg = chatMessage.getText().toString();
            if(msg != null && !msg.isEmpty() && msg.length() > 3){
                Reply newReply = new Reply(board.getCurrentUser().getUsername(), Utilities.getTimestamp(), chatMessage.getText().toString());
                SubmitReply replyHandler = new SubmitReply(ctx);
                replyHandler.execute(newReply);

                if (replyHandler.get() == Network.RESPONSE_OK) {
                    board.addReply(newReply);
                    adapter = new ChatAdapter(activity, ctx, R.layout.chat_row_even, board.getReplies());
                    chatList.setAdapter(adapter);
                    chatList.setSelection(adapter.getCount() - 1);
                    chatMessage.setText("");
                }
            } else {
                Utilities.showToast(ctx, "Enter a valid message to send.", Toast.LENGTH_SHORT);
            }
        }catch(Exception ex){}
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.discussion_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.board_refresh:
                Utilities.showToast(ctx, "Refreshing chat...", Toast.LENGTH_SHORT);
                adapter = new ChatAdapter(activity, ctx, R.layout.chat_row_even, board.getReplies());
                chatList.setAdapter(adapter);
                chatList.setSelection(adapter.getCount() - 1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
