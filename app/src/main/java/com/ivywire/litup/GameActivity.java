package com.ivywire.litup;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.ivywire.litup.game.fragments.GameFragment;
import com.ivywire.litup.game.fragments.PauseGameFragment;
import com.ivywire.litup.game.logic.GameController;


public class GameActivity extends Activity implements GameFragment.OnFragmentInteractionListener, PauseGameFragment.OnFragmentInteractionListener {
    GameFragment game;
    PauseGameFragment pause;
    GameController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // make full screen activity
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        if (savedInstanceState == null) {
            game = new GameFragment();
            pause = new PauseGameFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.gameContainer, game)
                    .commit();
            getFragmentManager().beginTransaction()
                    .add(R.id.gameContainer, pause)
                    .hide(pause)
                    .commit();
        }
    }

    public void pauseGameActivity(GameController controller) {
        this.controller = controller;
        controller.pauseGame();
        getFragmentManager().beginTransaction()
                .show(pause)
                .hide(game)
                .commit();
    }

    public void resumeGameActivity() {
        controller.resumeGame();
        getFragmentManager().beginTransaction()
                .hide(pause)
                .show(game)
                .commit();
    }

    public void restartGameActivity() {
        game = new GameFragment();
        pause = new PauseGameFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.gameContainer, game)
                .commit();
        getFragmentManager().beginTransaction()
                .add(R.id.gameContainer, pause)
                .hide(pause)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Method to bring

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
