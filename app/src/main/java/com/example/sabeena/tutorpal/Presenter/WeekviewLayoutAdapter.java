package com.example.sabeena.tutorpal.Presenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.sabeena.tutorpal.views.dayOfTheWeekFragment;

/**
 * Created by SaBeeNa on 4/18/2017.
 */

public class WeekviewLayoutAdapter extends FragmentStatePagerAdapter {

    String[] daysArray = { "Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday" };

    public WeekviewLayoutAdapter(FragmentManager fm) {
        super(fm);



    }

    @Override
    public Fragment getItem(int position) {

        dayOfTheWeekFragment tab1 = dayOfTheWeekFragment.newInstance(daysArray[position]);

        return tab1;

    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 7;
    }

}