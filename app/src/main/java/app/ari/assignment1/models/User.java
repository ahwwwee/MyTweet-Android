package app.ari.assignment1.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ari on 27/09/16.
 */
public class User {

    public String firstName;
    public String lastName;
    public String password;
    public String email;

    /**
     * constructor for a User
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     */
    public User(String firstName, String lastName, String email, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
