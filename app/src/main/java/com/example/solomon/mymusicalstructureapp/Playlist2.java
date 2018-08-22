package com.example.solomon.mymusicalstructureapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class Playlist2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_list);

        // Creates a custom class Arraylist of songs.
        ArrayList<Song> songs = new ArrayList<Song>();
        songs.add(new Song(R.drawable.song6, "Pizza", "Martin Garrix"));
        songs.add(new Song(R.drawable.song7, "Dont You Worry Child", "Swedish House Mafia ft. John Martin"));
        songs.add(new Song(R.drawable.song8, "Titanium", "David Guetta ft. Sia"));
        songs.add(new Song(R.drawable.song9, "More Than You Know", "Axwell v Ingrosso"));
        songs.add(new Song(R.drawable.song10, "Summer", "Calvin Harris"));

        // Save the songs arraylist on a static dataholder file.
        DataHolder.setData(songs);

        // Set song adapter as a new custom object arrayadapter.
        SongAdapter songsAdapter = new SongAdapter(this, songs);

        // Initiating list to listview.
        ListView listView = (ListView) findViewById(R.id.list);

        // Applying the song adapter to the list view.
        listView.setAdapter(songsAdapter);
    }
}