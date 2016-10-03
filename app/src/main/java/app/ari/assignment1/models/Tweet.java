package app.ari.assignment1.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Random;

/**
 * Created by ictskills on 03/10/16.
 */
public class Tweet {

    public Long id;
    public Date date;
    public String content;

    public Tweet(String content){
        id = new Random().nextLong();
        this.content = content;
        date = new Date();
    }

}
