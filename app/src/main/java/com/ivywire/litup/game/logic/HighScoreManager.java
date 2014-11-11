package com.ivywire.litup.game.logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Class to maanage high score updating, retrieving, etc. Built heavily on SharedPreferences access
 */
public class HighScoreManager {
    private static HighScoreManager instance;
    private Context context;
    private int []highScoreArray;

    private HighScoreManager(){}

    private HighScoreManager(Context context) {
        this.context = context;
        SharedPreferences highScorePref = context.getSharedPreferences("highScore", 0);
        highScoreArray = new int[10];

        // Initialize all high scores as their appropriate values or zero
        for (int i = 0; i < 10; i++) {
            highScoreArray[i] = highScorePref.getInt("Score" + i, 0);
//            Log.d("Score", ""highScoreArray[i]);
        }

        for (int i = 0; i < 10; i++) {
            Log.d("HighScores", "" + highScoreArray[i]);
        }
    }

    // We only want one instance at once
    public static HighScoreManager getInstance(Context ctx) {
        if (instance == null) {
            instance = new HighScoreManager(ctx);
        }
        return instance;
    }

    // Method to delete instance when done
    public static void deleteInstance() {
        instance = null;
    }

    // Add scores
    public void addScore(int score) {
        // cycle through array until we get to a point it fits in
        for (int i = 0; i < (highScoreArray.length); i++) {
            Log.d("compared score", "" + highScoreArray[i]);
            if (score >= highScoreArray[i]) {
                // Insert score to add it to shared prefences
                Log.d("Index", "" + i);
                insertScore(score, i);
                break;
            }
        }
    }

    // Private method to insert score between two entries
    private void insertScore(int score, int index) {
        SharedPreferences highScorePref = context.getSharedPreferences("highScore", 0);
        SharedPreferences.Editor highScorePrefEditor = highScorePref.edit();

        int temp1 = highScoreArray[index]; // temporarily holds value to be reassigned to next index
        int temp2;
        highScoreArray[index] = score;
        highScorePrefEditor.putInt("Score" + index, score);
        boolean written = highScorePrefEditor.commit();
        if(written) Log.d("Written", "Success");

        // At this point if index == 9 then stop since we don't need to go farther
        if (index == 9) {
            return;
        }

        for (int i = (index + 1); i < 10; i++) {
            temp2 = highScoreArray[i]; // Store the old value at i so we don't lose it
            highScoreArray[i] = temp1; // Assign the new value of i
            highScorePrefEditor.putInt("Score" + i, temp1); // Store in shared preferences
            highScorePrefEditor.commit();
            temp1 = temp2; // Store the old value of i to be moved to i + 1
        }
    }

    public int[] getHighScoreArray() {
        return highScoreArray;
    }
}
