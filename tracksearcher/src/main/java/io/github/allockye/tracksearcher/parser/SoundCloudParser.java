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
 * Created by allockye on 1/4/17.
 */

public class SoundCloudParser implements Parser {
    private Uri mBaseUri;

    public SoundCloudParser(String clientId) {
        mBaseUri = new Uri.Builder()
                .scheme("https")
                .authority("api.soundcloud.com")
                .appendPath("tracks")
                .appendQueryParameter("client_id", clientId)
                .build();
    }

    @Override
    public Track parse(String artistName, String trackName) {
        Track track = null;
        try {
            Uri apiEndpoint = mBaseUri.buildUpon()
                    .appendQueryParameter("q", artistName + " + " + trackName)
                    .build();
            OkHttpClient httpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(apiEndpoint.toString())
                    .build();
            Response response = httpClient.newCall(request).execute();
            JSONArray tracksJsonArray = new JSONArray(response.body().string());

            if(tracksJsonArray.length() > 0) {
                JSONObject trackJsonObject = tracksJsonArray.getJSONObject(0);

                String username = trackJsonObject.getJSONObject("user").getString("username");
                String title = trackJsonObject.getString("title");
                String genre = trackJsonObject.getString("genre");
                String artwork = trackJsonObject.getString("artwork_url");
                int duration = trackJsonObject.getInt("duration");

                track = new Track();
                track.setArtistName(username);
                track.setTrackName(title);
                track.setPrimaryGenre(genre);
                track.setArtworkUrl(artwork);
                track.setDuration(duration);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return track;
    }
}
