package io.github.allockye.tracksearcher.parser;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.github.allockye.tracksearcher.Parser;
import io.github.allockye.tracksearcher.entity.Track;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by allockye on 12/14/16.
 */

public class ITunesParser implements Parser {
    private Uri mBaseUri;

    public ITunesParser() {
        mBaseUri = new Uri.Builder()
                .scheme("https")
                .authority("itunes.apple.com")
                .appendPath("search")
                .build();
    }
    @Override
    public Track parse(String artistName, String trackName) {
        Track track = null;
        try {
            Uri apiEndpoint = mBaseUri.buildUpon()
                    .appendQueryParameter("term", artistName + " + " + trackName)
                    .appendQueryParameter("limit", "1")
                    .build();
            OkHttpClient httpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(apiEndpoint.toString())
                    .build();
            Response response = httpClient.newCall(request).execute();
            JSONArray resultsJsonArray = new JSONObject(response.body().string())
                    .getJSONArray("results");

            if(resultsJsonArray.length() > 0) {
                JSONObject trackJsonObject = resultsJsonArray.getJSONObject(0);

                String artist = trackJsonObject.getString("artistName");
                String name = trackJsonObject.getString("trackName");
                String album = trackJsonObject.getString("collectionName");
                String genre = trackJsonObject.getString("primaryGenreName");
                String country = trackJsonObject.getString("country");
                String artwork = trackJsonObject.getString("artworkUrl100");
                int duration = trackJsonObject.getInt("trackTimeMillis");

                track = new Track();
                track.setArtistName(artist);
                track.setTrackName(name);
                track.setAlbumName(album);
                track.setPrimaryGenre(genre);
                track.setCountry(country);
                track.setArtworkUrl(artwork);
                track.setDuration(duration);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return track;
    }
}
