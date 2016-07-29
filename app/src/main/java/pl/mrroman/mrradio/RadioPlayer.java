package pl.mrroman.mrradio;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;

public class RadioPlayer implements MediaPlayer.OnPreparedListener {

    private final Context context;
    private MediaPlayer mediaPlayer;
    private Station currentStation;
    private int currentUri;
    private StatusChangeListener statusChangeListener;
    private Status status;

    public RadioPlayer(Context context) {
        this.context = context;

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(this);
    }

    public void play(final Station station) {
        currentUri = 0;

        while (currentUri < station.getUris().length) {
            try {
                mediaPlayer.setDataSource(context, station.getUris()[currentUri]);
                mediaPlayer.prepareAsync();
                break;
            } catch (IOException e) {
                currentUri++;
            }
        }
    }

    public void stop() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            changeStatus(Status.STOPPED);
        }
    }

    public void pause() {
        if (status == Status.PLAYING) {
            mediaPlayer.pause();
            changeStatus(Status.PAUSED);
        } else if (status == Status.PAUSED) {
            mediaPlayer.start();
            changeStatus(Status.PLAYING);
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
        changeStatus(Status.PLAYING);
    }

    public void setStatusChangeListener(StatusChangeListener statusChangeListener) {
        this.statusChangeListener = statusChangeListener;
    }

    private void changeStatus(final Status status) {
        this.status = status;
        if (statusChangeListener != null) {
            statusChangeListener.onChanged(status);
        }
    }

    public interface StatusChangeListener {
        void onChanged(Status status);
    }

    public enum Status {
        STOPPED,
        PLAYING,
        PAUSED
    }
}
