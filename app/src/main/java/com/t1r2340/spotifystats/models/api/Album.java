package com.t1r2340.spotifystats.models.api;


import java.util.List;

/**
 * Class holding album information
 */
public class Album {

    /** The Spotify ID for the album */
    private String id;
    /** The type of the album */
    private String albumType;
    /** The images of the album */
    private List<ImageObject> images;
    /** The name of the album */
    private String name;


    public Album() {}
    /**
     * Constructor for Album
     */
    public Album(String id, String albumType, List<ImageObject> images, String name) {
        this.id = id;
        this.albumType = albumType;
        this.images = images;
        this.name = name;
    }

    /**
     * Gets the Spotify ID for the album
     * @return the Spotify ID for the album
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the type of the album
     * @return the type of the album
     */
    public String getAlbumType() {
        return albumType;
    }

    /**
     * Gets the images of the album
     * @return the images of the album
     */
    public List<ImageObject> getImages() {
        return images;
    }

    /**
     * Gets the name of the album
     * @return the name of the album
     */
    public String getName() {
        return name;
    }
}
