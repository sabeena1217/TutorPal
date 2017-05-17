package com.example.sabeena.tutorpal.views;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;

import android.content.Intent;
import android.media.Ringtone;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.sabeena.tutorpal.Presenter.AlarmReceiver;
import com.example.sabeena.tutorpal.Presenter.RingtonePlayingService;
import com.example.sabeena.tutorpal.R;
import com.example.sabeena.tutorpal.models.Day;
import com.example.sabeena.tutorpal.models.TuitionClass;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class AddTuitionFragment extends Fragment {

    static final int DIALOG_ID = 0;
    int hour_x;
    int minute_x;
    EditText etStartTime;
    EditText etEndTime;
    private static final String ARG_PARAM1 = "param1";
    private int mParam1;//last childID
    private Switch tuitionSwitch;
    Button btnPlacePicker;
    private static int PLACE_PICKER_REQUEST = 1;
    EditText etVenue;
    double longitude;
    double latitude;
    Switch notificationSwitch;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    private OnFragmentInteractionListener mListener;

    public AddTuitionFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AddTuitionFragment newInstance(int param1) {
        AddTuitionFragment fragment = new AddTuitionFragment();
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

    public Calendar showStartTimePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
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
                        calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                        calendar.set(Calendar.MINUTE, selectedMinute);
                    }
                }, hour, minute, true);//Yes 12 hour time
                mTimePicker.setTitle("Select Start Time");
                //mTimePicker.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
                mTimePicker.show();

            }
        });
        return calendar;
    }

    public Calendar showEndTimePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
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
                        calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                        calendar.set(Calendar.MINUTE, selectedMinute);
                    }
                }, hour, minute, true);//Yes 12 hour time
                mTimePicker.setTitle("Select End Time");
                //mTimePicker.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
                mTimePicker.show();
            }
        });
        return calendar;
    }

    // private void scheduleAlarm(int dayOfWeek, int hourOfDay, int minute) {

    //@Override
    //protected Dialog onCreateDialog(int id){
    //  if(id == DIALOG_ID)
    //    return new TimePickerDialog(getActivity(),kTimePickerListener,hour_x,minute_x,false);
    //return null;
    //}

    protected TimePickerDialog.OnTimeSetListener kTimePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour_x = hourOfDay;
            minute_x = minute;
            Toast.makeText(getActivity(), hour_x + " : " + minute_x, Toast.LENGTH_LONG).show();

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_tuition, container, false);


        notificationSwitch = (Switch) view.findViewById(R.id.switchNotification);
        notificationSwitch.setChecked(true);

        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    Toast.makeText(getContext(), "set off", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), "set on", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Spinner days_spinner = (Spinner) view.findViewById(R.id.spinnerDay);

        //create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.days_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        days_spinner.setAdapter(adapter);

        //tuitionSwitch = (Switch) view.findViewById(R.id.switchTuition);
        //tuitionSwitch.setOnCheckedChangeListener(this);

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
        final Integer lastChildID = mParam1;

        final EditText etSubject = (EditText) view.findViewById(R.id.etSubject);
        final EditText etTutorName = (EditText) view.findViewById(R.id.etTutorName);
        final EditText etTutorACNumber = (EditText) view.findViewById(R.id.etTutorACNumber);
        final EditText etFee = (EditText) view.findViewById(R.id.etFee);
        etVenue = (EditText) view.findViewById(R.id.etVenue);
        etVenue.setFocusable(false);
        etStartTime = (EditText) view.findViewById(R.id.etStartTime);
        final Calendar startTime = showStartTimePickerDialog();

        etEndTime = (EditText) view.findViewById(R.id.etEndTime);
        final Calendar endTime = showEndTimePickerDialog();

        btnPlacePicker = (Button) view.findViewById(R.id.btnPlacePicker);
        btnPlacePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    System.out.println("sabeeeeeeeeee");
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    System.out.println("eeeeeeeeee");
                    e.printStackTrace();
                }
            }


        });

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
                    tuition.setLocation(etVenue.getText().toString());
                    tuition.setLongitude(Double.toString(longitude));
                    tuition.setLatitude(Double.toString(latitude));
                    if(notificationSwitch.isChecked()) {
                        tuition.setNotification(1);
                    }else{
                        tuition.setNotification(0);
                    }

                    int dayOfWeek = 0;
                    Day.DayOfTheWeek selectedDay = Day.DayOfTheWeek.MONDAY;
                    if (days_spinner.getSelectedItem().toString().equals("Monday")) {
                        selectedDay = Day.DayOfTheWeek.MONDAY;
                        startTime.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                        endTime.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                    } else if (days_spinner.getSelectedItem().toString().equals("Tuesday")) {
                        selectedDay = Day.DayOfTheWeek.TUESDAY;
                        startTime.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                        endTime.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                    } else if (days_spinner.getSelectedItem().toString().equals("Wednesday")) {
                        selectedDay = Day.DayOfTheWeek.WEDNESDAY;
                        startTime.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                        endTime.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                    } else if (days_spinner.getSelectedItem().toString().equals("Thursday")) {
                        selectedDay = Day.DayOfTheWeek.THURSDAY;
                        startTime.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                        endTime.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                    } else if (days_spinner.getSelectedItem().toString().equals("Friday")) {
                        selectedDay = Day.DayOfTheWeek.FRIDAY;
                        startTime.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                        endTime.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                    } else if (days_spinner.getSelectedItem().toString().equals("Saturday")) {
                        selectedDay = Day.DayOfTheWeek.SATURDAY;
                        startTime.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                        endTime.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                    } else if (days_spinner.getSelectedItem().toString().equals("Sunday")) {
                        selectedDay = Day.DayOfTheWeek.SUNDAY;
                        startTime.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                        endTime.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                    }
                    //days_spinner.getSelectedItem().toString(),etStartTime.getText().toString(),etEndTime.getText().toString()
                    tuition.setDay(new Day(selectedDay, etStartTime.getText().toString(), etEndTime.getText().toString()));

                    //SimpleDateFormat dfH = new SimpleDateFormat("HH");
                    //SimpleDateFormat dfM = new SimpleDateFormat("mm");
                    tuition.setStartTime(startTime);
                    tuition.setEndTime(endTime);
                    //String st = dfM.format(startTime.getTime());
                    //scheduleAlarm(startTime, lastChildID + 1, );
                    //scheduleAlarm(endTime);
                    //Toast.makeText(getActivity(), "start time " + startTime.getTime(), Toast.LENGTH_LONG).show();
                    onButtonPressed(tuition);
                }

            }
        });
        Button btnQuit = (Button) view.findViewById(R.id.btnQuit);
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(null);
                //FragmentManager fm = getFragmentManager();
                //fm.findFragmentById()
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, getActivity());
                etVenue.setText(place.getName() + " " + place.getAddress());
                longitude = place.getLatLng().longitude;
                latitude = place.getLatLng().latitude;
                //String toastMsg = String.format("Place: %s", );
                //Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(TuitionClass tuition) {
        if (mListener != null) {
            mListener.onFragmentInteraction(tuition);
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

   /* @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
        } else {
        }
    }*/

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
        void onFragmentInteraction(TuitionClass uri);
    }
}
