package com.example.sabeena.tutorpal.Presenter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.sabeena.tutorpal.models.Day;
import com.example.sabeena.tutorpal.models.TuitionClass;
import com.example.sabeena.tutorpal.views.ViewTuitionFragment;

import java.util.ArrayList;

/**
 * Created by SaBeeNa on 5/17/2017.
 */

public class NotifyTuitionLayoutAdapter extends FragmentStatePagerAdapter  {


    int tuitionID;
    DatabaseHandler tutorPalDB;
    Context context;

    ArrayList<TuitionClass> tuitionsArrayList = new ArrayList<TuitionClass>();


    public NotifyTuitionLayoutAdapter(FragmentManager fm, int receivedTuitionID, Context context) {
        super(fm);
        this.tuitionID = receivedTuitionID;
        this.context = context;
        DatabaseHandler tutorPalDB = new DatabaseHandler(context);
        SQLiteDatabase db = tutorPalDB.getReadableDatabase();
        Cursor tuitions = tutorPalDB.getTuition(db, tuitionID);
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
            } while (tuitions.moveToNext());
        }
        tutorPalDB.close();

    }

    @Override
    public Fragment getItem(int position) {

        //ViewTuitionFragment tab1 = ViewTuitionFragment.newInstance(tuitionID);
        ViewTuitionFragment tab1 = ViewTuitionFragment.newInstance(tuitionsArrayList.get(position).getTuitionID());

        return tab1;


    }

    @Override
    public int getCount() {
        return tuitionsArrayList.size();
    }
}

