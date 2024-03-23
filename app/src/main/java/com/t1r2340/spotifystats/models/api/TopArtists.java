package com.t1r2340.spotifystats.models.api;

import androidx.annotation.NonNull;

/**
 * Class holding user's top artists
 */
public class TopArtists extends TopItems {

    /** The list of artists */
    private ArtistObject[] items;

    /**
     * Constructor for TopArtists
     */
    public TopArtists(int limit, String next, int offset, int total, ArtistObject[] items) {
        super(limit, next, offset, total);
        this.items = items;
    }

    /**
     * Gets the list of artists
     * @return the list of artists
     */
    public ArtistObject[] getItems() {
        return items;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ArtistObject artist : items) {
            sb.append(artist.getName()).append(": ").append(artist.getId()).append("\n");
        }
        return sb.toString();
    }
}
