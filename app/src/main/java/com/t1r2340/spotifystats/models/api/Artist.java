package com.t1r2340.spotifystats.models.api;

/** Class holding artist information */
public class Artist {

  /** The Spotify ID for the artist */
  private String id;

  /** The name of the artist */
  private String name;

  public Artist() {}

  /** Constructor for Artist */
  public Artist(String id, String name) {
    this.id = id;
    this.name = name;
  }

  /**
   * Gets the Spotify ID for the artist
   *
   * @return the Spotify ID for the artist
   */
  public String getId() {
    return id;
  }

  /**
   * Gets the name of the artist
   *
   * @return the name of the artist
   */
  public String getName() {
    return name;
  }
}
