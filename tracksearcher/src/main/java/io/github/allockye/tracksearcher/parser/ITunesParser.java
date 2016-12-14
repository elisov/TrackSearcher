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
 * Created by allockye on 12/14/16.
 */

public class ITunesParser implements Parser {
    @Override
    public Track parse(String artistName, String trackName) {
        Track track = null;
        Uri apiEndpoint = new Uri.Builder()
                .scheme("https")
                .authority("itunes.apple.com")
                .appendPath("search")
                .appendQueryParameter("term", artistName + " + " + trackName)
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

        JSONObject jsonRootObject = new JSONObject(json);
        int resultCount = jsonRootObject.getInt("resultCount");

        if(resultCount != 0){
            JSONArray jsonRootArray = jsonRootObject.getJSONArray("results");

            for(int i = 0; i < jsonRootArray.length(); i++){
                JSONObject jsonObject = jsonRootArray.getJSONObject(i);

                String artistName = jsonObject.has("artistName")
                        ? jsonObject.getString("artistName")
                        : "";
                String trackName = jsonObject.has("trackName")
                        ? jsonObject.getString("trackName")
                        : "";
                String albumName = jsonObject.has("collectionName")
                        ? jsonObject.getString("collectionName")
                        : "";
                String primaryGenre = jsonObject.has("primaryGenreName")
                        ? jsonObject.getString("primaryGenreName")
                        : "";
                String country = jsonObject.has("country")
                        ? jsonObject.getString("country")
                        : "";
                String artworkUrl = jsonObject.has("artworkUrl100")
                        ? jsonObject.getString("artworkUrl100")
                        : "";

                Track track = new Track();
                track.setArtistName(artistName);
                track.setTrackName(trackName);
                track.setAlbumName(albumName);
                track.setPrimaryGenre(primaryGenre);
                track.setCountry(country);
                track.setArtworkUrl(artworkUrl);
                tracks.add(track);
            }
        }
        return tracks;
    }
}
