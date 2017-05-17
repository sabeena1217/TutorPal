package com.example.sabeena.tutorpal.Presenter;

import com.example.sabeena.tutorpal.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    DatabaseHandler tutorPalDB;

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "ALARM ON", Toast.LENGTH_SHORT).show();
        //Log.d("ADD CHILD NOT....", "ADD CHILD NOT....");

        Intent intent1 = new Intent(context, RingtonePlayingService.class);
        context.startService(intent1);

        ID_array = intent.getIntArrayExtra("ID_array");

        Log.d("Add receive" + ID_array[0], "Add receive" + ID_array[0]);
        createNotification(context, intent.getIntArrayExtra("ID_array"));
    }

    public void createNotification(Context context, int[] ID_array) {
        tutorPalDB = new DatabaseHandler(context);
        SQLiteDatabase db = tutorPalDB.getReadableDatabase();
        String childName = tutorPalDB.getChild(ID_array[0]).getName();
        Cursor tuition = tutorPalDB.getTuition(db, ID_array[1]);

        tuition.moveToFirst();
        String subject = tuition.getString(2);

        Log.d("ADD notify" + ID_array[0], "ADD notify" + ID_array[0]);
        if (ID_array[2] == 0) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_event_white_24dp)
                    .setAutoCancel(false)
                    .setContentTitle("TutorPal")
                    .setContentText("Tuition Start Alert!")
                    .setSubText(childName + " has " + subject + " now!\n Tap to cancel the ringtone")
                    //.setStyle(new NotificationCompat.BigTextStyle())
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_ALL);



            //intent.putExtra("ID of the Child",Integer.parseInt((String) IDTxt.getText()));
            Intent intent = new Intent(context, NotifyTuition.class);
            intent.putExtra("ID_array", ID_array);
            Log.d("ADD CHILD" + Integer.toString(ID_array[0]), "ADD CHILD" + Integer.toString(ID_array[0]));
            //
            // context.startActivity(int1);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,
                    123, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);

            //to add a dismiss button
            Intent dismissIntent = new Intent(context, RingtonePlayingService.class);
            dismissIntent.setAction(RingtonePlayingService.ACTION_DISMISS);//action button
            PendingIntent pendingIntent1 = PendingIntent.getService(context,
                    123, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Action action = new NotificationCompat
                    .Action(android.R.drawable.ic_lock_idle_alarm,
                    "DISMISS", pendingIntent1);
            builder.addAction(action);


            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = builder.build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(321, notification);


        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_event_white_24dp)
                    .setContentTitle("TuitorPal")
                    .setContentText("Tuition End Alert!")
                    .setSubText(childName + " has " + subject + " now!" + "\n" + "Tap to cancel the ringtone")
                    .setPriority(NotificationCompat.PRIORITY_HIGH);


            //intent.putExtra("ID of the Child",Integer.parseInt((String) IDTxt.getText()));
            Intent intent = new Intent(context, NotifyTuition.class);
            intent.putExtra("ID_array", ID_array);
            Log.d("ADD CHILD" + Integer.toString(ID_array[0]), "ADD CHILD" + Integer.toString(ID_array[0]));
            //
            // context.startActivity(int1);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,
                    123, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);

            //to add a dismiss button
            Intent dismissIntent = new Intent(context, RingtonePlayingService.class);
            dismissIntent.setAction(RingtonePlayingService.ACTION_DISMISS);//action button
            PendingIntent pendingIntent1 = PendingIntent.getService(context,
                    123, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Action action = new NotificationCompat
                    .Action(android.R.drawable.ic_lock_idle_alarm,
                    "DISMISS", pendingIntent1);
            builder.addAction(action);


            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = builder.build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(321, notification);


        }


    }
}
