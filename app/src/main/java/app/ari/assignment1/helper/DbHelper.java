package app.ari.assignment1.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import app.ari.assignment1.models.Tweet;

/**
 * Created by ictskills on 24/11/16.
 */
public class DbHelper extends SQLiteOpenHelper
{
    static final String TAG = "DbHelper";
    static final String DATABASE_NAME = "myTweet.db";
    static final int DATABASE_VERSION = 1;
    static final String TABLE_TWEETS = "tableTweets";
    static final String TABLE_USERS = "tableUsers";

    static final String PRIMARY_KEY = "_id";
    static final String CONTENT = "content";
    static final String DATE = "date";
    static final String FIRSTNAME = "firstName";
    static final String LASTNAME = "lastName";
    static final String PASSWORD = "password";
    static final String EMAIL = "email";

    Context context;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tweetTable =
                "CREATE TABLE tableTweets " +
                        "(_id text primary key, " +
                        "content text," +
                        "date text)";
        String userTable =
                "CREATE TABLE tableUsers " +
                        "(_id text primary key, " +
                        "firstName text," +
                        "lastName text," +
                        "email text," +
                        "password text)";

        db.execSQL(tweetTable);
        db.execSQL(userTable);
        Log.d(TAG, "DbHelper.onCreated: " + tweetTable + userTable);
    }

    /**
     * @param tweet Reference to tweet object to be added to database
     */
    public void addTweet(Tweet tweet) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRIMARY_KEY, tweet._id);
        values.put(CONTENT, tweet.content);
        values.put(DATE, tweet.date);

        // Insert record
        db.insert(TABLE_TWEETS, null, values);
        db.close();
    }

    /**
     * Persist a list of tweets
     *
     * @param tweets The list of Tweet object to be saved to database.
     */
    public void addTweets(List<Tweet> tweets) {
        for (Tweet t : tweets) {
            addTweet(t);
        }
    }

    public Tweet selectTweets(String id) {
        Tweet tweet;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            tweet = new Tweet();

            cursor = db.rawQuery("SELECT * FROM tableTweets WHERE _id = ?", new String[]{id});

            if (cursor.getCount() > 0) {
                int columnIndex = 0;
                cursor.moveToFirst();
                tweet._id = cursor.getString(columnIndex++);
                tweet.date = cursor.getString(columnIndex++);
                tweet.content = cursor.getString(columnIndex++);
            }
        }
        finally {
            cursor.close();
        }
        return tweet;
    }

    public void deleteTweet(Tweet tweet) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete("tableTweets", "_id" + "=?", new String[]{tweet._id});
        }
        catch (Exception e) {
            Log.d(TAG, "delete tweet failure: " + e.getMessage());
        }
    }

    /**
     * Query database and select entire tableResidences.
     *
     * @return A list of Tweet object records
     */
    public List<Tweet> selectTweets() {
        List<Tweet> tweets = new ArrayList<Tweet>();
        String query = "SELECT * FROM " + "tableTweets";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            int columnIndex = 0;
            do {
                Tweet tweet = new Tweet();
                tweet._id = cursor.getString(columnIndex++);
                tweet.content = cursor.getString(columnIndex++);
                tweet.date = cursor.getString(columnIndex++);
                columnIndex = 0;

                tweets.add(tweet);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tweets;
    }

    /**
     * Delete all records
     */
    public void deleteTweets() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.execSQL("delete from tableTweets");
        }
        catch (Exception e) {
            Log.d(TAG, "delete tweets failure: " + e.getMessage());
        }
    }

    /**
     * Queries the database for the number of records.
     *
     * @return The number of records in the dataabase.
     */
    public long getCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long numberRecords = DatabaseUtils.queryNumEntries(db, TABLE_TWEETS);
        db.close();
        return numberRecords;
    }

    /**
     * Update an existing Residence record.
     * All fields except record id updated.
     *
     * @param tweet The Tweet record being updated.
     */
    public void updateTweet(Tweet tweet) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(PRIMARY_KEY, tweet._id);
            values.put(CONTENT, tweet.content);
            values.put(DATE, tweet.date);
            db.update("tableTweets", values, "_id" + "=?", new String[]{tweet._id});
        }
        catch (Exception e) {
            Log.d(TAG, "update tweets failure: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_TWEETS);
        Log.d(TAG, "onUpdated");
        onCreate(db);
    }


}