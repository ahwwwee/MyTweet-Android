package app.ari.assignment1;

import java.util.List;

import app.ari.assignment1.models.Tweet;
import app.ari.assignment1.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Dan & Ursula on 11/23/2016.
 */
public interface TweetService {

    @GET("/api/users")
    Call<List<User>> getAllUsers();

    @GET("/api/users/{id}")
    Call<User> getUser(@Path("id") String id);

    @POST("/api/users")
    Call<User> createUser(@Body User User);

    @GET("/api/tweets")
    Call<List<Tweet>> getAllTweets();

    @POST("/api/users/{id}/tweets")
    Call<Tweet> createTweet(@Path("id") String id, @Body Tweet tweet);

    @DELETE("/api/tweets/{id}")
    Call<Tweet> deleteOne(@Path("id") String id);

    @POST("/api/update/{id}")
    Call<Tweet> update(@Path("id") String id, @Body String content);
}
