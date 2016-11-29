package app.ari.assignment1.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.List;

/**
 * Created by Ari on 27/09/16.
 */
public class User {

    public String _id;
    public String firstName;
    public String lastName;
    public String password;
    public String email;
    public List<String> following;
    public List<String> followedBy;

    /**
     * constructor for a User
     */
    public User(){
    }
}
