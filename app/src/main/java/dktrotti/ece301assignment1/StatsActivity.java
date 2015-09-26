package dktrotti.ece301assignment1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class StatsActivity extends AppCompatActivity {
    private Button singleplayerStatsButton;
    private Button multiplayerStatsButton;
    private LinearLayout singleplayerLayout;
    private LinearLayout multiplayerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        singleplayerStatsButton = (Button)findViewById(R.id.singleplayerStatsButton);
        multiplayerStatsButton = (Button)findViewById(R.id.multiplayerStatsButton);
        singleplayerLayout = (LinearLayout)findViewById(R.id.SingleplayerLayout);
        multiplayerLayout = (LinearLayout)findViewById(R.id.MultiplayerLayout);

        showSingleplayerStats();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stats, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSingleplayerStatsButtonClick(View view) {
        showSingleplayerStats();
    }

    public void onMultiplayerStatsButtonClick(View view) {
        showMultiplayerStats();
    }

    private void showSingleplayerStats() {
        singleplayerLayout.setVisibility(View.VISIBLE);
        multiplayerLayout.setVisibility(View.GONE);
        singleplayerStatsButton.setEnabled(false);
        multiplayerStatsButton.setEnabled(true);
    }

    private void showMultiplayerStats() {
        singleplayerLayout.setVisibility(View.GONE);
        multiplayerLayout.setVisibility(View.VISIBLE);
        singleplayerStatsButton.setEnabled(true);
        multiplayerStatsButton.setEnabled(false);
    }
}
