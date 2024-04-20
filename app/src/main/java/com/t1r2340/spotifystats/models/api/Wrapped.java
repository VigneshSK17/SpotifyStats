package com.t1r2340.spotifystats.models.api;

import com.google.firebase.firestore.ServerTimestamp;
import com.t1r2340.spotifystats.R;
import com.t1r2340.spotifystats.helpers.SpotifyApiHelper;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Wrapped implements Serializable {

  /** The top artists */
  private TopArtists topArtists;

  /** The top tracks */
  private TopTracks topTracks;

  /** The top genres */
  private List<String> topGenres;

  /** The time the wrapped was generated */
  @ServerTimestamp private Date generatedAt;

  /** The firebase user ID */
  private String userId;
  /** The time range for the wrapped */
  private SpotifyApiHelper.TimeRange timeRange;

  public Wrapped() {}

  /** Constructor for Wrapped */
  public Wrapped(
      TopArtists topArtists,
      TopTracks topTracks,
      List<String> topGenres,
      Date generatedAt,
      String userId,
      SpotifyApiHelper.TimeRange timeRange) {
    this.topArtists = topArtists;
    this.topTracks = topTracks;
    this.topGenres = topGenres;
    this.generatedAt = generatedAt;
    this.userId = userId;
    this.timeRange = timeRange;
  }

  /**
   * Gets the top artists
   *
   * @return the top artists
   */
  public TopArtists getTopArtists() {
    return topArtists;
  }

  /**
   * Gets the top tracks
   *
   * @return the top tracks
   */
  public TopTracks getTopTracks() {
    return topTracks;
  }

  /**
   * Gets the top genres
   *
   * @return the top genres
   */
  public List<String> getTopGenres() {
    return topGenres;
  }

  /**
   * Gets the time the wrapped was generated
   *
   * @return the time the wrapped was generated
   */
  public Date getGeneratedAt() {
    return generatedAt;
  }

  /**
   * Gets the firebase user ID
   *
   * @return the firebase user ID
   */
  public String getUserId() {
    return userId;
  }

  /**
   * Gets the time range for the wrapped
   *
   * @return the time range for the wrapped
   */
  public SpotifyApiHelper.TimeRange getTimeRange() {
    return timeRange;
  }

  /**
   * Gets the color for the theme based on the time range
   *
   * @return the color for the theme
   */
  public int getColor() {
    switch (timeRange) {
        case SHORT_TERM:
            return R.color.short_term;
        case LONG_TERM:
            return R.color.long_term;
        default:
            return R.color.primary;
    }
  }

  public String toString() {
    return "Top Artists: "
        + topArtists.toString()
        + "Top Tracks: "
        + topTracks.toString()
        + "Top Genres: "
        + topGenres.toString()
        + "Generated At: "
        + generatedAt.toString()
        + "User ID: "
        + userId;
  }
}
