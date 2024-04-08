package com.t1r2340.spotifystats.models.api;

import java.util.List;

/**
 * Class holding track information
 */
public class TrackObject {
    /** The Spotify ID for the track */
    private String id;
    /** The album on which the track appears */
    private Album album;
    /** The artists who performed the track */
    private List<ArtistObject> artists;
    /** The duration of the track in milliseconds */
    private int durationMs;
    /** Whether or not the track has explicit lyrics */
    private boolean explicit;
    /** The name of the track */
    private String name;
    /** The URI of the track, used for playback */
    private String uri;

    public TrackObject() {}
    /**
     * Constructor for TrackObject
     */
    public TrackObject(String id, Album album, List<ArtistObject> artists, int durationMs, boolean explicit, String name, String uri) {
        this.id = id;
        this.album = album;
        this.artists = artists;
        this.durationMs = durationMs;
        this.explicit = explicit;
        this.name = name;
        this.uri = uri;
    }

    /**
     * Gets the Spotify ID for the track
     * @return the Spotify ID for the track
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the album on which the track appears
     * @return the album on which the track appears
     */
    public Album getAlbum() {
        return album;
    }

    /**
     * Gets the artists who performed the track
     * @return the artists who performed the track
     */
    public List<ArtistObject> getArtists() {
        return artists;
    }

    /**
     * Gets the duration of the track in milliseconds
     * @return the duration of the track in milliseconds
     */
    public int getDurationMs() {
        return durationMs;
    }

    /**
     * Gets whether or not the track has explicit lyrics
     * @return whether or not the track has explicit lyrics
     */
    public boolean isExplicit() {
        return explicit;
    }

    /**
     * Gets the name of the track
     * @return the name of the track
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the URI of the track
     * @return the URI of the track
     */
    public String getUri() {
        return uri;
    }
}
