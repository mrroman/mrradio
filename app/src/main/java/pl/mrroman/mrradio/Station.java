package pl.mrroman.mrradio;

import android.net.Uri;

public class Station {

    private int id;
    private String title;
    private Uri[] uris;

    public Station(int id, String title, Uri[] uris) {
        this.id = id;
        this.title = title;
        this.uris = uris;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Uri[] getUris() {
        return uris;
    }
}
