package app.ari.assignment1.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Random;
import android.graphics.Bitmap;


/**
 * Created by Ari on 03/10/16.
 */
public class Tweet {

    public String _id;
    public String content;
    public String date;
    public User tweeter;
    public String firstName;
    public String lastName;
    public String picture;
    public Bitmap photo;

    /**
     * constructor for a new tweet
     */
    public Tweet(){
    }

    public void setPhoto(Bitmap photo){
        this.photo = photo;
    }


}
