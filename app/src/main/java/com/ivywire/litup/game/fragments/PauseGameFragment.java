package com.ivywire.litup.game.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ivywire.litup.GameActivity;
import com.ivywire.litup.MenuActivity;
import com.ivywire.litup.R;
import com.ivywire.litup.SettingsActivity;
import com.ivywire.litup.game.logic.GameController;
import com.ivywire.resources.FontManager;

public class PauseGameFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    public PauseGameFragment() {
        // Required empty public constructor
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
        View rootview = inflater.inflate(R.layout.fragment_pause_game, container, false);
        Button resumeBtn = (Button) rootview.findViewById(R.id.resumeBtn);
        Button restartBtn = (Button) rootview.findViewById(R.id.restartBtn);
        Button quitBtn = (Button) rootview.findViewById(R.id.quitBtn);
        FontManager.applyFont(getActivity(), resumeBtn, "fonts/Raleway-Bold.otf");
        FontManager.applyFont(getActivity(), restartBtn, "fonts/Raleway-Bold.otf");
        FontManager.applyFont(getActivity(), quitBtn, "fonts/Raleway-Bold.otf");
        resumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((GameActivity) getActivity()).resumeGameActivity();
            }
        });
        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GameActivity.class);
                startActivity(intent);
            }
        });
        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MenuActivity.class);
                startActivity(intent);
            }
        });
        return rootview;
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
