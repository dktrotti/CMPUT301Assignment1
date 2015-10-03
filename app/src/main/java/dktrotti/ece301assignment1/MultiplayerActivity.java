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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class MultiplayerActivity extends AppCompatActivity {
    private ArrayList<Buzzer> buzzers = new ArrayList<>();
    private int playercount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);

        buzzers.add(0, (Buzzer)(findViewById(R.id.buzzer1)));
        buzzers.add(1, (Buzzer)(findViewById(R.id.buzzer2)));
        buzzers.add(2, (Buzzer)(findViewById(R.id.buzzer3)));
        buzzers.add(3, (Buzzer)(findViewById(R.id.buzzer4)));

        for (Buzzer buzzer: buzzers) {
            buzzer.primeBuzzer();
        }

        Intent intent = getIntent();
        playercount = intent.getIntExtra(getString(R.string.playercount), 4);

        if (playercount < 4) {
            buzzers.get(3).setVisibility(View.GONE);
        }
        if (playercount < 3) {
            buzzers.get(2).setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_multiplayer, menu);
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
    public void onPause() {
        StatisticsManager.getInstance().SaveData();
        super.onPause();
    }

    public void onMultiplayerBuzzerClick(View view) {
        Buzzer winner = (Buzzer)view;
        StatisticsManager.getInstance().AddMultiplayerWin(buzzers.indexOf(winner), playercount);
        for (Buzzer buzzer: buzzers) {
            if (buzzer != winner) {
                buzzer.disableBuzzer();
            }
        }
        showPrompt("Winner!", winner.getText() + " wins!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (Buzzer buzzer: buzzers) {
                    buzzer.primeBuzzer();
                }
                dialog.cancel();
            }
        });
    }

    private void showPrompt(CharSequence title, CharSequence message, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, listener)
                .show();
    }
}
