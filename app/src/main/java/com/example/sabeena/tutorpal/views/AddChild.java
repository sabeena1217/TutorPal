package com.example.sabeena.tutorpal.views;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;


import com.example.sabeena.tutorpal.R;

public class AddChild extends AppCompatActivity {
    //private FragmentTabHost tabHost;
    private TabHost tabHost;
    //FragmentManager fm = getFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);

        //tabHost = (FragmentTabHost)findViewById(R.id.tabHost);
        //tabHost.setup(this,getSupportFragmentManager(),R.id.tab1);
        //tabHost.addTab(tabHost.newTabSpec("TAB1").setIndicator("TAB1"),AddTuitionFragment.class,null);
        //tabHost.addTab(tabHost.newTabSpec("TAB2").setIndicator("TAB2"),AddTuitionFragment.class,null);
        //tabHost.addTab(tabHost.newTabSpec("TAB3").setIndicator("TAB3"),AddTuitionFragment.class,null);

        tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec spec1 = tabHost.newTabSpec("TAB 1");
        spec1.setIndicator("TAB 1");
        spec1.setContent(R.id.layout1);
        tabHost.addTab(spec1);

        TabHost.TabSpec spec2 = tabHost.newTabSpec("TAB 2");
        spec2.setIndicator("TAB 2");
        spec2.setContent(R.id.layout2);
        tabHost.addTab(spec2);



    }
}
