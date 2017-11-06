package edu.gatech.thethunderflyers.android.model;

import android.support.annotation.NonNull;
import com.google.gson.annotations.SerializedName;

class User implements Comparable<User>{
    @SerializedName("firstName")
    private final String firstName;

    @SerializedName("lastName")
    private final String lastName;

    @SerializedName("username")
    private final String userName;

    @SerializedName("password")
    private final String pass;

    @SerializedName("userType")
    private final UserMode um;


    /**
     * Constructor for User
     * @param firstName first name
     * @param lastName last name
     * @param userName username
     * @param pass password
     * @param um userMode (admin or user)
     */
    public User(String firstName, String lastName, String userName, String pass, UserMode um) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.pass = pass;
        this.um = um;
    }

    /**
     * Getter for first name
     * @return the first name
     */
    private String getFirstName() {
        return firstName;
    }

    /**
     * Getter for last name
     * @return the last name
     */
    private String getLastName() {
        return lastName;
    }

    /**
     * Getter for Username
     * @return the username
     */
    private String getUsername() {
        return userName;
    }

    /**
     * Getter for password
     * @return the password
     */
    private String getPassword() {
        return pass;
    }

    /**
     * Getter for the userMode
     * @return returns user or admin
     */
    private UserMode getUserMode() {
        return um;
    }

    @Override
    public int compareTo(@NonNull User user) {
        return userName.compareTo(user.getUsername());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        User u = (User) obj;
        return this.firstName.equals(u.getFirstName()) && this.lastName.equals(u.getLastName())
                && this.userName.equals(u.getUsername()) && this.pass.equals(u.getPassword())
                && (this.um == u.getUserMode());
    }
}
