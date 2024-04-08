package com.t1r2340.spotifystats.models.api;



/** Represents a Spotify user's profile as needed */
public class SpotifyProfile {
  /** The user's Spotify ID. */
  private String id;

  /** The name displayed on the user's profile. */
  private String displayName;

  /** The user's email address. */
  private String email;

  /** The user's profile image. */
  private ImageObject image;

  public SpotifyProfile() {}

  /** Parses Spotify profile JSON */
  public SpotifyProfile(String id, String displayName, String email, ImageObject image) {
    this.id = id;
    this.displayName = displayName;
    this.email = email;
    this.image = image;
  }

  /**
   * Gets the user's Spotify ID.
   *
   * @return the user's Spotify ID
   */
  public String getId() {
    return id;
  }

  /**
   * Gets the name displayed on the user's profile.
   *
   * @return the name displayed on the user's profile
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * Gets the user's email address.
   *
   * @return the user's email address
   */
  public String getEmail() {
    return email;
  }

  /**
   * Gets the user's profile image.
   *
   * @return the user's profile image
   */
  public ImageObject getImage() {
    return image;
  }

  /**
   * Returns a string representation of the Spotify profile
   *
   * @return a string representation of the Spotify profile
   */
  public String toString() {
    return "SpotifyProfile{"
        + "id='"
        + id
        + '\''
        + ", displayName='"
        + displayName
        + '\''
        + ", email='"
        + email
        + '\''
        + ", image="
        + image
        + '}';
  }
}
