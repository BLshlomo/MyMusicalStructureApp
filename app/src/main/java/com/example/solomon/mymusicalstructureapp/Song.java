package com.example.solomon.mymusicalstructureapp;

/**
 * Song represent an audio file to play, contains the file name, the artist name and a
 * thumbnail photo if exists.
 */
public class Song {

    // The file name.
    private String mName;

    // The artist name.
    private String mArtist;

    // The thumbnail photo of the file
    private int mThumbnail;

    /**
     * Constructor that is used to create an instance of the Song object.
     *
     * @param thumbnail is the file thumbnail photo.
     * @param name      is the file name.
     * @param artist    is the artist.
     */
    Song(int thumbnail, String name, String artist) {
        mThumbnail = thumbnail;
        mName = name;
        mArtist = artist;
    }

    // Method to get a thumbnail .
    public int getThumbnail() {
        return mThumbnail;
    }

    // Method to get the name.
    public String getName() {
        return mName;
    }

    // Method to get the artist name.
    public String getArtist() {
        return mArtist;
    }

}
