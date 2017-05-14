package com.example.sabeena.tutorpal.Presenter;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.sabeena.tutorpal.views.NotifyTuition;
import com.example.sabeena.tutorpal.views.ViewChild;

/**
 * Created by SaBeeNa on 5/11/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {

    int[] ID_array;

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context,"ALARM ON",Toast.LENGTH_SHORT).show();
        //Log.d("ADD CHILD NOT....", "ADD CHILD NOT....");

        Intent intent1 = new Intent(context, RingtonePlayingService.class);
        context.startService(intent1);

        ID_array = intent.getIntArrayExtra("ID_array");

        createNotification(context);
    }
    public void createNotification(Context context){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle("Tuition class")
                .setContentText("Alarm")
                .setSubText("Tap to cancel the ringtone")
                .setPriority(NotificationCompat.PRIORITY_HIGH);


        //intent.putExtra("ID of the Child",Integer.parseInt((String) IDTxt.getText()));
        Intent intent = new Intent(context, NotifyTuition.class);
        intent.putExtra("ID_array",ID_array);
        Log.d("ADD CHILD"+ Integer.toString(ID_array[0]),"ADD CHILD"+ Integer.toString(ID_array[0]));
        //
        // context.startActivity(int1);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                123,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        //to add a dismiss button
        Intent dismissIntent = new Intent(context, RingtonePlayingService.class);
        dismissIntent.setAction(RingtonePlayingService.ACTION_DISMISS);//action button
        PendingIntent pendingIntent1 = PendingIntent.getService(context,
                123,dismissIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action action = new NotificationCompat
                .Action(android.R.drawable.ic_lock_idle_alarm,
                        "DISMISS",pendingIntent1);
        builder.addAction(action);


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(321,notification);



    }
}
