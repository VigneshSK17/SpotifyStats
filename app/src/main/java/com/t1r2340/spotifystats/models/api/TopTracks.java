package com.t1r2340.spotifystats.models.api;

/**
 * Class holding top tracks information

 */
public class TopTracks extends TopItems {

    /** The list of tracks */
    private TrackObject[] items;

    /**
     * Constructor for TopTracks
     */
    public TopTracks(int limit, String next, int offset, int total, TrackObject[] items) {
        super(limit, next, offset, total);
        this.items = items;
    }

    /**
     * Gets the track items
     * @return the track items
     */
    public TrackObject[] getItems() {
        return items;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (TrackObject track : items) {
            sb.append(track.getName()).append(": ").append(track.getAlbum().getName()).append("\n");
        }
        return sb.toString();
    }

}
