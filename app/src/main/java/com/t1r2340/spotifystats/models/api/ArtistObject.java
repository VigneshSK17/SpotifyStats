package com.t1r2340.spotifystats.models.api;

/**
 * Class holding more specific artist information
 */
public class ArtistObject extends Artist {

    /** Genres of the artist */
    private String[] genres;
    /** Images of the artist */
    private ImageObject[] images;

    /**
     * Constructor for ArtistObject
     */
    public ArtistObject(String id, String name, String[] genres, ImageObject[] images) {
        super(id, name);
        this.genres = genres;
        this.images = images;
    }

    /**
     * Gets the genres of the artist
     * @return the genres of the artist
     */
    public String[] getGenres() {
        return genres;
    }

    /**
     * Gets the images of the artist
     * @return the images of the artist
     */
    public ImageObject[] getImages() {
        return images;
    }
}
