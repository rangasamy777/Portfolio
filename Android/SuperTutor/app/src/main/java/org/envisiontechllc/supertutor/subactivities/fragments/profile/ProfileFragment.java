package org.envisiontechllc.supertutor.subactivities.fragments.profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.adapters.pagers.ProfilePager;
import org.envisiontechllc.supertutor.data.SQLManager;
import org.envisiontechllc.supertutor.internal.utils.Utilities;
import org.envisiontechllc.supertutor.internal.wrappers.User;
import org.envisiontechllc.supertutor.network.Network;
import org.envisiontechllc.supertutor.network.tasks.AddFollower;
import org.envisiontechllc.supertutor.network.managers.ProfileManager;
import org.envisiontechllc.supertutor.settings.AppContext;

import java.util.concurrent.ExecutionException;

/**
 * Created by EmileBronkhorst on 18/03/16.
 * Copyright 2016 Envision Tech LLC
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private AppCompatActivity activity;
    private AppContext context;
    private AddFollower followerTask;

    private FloatingActionButton editBtn;
    private EditText profileStatus;
    private ImageView profileImage;
    private ViewPager profileFeed;
    private TabLayout tabLayout;

    private User currentUser;
    private String imageBytes;
    private int RESPONSE_IMAGE_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstance){
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.profile_layout, parent, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = AppContext.getContext();
        initFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileStatus = initEditText(view, profileStatus);
        profileImage = initProfileView(view, profileImage);
        profileFeed = initViewPager(view, profileFeed);
        editBtn = initFloatingButton(view, editBtn);
    }

    private void initFragment(){
        activity = (AppCompatActivity)getActivity();

        Bundle bundle = getArguments();
        if(bundle != null){
            User tempUser = (User)bundle.getSerializable("user");
            currentUser = (tempUser != null ? tempUser : context.getCurrentUser());
        }

        activity.getSupportActionBar().setTitle(currentUser.getUsername() + "'s profile");
    }

    private FloatingActionButton initFloatingButton(View view, FloatingActionButton btn){
        if(btn == null){
            btn = (FloatingActionButton)view.findViewById(R.id.editProfileBtn);
            btn.setOnClickListener(this);
        }
        return btn;
    }

    private ViewPager initViewPager(View view, ViewPager pager){
        if(pager == null){
            pager = (ViewPager)view.findViewById(R.id.profileFeed);
            tabLayout = (TabLayout)view.findViewById(R.id.follower_tab_tabLayout);
            setupViewPager(pager, tabLayout);
        }
        return pager;
    }

    private ImageView initProfileView(View view, ImageView imageView){
        if(imageView == null){
            imageView = (ImageView)view.findViewById(R.id.profileBackground);
            imageView.setOnClickListener(null);

            if(currentUser.getImageBytes() != null && !currentUser.getImageBytes().isEmpty() && !currentUser.getImageBytes().equalsIgnoreCase("null")){
                Bitmap bitmapImage = Utilities.decodeImageFromStream(currentUser.getImageBytes());
                imageView.setImageBitmap(bitmapImage);
            } else {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.default_image, activity.getTheme()));
            }
        }
        return imageView;
    }

    private EditText initEditText(View view, EditText textView){
        if(textView == null){
            textView = (EditText) view.findViewById(R.id.profileStatus);
            String status = context.getCurrentUser().getProfileStatus();
            if(status != null && !status.equalsIgnoreCase("null") && !status.isEmpty()){
                textView.setText(status);
            } else {
                textView.setHint("Click edit to change your status and profile image.");
            }
        }
        return textView;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.editProfileBtn:
                handleStatus();
                break;
            case R.id.profileBackground:
                openGallery();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESPONSE_IMAGE_CODE && data != null && data.getData() != null){
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), imageUri);
                if(bitmap != null){
                    profileImage.setImageBitmap(bitmap);
                    imageBytes = Utilities.encodeImage(bitmap);
                    context.getCurrentUser().setImageBytes(imageBytes);
                    new SQLManager(activity).setUserImage(currentUser.getUsername(), imageBytes);
                }
            }catch(Exception ex){}
        }
    }

    private void handleStatus(){
        if(editBtn.getDrawable().getConstantState().equals(getResources().getDrawable(R.mipmap.edit_pen, activity.getTheme()).getConstantState())){
            editBtn.setImageDrawable(getResources().getDrawable(R.mipmap.white_check, activity.getTheme()));

            profileStatus.requestFocus();
            profileImage.setOnClickListener(this);
        } else if(editBtn.getDrawable().getConstantState().equals(getResources().getDrawable(R.mipmap.white_check, activity.getTheme()).getConstantState())){
            String status = profileStatus.getText().toString();
            if(status != null && !status.isEmpty() && status.length() > 0){
                context.getCurrentUser().setStatus(profileStatus.getText().toString());
                new ProfileManager(activity).execute(context.getCurrentUser());
            }
            profileStatus.clearFocus();
            profileImage.setOnClickListener(null);
            editBtn.setImageDrawable(getResources().getDrawable(R.mipmap.edit_pen, activity.getTheme()));
        }
    }

    public void openGallery(){
        Intent galleryIntent= new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(galleryIntent,"Select a picture"), RESPONSE_IMAGE_CODE);
    }

    private void setupViewPager(ViewPager pager, TabLayout layout){
        ProfilePager profilePager = new ProfilePager(getFragmentManager());

        FollowerFragment tFragment = new FollowerFragment();
        ProfileFeedFragment feedFragment = new ProfileFeedFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("user", currentUser);

        feedFragment.setArguments(bundle);
        tFragment.setArguments(bundle);
        profilePager.addFragment("Studying", feedFragment);
        profilePager.addFragment("Connections", tFragment);

        pager.setAdapter(profilePager);
        layout.setupWithViewPager(pager);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.profile_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.profileAddFriend:
                addUser();
                break;
            case R.id.profileRefresh:
                Utilities.showToast(activity, "Refreshing Profile Feed", Toast.LENGTH_SHORT);
                setupViewPager(profileFeed, tabLayout);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addUser(){
        final EditText userInput = new EditText(activity);
        AlertDialog newDialog = new AlertDialog.Builder(activity).setTitle("Add friend").setMessage("Enter the name of the user you wish to add").setView(userInput).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String username = userInput.getText().toString();
                try {
                    if(username != null && !username.isEmpty()){
                        followerTask = new AddFollower(activity, username);
                        followerTask.execute(context.getCurrentUser());
                        if(followerTask.get() == Network.RESPONSE_OK){
                            setupViewPager(profileFeed, tabLayout);
                        }
                    }
                }catch(InterruptedException | ExecutionException ex){}
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();
        newDialog.show();
    }
}
