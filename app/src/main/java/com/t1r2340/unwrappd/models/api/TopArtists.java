package com.t1r2340.unwrappd.models.api;


import java.io.Serializable;
import java.util.List;

/** Class holding user's top artists */
public class TopArtists extends TopItems implements Serializable {

  /** The list of artists */
  private List<ArtistObject> items;

  public TopArtists() {}

  /** Constructor for TopArtists */
  public TopArtists(int limit, String next, int offset, int total, List<ArtistObject> items) {
    super(limit, next, offset, total);
    this.items = items;
  }

  /**
   * Gets the list of artists
   *
   * @return the list of artists
   */
  public List<ArtistObject> getItems() {
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
