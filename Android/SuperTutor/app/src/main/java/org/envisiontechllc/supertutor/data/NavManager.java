package org.envisiontechllc.supertutor.data;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.internal.library.AppLibrary;
import org.envisiontechllc.supertutor.subactivities.LoginScreen;

/**
 * Created by EmileBronkhorst on 15/03/16.
 * Copyright 2016 Envision Tech LLC
 */
public class NavManager {

    private Context ctx;
    private FragmentManager fragmentManager;

    private final String EMAIL_ADDRESS = "support@envisiontechllc.org";
    private final String EMAIL_SUBJECT = "[TICKET]: Application Support";
    private final String TWITTER_URL = "https://twitter.com/SuperTutor_UOW";
    private final String INSTAGRAM_URL = "https://www.instagram.com/super_tutor_uow/";

    /**
     * Constructor creating a new instance of NavManager - Handles all processes relating to the Navigation Drawer.
     * @param ctx The application context giving access to method provider.
     * @param fragmentManager The Support Fragment Manager for the application context.
     */
    public NavManager(Context ctx, FragmentManager fragmentManager){
        this.ctx = ctx;
        this.fragmentManager = fragmentManager;
    }

    /**
     * Logs the current user out of the application if the "Yes" option is clicked
     */
    public void userLogout(final Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage("Logging out will remove any local data stored on your device.\n\nAre you sure you wish to logout?").setTitle("Confirm logout").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLManager manager = new SQLManager(activity);
                manager.resetDatabase();
                AppLibrary.getInstance().resetLibrary();

                Intent intent = new Intent(ctx, LoginScreen.class);
                ctx.startActivity(intent);
                activity.finish();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Opens a new browser (of your choice - via selection menu if applicable) to the official Twitter page of SuperTutor
     */
    public void openTwitter(){
        Intent twitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(TWITTER_URL));
        ctx.startActivity(twitterIntent);
    }

    /**
     * Opens a new browser (of your choice - via selection menu if applicable) to the official Instagram page of SuperTutor
     */
    public void openInstagram(){
        Intent instaIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(INSTAGRAM_URL));
        ctx.startActivity(instaIntent);
    }

    /**
     * Opens a new Email activity, with the default Email set to the support email, Subject as "[TICKET]: "
     * @param intent the intent for which to start the email activity
     */
    public void startEmailActivity(Intent intent){
        try {
            Intent emailIntent = intent;
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{EMAIL_ADDRESS});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, EMAIL_SUBJECT);
            emailIntent.setType("message/rfc822");

            ctx.startActivity(emailIntent);
        }catch(Exception ex){}
    }

    /**
     * Replaes the include_layout FrameLayout with the fragment in question
     * @param tag the TAG to provide the Fragment when storing it.
     * @param fragment the Fragment for which to commit to the FragmentManager
     */
    public void commitFragment(String tag, Fragment fragment){
        try {
            Fragment tempFragment = fragmentManager.findFragmentByTag(tag);
            if(tempFragment == null){
                fragmentManager.beginTransaction().replace(R.id.includeLayout, fragment, tag).commit();
                return;
            }

            fragmentManager.beginTransaction().replace(R.id.includeLayout, tempFragment).commit();
        }catch(Exception ex){}
    }
}
