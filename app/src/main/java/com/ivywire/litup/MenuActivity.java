package com.ivywire.litup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ivywire.litup.game.views.DotView;
import com.ivywire.resources.FontManager;

/**
 *  Menu Activity shown to present initial game options
 */
public class MenuActivity extends Activity implements View.OnClickListener{
    static Context ctx;
    DotView dotView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // make full screen activity
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);
        // Assign activity ctx
        ctx = this;

        // Apply fonts
        TextView menuTitle = (TextView) findViewById(R.id.menuTitle);
        FontManager.applyFont(this, menuTitle, "fonts/Raleway-SemiBold.otf");
//        String text = "<font color='#488475'>Lit</font><font color='#5aa592'>Up!</font>";
//        menuTitle.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);

        dotView = (DotView) findViewById(R.id.exampleDot);
        dotView.setOnClickListener(this);

        // Get menu action buttons
        Button playButton = (Button) findViewById(R.id.menuPlayGame);
        menuButtonStyle(playButton);
        Button highScoreButton = (Button) findViewById(R.id.menuHighScores);
        menuButtonStyle(highScoreButton);
        Button settingsButton = (Button) findViewById(R.id.menuSettings);
        menuButtonStyle(settingsButton);

        // Attach listeners to different buttons
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, GameActivity.class);
                startActivity(intent);
            }
        });
        highScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, HighScoresActivity.class);
                startActivity(intent);
            }
        });
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(ctx, SettingsActivity.class);
//                startActivity(intent);
                Toast toast = Toast.makeText(ctx, "Coming soon!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        // First time ran update high scores with all zeros
        SharedPreferences settings = getSharedPreferences("Global", 0);

        if (settings.getBoolean("my_first_time", true)) {
            //the app is being launched for first time, do something
            Log.d("Comments", "First time");

            // first time task
            SharedPreferences highScorePref = this.getSharedPreferences("highScore", 0);
            SharedPreferences.Editor highScorePrefEditor = highScorePref.edit();
            for (int i = 0; i < 10; i++) {
                highScorePrefEditor.putInt("Score" + i, 0); // Store in shared preferences
                highScorePrefEditor.commit();
            }

            // Indicate first time task has ran
            settings.edit().putBoolean("my_first_time", false).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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

    @Override
    public void onClick(View view) {
        dotView.toggleLight();
    }

    // Method to style menu buttons TODO: Change to appropriate style
    @SuppressLint("ResourceAsColor")
    private void menuButtonStyle(Button button) {
        FontManager.applyFont(this, button, "fonts/Raleway-SemiBold.otf");
    }
}
