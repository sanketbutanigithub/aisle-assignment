package com.example.aisle_assignment.models;

public class Profile {
    public String first_name;
    public String avatar;

    public Profile(String first_name, String avatar) {
        this.first_name = first_name;
        this.avatar = avatar;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
