package io.github.allockye.tracksearcher;

import io.github.allockye.tracksearcher.entity.Track;

/**
 * Created by allockye on 12/14/16.
 */

public interface Parser {

    Track parse(String artistName, String trackName);

}
