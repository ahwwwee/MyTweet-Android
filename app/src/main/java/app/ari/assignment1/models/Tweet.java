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
    public String date;
    public String content;

    public static final String JSON_ID = "id";
    public static final String JSON_CONTENT = "content";
    public static final String JSON_DATE = "date";

    public Tweet(){
        //id = unsignedLong();
        id = Math.abs(new Random().nextLong());
        date = new Date().toString();
    }

    public Tweet(JSONObject json) throws JSONException{
        id = json.getLong(JSON_ID);
        content = json.getString(JSON_CONTENT);
        date = json.getString(JSON_DATE);
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_ID, Long.toString(id));
        json.put(JSON_CONTENT, content);
        json.put(JSON_DATE, date);
        return json;
    }

    private Long unsignedLong() {
        long rndVal = 0;
        do {
            rndVal = new Random().nextLong();
        } while (rndVal <= 0);
        return rndVal;
    }

}
