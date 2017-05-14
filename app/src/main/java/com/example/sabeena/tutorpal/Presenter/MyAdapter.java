package com.example.sabeena.tutorpal.Presenter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sabeena.tutorpal.R;
import com.example.sabeena.tutorpal.models.Child;
import com.example.sabeena.tutorpal.views.DashboardFragment;
import com.example.sabeena.tutorpal.views.ViewChild;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SaBeeNa on 3/30/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.RecyclerVH> {

    Context c;
    private ArrayList<Child> myChildren = new ArrayList<>();
    DatabaseHandler tutorPalDB;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    //ViewHolder class
    public static class RecyclerVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView IDTxt;
        public TextView nameTxt;
        public TextView genderTxt;
        public TextView noOfClassesTxt;
        private final Context context;

        public RecyclerVH(View v) {
            super(v);
            context = v.getContext();

            IDTxt = (TextView) v.findViewById(R.id.IDTxt);
            nameTxt = (TextView) v.findViewById(R.id.nameTxt);
            genderTxt = (TextView) v.findViewById(R.id.genderTxt);
            noOfClassesTxt = (TextView) v.findViewById(R.id.noOfClassesTxt);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent int1 = new Intent(context, ViewChild.class);
            int1.putExtra("ID of the Child",Integer.parseInt((String) IDTxt.getText()));
            context.startActivity(int1);
            //Toast.makeText(v.getContext(), "position = " + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Context c, ArrayList<Child> myChildren) {
        this.myChildren = myChildren;//nullpointer exception
        this.c = c;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.model, parent, false);
        RecyclerVH vh = new RecyclerVH(v);
        //View cannot be converted to TextView
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerVH holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Child myChild = myChildren.get(position);
        holder.nameTxt.setText(myChild.getName());
        holder.genderTxt.setText(myChild.getGender());
        holder.noOfClassesTxt.setText(Integer.toString(myChild.getNoOfClasses()));
        holder.IDTxt.setText(Integer.toString(myChild.getID()));
        //childreBuffer.append("ID :" + result.getString(0) + "\n");
        //childreBuffer.append("ID :" + result.getString(0) + "\n");

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return myChildren.size();
    }
}

