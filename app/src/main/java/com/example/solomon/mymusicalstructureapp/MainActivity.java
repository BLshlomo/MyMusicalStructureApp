package com.example.solomon.mymusicalstructureapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Variables declaration.
    ImageView playMain; // Play and pause song button.
    ImageView mThumbnail; // loaded song Thumbnail.
    ImageView mPhoto; // Loaded song screen size photo.
    ImageView explorer; // Music explorer screen.
    ImageView nextSong; // Play the playlist next song.
    ImageView previousSong; // Play the playlist previous song.
    ImageView mFav; // Add / remove to / from favorites list button.
    TextView mName; // Loaded song name.
    TextView mArtist; // Loaded song artist name.
    boolean playing; // Is music playing boolean.
    boolean approve; // Checks if a song is chosen by the user, passed by intent.
    boolean inactivity; // A boolean to mark the start of the inactivity timer.
    boolean favSongExist; // Signaling if the song is to be removed from the favorite songs list.
    ArrayList<Song> songs; // The current chosen music list.
    ArrayList<Song> myFavSongs; // The favorite songs list.
    int currentPosition; // The ArrayList index of the chosen song.
    RelativeLayout playerControls; // The control buttons of the music player.
    RelativeLayout songDetails; // The song details relative layout.
    LinearLayout songResources; // The song resources linear layout.
    Handler handler;
    Runnable r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views.
        playMain = (ImageView) findViewById(R.id.main_play);
        mThumbnail = (ImageView) findViewById(R.id.main_song_thumbnail);
        mPhoto = (ImageView) findViewById(R.id.main_song_photo);
        mName = (TextView) findViewById(R.id.main_song_name);
        mArtist = (TextView) findViewById(R.id.main_song_artist);
        mFav = (ImageView) findViewById(R.id.main_btn_fav);
        explorer = (ImageView) findViewById(R.id.main_btn_explorer);
        nextSong = (ImageView) findViewById(R.id.main_btn_next);
        previousSong = (ImageView) findViewById(R.id.main_btn_previous);
        playerControls = (RelativeLayout) findViewById(R.id.player_controls);
        songDetails = (RelativeLayout) findViewById(R.id.song_details);
        songResources = (LinearLayout) findViewById(R.id.main_song_resources);

        // Retrieve an ArrayList object from a static field.
        songs = DataHolder.getData();

        // Retrieve the user chosen song from an intent, if there is no data to retrieve,
        // default value is set to 0.
        currentPosition = getIntent().getIntExtra("position", 0);

        if (savedInstanceState != null) {

            // Checks if the bundle is not null and if its not restore the state of the variables.
            approve = savedInstanceState.getBoolean("isApprove");
            playing = savedInstanceState.getBoolean("isPlaying");
            favSongExist = savedInstanceState.getBoolean("isFavSongExist");
            currentPosition = savedInstanceState.getInt("currentPositionNumber");

            if (playing) {

                // Set the main play button to pause if the playing boolean is true,
                // also changes the inactivity boolean to true.
                playMain.setImageResource(R.drawable.button_pause);
                inactivity = true;

                handler = new Handler();
                r = new Runnable() {

                    @Override
                    public void run() {

                        // Makes the following layouts disappear once the countdown
                        // timer is over
                        songResources.setVisibility(View.GONE);
                        songDetails.setVisibility(View.GONE);
                        playerControls.setVisibility(View.GONE);
                    }
                };
                startHandler();
            }

            // Checks if the favSongExist boolean signaling that a song removed from the
            // list is true.
            if (favSongExist) {

                // Checks the size of the songs list, if its empty calls the playlistIntent
                // method to launch an intent to chose another list and acknowledges the
                // user with a toast message.
                if (songs.size() == 0) {
                    playlistIntent();
                    Toast.makeText(getBaseContext(), R.string.fav_list_empty,
                            Toast.LENGTH_LONG).show();
                    approve = false;
                    favSongExist = false;

                } else {

                    // Checks if the index number is out of the list bounds, if it is,
                    // subtract the difference and update the index number.
                    if (currentPosition > songs.size()) {
                        currentPosition -= songs.size();

                    } else if (currentPosition == songs.size()) {

                        // Decrease the currentPosition index by one if
                        // the index number equals the song list size.
                        currentPosition -= 1;
                    }

                    // Changes the favSongExist boolean to false.
                }
                favSongExist = false;
            }
        } else {

            // Retrieve a boolean from an intent signifying the user has chosen a song,
            // if no song is selected yet, the default value is set to false.
            approve = getIntent().getBooleanExtra("loadSong", false);
        }

        // Checks to see if the approve boolean is true signifying a song has chosen.
        if (approve) {

            // Call the loadSong method.
            loadSong();

            // Changes the visibility  of the current song details and resources layouts to visible.
            songDetails.setVisibility(View.VISIBLE);
            songResources.setVisibility(View.VISIBLE);

            // A button to add or remove a song to / from the favorite song list.
            // Set an event listener for the add favourite song button.
            mFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Variables declaration and initialization.
                    int delSongIndex = 9;
                    favSongExist = false;

                    // Initiate the variable with the FavSongs static list from another activity.
                    myFavSongs = FavSongs.retrieveFavSongsList();

                    // Loops through the favorite songs list.
                    for (Song item : myFavSongs) {

                        // Checks if the current song name and artist matches a song from the list.
                        if (item.getName().equals(songs.get(currentPosition).getName()) &&
                                item.getArtist().equals(songs.get(currentPosition).getArtist())) {

                            // If it does, change the variable delSongIndex for the index of the
                            // matched song and changes the boolean favSongExists to true.
                            delSongIndex = myFavSongs.indexOf(item);
                            favSongExist = true;
                        }
                    }

                    if (favSongExist) {

                        // Removes the favorite song from the favorite songs list
                        // and acknowledges the user with a long length
                        // toast message if the current song already match a song from the list.
                        Toast.makeText(getApplicationContext(), songs.get(currentPosition).getName() +
                                " " + getString(R.string.del_fav_list), Toast.LENGTH_LONG).show();
                        FavSongs.delFavSong(delSongIndex);
                        songsObject();

                    } else {

                        // Adds the current song to the fav songs list, acknowledges the user with
                        // a long length toast message and calls the songsObject method
                        // to update the list to the tinydb shared preferences file.
                        FavSongs.addFavSong(songs.get(currentPosition).getThumbnail(),
                                songs.get(currentPosition).getName(),
                                songs.get(currentPosition).getArtist());
                        Toast.makeText(getApplicationContext(), songs.get(currentPosition).getName() +
                                " " + getString(R.string.add_fav_list), Toast.LENGTH_LONG).show();
                        songsObject();
                    }
                }
            });
        }

        // Set a click listener on main_play.
        playMain.setOnClickListener(new View.OnClickListener() {
            // This code will run once the play image is clicked
            @Override
            public void onClick(View view) {

                // Checks if a song entry is loaded using the approve boolean, if it does,
                // change the play/pause image accordingly to the playing boolean.
                if (approve) {
                    if (!playing) {

                        // Set the main play button to pause if the playing boolean is false,
                        // also changes the playing and inactivity booleans to true.
                        playMain.setImageResource(R.drawable.button_pause);
                        playing = true;
                        inactivity = true;

                        handler = new Handler();
                        r = new Runnable() {

                            @Override
                            public void run() {

                                // Makes the following layouts disappear once the countdown
                                // timer is over
                                songResources.setVisibility(View.GONE);
                                songDetails.setVisibility(View.GONE);
                                playerControls.setVisibility(View.GONE);
                            }
                        };
                        startHandler();
                    } else {

                        // Calls the pauseSong method if the playing boolean is true.
                        pauseSong();
                    }
                } else {

                    // Call the playlistIntent method.
                    playlistIntent();
                }
            }
        });

        // Set a click listener on main_btn_explorer.
        explorer.setOnClickListener(new View.OnClickListener() {
            // This code will run once the play image is clicked
            @Override
            public void onClick(View view) {
                if (playing) {

                    // Calls the pauseSong method if the playing boolean is true.
                    pauseSong();
                }

                // Call the playlistIntent method.
                playlistIntent();
            }
        });

        // Set a click listener on main_btn_next.
        nextSong.setOnClickListener(new View.OnClickListener() {
            // This code will run once the next image is clicked.
            @Override
            public void onClick(View view) {

                // Checks if a song entry is loaded using the approve boolean, if it does,
                // go to the next entry of the songs arraylist.
                if (approve) {
                    if (playing) {

                        // Calls the pauseSong method if the playing boolean is true.
                        pauseSong();
                    }

                    // Checks if the favSongExist boolean signaling that a song removed from the
                    // list is true.
                    if (favSongExist) {
                        if (songs.size() == 0) {

                            // Checks the size of the songs list, if its empty calls the playlistIntent
                            // method to launch an intent to chose another list and acknowledges the
                            // user with a toast message.
                            playlistIntent();
                            Toast.makeText(getBaseContext(), R.string.fav_list_empty,
                                    Toast.LENGTH_LONG).show();

                        } else {

                            // Decrease the currentPosition index by one and changes the favSongExist
                            // boolean to false.
                            currentPosition--;
                            favSongExist = false;
                        }
                    } else if (currentPosition == (songs.size() - 1)) {

                        // Checks if the song is not the the last index of the songs arraylist,
                        // if it does change the currentPosition to the first entry of the songs arraylist,
                        // and call the loadSong method.
                        currentPosition = 0;
                        loadSong();
                    } else {

                        // Increase the currentPosition index by one, and call the loadSong method.
                        currentPosition++;
                        loadSong();
                    }
                } else {

                    // Call the playlistIntent method.
                    playlistIntent();
                }
            }
        });

        // Set a click listener on main_btn_previous.
        previousSong.setOnClickListener(new View.OnClickListener() {
            // This code will run once the previous image is clicked.
            @Override
            public void onClick(View view) {

                // Checks if a song entry is loaded using the approve boolean, if it does,
                // go to the previous entry of the songs arraylist.
                if (approve) {

                    if (playing) {

                        // Calls the pauseSong method if the playing boolean is true.
                        pauseSong();
                    }

                    if (songs.size() == 0) {

                        // Checks the size of the songs list, if its empty calls the playlistIntent
                        // method to launch an intent to chose another list and acknowledges the
                        // user with a toast message.
                        playlistIntent();
                        Toast.makeText(getBaseContext(), R.string.fav_list_empty,
                                Toast.LENGTH_LONG).show();

                    } else if (currentPosition == 0) {

                        // Checks if the song is not the first index of the songs arraylist,
                        // if it does change the currentPosition to the last entry of the songs arraylist,
                        // and call the loadSong method.
                        currentPosition = (songs.size() - 1);
                        loadSong();
                    } else {

                        // Decrease the currentPosition index by one, and call the loadSong method.
                        currentPosition--;
                        loadSong();
                    }
                } else {

                    // Call the playlistIntent method.
                    playlistIntent();
                }
            }
        });
    }

    @Override
    public void onUserInteraction() {

        // Run this code once the screen is interacted with.
        // TODO Auto-generated method stub
        super.onUserInteraction();

        // Calls the methods to start and stops an inactivity counter if the playing boolean is true.
        if (inactivity) {

            stopHandler();//stop first and then start
            startHandler();
        }
    }

    public void stopHandler() {

        handler.removeCallbacks(r);
        // Make the Layouts visible again, once the screen is touched.
        songResources.setVisibility(View.VISIBLE);
        songDetails.setVisibility(View.VISIBLE);
        playerControls.setVisibility(View.VISIBLE);
    }

    public void startHandler() {

        // Start a timer of inactivity to run a code once the timer is over.
        handler.postDelayed(r, 5 * 1000); //for 15 seconds
    }

    // Creates a public method with no arguments.
    public void playlistIntent() {

        // Create a new intent to launch the MyMusic.
        Intent pIntent = new Intent(MainActivity.this, MyMusic.class);

        // Start the new activity
        startActivity(pIntent);
    }

    // Creates a public method with no arguments.
    public void loadSong() {

        // Load the respective views with current song details fetched according
        // to the currentPosition index number from the loaded arraylist object.
        mThumbnail.setImageResource(songs.get(currentPosition).getThumbnail());
        mPhoto.setImageResource(songs.get(currentPosition).getThumbnail());
        mName.setText(songs.get(currentPosition).getName());
        mArtist.setText(songs.get(currentPosition).getArtist());
    }

    // Creates a public void method.
    public void pauseSong() {

        // Calls the stopHandler method, Set the main play button to play,
        // also changes the playing and inactivity booleans to false.
        stopHandler();
        playMain.setImageResource(R.drawable.button_play);
        playing = false;
        inactivity = false;
    }

    private void songsObject() {

        // A public void method to copy the favourite songs list to tinydb (shared preferences)
        // file so its saved in the phone memory after application closure.
        ArrayList<Object> favSongObjects = new ArrayList<Object>();
        TinyDB tinydb = new TinyDB(getApplicationContext());

        // Initiate the variable with the FavSongs static list from another activity.
        myFavSongs = FavSongs.retrieveFavSongsList();

        // A for loop file to change the Song objects into an Object object following the tinydb
        // guidelines.
        for (Song a : myFavSongs) {
            favSongObjects.add((Object) a);
        }

        // Loading the tinydb file with the favourite songs list object.
        tinydb.putListObject("mySongs", favSongObjects);
    }

    // Runs just after on create or in case of regaining focus, as an example, on back button pressed.
    @Override
    protected void onResume() {
        super.onResume();

        // Checks if the approve boolean which signal if a song is loaded is true.
        if (approve) {

            // Checks the size of the songs list.
            if (songs.size() == 0) {

                if (playing) {

                    // Run the pauseSong method and change visibility if the playing boolean is true.
                    pauseSong();
                    songResources.setVisibility(View.GONE);
                    songDetails.setVisibility(View.GONE);

                }

                //  Changes the approve and the favSongExist booleans to false if the list is empty.
                approve = false;
                favSongExist = false;
            }
        }

    }

    // Use onSaveInstanceState(Bundle).
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putBoolean("isPlaying", playing);
        savedInstanceState.putBoolean("isFavSongExist", favSongExist);
        savedInstanceState.putBoolean("isApprove", approve);
        savedInstanceState.putInt("currentPositionNumber", currentPosition);

        // etc.

        super.onSaveInstanceState(savedInstanceState);
    }
}
