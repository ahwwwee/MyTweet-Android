package app.ari.assignment1;

import java.util.List;

import app.ari.assignment1.models.Token;
import app.ari.assignment1.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Ari on 01/12/16.
 * Interface to access the api on the node application on Heroku
 */
public interface TweetServiceOpen {

    @POST("/api/users/aurAuthenticate")
    Call<Token> auth(@Body User user);

    @POST("/api/users")
    Call<User> createUser(@Body User user);

    @POST("/api/users/{email}")
    Call<User> findOne(@Path("email") String email);
}
