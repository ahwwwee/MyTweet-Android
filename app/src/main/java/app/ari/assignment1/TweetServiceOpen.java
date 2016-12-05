package app.ari.assignment1;

import java.util.List;

import app.ari.assignment1.models.Token;
import app.ari.assignment1.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by ictskills on 01/12/16.
 */
public interface TweetServiceOpen {

    @POST("/api/users/authenticate")
    Call<Token> auth(@Body User user);

    @GET("/api/users")
    Call<List<User>> getAllUsers();

    @POST("/api/users")
    Call<User> createUser(@Body User User);
}
