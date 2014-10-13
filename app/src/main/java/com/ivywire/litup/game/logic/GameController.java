package com.ivywire.litup.game.logic;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
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

    private int[] dotIdArray;
    private Boolean[] dotStatusArray;
    private int counter;

    public GameController(final Context context, final int[]dotIdArray, final Boolean[] dotStatusArray) {
        this.context = context;
        rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
        this.dotIdArray = dotIdArray;
        this.dotStatusArray = dotStatusArray;
        counter = 60;
        timer = new CountDownTimer(60*1000, 2000) {
            @Override
            public void onTick(long l) {
                if (counter == 0) {
                    endGame();
                }
                
                ((Activity)context).runOnUiThread(new Runnable() {

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
                        counter--;
                    }
                });
            }

            @Override
            public void onFinish() {

            }
        };
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
}
