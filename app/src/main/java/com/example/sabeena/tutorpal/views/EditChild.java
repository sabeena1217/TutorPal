package com.example.sabeena.tutorpal.views;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sabeena.tutorpal.Presenter.AlarmReceiver;
import com.example.sabeena.tutorpal.Presenter.DatabaseHandler;
import com.example.sabeena.tutorpal.Presenter.EditTuitionTabLayoutAdapter;
import com.example.sabeena.tutorpal.Presenter.ViewTuitionTabLayoutAdapter;
import com.example.sabeena.tutorpal.R;
import com.example.sabeena.tutorpal.models.Child;
import com.example.sabeena.tutorpal.models.Day;
import com.example.sabeena.tutorpal.models.TuitionClass;

import java.util.ArrayList;
import java.util.Calendar;

public class EditChild extends AppCompatActivity implements NewTuitionFragment.OnFragmentInteractionListener, EditTuitionFragment.OnFragmentInteractionListener {

    int receivedChildID;
    DatabaseHandler tutorPalDB;
    TextView nameTxt;
    RadioButton radiobtnGender;
    RadioGroup radioGenderGrp;
    RadioButton radiobtnMale;
    RadioButton radiobtnFemale;
    EditTuitionTabLayoutAdapter adapter;
    Button editChild;
    private ArrayList<Fragment> tuitionsFragments = new ArrayList<Fragment>();
    ArrayList<TuitionClass> tuitionsArrayList = new ArrayList<TuitionClass>();
    TabLayout tabLayout;
    ViewPager viewPager;
    int tabCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receivedChildID = getIntent().getIntExtra("ID of the Child", 0);
        setContentView(R.layout.activity_edit_child);


        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addTab(tabLayout.newTab().setText("NEW"));
        NewTuitionFragment tab3 = NewTuitionFragment.newInstance();
        tuitionsFragments.add(tab3);

        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new EditTuitionTabLayoutAdapter(getSupportFragmentManager(), tuitionsFragments);
        tutorPalDB = new DatabaseHandler(this);
        Child c = tutorPalDB.getChild(receivedChildID);
        SQLiteDatabase db = tutorPalDB.getReadableDatabase();


        Cursor tuitions = tutorPalDB.getAllTuition(db, receivedChildID);
        tuitions.moveToFirst();
        if (tuitions.getCount() != 0) {
            do {
                TuitionClass t = new TuitionClass(tuitions.getInt(1));
                t.setTuitionID(tuitions.getInt(0));
                t.setSubject(tuitions.getString(2));
                t.setTutorName(tuitions.getString(3));
                t.setTutorACNumber(tuitions.getString(4));
                t.setLocation(tuitions.getString(5));
                t.setTuitionFee(tuitions.getDouble(6));
                t.setLongitude(tuitions.getString(7));
                t.setLatitude(tuitions.getString(8));

                Cursor tuitionDays = tutorPalDB.getTuitionDay(db, t.getTuitionID());
                tuitionDays.moveToFirst();
                if (tuitionDays.getCount() != 0) {
                    Day.DayOfTheWeek selectedDay = Day.DayOfTheWeek.MONDAY;

                    if (tuitionDays.getString(2).equals("Monday"))
                        selectedDay = Day.DayOfTheWeek.MONDAY;
                    else if (tuitionDays.getString(2).equals("Tuesday"))
                        selectedDay = Day.DayOfTheWeek.TUESDAY;
                    else if (tuitionDays.getString(2).equals("Wednesday"))
                        selectedDay = Day.DayOfTheWeek.WEDNESDAY;
                    else if (tuitionDays.getString(2).equals("Thursday"))
                        selectedDay = Day.DayOfTheWeek.THURSDAY;
                    else if (tuitionDays.getString(2).equals("Friday"))
                        selectedDay = Day.DayOfTheWeek.FRIDAY;
                    else if (tuitionDays.getString(2).equals("Saturday"))
                        selectedDay = Day.DayOfTheWeek.SATURDAY;
                    else if (tuitionDays.getString(2).equals("Sunday"))
                        selectedDay = Day.DayOfTheWeek.SUNDAY;

                    t.setDay(new Day(selectedDay, tuitionDays.getString(3), tuitionDays.getString(4)));
                }
                tuitionsArrayList.add(t);
                tabCount += 1;
                tabLayout.addTab(tabLayout.newTab().setText("CLASS " + tabCount));
                EditTuitionFragment tab2 = EditTuitionFragment.newInstance(t.getTuitionID(), receivedChildID);
                tuitionsFragments.add(tab2);
                adapter = new EditTuitionTabLayoutAdapter(getSupportFragmentManager(), tuitionsFragments);
                viewPager.setAdapter(adapter);

            } while (tuitions.moveToNext());
        }
        tutorPalDB.close();


        //adapter.addTabPage();
        //tab layout get changed here

        //if(tabLayout.getTabCount() != 1){
        //  for(int i=tabLayout.getTabCount()-1 ; i>0 ;i--){


        nameTxt = (TextView) findViewById(R.id.nameTxt);
        nameTxt.setText(c.getName());
        //nameTxt.setInputType(InputType.TYPE_NULL);

        //radioGenderGrp = (RadioGroup) findViewById(R.id.radioBtnGrp);
        radiobtnFemale = (RadioButton) findViewById(R.id.radioBtnFemale);
        radiobtnMale = (RadioButton) findViewById(R.id.radioBtnMale);

        if (c.getGender().equals(radiobtnFemale.getText())) {
            radiobtnFemale.setChecked(true);
            radiobtnMale.setChecked(false);
            radiobtnMale.setEnabled(false);
        } else if (c.getGender().equals(radiobtnMale.getText())) {
            radiobtnMale.setChecked(true);
            radiobtnFemale.setChecked(false);
            radiobtnFemale.setEnabled(false);
        }

        //Toast.makeText(this, c.getName()+c.getGender(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onFragmentInteraction(TuitionClass tuition, int isUpdatedOrDeleted) {
        if (new Integer(isUpdatedOrDeleted).intValue() == 0) {//update
            tutorPalDB.updateClass(tuition);
            Toast.makeText(this, "Successfully updated!", Toast.LENGTH_LONG).show();
        } else if (new Integer(isUpdatedOrDeleted).intValue() == 1) {//add new

            tutorPalDB.insertClass(tuition);

            tutorPalDB.insertDay(tutorPalDB.getLastTuitionID(), tuition.getDay());
            Toast.makeText(this, "You added a new Tuition for the child!", Toast.LENGTH_LONG).show();
            if (new Integer(tuition.isNotification()).equals(1)) {
                scheduleStartAlarm(tuition.getStartTime(), tuition.getChildID(), tutorPalDB.getLastTuitionID(), 0);
                scheduleEndAlarm(tuition.getEndTime(), tuition.getChildID(), tutorPalDB.getLastTuitionID(), 1);
            }
        }

    }

    private void scheduleStartAlarm(Calendar cal, int childID, int tuitionID, int startOrEnd) {
        Calendar calendar = cal;
        //field, value
        //calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        //calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        //calendar.set(Calendar.MINUTE, minute);

        Toast.makeText(this, "alarm set on " + calendar.getTime(), Toast.LENGTH_LONG).show();
        //check we aren't setting it in the past which would trigger it to fire instantly
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            //  Toast.makeText(getActivity(),calendar.getTimeInMillis() + " " + System.currentTimeMillis() , Toast.LENGTH_LONG).show();
            calendar.add(Calendar.DAY_OF_YEAR, 7);
        }

        Intent intent = new Intent(this, AlarmReceiver.class);
        int[] ID_array = new int[]{childID, tuitionID, startOrEnd};
        intent.putExtra("ID_array", ID_array);
        Log.d("Add schedule" + ID_array[0], "Add schedule" + ID_array[0]);
        Log.d("Add alarm" + calendar.getTimeInMillis(), "Add schedule" + ID_array[0]);
        Log.d("Add current" + System.currentTimeMillis(), "Add schedule" + ID_array[0]);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int) calendar.getTimeInMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 15, pendingIntent);
//        Log.d("ADD CHILD NOT....", "ADD CHILD NOT....");
    }


    private void scheduleEndAlarm(Calendar cal, int childID, int tuitionID, int startOrEnd) {
        Calendar calendar = cal;
        //field, value
        //calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        //calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        //calendar.set(Calendar.MINUTE, minute);

        Toast.makeText(this, "alarm set on " + calendar.getTime(), Toast.LENGTH_LONG).show();
        //check we aren't setting it in the past which would trigger it to fire instantly
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            //  Toast.makeText(getActivity(),calendar.getTimeInMillis() + " " + System.currentTimeMillis() , Toast.LENGTH_LONG).show();
            calendar.add(Calendar.DAY_OF_YEAR, 7);
        }

        Intent intent = new Intent(this, AlarmReceiver.class);
        int[] ID_array = new int[]{childID, tuitionID, startOrEnd};
        intent.putExtra("ID_array", ID_array);
        Log.d("Add schedule" + ID_array[0], "Add schedule" + ID_array[0]);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int) calendar.getTimeInMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);
//        Log.d("ADD CHILD NOT....", "ADD CHILD NOT....");
    }

    @Override
    public void onFragmentInteraction(Boolean clicked) {
        if (clicked) {
            tabCount += 1;
            tabLayout.addTab(tabLayout.newTab().setText("CLASS " + tabCount));

            EditTuitionFragment tab2 = EditTuitionFragment.newInstance(tutorPalDB.getLastTuitionID() + 1, receivedChildID);
            tuitionsFragments.add(tab2);
            adapter = new EditTuitionTabLayoutAdapter(getSupportFragmentManager(), tuitionsFragments);
            viewPager.setAdapter(adapter);

            //Toast.makeText(this, "will this work at least?", Toast.LENGTH_SHORT).show();
        }
    }
}
