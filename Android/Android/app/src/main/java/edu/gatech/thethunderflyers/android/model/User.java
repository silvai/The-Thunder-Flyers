package edu.gatech.thethunderflyers.android.model;

import android.support.annotation.NonNull;
import com.google.gson.annotations.SerializedName;

public class User implements Comparable<User>{
    @SerializedName("firstName")
    private String fname;

    @SerializedName("lastName")
    private String lname;

    @SerializedName("username")
    private String usern;

    @SerializedName("password")
    private String pass;

    @SerializedName("userType")
    private UserMode um;

    /**
     * Constructor for User
     * @param fname first name
     * @param lname last name
     * @param usern username
     * @param pass password
     * @param um usermode (admin or user)
     */
    public User(String fname, String lname, String usern, String pass, UserMode um) {
        this.fname = fname;
        this.lname = lname;
        this.usern = usern;
        this.pass = pass;
        this.um = um;
    }

    /**
     * Setter for first name
     * @param fname new first name
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * Setter for last name
     * @param lname new last name
     */
    public void setLname(String lname) {
        this.lname = lname;
    }

    /**
     * Setter for Username
     * @param usern new username
     */
    public void setUsern(String usern) {
        this.usern = usern;
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
     * @param um the new usermode
     */
    public void setUserMode(UserMode um) {
        this.um = um;
    }

    /**
     * Getter for first name
     * @return the first name
     */
    public String getFname() {
        return fname;
    }

    /**
     * Getter for last name
     * @return the last name
     */
    public String getLname() {
        return lname;
    }

    /**
     * Getter for Username
     * @return the username
     */
    public String getUsername() {
        return usern;
    }

    /**
     * Getter for password
     * @return the password
     */
    public String getPassword() {
        return pass;
    }

    /**
     * Getter for the usermode
     * @return returns user or admin
     */
    public UserMode getUserMode() {
        return um;
    }

    @Override
    public int compareTo(@NonNull User user) {
        return usern.compareTo(user.getUsername());
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
        return this.fname.equals(u.getFname()) && this.lname.equals(u.getLname())
                && this.usern.equals(u.getUsername()) && this.pass.equals(u.getPassword())
                && this.um == u.getUserMode();
    }
}
