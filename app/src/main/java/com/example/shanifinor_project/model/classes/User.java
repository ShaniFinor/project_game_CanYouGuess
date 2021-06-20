package com.example.shanifinor_project.model.classes;

import com.example.shanifinor_project.model.db.Repository;
import com.example.shanifinor_project.model.db.UserDao;
import com.example.shanifinor_project.model.utils.Observer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User is a singleton class which holds and updates the current user.
 * This class goes through all the information in the Repository and searches for the current user who is connected to the application.
 * Because the Repository is always updated when there are changes to the database, the User is also always updated.
 * The User holds within it a UserDao variable that constitutes the current user.
 * If I need to get or change information in the current user, I will refer to this class.
 */
public class User implements Observer {
    private UserDao userDao;
    private String key;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    // Singleton
    private static User instance = null;

    // Observer
    private ArrayList<Observer> observers = new ArrayList<>();

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    private User() {
        Repository.getInstance().register(this);
    }

    public void register(Observer observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    @Override
    public void update() {
        updateThisUser();
        notifyObservers();
    }

    private void updateThisUser() {
        Map<String, UserDao> users = Repository.getInstance().getUsers();
        FirebaseUser cUser = mAuth.getCurrentUser();
        userDao = null;
        key = null;
        if (cUser != null) {
            for (Map.Entry<String, UserDao> entry : users.entrySet()) {
                if (entry.getValue().getEmail().equals(cUser.getEmail())) {
                    userDao = entry.getValue();
                    key = entry.getKey();
                    break;
                }
            }
        }
    }

    public void registerNewUserToFirebase(UserDao newUser) {
        // Register new user
        DatabaseReference userRef = database.getReference("users");
        userRef.push().setValue(newUser);
    }

    public void login() {
        updateThisUser();
    }

    public void logOut() {
        mAuth.signOut();
        updateThisUser();
    }

    private void updateThisUserInDatabase() {
        // Update my user in realtime database.
        DatabaseReference userRef = database.getReference("users/" + key);
        userRef.setValue(userDao);
    }

//    public void userUpdate(){
//        invalidateOptionsMenu();
//    }


    public boolean isLoggedIn() {
        // Check if a user is logged in.
        FirebaseUser cUser = mAuth.getCurrentUser();
        if (cUser == null) {
            updateThisUser();
            return false;
        }

        // Check if the logged in user exists.
        Map<String, UserDao> users = Repository.getInstance().getUsers();
        boolean userExists = false;
        for (Map.Entry<String, UserDao> entry : users.entrySet()) {
            if (entry.getValue().getEmail().equals(cUser.getEmail())) {
                userExists = true;
                break;
            }
        }

//        if (userExists) {
//            login();
//        } else {
//            logOut();
//        }
        return userExists;
    }


    public List<Integer> getPlaceChosenFromSuggestedString() {
        return userDao.getPlaceChosenFromSuggestedString();
    }

    public void setPlaceChosenFromSuggestedString(List<Integer> placeChosenFromSuggestedString) {
        userDao.setPlaceChosenFromSuggestedString(placeChosenFromSuggestedString);
        updateThisUserInDatabase();
    }

    public List<String> getDatabaseOpenSquares() {
        return userDao.getDatabaseOpenSquares();
    }

    public void setDatabaseOpenSquares(List<String> databaseOpenSquares) {
        userDao.setDatabaseOpenSquares(databaseOpenSquares);
        updateThisUserInDatabase();
    }

    public String getSuggestedString() {
        return userDao.getSuggestedString();
    }

    public void setSuggestedString(String suggestedString) {
        userDao.setSuggestedString(suggestedString);
        updateThisUserInDatabase();
    }

    public String getGuessedAnswer() {
        return userDao.getGuessedAnswer();
    }

    public void setGuessedAnswer(String guessedAnswer) {
        userDao.setGuessedAnswer(guessedAnswer);
        updateThisUserInDatabase();
    }

    public String getEmail() {
        return userDao.getEmail();
    }

    public void setEmail(String email) {
        userDao.setEmail(email);
        updateThisUserInDatabase();
    }

    public void setPlace(int place) {
        userDao.setPlace(place);
        updateThisUserInDatabase();
    }

    public void setIcon(String icon) {
        userDao.setIcon(icon);
        updateThisUserInDatabase();
    }

    public void setName(String name) {
        userDao.setName(name);
        updateThisUserInDatabase();
    }

    public void setNumOfWin(int numOfWin) {
        userDao.setNumOfWin(numOfWin);
        updateThisUserInDatabase();
    }

    public int getStars() {
        return userDao.getStars();
    }

    public void setStars(int stars) {
        userDao.setStars(stars);
        updateThisUserInDatabase();
    }

    public int getPlace() {
        return userDao.getPlace();
    }

    public String getIcon() {
        return userDao.getIcon();
    }

    public String getName() {
        return userDao.getName();
    }

    public int getNumOfWin() {
        return userDao.getNumOfWin();
    }
}
