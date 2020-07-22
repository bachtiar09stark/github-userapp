package com.ngoding.githubuserapp.receiver;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.ngoding.githubuserapp.R;
import com.ngoding.githubuserapp.activity.HomeActivity;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {

    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        showAlarmNotification(context);
    }

    public void setRepeatingAlarm(Context context) {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(context, "Reminder ON", Toast.LENGTH_SHORT).show();
    }

    public void cancelRepeatingAlarm(Context context) {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(context, "Reminder OFF", Toast.LENGTH_SHORT).show();
        }
    }

    private void showAlarmNotification(Context context) {

        final int NOTIFICATION_ID = 1;
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "GithubUser channel";

        Intent intent = new Intent(context, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.github_mark_light_32px)
                .setContentTitle(context.getString(R.string.title_reminder))
                .setContentText(context.getString(R.string.content_title_reminder))
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.setDescription(CHANNEL_NAME);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }

            Notification notification = builder.build();

            if (notificationManagerCompat != null) {
                notificationManagerCompat.notify(NOTIFICATION_ID, notification);
            }
        }
    }
}
