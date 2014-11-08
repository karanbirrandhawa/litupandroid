package com.ivywire.litup.game.logic;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ivywire.litup.GameActivity;
import com.ivywire.litup.R;
import com.ivywire.litup.game.views.DotView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by KaranDesktop on 10/12/2014.
 */
public class GameController {
    private GameActivity context;
    private View rootView;
    private GameCountDownTimer timer;
    private CountDownTimer counterTimer;
    private Runnable litUpTask;
    private Runnable counterTask;

    private int[] dotIdArray;
    private Boolean[] dotStatusArray;

    private int countDownTime;
    private int countDownInterval;
    private int counter;

    private boolean isFrozen;

    private int gameScore;

    public GameController(final GameActivity context, final int[]dotIdArray, final Boolean[] dotStatusArray) {
        this.context = context;
        rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
        this.dotIdArray = dotIdArray;
        this.dotStatusArray = dotStatusArray;


        countDownTime = 5*1000; // 5 seconds for the first bit
        countDownInterval = 1500; // start with a 2 second gap between dots lighting up
        counter = 60;

        isFrozen = false;

        gameScore = 0; // start with a score of zero

        // The runnable obect responsible for
        litUpTask = new Runnable() {

            @Override
            public void run() {
                // TODO: Figure out why this executes twice
                timer.cancel(); // need this otherwise it executes twice in a second
                int index = randomIndex();

                do {
                    index = randomIndex();
                    if (index == -1 || counter < 0) {
                        // Cancel timer
                        endGame();
                        return;
                    }
                } while (dotStatusArray[index]);

                if (!isFrozen) {
                    DotView dotView = (DotView) rootView.findViewById(dotIdArray[index]);
                    dotView.toggleLight();
                    dotStatusArray[index] = new Boolean(!dotStatusArray[index]);
                }

            }
        };

        // Secondary timer class
        counterTask = new Runnable() {

            @Override
            public void run() {
                // TODO: Figure out why this executes twice
                counterTimer.cancel(); // need this otherwise it executes twice in a second

                TextView counterView = (TextView) rootView.findViewById(R.id.timeView);
                counterView.setText(Integer.toString(counter--));
            }
        };
        // Create the timer for lighting up, called standard 'timer'
        timer = new GameCountDownTimer(countDownTime, countDownInterval);
        // Create timer for counting down seconds left, called 'counterTimer'
        counterTimer = new CountDownTimer(60*1000, 1000) {
            @Override
            public void onTick(long l) {
                ((Activity)context).runOnUiThread(counterTask);
            }

            @Override
            public void onFinish() {
                // Don't need to do anything since the other time takes care of this
            }
        };
    }

    public void startGame() {
        isFrozen = false;
        timer.enableTimer();
        timer.start();
        counterTimer.start();
    }

    public void pauseGame() {
        isFrozen = true;
        timer.disableTimer();
        timer.cancel();
        counterTimer.cancel();
    }

    public void resumeGame() {
        setTimerDetails();
        timer = new GameCountDownTimer(countDownTime, countDownInterval);
        startGame();
    }

    public void endGame() {
        // Pause the game timer
        pauseGame();

        // Respond to game ending on screen
        ((GameActivity) context).completeGameActivity(gameScore);
    }

    public int randomIndex() {
        if (areAllFilled())
            return -1;

        Random r = new Random();
        int i = Math.abs(r.nextInt() % 25);

        return i;
    }

    public boolean areAllFilled() {
        for (int i = 0; i < dotStatusArray.length; i++) {
            if (!dotStatusArray[i])
                return false;
        }
        return true;
    }

    public int getGameScore() {
        return gameScore;
    }
    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
    }

    public void setTimerDetails() {
        if (counter > 55 && counter < 61) {

            int counterDifference = 60 - counter;
            countDownTime = (5 - counterDifference) * 1000;
            countDownInterval = 1000; // Slow (1000)

        } else if (counter > 45 && counter < 56) {

            int counterDifference = 55 - counter;
            countDownTime = (10 - counterDifference) * 1000;
            countDownInterval = 500; // Medium (500)

        } else if (counter > 30 && counter < 46) {

            int counterDifference = 45 - counter;
            countDownTime = (15 - counterDifference) * 1000;
            countDownInterval = 250; // Fast (250)

        } else if (counter < 31) {

            int counterDifference = 30 - counter;
            countDownTime = (30 - counterDifference) * 1000;
            countDownInterval = 100; // Very fast (100)
        }
//        } else if (counter > 25 && counter < 31) {
//
//            int counterDifference = 30 - counter;
//            countDownTime = (10 - counterDifference) * 1000;
//            countDownInterval = 50; // Impossibly fast (50)
//
//        } else if (counter < 26) {
//
//            int counterDifference = 25 - counter;
//            countDownTime = (5 - counterDifference) * 1000;
//            countDownInterval = 25; // Fastest (25)
//
//        }
    }

    // Custom timer class to handle the varying dot light up speeds
    class GameCountDownTimer extends CountDownTimer {
        private boolean isActive;

        public GameCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            isActive = true;
        }

        @Override
        public void onTick(long l) {
            if (counter < 0) {
                endGame(); // by the fifth change in speed we should stop the game
            }

            ((Activity)context).runOnUiThread(litUpTask);
        }

        @Override
        public void onFinish() {
            setTimerDetails();
            this.cancel();
            if (isActive) {
                timer = new GameCountDownTimer(countDownTime, countDownInterval);
                timer.start();
            }
        }

        public void disableTimer() {
            isActive = false;
        }
        public void enableTimer() {
            isActive = true;
        }
    }
}
