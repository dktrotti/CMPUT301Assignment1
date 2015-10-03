/*The MIT License (MIT)

Copyright (c) 2015 Dominic Trottier

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.*/

package dktrotti.ece301assignment1;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.NoSuchElementException;

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

    @Override
    public void onResume() {
        showSingleplayerStats();

        super.onResume();
    }

    public void onSingleplayerStatsButtonClick(View view) {
        showSingleplayerStats();
    }

    public void onMultiplayerStatsButtonClick(View view) {
        showMultiplayerStats();
    }

    public void onClearStatsButtonClick(View view) {
        StatisticsManager.getInstance().ClearStats();
        this.finish();
    }

    public void onEmailStatsButtonClick(View view) {
        Intent emailintent = new Intent(Intent.ACTION_SENDTO);
        emailintent.setData(Uri.parse("mailto:"));
        emailintent.putExtra(Intent.EXTRA_SUBJECT, "Trivia Buzzer Statistics");
        emailintent.putExtra(Intent.EXTRA_TEXT, StatisticsManager.getInstance().GetFormattedStats());
        startActivity(Intent.createChooser(emailintent, "Send Statistics"));
    }

    private void showSingleplayerStats() {
        getSingleplayerStats();
        singleplayerLayout.setVisibility(View.VISIBLE);
        multiplayerLayout.setVisibility(View.GONE);
        singleplayerStatsButton.setEnabled(false);
        multiplayerStatsButton.setEnabled(true);
    }

    private void showMultiplayerStats() {
        getMultiplayerStats();
        singleplayerLayout.setVisibility(View.GONE);
        multiplayerLayout.setVisibility(View.VISIBLE);
        singleplayerStatsButton.setEnabled(true);
        multiplayerStatsButton.setEnabled(false);
    }

    private void getSingleplayerStats() {
        StatisticsManager stats = StatisticsManager.getInstance();
        try {
            ((TextView) findViewById(R.id.allTimeMinTextView)).setText("Minimim: " + stats.GetMinimumReaction());
            ((TextView) findViewById(R.id.allTimeMaxTextView)).setText("Maximum: " + stats.GetMaximumReaction());
            ((TextView) findViewById(R.id.allTimeMedTextView)).setText("Median: " + stats.GetMedianReaction());
            ((TextView) findViewById(R.id.allTimeAvgTextView)).setText("Average: " + stats.GetAverageReaction());

            ((TextView) findViewById(R.id.last100MinTextView)).setText("Minimim: " + stats.GetMinimumOfNReactions(100));
            ((TextView) findViewById(R.id.last100MaxTextView)).setText("Maximum: " + stats.GetMaximumOfNReactions(100));
            ((TextView) findViewById(R.id.last100MedTextView)).setText("Median: " + stats.GetMedianofNReactions(100));
            ((TextView) findViewById(R.id.last100AvgTextView)).setText("Average: " + stats.GetAverageOfNReactions(100));

            ((TextView) findViewById(R.id.last10MinTextView)).setText("Minimim: " + stats.GetMinimumOfNReactions(10));
            ((TextView) findViewById(R.id.last10MaxTextView)).setText("Maximum: " + stats.GetMaximumOfNReactions(10).toString());
            ((TextView) findViewById(R.id.last10MedTextView)).setText("Median: " + stats.GetMedianofNReactions(10));
            ((TextView) findViewById(R.id.last10AvgTextView)).setText("Average: " + stats.GetAverageOfNReactions(10).toString());
        } catch (IndexOutOfBoundsException e) {
            //Swallowing exception for when there are no singleplayer values.
        }catch (NoSuchElementException e) {
            //Swallowing exception for when there are no singleplayer values.
        }
    }

    private void getMultiplayerStats() {
        StatisticsManager stats = StatisticsManager.getInstance();

        ((TextView) findViewById(R.id.TwoPlayerP1WinsTextView)).setText("Player 1 wins: " + stats.GetTwoPlayerCounts().get(0).toString());
        ((TextView) findViewById(R.id.TwoPlayerP2WinsTextView)).setText("Player 2 wins: " + stats.GetTwoPlayerCounts().get(1).toString());

        ((TextView) findViewById(R.id.ThreePlayerP1WinsTextView)).setText("Player 1 wins: " + stats.GetThreePlayerCounts().get(0).toString());
        ((TextView) findViewById(R.id.ThreePlayerP2WinsTextView)).setText("Player 2 wins: " + stats.GetThreePlayerCounts().get(1).toString());
        ((TextView) findViewById(R.id.ThreePlayerP3WinsTextView)).setText("Player 3 wins: " + stats.GetThreePlayerCounts().get(2).toString());

        ((TextView) findViewById(R.id.FourPlayerP1WinsTextView)).setText("Player 1 wins: " + stats.GetFourPlayerCounts().get(0).toString());
        ((TextView) findViewById(R.id.FourPlayerP2WinsTextView)).setText("Player 2 wins: " + stats.GetFourPlayerCounts().get(1).toString());
        ((TextView) findViewById(R.id.FourPlayerP3WinsTextView)).setText("Player 3 wins: " + stats.GetFourPlayerCounts().get(2).toString());
        ((TextView) findViewById(R.id.FourPlayerP4WinsTextView)).setText("Player 4 wins: " + stats.GetFourPlayerCounts().get(3).toString());
    }
}
