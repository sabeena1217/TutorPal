package com.example.sabeena.tutorpal.views;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.sabeena.tutorpal.Presenter.DatabaseHandler;
import com.example.sabeena.tutorpal.Presenter.dayOfTheWeekAdapter;
import com.example.sabeena.tutorpal.R;
import com.example.sabeena.tutorpal.models.TuitionClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DayviewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DayviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DayviewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    //private String mParam2;
    private RecyclerView mRecyclerView;
    private ArrayList<TuitionClass> tuitionsForTheDay = new ArrayList<>();
    private OnFragmentInteractionListener mListener;

    public DayviewFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static DayviewFragment newInstance(int param1) {
        DayviewFragment fragment = new DayviewFragment();
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
        Integer total = mParam1*34;
        onButtonPressed(total.toString());
        View view =  inflater.inflate(R.layout.fragment_dayview, container, false);


        final java.util.Calendar calendar = java.util.Calendar.getInstance();

        Log.d("today", String.valueOf( calendar.getTime()));

        DatabaseHandler tutorPalDB = new DatabaseHandler(this.getActivity());
        SQLiteDatabase db = tutorPalDB.getReadableDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Log.d("today", sdf.format(calendar.getTime()));

        int tabNumber = 6;
        ArrayList<Integer> tuitionIDs= new ArrayList<>();
        if( sdf.format(calendar.getTime()).equals("Monday")){


            tuitionIDs = tutorPalDB.getAllTuitionOfTheDay(db, "Monday");

        }else if(sdf.format(calendar.getTime()).equals("Tuesday")){
             tuitionIDs = tutorPalDB.getAllTuitionOfTheDay(db, "Tuesday");
        }else if(sdf.format(calendar.getTime()).equals("Wednesday")){
            tuitionIDs = tutorPalDB.getAllTuitionOfTheDay(db, "Wednesday");
        }else if(sdf.format(calendar.getTime()).equals("Thursday")){
            tuitionIDs = tutorPalDB.getAllTuitionOfTheDay(db, "Thursday");
        }else if(sdf.format(calendar.getTime()).equals("Friday")){
            tuitionIDs = tutorPalDB.getAllTuitionOfTheDay(db, "Friday");
        }else if(sdf.format(calendar.getTime()).equals("Saturday")){
            tuitionIDs = tutorPalDB.getAllTuitionOfTheDay(db, "Saturday");
        }else if(sdf.format(calendar.getTime()).equals("Sunday")){
            tuitionIDs = tutorPalDB.getAllTuitionOfTheDay(db, "Sunday");
        }

        tutorPalDB.close();
/*
        for(int i=0;i< tuitionIDs.size();i++){
            Cursor tuition = tutorPalDB.getTuition(db, tuitionIDs.get(i));
            if (tuition.getCount() > 0) {
                TuitionClass t = new TuitionClass(tuition.getInt(1));
                t.setSubject(tuition.getString(2));
                t.setTutorName(tuition.getString(3));
                t.setTutorACNumber(tuition.getString(4));
                t.setLocation(tuition.getString(5));
                t.setTuitionFee(tuition.getDouble(6));
t.setLongitude(tuition.getString(7));
                t.setLatitude(tuition.getString(8));
                t.setNotification(tuition.getInt(9));

                Cursor tuitionDays = tutorPalDB.getTuitionDay(db, tuition.getInt(0));
                tuitionDays.moveToFirst();
                if (tuitionDays.getCount() != 0) {

                    if (tuitionDays.getString(2).equals("Monday"))
                        days_spinner.setSelection(0);
                    else if (tuitionDays.getString(2).equals("Tuesday"))
                        days_spinner.setSelection(1);
                    else if (tuitionDays.getString(2).equals("Wednesday"))
                        days_spinner.setSelection(2);
                    else if (tuitionDays.getString(2).equals("Thursday"))
                        days_spinner.setSelection(3);
                    else if (tuitionDays.getString(2).equals("Friday"))
                        days_spinner.setSelection(4);
                    else if (tuitionDays.getString(2).equals("Saturday"))
                        days_spinner.setSelection(5);
                    else if (tuitionDays.getString(2).equals("Sunday"))
                        days_spinner.setSelection(6);


                    etStartTime = (EditText) view.findViewById(R.id.etStartTime);
                    etStartTime.setText(tuitionDays.getString(3));

                    etEndTime = (EditText) view.findViewById(R.id.etEndTime);
                    etEndTime.setText(tuitionDays.getString(4));
                }
            }
        }
        mRecyclerView.setAdapter(new dayOfTheWeekAdapter(getActivity(), tuitionsForTheDay));//hghjglk
*/
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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
