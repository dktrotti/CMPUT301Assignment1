package dktrotti.ece301assignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class PlayerSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_select);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player_select, menu);
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

    public void onTwoPlayerButtonClick(View view) {
        Intent intent = new Intent(this, MultiplayerActivity.class);
        intent.putExtra(getString(R.string.playercount), 2);
        startActivity(intent);
    }

    public void onThreePlayerButtonClick(View view) {
        Intent intent = new Intent(this, MultiplayerActivity.class);
        intent.putExtra(getString(R.string.playercount), 3);
        startActivity(intent);
    }

    public void onFourPlayerButtonClick(View view) {
        Intent intent = new Intent(this, MultiplayerActivity.class);
        intent.putExtra(getString(R.string.playercount), 4);
        startActivity(intent);
    }


}
