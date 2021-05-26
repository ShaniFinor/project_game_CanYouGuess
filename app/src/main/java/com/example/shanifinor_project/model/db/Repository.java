package com.example.shanifinor_project.model.db;

import androidx.annotation.NonNull;

import com.example.shanifinor_project.model.utils.Observer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Repository {
    private Map<String, UserDao> users = new HashMap<>();

    // Signleton
    private static Repository instance = null;

    // Observer
    private ArrayList<Observer> observers = new ArrayList<>();

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    private Repository() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRf = database.getReference("users");
        myRf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for (DataSnapshot userDb : snapshot.getChildren()) {
                    UserDao userDao = userDb.getValue(UserDao.class);
                    users.put(userDb.getKey(), userDao);
                }
                notifyObservers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public Map<String, UserDao> getUsers() {
        return users;
    }

    public void register(Observer observer) {
        observers.add(observer);
    }

    public void unregister(Observer observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
