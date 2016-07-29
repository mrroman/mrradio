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

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setPlayerButtons();
        createPlayer();
        startPlaying(new Station(1, "SomaFM: GrooveSalad", "http://ice1.somafm.com/groovesalad-128-mp3"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void createPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                ((Button)findViewById(R.id.play)).setText(R.string.icon_pause);
            }
        });
    }

    private void startPlaying(final Station station) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }

        try {
            mediaPlayer.setDataSource(this, Uri.parse(station.getUrl()));
            mediaPlayer.prepareAsync();
            ((TextView)findViewById(R.id.station_title)).setText(station.getTitle());
        } catch (IOException e) {
        }
    }

    private void setPlayerButtons() {
        Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );
        ((Button)findViewById(R.id.play)).setTypeface(font);
        ((Button)findViewById(R.id.stop)).setTypeface(font);
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
}
