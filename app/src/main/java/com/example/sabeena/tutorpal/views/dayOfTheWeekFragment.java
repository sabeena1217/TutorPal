package com.example.sabeena.tutorpal.views;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sabeena.tutorpal.Presenter.DatabaseHandler;
import com.example.sabeena.tutorpal.Presenter.dayOfTheWeekAdapter;
import com.example.sabeena.tutorpal.R;
import com.example.sabeena.tutorpal.models.Day;
import com.example.sabeena.tutorpal.models.TuitionClass;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link dayOfTheWeekFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link dayOfTheWeekFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class dayOfTheWeekFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ArrayList<TuitionClass> tuitionsForTheDay = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public dayOfTheWeekFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment MondayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static dayOfTheWeekFragment newInstance(String param1) {
        dayOfTheWeekFragment fragment = new dayOfTheWeekFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_day_of_the_week, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.dayOfTheWeekRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);

        DatabaseHandler tutorPalDB = new DatabaseHandler(this.getActivity());
        SQLiteDatabase db = tutorPalDB.getReadableDatabase();

        ArrayList<Integer> tuitionIDs = tutorPalDB.getAllTuitionOfTheDay(db, mParam1);

        for (int i = 0; i < tuitionIDs.size(); i++) {
            Cursor tuition = tutorPalDB.getTuition(db, tuitionIDs.get(i));
            if (tuition.getCount() > 0) {
                tuition.moveToFirst();

                TuitionClass t = new TuitionClass(tuition.getInt(1));
                t.setSubject(tuition.getString(2));
                t.setTutorName(tuition.getString(3));
                t.setTutorACNumber(tuition.getString(4));
                t.setLocation(tuition.getString(5));
                t.setTuitionFee(tuition.getDouble(6));
                t.setLongitude(tuition.getString(7));
                t.setLatitude(tuition.getString(8));

                Day.DayOfTheWeek d = Day.DayOfTheWeek.MONDAY;

                Cursor tuitionDays = tutorPalDB.getTuitionDay(db, tuition.getInt(0));//from the DAY_TABLE
                tuitionDays.moveToFirst();
                if (tuitionDays.getCount() != 0) {

                    if (tuitionDays.getString(2).equals("Monday"))
                        d = Day.DayOfTheWeek.MONDAY;
                    else if (tuitionDays.getString(2).equals("Tuesday"))
                        d = Day.DayOfTheWeek.THURSDAY;
                    else if (tuitionDays.getString(2).equals("Wednesday"))
                        d = Day.DayOfTheWeek.WEDNESDAY;
                    else if (tuitionDays.getString(2).equals("Thursday"))
                        d = Day.DayOfTheWeek.THURSDAY;
                    else if (tuitionDays.getString(2).equals("Friday"))
                        d = Day.DayOfTheWeek.FRIDAY;
                    else if (tuitionDays.getString(2).equals("Saturday"))
                        d = Day.DayOfTheWeek.SATURDAY;
                    else if (tuitionDays.getString(2).equals("Sunday"))
                        d = Day.DayOfTheWeek.SUNDAY;
                }
                Day day = new Day(d, tuitionDays.getString(3), tuitionDays.getString(3));
                t.setDay(day);
                tuitionsForTheDay.add(t);
            }
        }
        tutorPalDB.close();
        mRecyclerView.setAdapter(new dayOfTheWeekAdapter(getActivity(), tuitionsForTheDay));
        Log.d(mParam1, mParam1);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        void onFragmentInteraction(Uri uri);
    }
}
