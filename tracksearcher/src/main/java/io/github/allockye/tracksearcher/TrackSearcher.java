package io.github.allockye.tracksearcher;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import io.github.allockye.tracksearcher.entity.Track;

/**
 * Created by allockye on 12/14/16.
 */

public class TrackSearcher {
    private List<Parser> mParsers;

    public interface Callback {
        void onSuccess(Track track);
        void onFailure();
    }

    public TrackSearcher() {
        mParsers = new ArrayList<>();
    }

    public void addParser(Parser parser){
        if(!mParsers.contains(parser)) {
            mParsers.add(parser);
        }
    }

    public void search(final String artistName, final String trackName, final Callback callback){
        new AsyncTask<Void, Void, Track>(){
            @Override
            protected Track doInBackground(Void... voids) {
                Track track = null;
                for (Parser parser: mParsers){
                    track = parser.parse(artistName, trackName);
                    if(track != null) {
                        break;
                    }
                }
                return track;
            }

            @Override
            protected void onPostExecute(Track track) {
                super.onPostExecute(track);
                if(track != null){
                    callback.onSuccess(track);
                } else {
                    callback.onFailure();
                }
            }
        }.execute();
    }
}
