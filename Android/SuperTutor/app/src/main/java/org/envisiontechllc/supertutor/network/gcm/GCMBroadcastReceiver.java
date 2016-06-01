package org.envisiontechllc.supertutor.network.gcm;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by EmileBronkhorst on 01/05/16.
 * Copyright 2016 Envision Tech LLC
 */
public class GCMBroadcastReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ComponentName componenet = new ComponentName(context.getPackageName(), GCMNotificationService.class.getName());
        startWakefulService(context, intent.setComponent(componenet));
        setResultCode(Activity.RESULT_OK);
    }

}
