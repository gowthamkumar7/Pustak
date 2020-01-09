package com.gtm.archcomponents.notes.utility;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.gtm.archcomponents.R;
import com.gtm.archcomponents.notes.ui.dashboard.NotesDashBoardActivity;

public class NotificationBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("NotificationBroadCast", "onReceive: ");

        Intent notificationIntent = new Intent(context, NotesDashBoardActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
/*
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(cls);
        stackBuilder.addNextIntent(notificationIntent);*/


        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, 234, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel("123",
                    "My channel",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Random Description");
            notificationManager.createNotificationChannel(channel);

        }


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "YOUR_CHANNEL_ID")
                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                .setContentTitle("Reminder") //  title for notification
                .setContentText("" + System.currentTimeMillis())// message for notification
                .setAutoCancel(true)
                .setChannelId("123"); // clear notification after click

        mBuilder.setContentIntent(pendingIntent1);

        notificationManager.notify(1, mBuilder.build());
    }
}
