package com.example.sabeena.tutorpal.views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sabeena.tutorpal.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewTuitionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewTuitionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewTuitionFragment extends Fragment {

    TextView txtClickHere;
    View view;
    boolean clicked = false;
    // TODO: Rename parameter arguments, choose names that match

    private OnFragmentInteractionListener mListener;

    public NewTuitionFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static NewTuitionFragment newInstance() {
        NewTuitionFragment fragment = new NewTuitionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_tuition, container, false);
        txtClickHere = (TextView) view.findViewById(R.id.txtClickHere);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked = true;
                //FragmentManager fm = getFragmentManager();
                //AddTuitionFragment add = AddTuitionFragment.newInstance();
                //fm.beginTransaction().replace(R.id.fragment_layout,add,add.getTag()).commit();
                onButtonPressed(clicked);
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Boolean clicked) {
        if (mListener != null) {
            mListener.onFragmentInteraction(clicked);
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
        void onFragmentInteraction(Boolean clicked);
    }
}
