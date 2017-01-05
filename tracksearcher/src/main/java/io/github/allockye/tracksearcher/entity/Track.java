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
    private int mDuration;

    public Track() {
        mArtistName = "Unknown";
        mTrackName = "Unknown";
        mAlbumName = "Unknown";
        mPrimaryGenre = "Unknown";
        mArtworkUrl = "https://s.discogs.com/images/default-release.png";
        mCountry = "Unknown";
        mDuration = 0;
    }

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

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }
}
