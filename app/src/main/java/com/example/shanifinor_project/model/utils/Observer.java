package com.example.shanifinor_project.model.utils;

/**
 * Observer is an interface which can only update.
 * interface means I can not create an Observer variable, but only give other classes the Observer attributes.
 * All Observer classes can be "subscribed" to other classes (using a register operation that receives the Observer and adds it to the class' Observers array). And update when these classes tell them.
 *
 * Whenever there is a change in the database, the Repository updates all the values stored in it and alerts the User, who is an Observer registered with it, that something has changed.
 * Immediately afterwards, the update operation is activated by the User and it checks whether changes have been made to the current user.
 * If changes are made, he updates the values he keeps. This way the User knows to update himself whenever there is a change in the database.
 */
public interface Observer {
    void update();
}
