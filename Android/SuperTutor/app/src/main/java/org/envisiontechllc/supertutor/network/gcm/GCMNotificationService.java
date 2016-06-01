package org.envisiontechllc.supertutor.network.gcm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.envisiontechllc.supertutor.R;
import org.envisiontechllc.supertutor.network.Network;

import java.io.IOException;

/**
 * Created by EmileBronkhorst on 01/05/16.
 * Copyright 2016 Envision Tech LLC
 */
public class GCMNotificationService extends IntentService {

    public static final int MESSAGE_NOTIFICATION_ID = 9999999;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public GCMNotificationService() {
        super("GCM Notification Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        Bundle args = intent.getExtras();

        if(args != null){
            createNotification(args.getString("title"), args.getString("message"));
        }

        GCMBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void createNotification(String title, String body) {
        if(title != null && !title.isEmpty() && body != null && !body.isEmpty()){
            Context context = getBaseContext();
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title)
                    .setContentText(body);
            NotificationManager mNotificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, mBuilder.build());
        }
    }
}
