package dktrotti.ece301assignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MultiplayerActivity extends AppCompatActivity {
    private Buzzer buzzer1;
    private Buzzer buzzer2;
    private Buzzer buzzer3;
    private Buzzer buzzer4;
    private int playercount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);

        buzzer1 = (Buzzer)(findViewById(R.id.buzzer1));
        buzzer2 = (Buzzer)(findViewById(R.id.buzzer2));
        buzzer3 = (Buzzer)(findViewById(R.id.buzzer3));
        buzzer4 = (Buzzer)(findViewById(R.id.buzzer4));

        Intent intent = getIntent();
        playercount = intent.getIntExtra(getString(R.string.playercount), 4);

        if (playercount < 4) {
            buzzer4.setVisibility(View.GONE);
        }
        if (playercount < 3) {
            buzzer3.setVisibility(View.GONE);
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
}
