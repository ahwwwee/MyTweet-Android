package app.ari.assignment1.models;

import java.nio.ByteBuffer;
import android.graphics.Bitmap;
import android.util.Base64;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Created by Ari on 03/10/16.
 */
public class Tweet {

    public String _id;
    public String content;
    public String date;
    public User tweeter;
    public Picture picture;
    public byte[] data;
    public String path;
    public Bitmap bmp;
    public String firstName;
    public String lastName;

    /**
     * constructor for a new tweet
     */
    public Tweet(){
    }
}
