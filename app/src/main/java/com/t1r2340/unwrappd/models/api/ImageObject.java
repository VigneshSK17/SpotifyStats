package com.t1r2340.unwrappd.models.api;


import java.io.Serializable;

/** Represents an image object. */
public class ImageObject implements Serializable {
  /** The height of the image in pixels. */
  private int height;

  /** The width of the image in pixels. */
  private int width;

  /** The URL of the image. */
  private String url;

  public ImageObject() {}

  /** Creates an image object from JSON. */
  public ImageObject(int height, int width, String url) {
    this.height = height;
    this.width = width;
    this.url = url;
  }

  /**
   * Gets the height of the image.
   *
   * @return the height of the image
   */
  public int getHeight() {
    return height;
  }

  /**
   * Gets the width of the image.
   *
   * @return the width of the image
   */
  public int getWidth() {
    return width;
  }

  /**
   * Gets the URL of the image.
   *
   * @return the URL of the image
   */
  public String getUrl() {
    return url;
  }
}
