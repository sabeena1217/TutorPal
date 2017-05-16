package com.example.sabeena.tutorpal.Presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sabeena.tutorpal.R;
import com.example.sabeena.tutorpal.models.TuitionClass;
import com.example.sabeena.tutorpal.views.ViewChild;

import java.util.ArrayList;

/**
 * Created by SaBeeNa on 5/14/2017.
 */

public class dayOfTheWeekAdapter extends RecyclerView.Adapter<dayOfTheWeekAdapter.RecyclerVH> {

    private ArrayList<TuitionClass> tuitionsForTheDay = new ArrayList<>();
    Context c;

    public dayOfTheWeekAdapter(Context c, ArrayList<TuitionClass> tuitionsForTheDay) {
        this.tuitionsForTheDay = tuitionsForTheDay;//nullpointer exception
        Log.d("tuitiondfrottheday" + tuitionsForTheDay.size(), "tuitiondfrottheday" + tuitionsForTheDay.size());
        this.c = c;
    }
    public static class RecyclerVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView nameTxt;
        public TextView subjectTxt;
        public TextView fromTxt;
        public TextView toTxt;
        private final Context context;

        public RecyclerVH(View v) {
            super(v);
            context = v.getContext();

            nameTxt = (TextView) v.findViewById(R.id.nameTxt);
            subjectTxt = (TextView) v.findViewById(R.id.subjectTxt);
            toTxt = (TextView) v.findViewById(R.id.toTxt);
            fromTxt = (TextView) v.findViewById(R.id.fromTxt);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //Intent int1 = new Intent(context, ViewChild.class);
            //int1.putExtra("ID of the Child",Integer.parseInt((String) IDTxt.getText()));
            //context.startActivity(int1);
            //Toast.makeText(v.getContext(), "position = " + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public dayOfTheWeekAdapter.RecyclerVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.model_dayoftheweek, parent, false);
        dayOfTheWeekAdapter.RecyclerVH vh = new dayOfTheWeekAdapter.RecyclerVH(v);
        //View cannot be converted to TextView
        return vh;
    }

    @Override
    public void onBindViewHolder(dayOfTheWeekAdapter.RecyclerVH holder, int position) {
// - get element from your dataset at this position
        // - replace the contents of the view with that element

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TuitionClass t = tuitionsForTheDay.get(position);
        holder.nameTxt.setText(t.getSubject());
        holder.subjectTxt.setText(t.getSubject());
        holder.fromTxt.setText(t.getDay().getStarTime());
        holder.toTxt.setText(t.getDay().getEndTime());

    }

    @Override
    public int getItemCount() {
        return tuitionsForTheDay.size();
    }
}
