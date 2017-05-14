package com.example.sabeena.tutorpal.views;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sabeena.tutorpal.Presenter.DatabaseHandler;
import com.example.sabeena.tutorpal.Presenter.TabLayoutAdapter;
import com.example.sabeena.tutorpal.Presenter.ViewTuitionTabLayoutAdapter;
import com.example.sabeena.tutorpal.R;
import com.example.sabeena.tutorpal.models.Child;
import com.example.sabeena.tutorpal.models.TuitionClass;

public class ViewChild extends AppCompatActivity implements ViewTuitionFragment.OnFragmentInteractionListener{

    int receivedChildID;
    DatabaseHandler tutorPalDB;
    TextView nameTxt;
    RadioButton radiobtnGender;
    RadioGroup radioGenderGrp;
    RadioButton radiobtnMale;
    RadioButton radiobtnFemale;
    TabLayout tabLayout;
    ViewTuitionTabLayoutAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receivedChildID = getIntent().getIntExtra("ID of the Child",0);
        setContentView(R.layout.activity_view_child);

        tutorPalDB = new DatabaseHandler(this);
        Child c = tutorPalDB.getChild(receivedChildID);

        nameTxt=(TextView) findViewById(R.id.nameTxt);
        nameTxt.setText(c.getName());
        //nameTxt.setInputType(InputType.TYPE_NULL);

        //radioGenderGrp = (RadioGroup) findViewById(R.id.radioBtnGrp);
        radiobtnFemale = (RadioButton) findViewById(R.id.radioBtnFemale);
        radiobtnMale = (RadioButton) findViewById(R.id.radioBtnMale);

        if(c.getGender().equals(radiobtnFemale.getText())){
            radiobtnFemale.setChecked(true);
            radiobtnMale.setChecked(false);
            radiobtnMale.setEnabled(false);
        }else if(c.getGender().equals(radiobtnMale.getText())){
            radiobtnMale.setChecked(true);
            radiobtnFemale.setChecked(false);
            radiobtnFemale.setEnabled(false);
        }
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        SQLiteDatabase db = tutorPalDB.getReadableDatabase();
        Cursor tuitions = tutorPalDB.getAllTuition(db, receivedChildID);

        for(int i=0;i<tuitions.getCount();i++){
            tabLayout.addTab(tabLayout.newTab().setText("CLASS "+ (i+1)));

        }

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new ViewTuitionTabLayoutAdapter(getSupportFragmentManager(), receivedChildID, this);

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

        //Toast.makeText(this, c.getName()+c.getGender(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
