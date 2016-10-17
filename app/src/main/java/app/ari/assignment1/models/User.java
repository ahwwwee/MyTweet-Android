package app.ari.assignment1.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ictskills on 27/09/16.
 */
public class User {

    public String firstName;
    public String lastName;
    public String password;
    public String email;

    public User(String firstName, String lastName, String email, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
