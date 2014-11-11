package com.ivywire.litup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ivywire.litup.game.logic.HighScoreManager;
import com.ivywire.resources.FontManager;

import java.util.ArrayList;
import java.util.List;


public class HighScoresActivity extends Activity {
    private Context ctx;
    private ListView listView;
    private List<String> highScores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        ctx = this;
        highScores = new ArrayList<String>();

        Button backButton = (Button) findViewById(R.id.backButton);
        TextView highScoreLabel = (TextView) findViewById(R.id.highScoreLabelView);
        FontManager.applyFont(this, backButton, "fonts/Raleway-SemiBold.otf");
        FontManager.applyFont(this, highScoreLabel, "fonts/Raleway-ExtraBold.otf");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, MenuActivity.class);
                startActivity(intent);
            }
        });

        HighScoreManager manager = HighScoreManager.getInstance(this);
        int[] highScoreArray = manager.getHighScoreArray();
        for (int i = 0; i < highScoreArray.length; i++) {
            highScores.add("" + highScoreArray[i]);
        }
        manager.deleteInstance();

        listView = (ListView) findViewById(R.id.highScoreListView);

        HighScoreArrayAdapter adapter = new HighScoreArrayAdapter(this, R.layout.high_score_list_item, highScores);

        listView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.high_scores, menu);
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

    private class HighScoreArrayAdapter extends ArrayAdapter<String> {
        private Context context;
        private List<String> items;

        public HighScoreArrayAdapter(Context context, int resource) {
            super(context, resource);
            this.context = context;
        }

        public HighScoreArrayAdapter(Context context, int resource, List<String> items) {
            super(context, resource, items);
            this.context = context;
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            if (v == null) {
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                v = inflater.inflate(R.layout.high_score_list_item, parent, false);
            }

            TextView posLabel = (TextView) v.findViewById(R.id.positionLabel);
            posLabel.setText("" + (position + 1));

            TextView scoreLabel = (TextView) v.findViewById(R.id.text1);
            scoreLabel.setText(items.get(position));

            FontManager.applyFont(context, posLabel, "fonts/Raleway-SemiBold.otf");
            FontManager.applyFont(context, scoreLabel, "fonts/Raleway-SemiBold.otf");

            return v;

        }
    }
}
