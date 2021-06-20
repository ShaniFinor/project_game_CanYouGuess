package com.example.shanifinor_project.model.db;

import java.util.ArrayList;
import java.util.List;

/**
 * userDao is a DAO class for users. (DAO- Data Access Object)
 * the DAO provides some specific data operations without exposing details of the database.
 * I need this class to save and remove users from the database.
 * When I add a new user to the database, I create a new UserDao variable.
 * When I want to update or get a user's details, I get a UserDao variable.
 */
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
