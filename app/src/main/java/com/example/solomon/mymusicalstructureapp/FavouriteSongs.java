package com.example.solomon.mymusicalstructureapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class FavouriteSongs extends AppCompatActivity {

    // Declaration favSongs as an arraylist of type Song.
    ArrayList<Song> favSongs; // Favorite songs list.

    SongAdapter songsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_list);

        // Loads the variable with the favorite songs list from the application class.
        favSongs = FavSongs.retrieveFavSongsList();

        // Save the favorite songs arraylist on a static dataholder file.
        DataHolder.setData(favSongs);

        // Set song adapter as a new custom object arrayadapter.
        songsAdapter = new SongAdapter(this, favSongs);

        // Initiating list to listview
        ListView listView = (ListView) findViewById(R.id.list);

        // Applying the song adapter to the list view.
        listView.setAdapter(songsAdapter);
    }

    // Runs just after on create or in case of regaining focus, as an example, on back button pressed.
    @Override
    protected void onResume() {
        super.onResume();

        // Loads the variable with the favorite songs list from the application class.
        favSongs = FavSongs.retrieveFavSongsList();

        // Save the favorite songs arraylist on a static dataholder file.
        DataHolder.setData(favSongs);

        // Makes changes to the UI if the favorite songs list is changed.
        songsAdapter.notifyDataSetChanged();
    }
}
