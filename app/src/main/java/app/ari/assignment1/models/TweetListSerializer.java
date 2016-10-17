package app.ari.assignment1.models;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by Ari on 04/10/16.
 */
public class TweetListSerializer {

    private Context tContext;
    private String tFilename;

    /**
     * constructor for tweetListSerializer
     * @param c
     * @param f
     */
    public TweetListSerializer(Context c, String f){
        tContext = c;
        tFilename = f;
    }

    /**
     * saves tweets, allowing them to persist after closing the app.
     * @param tweets
     * @throws JSONException
     * @throws IOException
     */
    public void saveTweet(ArrayList<Tweet> tweets) throws JSONException, IOException{
        JSONArray array = new JSONArray();
        for(Tweet t : tweets){
            array.put(t.toJSON());
        }

        Writer writer = null;
        try{
            OutputStream out = tContext.openFileOutput(tFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        }
        finally {
            if(writer != null){
                writer.close();
            }
        }
    }

    /**
     * reloads the tweets when the app is reopened
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public ArrayList<Tweet> loadTweets() throws JSONException, IOException{
        ArrayList<Tweet> tweets = new ArrayList<>();
        BufferedReader reader = null;
        try{
            InputStream in = tContext.openFileInput(tFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null){
                jsonString.append(line);
            }
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            for(int i = 0; i < array.length(); i++){
                tweets.add(new Tweet(array.getJSONObject(i)));
            }
        }
        catch (FileNotFoundException e){

        }
        finally {
            if(reader != null){
                reader.close();
            }
        }
        return tweets;
    }
}
