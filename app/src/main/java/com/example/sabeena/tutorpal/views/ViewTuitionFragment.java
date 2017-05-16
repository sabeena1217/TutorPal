package com.example.sabeena.tutorpal.views;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;

import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.sabeena.tutorpal.Presenter.DatabaseHandler;
import com.example.sabeena.tutorpal.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewTuitionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewTuitionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewTuitionFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    EditText etStartTime;
    EditText etEndTime;
    private static final String ARG_PARAM1 = "param1";
    private int mParam1;//last childID
    private static final String ARG_PARAM2 = "param2";
    MapView mapView;
    GoogleMap map;
    LocationManager locationManager;
    Marker marker;
    double latitude;
    double longitude;
    EditText etVenue;

    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;

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

        mapView = (MapView) view.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this); //this is important

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
        etVenue = (EditText) view.findViewById(R.id.etVenue);


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

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //mapView.onPause();

        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        //map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        map.getUiSettings().setZoomControlsEnabled(true);
        // map.getUiSettings().setMyLocationButtonEnabled(true);
//------



        // mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                map.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            buildGoogleApiClient();
            map.setMyLocationEnabled(true);
        }



        //--------
        //map.addMarker(new MarkerOptions().position(new LatLng(50,6)));
        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(50,6), 10));
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = map.addMarker(markerOptions);

        //move map camera
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));


        String loc = etVenue.getText().toString();
        Toast.makeText(getContext(),loc,Toast.LENGTH_SHORT).show();
        List<Address> addressList = null;
        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(getContext());
            try {
                addressList = geocoder.getFromLocationName(loc, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Address address = addressList.get(0);
            LatLng latLng1 = new LatLng(address.getLatitude(),address.getLongitude());
            MarkerOptions markerOptions1 = new MarkerOptions();
            markerOptions1.position(latLng1);
            markerOptions1.title("Tuition");
            markerOptions1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            mCurrLocationMarker = map.addMarker(markerOptions1);

            //move map camera
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng1));

        }

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getContext())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        //mLocationRequest.setInterval(1000);
        //mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        map.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}
