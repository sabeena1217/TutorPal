package com.example.sabeena.tutorpal.views;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.example.sabeena.tutorpal.Presenter.DatabaseHandler;
import com.example.sabeena.tutorpal.R;
import com.example.sabeena.tutorpal.models.Day;
import com.example.sabeena.tutorpal.models.TuitionClass;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.Marker;

import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditTuitionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditTuitionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditTuitionFragment extends Fragment {


    Button btnPlacePicker;
    EditText etStartTime;
    EditText etEndTime;
    //private static final String ARG_PARAM1 = "param1";
    //private int mParam1;//last childID
    //private static final String ARG_PARAM2 = "param2";
    MapView mapView;
    GoogleMap map;
    LocationManager locationManager;
    Marker marker;
    double latitude;
    double longitude;
    EditText etVenue;
    Switch notificationSwitch;

    private static int PLACE_PICKER_REQUEST = 1;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    Integer tuitionID;
    Integer childID;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private int mParam2;


    private OnFragmentInteractionListener mListener;

    public EditTuitionFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static EditTuitionFragment newInstance(int param1, int param2) {

        EditTuitionFragment fragment = new EditTuitionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_tuition, container, false);


        notificationSwitch = (Switch) view.findViewById(R.id.switchNotification);
        notificationSwitch.setChecked(true);
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    Toast.makeText(getContext(), "set off", Toast.LENGTH_SHORT).show();
                    //onButtonPressed(true);
                    //notificationSwitch.setClickable(false);
                }
            }
        });


        final Spinner days_spinner = (Spinner) view.findViewById(R.id.spinnerDay);

        //create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.days_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        days_spinner.setAdapter(adapter);

        etStartTime = (EditText) view.findViewById(R.id.etStartTime);


        etEndTime = (EditText) view.findViewById(R.id.etEndTime);


        final Calendar startTime = showStartTimePickerDialog();


        final Calendar endTime = showEndTimePickerDialog();

        tuitionID = mParam1;
        childID = mParam2;

        final EditText etSubject = (EditText) view.findViewById(R.id.etSubject);
        final EditText etTutorName = (EditText) view.findViewById(R.id.etTutorName);
        final EditText etTutorACNumber = (EditText) view.findViewById(R.id.etTutorACNumber);
        final EditText etFee = (EditText) view.findViewById(R.id.etFee);
        etVenue = (EditText) view.findViewById(R.id.etVenue);
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

        final DatabaseHandler tutorPalDB = new DatabaseHandler(this.getActivity());
        SQLiteDatabase db = tutorPalDB.getReadableDatabase();

        Cursor tuition = tutorPalDB.getTuition(db, tuitionID);
        if (tuition.getCount() > 0) {
            tuition.moveToFirst();
            etSubject.setText(tuition.getString(2));
            etTutorName.setText(tuition.getString(3));
            etTutorACNumber.setText(tuition.getString(4));
            etVenue.setText(tuition.getString(5));
            etFee.setText(Double.toString(tuition.getDouble(6)));

            longitude = Double.parseDouble(tuition.getString(7));
            latitude = Double.parseDouble(tuition.getString(8));

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
        }


        //-------------------------------


        Button btnOK = (Button) view.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etSubject.getText().toString().length() > 0 && etFee.getText().toString().length() > 0 && etVenue.getText().toString().length() > 0) {
                if (tutorPalDB.getLastTuitionID() >= tuitionID) {

                        TuitionClass tuition = new TuitionClass(childID);
                    tuition.setTuitionID(tuitionID);
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
                        if (notificationSwitch.isChecked()) {
                            tuition.setNotification(1);
                        } else {
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
                        onButtonPressed(tuition, 0);//update
                    }else{

                    TuitionClass tuition = new TuitionClass(childID);
                    //tuition.setTuitionID(tuitionID);
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
                    if (notificationSwitch.isChecked()) {
                        tuition.setNotification(1);
                    } else {
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
                    onButtonPressed(tuition, 1);//new class

                }

                }
            }
        });


        //------------------------------------


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(TuitionClass tuition1, int isUpdatedOrDeleted) {
        if (mListener != null) {
            mListener.onFragmentInteraction(tuition1 , isUpdatedOrDeleted);
        }
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
        void onFragmentInteraction(TuitionClass tuition, int isUpdatedOrDeleted);
    }
}
