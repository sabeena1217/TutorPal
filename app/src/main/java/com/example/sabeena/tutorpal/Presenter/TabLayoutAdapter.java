package com.example.sabeena.tutorpal.Presenter;

/**
 * Created by SaBeeNa on 4/8/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.Toast;

import com.example.sabeena.tutorpal.R;
import com.example.sabeena.tutorpal.views.AddChild;
import com.example.sabeena.tutorpal.views.AddTuitionFragment;
import com.example.sabeena.tutorpal.views.NewTuitionFragment;

import java.util.ArrayList;

public class TabLayoutAdapter extends FragmentStatePagerAdapter {

    int NumOfTabs=0;
    FragmentManager fm;
    int lastChildId;
    private ArrayList<Fragment> tuitions;

    //public TabLayoutAdapter(FragmentManager fm, int NumOfTabs, int lastChildID) {
    public TabLayoutAdapter(FragmentManager fm, ArrayList<Fragment> tuitions) {
        super(fm);
        this.tuitions = tuitions;

        //this.fm= fm;
        //this.NumOfTabs = NumOfTabs;
        //this.lastChildId = lastChildID;

        //setFragments();
    }

    //public void setFragments() {
      //  //complete this next time
        //if (NumOfTabs > 1) {

//        for (int i = 0;i<NumOfTabs-1;i++){


  //          AddTuitionFragment add = AddTuitionFragment.newInstance(lastChildID);
    //        fm.beginTransaction().replace(R.id.fragment_layout, add, add.getTag()).commit();
      //  }
        //}

    //}

    public void addTabPage() {

        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return tuitions.get(position);
    }

    @Override
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