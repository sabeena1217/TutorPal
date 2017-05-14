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

public class TabLayoutAdapter extends FragmentStatePagerAdapter {

    int NumOfTabs=0;
    FragmentManager fm;
    int lastChildId;

    public TabLayoutAdapter(FragmentManager fm, int NumOfTabs, int lastChildID) {
        super(fm);
        this.fm= fm;
        this.NumOfTabs = NumOfTabs;
        this.lastChildId = lastChildID;
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

        switch (position) {
            case 0: //last tab
                NewTuitionFragment tab3 = NewTuitionFragment.newInstance();
                return tab3;
            case -1: //last tab, this is not working
                NewTuitionFragment tab1 = NewTuitionFragment.newInstance();
                return tab1;
            default:
                int lastChildID=this.lastChildId;
                AddTuitionFragment tab2 = AddTuitionFragment.newInstance(lastChildID);
                return tab2;

        }
    }

    @Override
    public int getCount() {
        return NumOfTabs;
    }
}