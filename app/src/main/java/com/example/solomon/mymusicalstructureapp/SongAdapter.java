package com.example.solomon.mymusicalstructureapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SongAdapter extends ArrayAdapter<Song> {

    // Variables declaration.
    private boolean approve = true; // Signified if the user chosen a song to play, passed by intent.
    private ArrayList<Song> myFavSongs; // The favorite songs list.
    private Context mContext; // Context.


    /**
     * Creates a new SongAdapter Object.
     *
     * @param context is the current context (activity) that adapter is being created in.
     * @param songs   is the list {@link Song} to be displayed.
     */
    SongAdapter(Context context, ArrayList<Song> songs) {
        super(context, 0, songs);
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view.
        View ListItemView = convertView;
        if (ListItemView == null) {
            ListItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,
                    parent, false);
        }

        // Get the {@link Song} object located at this position on the list.
        final Song clickedSong = getItem(position);


        // Lookup view for data population.
        ImageView sThumbnail = (ImageView) ListItemView.findViewById(R.id.thumbnail);
        TextView sName = (TextView) ListItemView.findViewById(R.id.name);
        TextView sArtist = (TextView) ListItemView.findViewById(R.id.artist);
        ImageView sPlay = (ImageView) ListItemView.findViewById(R.id.list_button_play);
        ImageView sFav = (ImageView) ListItemView.findViewById(R.id.list_button_fav);

        // Populate the data into the template view using the data object.
        sThumbnail.setImageResource(clickedSong.getThumbnail());
        sName.setText(clickedSong.getName());
        sArtist.setText(clickedSong.getArtist());

        // Set an event listener for the play button.
        sPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Runs an intent to launch the main activity with the chosen song details,
                // the position index and a boolean signaling a song is chosen.
                Intent playIntent = new Intent(mContext, MainActivity.class);
                playIntent.putExtra("position", position);
                playIntent.putExtra("loadSong", approve);

                // Launch the main activity.
                mContext.startActivity(playIntent);
            }
        });

        // A button to add or remove a song to / from the favorite song list.
        // Set an event listener for the add favourite song button.
        sFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Variables declaration and initialization.
                int delSongIndex = 0;
                boolean favSongExist = false;

                // Initiate the variable with the FavSongs static list from another activity.
                myFavSongs = FavSongs.retrieveFavSongsList();

                // Loops through the favorite songs list.
                for (Song item : myFavSongs) {

                    // Checks if the clicked song name and artist matches a song from the list.
                    if (item.getName().equals(clickedSong.getName()) && item.getArtist().equals(clickedSong.getArtist())) {

                        // If it does, change the variable delSongIndex for the index of the
                        // matched song and changes the boolean favSongExists to true.
                        delSongIndex = myFavSongs.indexOf(item);
                        favSongExist = true;
                    }
                }

                // Calls the remove favorite song method and acknowledges the user with a long length
                // toast message if the clicked song already match a song from the list.
                if (favSongExist) {

                    removeFavSong(delSongIndex);
                    Toast.makeText(mContext, clickedSong.getName() + " " +
                            getContext().getString(R.string.del_fav_list), Toast.LENGTH_LONG).show();
                } else {

                    // Adds the clicked song to the fav songs list, acknowledges the user with
                    // a long length toast message and calls the songsObject method
                    // to update the list to the tinydb shared preferences file.
                    FavSongs.addFavSong(clickedSong.getThumbnail(), clickedSong.getName(),
                            clickedSong.getArtist());
                    Toast.makeText(mContext, clickedSong.getName() + " " +
                            getContext().getString(R.string.add_fav_list), Toast.LENGTH_LONG).show();
                    songsObject();
                }
            }
        });

        // Return the completed view to render on screen.
        return ListItemView;
    }

    // A method to remove a song from the favorite song list and update the list view ui.
    private void removeFavSong(int songIndex) {

        // Removes a song from the favorite songs list.
        FavSongs.delFavSong(songIndex);

        // Updates the list view ui according to the change.
        notifyDataSetChanged();

        // Calls the songsObject method to update the list ot the tinydb shared preferences file.
        songsObject();
    }

    private void songsObject() {

        // A public void method to copy the favourite songs list to tinydb (shared preferences)
        // file so its saved in the phone memory after application closure.
        ArrayList<Object> favSongObjects = new ArrayList<Object>();
        TinyDB tinydb = new TinyDB(getContext());

        myFavSongs = FavSongs.retrieveFavSongsList();

        // A for loop file to change the Song objects into an Object object following the tinydb
        // guidelines.
        for (Song a : myFavSongs) {
            favSongObjects.add((Object) a);
        }

        // Loading the tinydb file with the favourite songs list object.
        tinydb.putListObject("mySongs", favSongObjects);
    }
}
