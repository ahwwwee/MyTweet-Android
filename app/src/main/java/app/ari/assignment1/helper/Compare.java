package app.ari.assignment1.helper;

import java.util.Comparator;

import app.ari.assignment1.models.Tweet;

/**
 * Created by ictskills on 05/01/17.
 */
public class Compare implements Comparator<Tweet> {
    @Override
    public int compare(Tweet t, Tweet t1) {
        return t.date.compareTo(t1.date);
    }
}
