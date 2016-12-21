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
    public String firstName;
    public String lastName;
    public JSONObject picture;
    public byte[] data;
    public String path;

    /**
     * constructor for a new tweet
     */
    public Tweet(){
    }
}
