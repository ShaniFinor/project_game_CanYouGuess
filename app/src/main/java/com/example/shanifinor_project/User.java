package com.example.shanifinor_project;

import android.graphics.Bitmap;

public class User {

    private int place;
    private String icon;
    private String name;
    private String email;
    private int numOfWin;
    private int stars;
///icon---Bitmap
    public User() {
    }

    public User(int place, String icon, String name, String email, int numOfWin, int stars) {
        this.place = place;
        this.icon = icon;
        this.name = name;
        this.email = email;
        this.numOfWin = numOfWin;
        this.stars = stars;
    }

    public User(int place, String icon, String name, int numOfWin) {
        this.place = place;
        this.icon = icon;
        this.name = name;
        this.numOfWin = numOfWin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumOfWin(int numOfWin) {
        this.numOfWin = numOfWin;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getPlace() {
        return place;
    }

    public String getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public int getNumOfWin() {
        return numOfWin;
    }
}
