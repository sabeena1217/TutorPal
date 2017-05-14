package com.example.sabeena.tutorpal.Presenter;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

/**
 * Created by SaBeeNa on 5/11/2017.
 */

public class RingtonePlayingService extends Service {

    public static final String URI_BASE = RingtonePlayingService.class.getName() + ".";
    public static final String ACTION_DISMISS = URI_BASE + "ACTION_DISMISS";
    private Ringtone ringtone;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String action = intent.getAction();
        if (ACTION_DISMISS.equals(action)) {
            dismissAlarm();
        } else {
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

            if (uri == null) {
                uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }
            ringtone = RingtoneManager.getRingtone(this, uri);
            ringtone.play();
        }

        return START_NOT_STICKY;
        //return super.onStartCommand(intent, flags, startId);
    }

    private void dismissAlarm() {
        Intent i = new Intent(this, RingtonePlayingService.class);
        stopService(i);

        i = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        NotificationManager notificationManger = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        notificationManger.cancel(321);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ringtone.stop();
    }
}
