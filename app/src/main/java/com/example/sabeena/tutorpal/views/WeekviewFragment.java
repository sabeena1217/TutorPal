package com.example.sabeena.tutorpal.views;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.icu.util.Calendar;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.support.v4.app.FragmentManager;

import com.example.sabeena.tutorpal.Presenter.TabLayoutAdapter;
import com.example.sabeena.tutorpal.Presenter.WeekviewLayoutAdapter;
import com.example.sabeena.tutorpal.R;

import java.text.SimpleDateFormat;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WeekviewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WeekviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeekviewFragment extends Fragment implements dayOfTheWeekFragment.OnFragmentInteractionListener {

    private ViewPager viewPager;
    private WeekviewLayoutAdapter mAdapter;
    private ActionBar actionBar;
    // Tab titles
    private String[] tabs = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    //private String mParam2;

    private OnFragmentInteractionListener mListener;

    public WeekviewFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static WeekviewFragment newInstance(int param1) {
        WeekviewFragment fragment = new WeekviewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Integer total = mParam1*10;
        //onButtonPressed(total.toString());
        View view = inflater.inflate(R.layout.fragment_weekview, container, false);


        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);

        //SQLiteDatabase db = tutorPalDB.getReadableDatabase();
        //Cursor tuitions = tutorPalDB.getAllTuition(db, receivedChildID);

        tabLayout.addTab(tabLayout.newTab().setText("MONDAY"));
        tabLayout.addTab(tabLayout.newTab().setText("TUESDAY"));
        tabLayout.addTab(tabLayout.newTab().setText("WEDNESDAY"));
        tabLayout.addTab(tabLayout.newTab().setText("THURSDAY"));
        tabLayout.addTab(tabLayout.newTab().setText("FRIDAY"));
        tabLayout.addTab(tabLayout.newTab().setText("SATURDAY"));
        tabLayout.addTab(tabLayout.newTab().setText("SUNDAY"));

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
        WeekviewLayoutAdapter adapter = new WeekviewLayoutAdapter(getActivity().getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        Log.d("monday", String.valueOf(Calendar.MONDAY));
        Log.d("tuesday", String.valueOf(Calendar.TUESDAY));
        Log.d("sunday", String.valueOf(Calendar.SUNDAY));


        final java.util.Calendar calendar = java.util.Calendar.getInstance();

        Log.d("today", String.valueOf( calendar.getTime()));


        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Log.d("today", sdf.format(calendar.getTime()));

        int tabNumber = 6;
        if( sdf.format(calendar.getTime()).equals("Monday")){
            tabNumber = 0;
        }else if(sdf.format(calendar.getTime()).equals("Tuesday")){
            tabNumber = 1;
        }else if(sdf.format(calendar.getTime()).equals("Wednesday")){
            tabNumber = 2;
        }else if(sdf.format(calendar.getTime()).equals("Thursday")){
            tabNumber = 3;
        }else if(sdf.format(calendar.getTime()).equals("Friday")){
            tabNumber = 4;
        }else if(sdf.format(calendar.getTime()).equals("Saturday")){
            tabNumber = 5;
        }else if(sdf.format(calendar.getTime()).equals("Sunday")){
            tabNumber = 6;
        }
        //Log.d("today tabNumber : ", String.valueOf(tabNumber));
        //monday 2

        switch (tabNumber) {
            case 1:
                viewPager.setCurrentItem(tabNumber);
            case 2:
                viewPager.setCurrentItem(tabNumber);
            case 3:
                viewPager.setCurrentItem(tabNumber);
            case 4:
                viewPager.setCurrentItem(tabNumber);
            case 5:
                viewPager.setCurrentItem(tabNumber);
            case 6:
                viewPager.setCurrentItem(tabNumber);
            case 7:
                viewPager.setCurrentItem(tabNumber);
            default:
                viewPager.setCurrentItem(tabNumber);

        }

        //replaceCurrentTab();
       /* final ViewPager viewPager = (ViewPager)view. findViewById(R.id.pager);
        WeekviewLayoutAdapter adapter = new WeekviewLayoutAdapter(getActivity().getSupportFragmentManager());


        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
*/
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    //a method to call to send data from this fragment to outside, CALL BACK METHOD!!
    public void onButtonPressed(String data) {
        if (mListener != null) {
            mListener.onFragmentInteraction(data);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String data);
    }
}
