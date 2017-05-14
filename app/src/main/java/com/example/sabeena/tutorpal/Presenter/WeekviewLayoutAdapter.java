package com.example.sabeena.tutorpal.Presenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.sabeena.tutorpal.views.DayFragment;

/**
 * Created by SaBeeNa on 4/18/2017.
 */

public class WeekviewLayoutAdapter extends FragmentStatePagerAdapter {



    public WeekviewLayoutAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        DayFragment dayTab = DayFragment.newInstance();
        return dayTab;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
