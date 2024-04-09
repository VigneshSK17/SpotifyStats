package com.t1r2340.spotifystats.models.api;

import java.io.Serializable;
import java.util.List;

/** Class holding more specific artist information */
public class ArtistObject extends Artist implements Serializable {

  /** Genres of the artist */
  private List<String> genres;

  /** Images of the artist */
  private List<ImageObject> images;

  public ArtistObject() {}

  /** Constructor for ArtistObject */
  public ArtistObject(String id, String name, List<String> genres, List<ImageObject> images) {
    super(id, name);
    this.genres = genres;
    this.images = images;
  }

  /**
   * Gets the genres of the artist
   *
   * @return the genres of the artist
   */
  public List<String> getGenres() {
    return genres;
  }

  /**
   * Gets the images of the artist
   *
   * @return the images of the artist
   */
  public List<ImageObject> getImages() {
    return images;
  }
}
