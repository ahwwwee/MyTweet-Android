package app.ari.assignment1.app;

import java.util.List;

import app.ari.assignment1.models.Tweet;
import retrofit2.Call;

import app.ari.assignment1.models.User;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by ictskills on 15/11/16.
 */
public interface TweetService {

    @GET("/api/users")
    Call<List<User>> getAllUsers();

    @GET("/api/users/{id}")
    Call<User> getUser(@Path("id") String id);

    @POST("/api/users")
    Call<User> createUser(@Body User user);

    @DELETE("/api/users/{id}")
    Call<User> deleteUser(@Path("id") String id);

    @DELETE("/api/users")
    Call<User> deleteAllUsers();

    @GET("/api/tweets")
    Call<List<Tweet>> getAllTweets();

    @GET("/api/tweets/{id}")
    Call<Tweet> getTweet(@Path("id") Long id);

    @POST("/api/users/{id}/tweets")
    Call<Tweet> createTweet(@Path("id") Long id, @Body Tweet tweet);

    @DELETE("/api/tweets/{id}")
    Call<Tweet> deleteTweet(@Path("id") String id);

    @DELETE("/api/tweets")
    Call<Tweet> deleteAllTweets();

    @DELETE("/api/users/{id}/tweets")
    Call<Tweet> deleteUsersTweets(@Path("id") String id);
}
