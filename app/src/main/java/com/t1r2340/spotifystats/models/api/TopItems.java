package com.t1r2340.spotifystats.models.api;

/**
 * Abstract class to hold top artists/tracks information
 */
public abstract class TopItems {
    /** Number of items returned */
    private int limit;
    /** URL for next page of items */
    private String next;
    /** Offset of items */
    private int offset;
    /** Total number of items */
    private int total;

    /**
     * Constructor for TopItems
     */
    public TopItems(int limit, String next, int offset, int total) {
        this.limit = limit;
        this.next = next;
        this.offset = offset;
        this.total = total;
    }

    /**
     * Gets the number of items returned
     * @return the number of items returned
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Gets the URL for the next page of items
     * @return the URL for the next page of items
     */
    public String getNext() {
        return next;
    }

    /**
     * Gets the offset of items
     * @return the offset of items
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Gets the total number of items
     * @return the total number of items
     */
    public int getTotal() {
        return total;
    }

}
