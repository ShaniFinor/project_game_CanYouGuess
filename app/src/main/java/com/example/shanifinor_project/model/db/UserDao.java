package com.example.shanifinor_project.model.db;

import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private int place;
    private String icon;
    private String name;
    private String email;
    private int numOfWin;
    private int stars;
    private String guessedAnswer;
    private String suggestedString;
    private List<String> databaseOpenSquares = new ArrayList<>();
    private List<Integer> placeChosenFromSuggestedString = new ArrayList<>();

    public UserDao() {
    }

//    public User(int place, String icon, String name, String email, int numOfWin, int stars) {
//        this.place = place;
//        this.icon = icon;
//        this.name = name;
//        this.email = email;
//        this.numOfWin = numOfWin;
//        this.stars = stars;
//    }


//    public User(int place, String icon, String name, String email, int numOfWin, int stars, String guessedAnswer, String suggestedString, String databaseOpenSquares) {
//        this.place = place;
//        this.icon = icon;
//        this.name = name;
//        this.email = email;
//        this.numOfWin = numOfWin;
//        this.stars = stars;
//        this.guessedAnswer = guessedAnswer;
//        this.suggestedString = suggestedString;
//        this.databaseOpenSquares = databaseOpenSquares;
//    }

    public UserDao(int place, String icon, String name, int numOfWin) {
        this.place = place;
        this.icon = icon;
        this.name = name;
        this.numOfWin = numOfWin;
    }
    public UserDao(String icon, String name, int numOfWin) {
        this.icon = icon;
        this.name = name;
        this.numOfWin = numOfWin;
    }

    public UserDao(int place, String icon, String name, String email, int numOfWin, int stars, String guessedAnswer, String suggestedString, List<String> databaseOpenSquares, List<Integer> placeChosenFromSuggestedString) {
        this.place = place;
        this.icon = icon;
        this.name = name;
        this.email = email;
        this.numOfWin = numOfWin;
        this.stars = stars;
        this.guessedAnswer = guessedAnswer;
        this.suggestedString = suggestedString;
        this.databaseOpenSquares = databaseOpenSquares;
        this.placeChosenFromSuggestedString = placeChosenFromSuggestedString;
    }

    public List<Integer> getPlaceChosenFromSuggestedString() {
        return placeChosenFromSuggestedString;
    }

    public void setPlaceChosenFromSuggestedString(List<Integer> placeChosenFromSuggestedString) {
        this.placeChosenFromSuggestedString = placeChosenFromSuggestedString;
    }

    public List<String> getDatabaseOpenSquares() {
        return databaseOpenSquares;
    }

    public void setDatabaseOpenSquares(List<String> databaseOpenSquares) {
        this.databaseOpenSquares = databaseOpenSquares;
    }

    public String getSuggestedString() {
        return suggestedString;
    }

    public void setSuggestedString(String suggestedString) {
        this.suggestedString = suggestedString;
    }

    public String getGuessedAnswer() {
        return guessedAnswer;
    }

    public void setGuessedAnswer(String guessedAnswer) {
        this.guessedAnswer = guessedAnswer;
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
