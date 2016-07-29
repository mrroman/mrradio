package pl.mrroman.mrradio;

import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements RadioPlayer.StatusChangeListener {

    private RadioPlayer radioPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setPlayerButtons();
        createPlayer();
        radioPlayer.play(new Station(1, "SomaFM: GrooveSalad", new Uri[] {
                Uri.parse("http://ice1.somafm.com/groovesalad-128-mp3")
        }));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
    }

    private void createPlayer() {
        radioPlayer = new RadioPlayer(this);
        radioPlayer.setStatusChangeListener(this);
    }

    private void setPlayerButtons() {
        Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );
        final Button play = (Button) findViewById(R.id.play);
        if (play != null) {
            play.setTypeface(font);
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    radioPlayer.pause();
                }
            });
        }

        final Button stop = (Button) findViewById(R.id.stop);
        if (stop != null) {
            stop.setTypeface(font);
            stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    radioPlayer.stop();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void onChanged(RadioPlayer.Status status) {
        final Button play = (Button) findViewById(R.id.play);

        if (play != null) {
            switch (status) {
                case STOPPED:
                    play.setText(R.string.icon_play);
                    break;
                case PLAYING:
                    play.setText(R.string.icon_pause);
                    break;
                case PAUSED:
                    play.setText(R.string.icon_play);
                    break;
            }
        }
    }
}
