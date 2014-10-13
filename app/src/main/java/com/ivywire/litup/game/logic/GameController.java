package com.ivywire.litup.game.logic;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;

import com.ivywire.litup.game.views.DotView;

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
    private boolean[] dotStatusArray;
    private int counter; // 0 to 59 (60 seconds in total)

    public GameController(final Context context, final int[]dotIdArray, final boolean[] dotStatusArray) {
        this.context = context;
        rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
        this.dotIdArray = dotIdArray;
        this.dotStatusArray = dotStatusArray;
        counter = 0;
        timer = new CountDownTimer(20*1000, 2000) {
            @Override
            public void onTick(long l) {
                ((Activity)context).runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO: Figure out why this executes twice
                        timer.cancel(); // need this otherwise it executes twice in a second
                        if (counter < 24) {
                            DotView dotView = (DotView) rootView.findViewById(dotIdArray[counter]);
                            dotView.toggleLight();
                            dotStatusArray[counter] = !dotStatusArray[counter];
                            counter++;
                        }
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
}
