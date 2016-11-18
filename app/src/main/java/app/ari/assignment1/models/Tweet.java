package app.ari.assignment1.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Random;

/**
 * Created by Ari on 03/10/16.
 */
public class Tweet {

    public String _id;
    public String date;
    public String content;

    public static final String JSON_ID = "_id";
    public static final String JSON_CONTENT = "content";
    public static final String JSON_DATE = "date";

    /**
     * constructor for a new tweet
     */
    public Tweet(){
        date = new Date().toString();
    }

    /**
     * Overloaded constructor for a new tweet with a json object for persistence
     * @param json
     * @throws JSONException
     */
    public Tweet(JSONObject json) throws JSONException{
        _id = json.getString(JSON_ID);
        content = json.getString(JSON_CONTENT);
        date = json.getString(JSON_DATE);
    }

    /**
     * used to create jsonArray
     * @return
     * @throws JSONException
     */
    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_ID, _id);
        json.put(JSON_CONTENT, content);
        json.put(JSON_DATE, date);
        return json;
    }

}
