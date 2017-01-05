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
 * Created by allockye on 1/5/17.
 */

public class LastFmParser implements Parser {
    private Uri mBaseUri;

    public LastFmParser(String clientId) {
        mBaseUri = new Uri.Builder()
                .scheme("http")
                .authority("ws.audioscrobbler.com")
                .appendPath("2.0")
                .appendQueryParameter("api_key", clientId)
                .appendQueryParameter("format", "json")
                .build();
    }

    @Override
    public Track parse(String artistName, String trackName) {
        Track track = null;

        try {
            Uri apiEndpoint = mBaseUri.buildUpon()
                    .appendQueryParameter("method", "track.search")
                    .appendQueryParameter("track", artistName + " - " + trackName)
                    .build();

            OkHttpClient httpClient = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(apiEndpoint.toString())
                    .build();

            Response response = httpClient.newCall(request).execute();

            JSONArray tracksJsonArray = new JSONObject(response.body().string())
                    .getJSONObject("results")
                    .getJSONObject("trackmatches")
                    .getJSONArray("track");

            if (tracksJsonArray.length() > 0) {
                JSONObject trackJsonObject = tracksJsonArray.getJSONObject(0);

                track = new Track();

                String name = trackJsonObject.getString("name");
                String artist = trackJsonObject.getString("artist");

                track.setTrackName(name);
                track.setArtistName(artist);

                String image = trackJsonObject.getJSONArray("image")
                        .getJSONObject(3)
                        .getString("#text");

                apiEndpoint = mBaseUri.buildUpon()
                        .appendQueryParameter("method", "track.getInfo")
                        .appendQueryParameter("artist", artist)
                        .appendQueryParameter("track", name)
                        .build();

                request = new Request.Builder()
                        .url(apiEndpoint.toString())
                        .build();

                response = httpClient.newCall(request).execute();

                JSONObject trackDetailsJsonObject = new JSONObject(response.body().string())
                        .getJSONObject("track");

                int duration = trackDetailsJsonObject.getInt("duration");
                track.setDuration(duration);

                if(trackDetailsJsonObject.has("album")) {
                    String album = trackDetailsJsonObject.getJSONObject("album")
                            .getString("title");
                    String artwork = trackDetailsJsonObject.getJSONObject("album")
                            .getJSONArray("image")
                            .getJSONObject(3)
                            .getString("#text");

                    track.setAlbumName(album);

                    if(artwork.length() > 0) track.setArtworkUrl(artwork);
                } else {
                    if(image.length() > 0) track.setArtworkUrl(image);
                }

                JSONArray tagsJsonArray = trackDetailsJsonObject.getJSONObject("toptags")
                        .getJSONArray("tag");

                if (tagsJsonArray.length() > 0) {
                    String genre = tagsJsonArray.getJSONObject(0).getString("name");
                    track.setPrimaryGenre(genre);
                }
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return track;
    }
}
