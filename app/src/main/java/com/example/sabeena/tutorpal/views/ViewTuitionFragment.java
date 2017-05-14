package com.example.sabeena.tutorpal.views;

import android.app.TimePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.sabeena.tutorpal.Presenter.DatabaseHandler;
import com.example.sabeena.tutorpal.R;
import com.example.sabeena.tutorpal.models.Day;
import com.example.sabeena.tutorpal.models.TuitionClass;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewTuitionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewTuitionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewTuitionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    EditText etStartTime;
    EditText etEndTime;
    private static final String ARG_PARAM1 = "param1";
    private int mParam1;//last childID
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters


    private OnFragmentInteractionListener mListener;

    public ViewTuitionFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ViewTuitionFragment newInstance(int param1) {
        ViewTuitionFragment fragment = new ViewTuitionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }

    public void showStartTimePickerDialog() {
        etStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String am_pm = "";
                        am_pm = (selectedHour < 12) ? "AM" : "PM";//12 hour clk
                        etStartTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 12 hour time
                mTimePicker.setTitle("Select Start Time");
                //mTimePicker.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
                mTimePicker.show();

            }
        });
    }

    public void showEndTimePickerDialog() {
        etEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                //final String am_pm = (hour < 12) ? "AM" : "PM";
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {//will convert to a 24 hour time
                        String am_pm = "";
                        am_pm = (selectedHour < 12) ? "AM" : "PM";//12 hour clk

                        //Calendar c = Calendar.getInstance();
                        //SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                        //String DateTime = sdf.format(selectedHour);
                        //datetime.set(Calendar.HOUR_OF_DAY,selectedHour);
                        etEndTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 12 hour time
                mTimePicker.setTitle("Select End Time");
                //mTimePicker.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
                mTimePicker.show();

            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_tuition, container, false);

        final Spinner days_spinner = (Spinner) view.findViewById(R.id.spinnerDay);

        //create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.days_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        days_spinner.setAdapter(adapter);

        etStartTime = (EditText) view.findViewById(R.id.etStartTime);
        showStartTimePickerDialog();

        etEndTime = (EditText) view.findViewById(R.id.etEndTime);
        showEndTimePickerDialog();

        //Button btnAdd = (Button) view.findViewById(R.id.btnAdd);
        //btnAdd.setOnClickListener(new View.OnClickListener() {
        // @Override
        //public void onClick(View v) {
        // FragmentManager fm = getFragmentManager();
        //fm.beginTransaction().remove();
        //      Toast.makeText(getActivity(), "Added", Toast.LENGTH_SHORT).show();
        //    }
        //}
        //);
        //Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        final Integer tuitionID = mParam1;

        final EditText etSubject = (EditText) view.findViewById(R.id.etSubject);
        final EditText etTutorName = (EditText) view.findViewById(R.id.etTutorName);
        final EditText etTutorACNumber = (EditText) view.findViewById(R.id.etTutorACNumber);
        final EditText etFee = (EditText) view.findViewById(R.id.etFee);
        final EditText etVenue = (EditText) view.findViewById(R.id.etVenue);


        etSubject.setFocusable(false);
        etTutorName.setFocusable(false);
        etTutorACNumber.setFocusable(false);
        etFee.setFocusable(false);
        etVenue.setFocusable(false);

        DatabaseHandler tutorPalDB = new DatabaseHandler(this.getActivity());
        SQLiteDatabase db = tutorPalDB.getReadableDatabase();

        Cursor tuition = tutorPalDB.getTuition(db, tuitionID);
        tuition.moveToFirst();
        etSubject.setText(tuition.getString(2));
        etTutorName.setText(tuition.getString(3));
        etTutorACNumber.setText(tuition.getString(4));
        etVenue.setText(tuition.getString(5));
        etFee.setText(Double.toString(tuition.getDouble(6)));

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.days_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        days_spinner.setAdapter(spinnerAdapter);


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
/*
        Button btnOK = (Button) view.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etSubject.getText().toString().length() > 0 && etFee.getText().toString().length() > 0 && etVenue.getText().toString().length() > 0) {
                    TuitionClass tuition = new TuitionClass(lastChildID + 1);
                    tuition.setSubject(etSubject.getText().toString());
                    if (etTutorName.getText().toString().length() > 0) {
                        tuition.setTutorName(etTutorName.getText().toString());
                    }
                    if (etTutorACNumber.getText().toString().length() > 0) {
                        tuition.setTutorACNumber(etTutorACNumber.getText().toString());
                    }
                    tuition.setTuitionFee(Double.parseDouble(etFee.getText().toString()));
                    tuition.setLocation(etSubject.getText().toString());

                    Day.DayOfTheWeek selectedDay = Day.DayOfTheWeek.MONDAY;
                    if (days_spinner.getSelectedItem().toString() == "Monday")
                        selectedDay = Day.DayOfTheWeek.MONDAY;
                    else if (days_spinner.getSelectedItem().toString() == "Tuesday")
                        selectedDay = Day.DayOfTheWeek.TUESDAY;
                    else if (days_spinner.getSelectedItem().toString() == "Wednesday")
                        selectedDay = Day.DayOfTheWeek.WEDNESDAY;
                    else if (days_spinner.getSelectedItem().toString() == "Thurssday")
                        selectedDay = Day.DayOfTheWeek.THURSDAY;
                    else if (days_spinner.getSelectedItem().toString() == "Friday")
                        selectedDay = Day.DayOfTheWeek.FRIDAY;
                    else if (days_spinner.getSelectedItem().toString() == "Saturday")
                        selectedDay = Day.DayOfTheWeek.SATURDAY;
                    else if (days_spinner.getSelectedItem().toString() == "Sunday")
                        selectedDay = Day.DayOfTheWeek.SUNDAY;
                    //days_spinner.getSelectedItem().toString(),etStartTime.getText().toString(),etEndTime.getText().toString()
                    tuition.setDay(new Day(selectedDay, etStartTime.getText().toString(), etEndTime.getText().toString()));

                }

            }
        });*/


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
