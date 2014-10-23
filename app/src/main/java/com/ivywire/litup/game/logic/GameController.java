package com.ivywire.litup.game.logic;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

import com.ivywire.litup.game.views.DotView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by KaranDesktop on 10/12/2014.
 */
public class GameController {
    private Context context;
    private View rootView;
    private CountDownTimer timer;
    private Runnable task;

    private int[] dotIdArray;
    private Boolean[] dotStatusArray;

    private int stage;
    private int countDownTime;
    private int countDownInterval;

    private int gameScore;

    public GameController(final Context context, final int[]dotIdArray, final Boolean[] dotStatusArray) {
        this.context = context;
        rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
        this.dotIdArray = dotIdArray;
        this.dotStatusArray = dotStatusArray;

        stage = 0; // Start with the first stage
        countDownTime = 5*1000; // 5 seconds for the first bit
        countDownInterval = 1500; // start with a 2 second gap between dots lighting up
        gameScore = 0; // start with a score of zero

        // The runnable obect responsible for TODO: Add as parameter in constructor
        task = new Runnable() {

            @Override
            public void run() {
                // TODO: Figure out why this executes twice
                timer.cancel(); // need this otherwise it executes twice in a second
                int index = randomIndex();

                do {
                    index = randomIndex();
                    if (index == -1) {
                        // Cancel timer
                        endGame();
                        return;
                    }
                } while (dotStatusArray[index]);

                DotView dotView = (DotView) rootView.findViewById(dotIdArray[index]);
                dotView.toggleLight();
                dotStatusArray[index] = new Boolean(!dotStatusArray[index]);
            }
        };

        // Create the countdowntimer
        timer = new GameCountDownTimer(countDownTime, countDownInterval);
    }

    public void startGame() {
        timer.start();
    }

    public void pauseGame() {
        timer.cancel();
    }

    public void resumeGame() {
        timer.start();
    }

    public void endGame() {
        pauseGame();
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
        switch(stage) {
            case 0:
                countDownTime = 5*1000;
                countDownInterval = 1000;
                break;
            case 1:
                countDownTime = 10*1000;
                countDownInterval = 500;
                break;
            case 2:
                countDownTime = 10*1000;
                countDownInterval = 250;
                break;
            case 3:
                countDownTime = 5*1000;
                countDownInterval = 100;
                break;
            case 4:
                countDownTime = 10*1000;
                countDownInterval = 50;
                break;
            default:
                countDownTime = 5*1000;
                countDownInterval = 25;
                break;
        }
        stage++;
    }

    class GameCountDownTimer extends CountDownTimer {

        public GameCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            if (stage == 5) {
                endGame(); // by the fifth change in speed we should stop the game
            }

            ((Activity)context).runOnUiThread(task);
        }

        @Override
        public void onFinish() {
            setTimerDetails();
            this.cancel();
            timer = new GameCountDownTimer(countDownTime, countDownInterval);
            timer.start();
        }
    }
}
