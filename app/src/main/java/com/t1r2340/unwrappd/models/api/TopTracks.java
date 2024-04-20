package com.t1r2340.unwrappd.models.api;

import java.io.Serializable;
import java.util.List;

/** Class holding top tracks information */
public class TopTracks extends TopItems implements Serializable {

  /** The list of tracks */
  private List<TrackObject> items;

  public TopTracks() {}

  /** Constructor for TopTracks */
  public TopTracks(int limit, String next, int offset, int total, List<TrackObject> items) {
    super(limit, next, offset, total);
    this.items = items;
  }

  /**
   * Gets the track items
   *
   * @return the track items
   */
  public List<TrackObject> getItems() {
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
