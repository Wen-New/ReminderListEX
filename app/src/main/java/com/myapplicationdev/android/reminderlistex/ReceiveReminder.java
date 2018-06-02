package com.myapplicationdev.android.reminderlistex;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class ReceiveReminder extends BroadcastReceiver {

    int reqCode = 12345;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default Channel", NotificationManager.IMPORTANCE_DEFAULT);

            channel.setDescription("This is for default notification");
            notificationManager.createNotificationChannel(channel);
        }
        // TODO: This method is called when the BroadcastReceiver is
        //receiving an Intent broadcast.
        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, reqCode,
                i, PendingIntent.FLAG_CANCEL_CURRENT);

        // build notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");
        builder.setContentTitle("Reminder");
        builder.setContentText("You have got events");
        builder.setSmallIcon(android.R.drawable.ic_dialog_info);
        builder.setContentIntent(pIntent);
        builder.setAutoCancel(true);
        Log.i("Result", "Got Notified");
        Uri uri = RingtoneManager.getDefaultUri
                (RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(uri);

        android.app.Notification n = builder.build();

        notificationManager.notify(123, n);
    }
}
