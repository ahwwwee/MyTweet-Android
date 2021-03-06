package app.ari.assignment1;

import java.util.ArrayList;
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
 * Created by Ari on 11/23/2016.
 * Interface to access protected api routes on the node application on Heroku
 */
public interface TweetService {

    @GET("/api/users/{id}")
    Call<User> getUser(@Path("id") String id);

    @GET("/api/users")
    Call<List<User>> getAllUsers();

    @GET("/api/tweets")
    Call<List<Tweet>> getAllTweets();

    @POST("/api/users/{id}/tweets")
    Call<Tweet> createTweet(@Path("id") String id, @Body Tweet tweet);

    @POST("/api/tweets/{id}")
    Call<User> deleteSome(@Path("id") String ID, @Body String[] id);

    @POST("/api/update/{id}")
    Call<Tweet> update(@Path("id") String id, @Body String content);

    @GET("/api/users/{id}/following")
    Call<List<Tweet>> getFollowing(@Path("id") String id);

    @POST("/api/users/{id}/follow")
    Call<User> follow(@Path("id") String _id, @Body String target);

    @POST("/api/users/{id}/unfollow")
    Call<User> unfollow(@Path("id") String _id, @Body String target);
}
