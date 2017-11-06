package edu.gatech.thethunderflyers.android.model;

import android.support.annotation.NonNull;
import com.google.gson.annotations.SerializedName;

class User implements Comparable<User>{
    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("username")
    private String userName;

    @SerializedName("password")
    private String pass;

    @SerializedName("userType")
    private UserMode um;


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
     * Setter for first name
     * @param firstName new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Setter for last name
     * @param lastName new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Setter for Username
     * @param userName new username
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Setter for password
     * @param pass new password
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * Setter for user mode (admin or user)
     * @param um the new userMode
     */
    public void setUserMode(UserMode um) {
        this.um = um;
    }

    /**
     * Getter for first name
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter for last name
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter for Username
     * @return the username
     */
    public String getUsername() {
        return userName;
    }

    /**
     * Getter for password
     * @return the password
     */
    public String getPassword() {
        return pass;
    }

    /**
     * Getter for the userMode
     * @return returns user or admin
     */
    public UserMode getUserMode() {
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
                && this.um == u.getUserMode();
    }
}
