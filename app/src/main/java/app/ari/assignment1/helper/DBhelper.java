package app.ari.assignment1.helper;

/**
 * Created by Ari on 24/10/16.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.UUID;

import app.ari.assignment1.models.Tweet;

public class DBhelper extends SQLiteOpenHelper {
    static final String TAG = "DbHelper";
    static final String TWEET_DATABASE_NAME = "Tweets.db";
    static final int DATABASE_VERSION = 1;
    static final String TABLE_TWEET = "tableTweets";
    static final String PRIMARY_KEY = "id";
    static final String CONTENT = "content";

    static final String USER_DATABASE_NAME = "User.db";
    static final String TABLE_USER = "tableUsers";
    static final String FIRSTNAME = "firstName";
    static final String LASTNAME = "lastName";
    static final String EMAIL = "email";
    static final String PASSWORD = "password";



    Context context;

    public DBhelper(Context context)
    {
        super(context, TWEET_DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String createTable =
                "CREATE TABLE tableTweets " +
                        "(id text primary key, " +
                        "content text)";

        db.execSQL(createTable);
        Log.d(TAG, "DbHelper.onCreated: " + createTable);
    }

    /**
     * @param tweet Reference to Tweet object to be added to database
     */
    public void addTweet(Tweet tweet)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRIMARY_KEY, tweet._id.toString());
        values.put(CONTENT, tweet.content);
        // Insert record
        db.insert(TABLE_TWEET, null, values);
        db.close();
    }

    public Tweet selectTweet(Long id) {
        Tweet tweet;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            tweet = new Tweet();

            cursor = db.rawQuery("SELECT * FROM tableTweets WHERE id = ?", new String[]{id.toString() + ""});

            if (cursor.getCount() > 0) {
                int columnIndex = 0;
                cursor.moveToFirst();
                tweet._id = cursor.getLong(columnIndex++);
                tweet.content = cursor.getString(columnIndex++);
            }
        } finally {
            cursor.close();
        }
        return tweet;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists " + TABLE_TWEET);
        Log.d(TAG, "onUpdated");
        onCreate(db);
    }
}
