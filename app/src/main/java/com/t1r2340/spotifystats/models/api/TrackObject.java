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
    /** Whether or not the track is playable in the Spotify client */
    private boolean isPlayable;
    /** The name of the track */
    private String name;
    /** A URL to a 30 second preview (MP3) of the track */
    private String previewUrl; // TODO: Use isPlayable to check if not null

    public TrackObject() {}
    /**
     * Constructor for TrackObject
     */
    public TrackObject(String id, Album album, List<ArtistObject> artists, int durationMs, boolean explicit, boolean isPlayable, String name, String previewUrl) {
        this.id = id;
        this.album = album;
        this.artists = artists;
        this.durationMs = durationMs;
        this.explicit = explicit;
        this.isPlayable = isPlayable;
        this.name = name;
        this.previewUrl = previewUrl;
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
     * Gets whether or not the track is playable in the Spotify client
     * @return whether or not the track is playable in the Spotify client
     */
    public boolean isPlayable() {
        return isPlayable;
    }

    /**
     * Gets the name of the track
     * @return the name of the track
     */
    public String getName() {
        return name;
    }

    /**
     * Gets a URL to a 30 second preview (MP3) of the track
     * @return a URL to a 30 second preview (MP3) of the track
     */
    public String getPreviewUrl() {
        return previewUrl;
    }
}
