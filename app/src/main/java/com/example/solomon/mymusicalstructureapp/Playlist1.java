package com.example.solomon.mymusicalstructureapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class Playlist1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_list);

        // Creates a custom class Arraylist of songs.
        ArrayList<Song> songs = new ArrayList<Song>();
        songs.add(new Song(R.drawable.song, "Hey Brother!", "Avicii ft. Dan Tyminski"));
        songs.add(new Song(R.drawable.song2, "Wake Me Up", "Avicii ft. Aloe Blacc"));
        songs.add(new Song(R.drawable.song3, "The Nights", "Avicii ft. Nicholas Furlong"));
        songs.add(new Song(R.drawable.song4, "I Could Be The One", "Avicii vs Nicky Romero"));
        songs.add(new Song(R.drawable.song5, "Levels", "Avicii"));

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