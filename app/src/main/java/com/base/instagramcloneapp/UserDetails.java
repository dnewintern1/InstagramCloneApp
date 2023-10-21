package com.base.instagramcloneapp;

import android.widget.RadioButton;

public class UserDetails {
    private String profileName;
    private String yourBio;
    private String yourProfession;
    private String yourHobbies;
    private String favSport;

    public UserDetails(String profileName, String yourBio, String yourProfession, String yourHobbies, String favSport) {
        this.profileName = profileName;
        this.yourBio = yourBio;
        this.yourProfession = yourProfession;
        this.yourHobbies = yourHobbies;
        this.favSport = favSport;
    }

    public UserDetails(){};

    public String getProfileName() {
        return profileName;
    }

    public String getYourBio() {
        return yourBio;
    }

    public String getYourProfession() {
        return yourProfession;
    }

    public String getYourHobbies() {
        return yourHobbies;
    }

    public String getFavSport() {
        return favSport;
    }
}