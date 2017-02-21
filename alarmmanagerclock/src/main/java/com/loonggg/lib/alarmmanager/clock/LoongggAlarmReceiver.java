package com.loonggg.lib.alarmmanager.clock;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static android.content.Context.NOTIFICATION_SERVICE;


public class LoongggAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        long intervalMillis = intent.getLongExtra("intervalMillis", 0);
        if (intervalMillis != 0) {
            AlarmManagerUtil.setAlarmTime(context, System.currentTimeMillis() + intervalMillis,
                    intent);
        }


        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(context)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic)
                .setTicker("Have your recorded yet?")
                .setContentTitle("Have your recorded yet?")
                .setContentText("Time to note your cat's health!")
                .setWhen(System.currentTimeMillis())
                .build();
        notification.defaults = Notification.DEFAULT_ALL;
        manager.notify(1, notification);
    }

}
