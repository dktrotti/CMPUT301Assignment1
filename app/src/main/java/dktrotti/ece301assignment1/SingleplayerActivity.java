package dktrotti.ece301assignment1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class SingleplayerActivity extends AppCompatActivity {
    private Handler timerHandler = new Handler();
    private Runnable primeBuzzerRunnable = new Runnable() {
        @Override
        public void run() {
            ((Buzzer)findViewById(R.id.buzzer)).primeBuzzer();
        }
    };

    private Random RNGesus = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleplayer);
        showPrompt("Single Player Training",
                "Press the button when it turns green.",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startGame();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_singleplayer, menu);
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

    private void startGame() {
        timerHandler.postDelayed(primeBuzzerRunnable, getRandomTime(10, 2000));
    }

    public void onBuzzerButtonClick(View view) {
        Buzzer buzzer = (Buzzer)findViewById(R.id.buzzer);
        if (buzzer.isBuzzerPrimed()) {
            //Buzzer pressed on time
            long totalMillis = buzzer.getTime();
            long seconds = totalMillis / 1000;
            long millis = totalMillis % 1000;
            buzzer.disableBuzzer();
            ((TextView) findViewById(R.id.TimeTextView)).setText(String.format("%d ms", totalMillis));
            StatisticsManager.getInstance().AddReactionTime(totalMillis);
            timerHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startGame();
                }
            }, 2000);
        } else {
            //Buzzer pressed too early
            timerHandler.removeCallbacks(primeBuzzerRunnable);
            showPrompt("Good Try",
                    "Press the button when it turns green. (You know, that colour that isn't red...)",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            startGame();
                        }
                    });

        }
    }

    //Potential issue if min is set too close to max
    private int getRandomTime(int min, int max) {
        int rv = RNGesus.nextInt(max);
        if (rv < min) {
            rv = getRandomTime(min, max);
        }
        return rv;
    }

    private void showPrompt(CharSequence title, CharSequence message, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, listener)
                .show();
    }
}
