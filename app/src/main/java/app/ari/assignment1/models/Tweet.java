package app.ari.assignment1.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Random;
import android.graphics.Bitmap;
import android.util.Base64;


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
    public Bitmap picture;
    public String photo;

    /**
     * constructor for a new tweet
     */
    public Tweet(){
    }
}
