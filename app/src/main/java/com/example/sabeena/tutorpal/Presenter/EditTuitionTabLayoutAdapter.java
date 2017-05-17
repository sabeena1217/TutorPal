package com.example.sabeena.tutorpal.Presenter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.sabeena.tutorpal.models.Day;
import com.example.sabeena.tutorpal.models.TuitionClass;
import com.example.sabeena.tutorpal.views.EditTuitionFragment;
import com.example.sabeena.tutorpal.views.ViewTuitionFragment;

import java.util.ArrayList;

/**
 * Created by SaBeeNa on 5/17/2017.
 */

public class EditTuitionTabLayoutAdapter extends FragmentStatePagerAdapter {


    int childID;
    DatabaseHandler tutorPalDB;
    Context context;

    ArrayList<TuitionClass> tuitionsArrayList = new ArrayList<TuitionClass>();
    private ArrayList<Fragment> tuitions = new ArrayList<Fragment>();


    public EditTuitionTabLayoutAdapter(FragmentManager fm, ArrayList<Fragment> tuitions) {
        super(fm);
        this.tuitions = tuitions;

        //this.fm= fm;
        //this.NumOfTabs = NumOfTabs;
        //this.lastChildId = lastChildID;

        //setFragments();
    }

    @Override
    public Fragment getItem(int position) {
        return tuitions.get(position);
    }

    public void addTabPage() {

        notifyDataSetChanged();
    }

    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "NEW";    // this is from a template, i have to
                break;             // return title dynamically
            case 1:
                title = "CLASS 1";
                break;
            case 2:
                title = "CLASS 2";
                break;
            case 3:
                title = "CLASS 3";
                break;
            case 4:
                title = "CLASS 4";
                break;
            case 5:
                title = "CLASS 5";
                break;
            case 6:
                title = "CLASS 6";
                break;
        }
        return title;
    }

    @Override
    public int getCount() {
        return tuitions.size();
    }
}
