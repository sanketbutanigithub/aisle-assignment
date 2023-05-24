package com.example.aisle_assignment.models;

import java.util.ArrayList;

public class Likes {
    public ArrayList<Profile> profiles;

    public Likes(ArrayList<Profile> profiles) {
        this.profiles = profiles;
    }

    public ArrayList<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(ArrayList<Profile> profiles) {
        this.profiles = profiles;
    }
}
