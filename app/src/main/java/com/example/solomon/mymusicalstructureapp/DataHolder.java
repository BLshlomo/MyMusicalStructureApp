package com.example.solomon.mymusicalstructureapp;

import java.util.ArrayList;

// Dataholder is a class to transfer object between activities.
public class DataHolder {

    // Makes a new Arraylist of type Song data static object to pass between the activities.
    private static ArrayList<Song> data;

    // Method to load the Arraylist of type Song from the data holder.
    public static ArrayList<Song> getData() {
        return data;
    }

    // A method to save an Arraylist of type song to the data holder.
    public static void setData(ArrayList<Song> data) {
        DataHolder.data = data;
    }
}
