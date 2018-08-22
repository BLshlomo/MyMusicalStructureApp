package com.example.solomon.mymusicalstructureapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class MyMusic extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_music);

        // Initialize variables.
        LinearLayout mPlaylist1 = (LinearLayout) findViewById(R.id.playlist_1);
        LinearLayout mPlaylist2 = (LinearLayout) findViewById(R.id.playlist_2);
        LinearLayout mAll = (LinearLayout) findViewById(R.id.all);

        // Set a click listener on playlist 1 linear layout.
        mPlaylist1.setOnClickListener(new View.OnClickListener() {
            // This code will run once the playlist 1 linear layout is clicked
            @Override
            public void onClick(View view) {

                // Create a new intent to launch the Playlist1 activity.
                Intent pl1Intent = new Intent(MyMusic.this, Playlist1.class);

                // Start the new activity.
                startActivity(pl1Intent);
            }
        });

        // Set a click listener on playlist 2 linear layout.
        mPlaylist2.setOnClickListener(new View.OnClickListener() {
            // This code will run once the playlist 1 linear layout is clicked
            @Override
            public void onClick(View view) {

                // Create a new intent to launch the Playlist2 activity.
                Intent pl2Intent = new Intent(MyMusic.this, Playlist2.class);

                // Start the new activity.
                startActivity(pl2Intent);
            }
        });

        // Set a click listener on favourite songs linear layout.
        mAll.setOnClickListener(new View.OnClickListener() {
            // This code will run once the all linear layout is clicked
            @Override
            public void onClick(View view) {

                // Create a new intent to launch the MyMusic.
                Intent allIntent = new Intent(MyMusic.this, FavouriteSongs.class);

                // Start the new activity.
                startActivity(allIntent);
            }
        });
    }
}
