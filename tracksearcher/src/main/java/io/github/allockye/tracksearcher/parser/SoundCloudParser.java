package io.github.allockye.tracksearcher.parser;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.github.allockye.tracksearcher.entity.Track;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by allockye on 1/4/17.
 */

public class SoundCloudParser implements Parser {
    private String mClientId;

    public SoundCloudParser(String clientId) {
        mClientId = clientId;
    }

    @Override
    public Track parse(String artistName, String trackName) {
        Track track = null;
        Uri apiEndpoint = new Uri.Builder()
                .scheme("https")
                .authority("api.soundcloud.com")
                .appendPath("tracks")
                .appendQueryParameter("client_id", mClientId)
                .appendQueryParameter("q", artistName + " + " + trackName)
                .build();
        try {
            OkHttpClient httpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(apiEndpoint.toString())
                    .build();
            Response response = httpClient.newCall(request).execute();
            String json = response.body().string();
            List<Track> tracks = getTrackListFromJson(json);

            if(tracks.size() > 0) {
                track = tracks.get(0);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return track;
    }

    private List<Track> getTrackListFromJson(String json) throws JSONException {
        List<Track> tracks = new ArrayList<>();

        JSONArray jsonRootArray = new JSONArray(json);

        for(int i = 0; i < jsonRootArray.length(); i++){
            JSONObject jsonObject = jsonRootArray.getJSONObject(i);

            String artistName = jsonObject.getJSONObject("user").getString("username");
            String trackName = jsonObject.getString("title");
            String primaryGenre = jsonObject.getString("genre");
            String artworkUrl = jsonObject.getString("artwork_url");

            Track track = new Track();
            track.setArtistName(artistName);
            track.setTrackName(trackName);
            //track.setAlbumName(albumName);
            track.setPrimaryGenre(primaryGenre);
            //track.setCountry(country);
            track.setArtworkUrl(artworkUrl);
            tracks.add(track);
        }

        return tracks;
    }
}
