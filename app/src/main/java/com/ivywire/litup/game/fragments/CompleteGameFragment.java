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
import android.widget.TextView;

import com.ivywire.litup.GameActivity;
import com.ivywire.litup.MenuActivity;
import com.ivywire.litup.R;
import com.ivywire.resources.FontManager;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CompleteGameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CompleteGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class CompleteGameFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SCORE  = "score";

    private int score;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param score score.
     * @return A new instance of fragment CompleteGameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CompleteGameFragment newInstance(int score) {
        CompleteGameFragment fragment = new CompleteGameFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SCORE, score);
        fragment.setArguments(args);
        return fragment;
    }
    public CompleteGameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            score = getArguments().getInt(ARG_SCORE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_complete_game, container, false);

        TextView scoreLabel = (TextView) rootview.findViewById(R.id.scoreLabel);
        TextView scoreView = (TextView) rootview.findViewById(R.id.scoreView);
        Button playAgainBtn = (Button) rootview.findViewById(R.id.playAgainBtn);
        Button mainMenuBtn = (Button) rootview.findViewById(R.id.mainMenuBtn);

        FontManager.applyFont(getActivity(), scoreLabel, "fonts/Raleway-SemiBold.otf");
        FontManager.applyFont(getActivity(), scoreView, "fonts/Raleway-Bold.otf");
        FontManager.applyFont(getActivity(), playAgainBtn, "fonts/Raleway-Bold.otf");
        FontManager.applyFont(getActivity(), mainMenuBtn, "fonts/Raleway-Bold.otf");

        scoreView.setText("" + score);

        playAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GameActivity.class);
                startActivity(intent);
            }
        });
        mainMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MenuActivity.class);
                startActivity(intent);
            }
        });

        // Useing HighScoreManager update high score
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
