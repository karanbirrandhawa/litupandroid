package com.ivywire.litup.game.logic;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Class to maanage high score updating, retrieving, etc. Built heavily on SharedPreferences access
 */
public class HighScoreManager {
    private static HighScoreManager instance;
    private Context context;
    private SharedPreferences highScorePref;
    private SharedPreferences.Editor highScorePrefEditor;
    private int []highScoreArray;

    private HighScoreManager(){}

    private HighScoreManager(Context context) {
        this.context = context;
        highScorePref = context.getSharedPreferences("highScore", context.MODE_PRIVATE);
        highScoreArray = new int[10];

        // Initialize all high scores as their appropriate values or zero
        for (int i = 0; i < 10; i++) {
            highScoreArray[i] = highScorePref.getInt("Score@" + (i + 1), 0);
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
        for (int i = 0; i < (highScoreArray.length - 1); i++) {
            if (i > highScoreArray[i + 1]) {
                // Insert score to add it to shared prefences
                insertScore(score, i);
                break;
            }
        }
    }

    // Private method to insert score between two entries
    private void insertScore(int score, int index) {
        highScorePrefEditor = highScorePref.edit();

        int temp1 = highScoreArray[index]; // temporarily holds value to be reassigned to next index
        int temp2;
        highScoreArray[index] = score;
        for (int i = (index + 1); i < 10; i++) {
            temp2 = highScoreArray[i]; // Store the old value at i so we don't lose it
            highScoreArray[i] = temp1; // Assign the new value of i
            highScorePrefEditor.putInt("Score@" + (i + 1), temp1); // Store in shared preferences
            temp1 = temp2; // Store the old value of i to be moved to i + 1
        }
    }

    private int[] getHighScoreArray() {
        return highScoreArray;
    }
}
