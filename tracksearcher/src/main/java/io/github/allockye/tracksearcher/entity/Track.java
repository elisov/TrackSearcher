package io.github.allockye.tracksearcher.entity;

/**
 * Created by allockye on 12/14/16.
 */

public class Track {
    private String mArtistName;
    private String mTrackName;
    private String mAlbumName;
    private String mPrimaryGenre;
    private String mArtworkUrl;
    private String mCountry;

    public String getArtistName() {
        return mArtistName;
    }

    public void setArtistName(String artistName) {
        mArtistName = artistName;
    }

    public String getTrackName() {
        return mTrackName;
    }

    public void setTrackName(String trackName) {
        mTrackName = trackName;
    }

    public String getAlbumName() {
        return mAlbumName;
    }

    public void setAlbumName(String albumName) {
        mAlbumName = albumName;
    }

    public String getPrimaryGenre() {
        return mPrimaryGenre;
    }

    public void setPrimaryGenre(String primaryGenre) {
        mPrimaryGenre = primaryGenre;
    }

    public String getArtworkUrl() {
        return mArtworkUrl;
    }

    public void setArtworkUrl(String artworkUrl) {
        mArtworkUrl = artworkUrl;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }
}
