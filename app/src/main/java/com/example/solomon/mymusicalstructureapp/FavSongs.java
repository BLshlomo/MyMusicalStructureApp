package com.example.solomon.mymusicalstructureapp;

import android.app.Application;

import java.util.ArrayList;

public class FavSongs extends Application {

    // Initiate the favorite songs list as a static arraylist variable of type song.
    private static ArrayList<Song> favouriteSongs = new ArrayList<Song>();
    // Variables declaration.
    TinyDB tinydb; // declare a tinydb class to receive a list from shared preferences.
    ArrayList<Object> favSongObjects; // A variable to pass through the tinydb.

    /**
     * A static method to add a song to the favorite songs list using the Song object constructor.
     *
     * @param fThumbnail is the file thumbnail photo.
     * @param fName      is the file name.
     * @param fArtist    is the artist.
     */
    public static void addFavSong(int fThumbnail, String fName, String fArtist) {
        favouriteSongs.add(new Song(fThumbnail, fName, fArtist));
    }

    // A static method to remove a song from the favorite songs list.
    public static void delFavSong(int songIndex) {
        favouriteSongs.remove(songIndex);
    }

    // A static method to retrieve the favorite songs list.
    public static ArrayList<Song> retrieveFavSongsList() {
        return favouriteSongs;
    }

    // Runs on the start of the application before any other activity.
    @Override
    public void onCreate() {
        super.onCreate();

        // Initiate the tinydb class.
        tinydb = new TinyDB(getApplicationContext());

        // Load the tinydb with the arraylist of objects containing the favorite songs list
        // from shared preferences.
        favSongObjects = tinydb.getListObject("mySongs", Song.class);

        // Loops through the arraylist of objects to transform it to an arraylist of type song.
        for (Object objs : favSongObjects) {
            favouriteSongs.add((Song) objs);
        }
    }

}
