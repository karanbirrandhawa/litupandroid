package com.ivywire.litup.game.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ivywire.litup.R;
import com.ivywire.litup.game.views.DotView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class GameFragment extends Fragment implements View.OnClickListener{
    // Int array to hold resource ids of all DotViews
    final int[] dotIdArray  = {
        R.id.dot0, R.id.dot1, R.id.dot2,  R.id.dot3,  R.id.dot4,  R.id.dot5,  R.id.dot6,
            R.id.dot7,  R.id.dot8,  R.id.dot9,  R.id.dot10,  R.id.dot11,  R.id.dot12,
            R.id.dot13,  R.id.dot14,  R.id.dot15,  R.id.dot16,  R.id.dot17,  R.id.dot18,
            R.id.dot19,  R.id.dot20,  R.id.dot21,  R.id.dot22,  R.id.dot23,  R.id.dot24
    };
    // Boolean array to hold status of each dot
    boolean[] dotStatusArray = new boolean[25];
    
    private OnFragmentInteractionListener mListener;

    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Arguments
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);
        TableLayout table = (TableLayout) rootView.findViewById(R.id.dotTable);

        for (int i = 0; i < 25; i++) {
            DotView dotView = (DotView) rootView.findViewById(dotIdArray[i]);
            dotView.setOnClickListener(this);
        }

        // Return layout view
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        DotView dv = (DotView) view;
        dv.toggleLight();
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
        public void onFragmentInteraction(Uri uri);
    }

}
