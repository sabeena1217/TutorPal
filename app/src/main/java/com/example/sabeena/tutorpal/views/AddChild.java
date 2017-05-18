package com.example.sabeena.tutorpal.views;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sabeena.tutorpal.Presenter.AlarmReceiver;
import com.example.sabeena.tutorpal.Presenter.DatabaseHandler;
import com.example.sabeena.tutorpal.Presenter.MyAdapter;
import com.example.sabeena.tutorpal.Presenter.RingtonePlayingService;
import com.example.sabeena.tutorpal.Presenter.TabLayoutAdapter;
import com.example.sabeena.tutorpal.R;
import com.example.sabeena.tutorpal.models.Child;
import com.example.sabeena.tutorpal.models.TuitionClass;

import java.util.ArrayList;
import java.util.Calendar;

public class AddChild extends AppCompatActivity implements AddTuitionFragment.OnFragmentInteractionListener, NewTuitionFragment.OnFragmentInteractionListener {

    DatabaseHandler tutorPalDB;
    EditText etName;
    RadioButton radiobtnGender;
    RadioGroup radioGenderGrp;
    Button btnDone, btnDecline;
    TabLayoutAdapter adapter;
    int tabCount = 0;
    TabLayout tabLayout;
    ViewPager viewPager;
    private ArrayList<Fragment> tuitions = new ArrayList<Fragment>();
    ArrayList<TuitionClass> tuitionClasses = new ArrayList<TuitionClass>();
    //MyAdapter myAdapter;

    int lastChildId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //myAdapter = (MyAdapter) getIntent().getSerializableExtra("MyAdapter");
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_add_child);
        tutorPalDB = new DatabaseHandler(this);
        //tabHost = (FragmentTabHost)findViewById(R.id.tabHost);
        //tabHost.setup(this,getSupportFragmentManager(),R.id.tab1);
        //tabHost.addTab(tabHost.newTabSpec("TAB1").setIndicator("TAB1"),AddTuitionFragment.class,null);
        //tabHost.addTab(tabHost.newTabSpec("TAB2").setIndicator("TAB2"),AddTuitionFragment.class,null);
        //tabHost.addTab(tabHost.newTabSpec("TAB3").setIndicator("TAB3"),AddTuitionFragment.class,null);

       /* btnAddTuition = (Button) findViewById(R.id.btnAddTuition);

        btnAddTuition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                AddTuitionFragment tuition = AddTuitionFragment.newInstance();
                fm.beginTransaction().replace(R.id.fragment_placer, tuition, tuition.getTag()).commit();
            }
        });*/

        etName = (EditText) findViewById(R.id.etName);
        radioGenderGrp = (RadioGroup) findViewById(R.id.radioBtnGrp);
        lastChildId = tutorPalDB.getLastChildID();
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        //tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        //tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));

        //tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);  // scorllable tab
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        //final EditText etNoOfClasses = (EditText)findViewById(R.id.etNoOfClasses);
        //Button btnGo = (Button)findViewById(R.id.btnGo);

        //btnGo.setOnClickListener(new View.OnClickListener() {
        // @Override
        //public void onClick(View v) {
        //  tabCount = Integer.parseInt(etNoOfClasses.getText().toString());
        //}
        // }
        // );

        //addTabItem();


        //---------------------------


        tabLayout.addTab(tabLayout.newTab().setText("NEW"));

        NewTuitionFragment tab3 = NewTuitionFragment.newInstance();

        tuitions.add(tab3);

        //replaceCurrentTab();
        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new TabLayoutAdapter(getSupportFragmentManager(), tuitions);
        //adapter.addTabPage();
        //tab layout get changed here

        //if(tabLayout.getTabCount() != 1){
        //  for(int i=tabLayout.getTabCount()-1 ; i>0 ;i--){
        //    tabLayout.getTabAt(i).setText("Class " +i);
        //}
        //}
        //if (adapter != null) {
        // adapter.restoreState();
        // setCurrentItemInternal(ss.position, false, true);
        //}

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

        //---------------------------------
        btnDone = (Button) findViewById(R.id.btnDone);

        addChild();

        btnDecline = (Button) findViewById(R.id.btnDecline);
        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(AddChild.this, "You have just added a new child to the system!", Toast.LENGTH_LONG).show();
                Toast.makeText(AddChild.this, Integer.toString(tutorPalDB.getLastChildID()), Toast.LENGTH_LONG).show();
            }
        });


    }

    public void addTabItem() {
//        tabLayout.addTab(tabLayout.newTab().setText("NEW"),tabLayout.getTabCount());

    }

    private void scheduleStartAlarm(Calendar cal, int childID, int tuitionID, int startOrEnd) {
        Calendar calendar = cal;
        //field, value
        //calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        //calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        //calendar.set(Calendar.MINUTE, minute);

        Toast.makeText(this, "alarm set on " + calendar.getTime(), Toast.LENGTH_LONG).show();
        //check we aren't setting it in the past which would trigger it to fire instantly
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            //  Toast.makeText(getActivity(),calendar.getTimeInMillis() + " " + System.currentTimeMillis() , Toast.LENGTH_LONG).show();
            calendar.add(Calendar.DAY_OF_YEAR, 7);
        }

        Intent intent = new Intent(this, AlarmReceiver.class);
        int[] ID_array = new int[]{childID, tuitionID, startOrEnd};
        intent.putExtra("ID_array", ID_array);
        Log.d("Add schedule" + ID_array[0], "Add schedule" + ID_array[0]);
        Log.d("Add alarm" + calendar.getTimeInMillis(), "Add schedule" + ID_array[0]);
        Log.d("Add alarm" + calendar.getTimeInMillis(), "Add schedule" + ID_array[0]);
        Log.d("Add current" + System.currentTimeMillis(), "Add schedule" + ID_array[0]);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int) calendar.getTimeInMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);
//        Log.d("ADD CHILD NOT....", "ADD CHILD NOT....");
    }


    private void scheduleEndAlarm(Calendar cal, int childID, int tuitionID, int startOrEnd) {
        Calendar calendar = cal;
        //field, value
        //calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        //calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        //calendar.set(Calendar.MINUTE, minute);

        Toast.makeText(this, "alarm set on " + calendar.getTime(), Toast.LENGTH_LONG).show();
        //check we aren't setting it in the past which would trigger it to fire instantly
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            //  Toast.makeText(getActivity(),calendar.getTimeInMillis() + " " + System.currentTimeMillis() , Toast.LENGTH_LONG).show();
            calendar.add(Calendar.DAY_OF_YEAR, 7);
        }

        Intent intent = new Intent(this, AlarmReceiver.class);
        int[] ID_array = new int[]{childID, tuitionID, startOrEnd};
        intent.putExtra("ID_array", ID_array);
        Log.d("Add schedule" + ID_array[0], "Add schedule" + ID_array[0]);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int) calendar.getTimeInMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);
//        Log.d("ADD CHILD NOT....", "ADD CHILD NOT....");
    }

    public void cancelAlarmStartAlarm() {
        Intent i = new Intent(this, RingtonePlayingService.class);
        stopService(i);

        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int) System.currentTimeMillis(), intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    public void cancelAlarmEndAlarm() {
        Intent i = new Intent(this, RingtonePlayingService.class);
        stopService(i);

        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int) System.currentTimeMillis(), intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    public void addChild() {
        btnDone.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (etName.getText().toString().length() > 0 && radioGenderGrp.getCheckedRadioButtonId() != -1) {

                            // get selected radio button from radioGroup
                            int selectedGender = radioGenderGrp.getCheckedRadioButtonId();

                            // find the radiobutton by returned id
                            radiobtnGender = (RadioButton) findViewById(selectedGender);

                            Child c = new Child(etName.getText().toString(), radiobtnGender.getText().toString());
                            //
                            boolean isInserted = tutorPalDB.insertChild(c);

                            if (isInserted) {
                                for (int i = 0; i < tuitionClasses.size(); i++) {
                                    boolean isTuitionInserted = tutorPalDB.insertClass(tuitionClasses.get(i));
                                    boolean isDayInserted = tutorPalDB.insertDay(tutorPalDB.getLastTuitionID(), tuitionClasses.get(i).getDay());
                                    if (!isTuitionInserted | !isDayInserted) {
                                        Log.d("ADD Tuition NOT....", "ADD CHILD NOT....");
                                        break;
                                    }
                                    if (new Integer(tuitionClasses.get(i).isNotification()).equals(1)) {
                                        scheduleStartAlarm(tuitionClasses.get(i).getStartTime(), tuitionClasses.get(i).getChildID(), tutorPalDB.getLastTuitionID(), 0);
                                        scheduleEndAlarm(tuitionClasses.get(i).getEndTime(), tuitionClasses.get(i).getChildID(), tutorPalDB.getLastTuitionID(), 1);
                                    }
                                    //Log.d("ADD CHILD", "ADD CHILD NOT....");
                                }
                                etName.setText(null);
                                radioGenderGrp.clearCheck();
                                Toast.makeText(AddChild.this, "You have just added a new child to the system! " + tuitionClasses.size(), Toast.LENGTH_LONG).show();
                                //have to update the Dashboard fragment with an insertion of
                                /*if(myAdapter == null) {
                                    myAdapter = (MyAdapter) getIntent().getSerializableExtra("MyAdapter");
                                    myAdapter.notifyDataSetChanged();
Log.d("myAdapter null", "ADD CHILD NOT....");
                                }*/
                                finish();
                            } else {
                                Toast.makeText(AddChild.this, "Error Occured!", Toast.LENGTH_LONG).show();
                            }

                           /* if(isInserted){
                                Cursor result = tutorPalDB.getAllChildren();
                                if (result.getCount() == 0){
                                    //show a message saying no data;
                                    showMessage("Error", "No Data Found!");
                                    return;
                                }

                                //create an instance of string buffer
                                StringBuffer childreBuffer = new StringBuffer();
                                while(result.moveToNext()){
                                    childreBuffer.append("ID :" + result.getString(0) + "\n");
                                    childreBuffer.append("Name :" + result.getString(1) + "\n");
                                    childreBuffer.append("Gender :" + result.getString(2) + "\n\n");
                                }
                                //show all data
                                showMessage("Data", childreBuffer.toString());
                            }else{
                                Toast.makeText(AddChild.this,"Error Occured!",Toast.LENGTH_LONG).show();
                            }*/
                        }else{
                            showMessage("Invalid Inputs!","Enter Subject, Fee, Venue and the Day of the tuition class!");
                        }
                    }
                }
        );
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioBtnFemale:
                if (checked)
                    //child is a female
                    break;
            case R.id.radioBtnMale:
                if (checked)
                    // child is a male
                    break;
        }
    }

    //public void replaceCurrentTab() {
    //  AddTuitionFragment add = AddTuitionFragment.newInstance();
    //fm.beginTransaction().replace(R.id.fragment_layout, add).commit();
    //}

    @Override
    public void onFragmentInteraction(Boolean clicked) {
        if (clicked) {
            tabCount += 1;
            tabLayout.addTab(tabLayout.newTab().setText("CLASS " + tabCount));
            int lastChildID = this.lastChildId;
            AddTuitionFragment tab2 = AddTuitionFragment.newInstance(lastChildID);
            tuitions.add(tab2);
            adapter = new TabLayoutAdapter(getSupportFragmentManager(), tuitions);
            viewPager.setAdapter(adapter);

            //Toast.makeText(this, "will this work at least?", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFragmentInteraction(TuitionClass tuition) {
//true to remove the fragment
        if (tuition == null) {

            //tuitions.remove();
            //adapter = new TabLayoutAdapter(getSupportFragmentManager(), tuitions);
            //viewPager.setAdapter(adapter);

            Toast.makeText(AddChild.this, "Tuition Removed", Toast.LENGTH_LONG).show();
        } else {
            tuitionClasses.add(tuition);
            Toast.makeText(AddChild.this, "noOfclasses " + tuitionClasses.size(), Toast.LENGTH_LONG).show();
        }
        //Toast.makeText(AddChild.this, "noOfclasses "+tuitionClasses.size(), Toast.LENGTH_LONG).show();
    }
}
