package com.gtm.archcomponents.notes.utility;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

public class NotificationJobService extends JobIntentService {
    @Override
    protected void onHandleWork(@NonNull Intent intent) {

    }

    private static final int JOB_ID = 1;

    public static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, NotificationJobService.class, JOB_ID, intent);
    }
}
