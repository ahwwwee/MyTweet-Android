package app.ari.assignment1.activities;

import java.util.ArrayList;
import java.util.List;

import app.ari.assignment1.R;
import app.ari.assignment1.app.TweetApp;
import app.ari.assignment1.models.TweetList;
import app.ari.assignment1.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ictskills on 29/11/16.
 */
public class UserList extends AppCompatActivity implements AdapterView.OnItemClickListener, Callback<List<User>> {
    public TweetApp app;
    public List<User> users;
    public ListView listView;
    public TweetList tweetList;
    public UserAdapter adapter;
    public TextView follow;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.activity_userlist);

        listView = (ListView) findViewById(R.id.userlist);

        app = TweetApp.getApp();
        tweetList = app.tweetList;
        users = tweetList.allUsers;
        Call<List<User>> call = (Call<List<User>>) app.tweetService.getAllUsers();
        call.enqueue(this);

        adapter = new UserAdapter(this, users);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Call<List<User>> call = (Call<List<User>>) app.tweetService.getAllUsers();
        call.enqueue(this);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
        List<User> another = new ArrayList<>();
        if (response.body() != null){
            for (User u : response.body()) {
                if (u._id.equals(app.currentUser._id)) {
                    app.currentUser = u;
                } else {
                    another.add(u);
                }
            }
        }
        app.tweetList.addAllUsers(another);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onFailure(Call<List<User>> call, Throwable t) {
        Toast toast = Toast.makeText(this, "Connection error, unable to retrieve users", Toast.LENGTH_SHORT);
        toast.show();
        app.tweetServiceAvailable = false;
        startActivity(new Intent(this, Timeline.class));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        final User user = adapter.getItem(position);
        String userId = user._id;
        if(app.currentUser.following != null) {
            if(app.currentUser.following.contains(userId)){
                Call<User> call = (Call<User>) app.tweetService.unfollow(app.currentUser._id, userId);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Toast toast = Toast.makeText(UserList.this, "unfollowed " + user.firstName+ " " + user.lastName, Toast.LENGTH_SHORT);
                        toast.show();
                        app.currentUser = response.body();
                        startActivity(new Intent(UserList.this, Timeline.class));
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast toast = Toast.makeText(UserList.this, "failed, try again soon ", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            } else if (!app.currentUser.following.contains(userId)){
                Call<User> call = (Call<User>) app.tweetService.follow(app.currentUser._id, userId);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Toast toast = Toast.makeText(UserList.this, "following " + user.firstName + " " + user.lastName, Toast.LENGTH_SHORT);
                        toast.show();
                        app.currentUser = response.body();
                        startActivity(new Intent(UserList.this, Timeline.class));
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast toast = Toast.makeText(UserList.this, "failed, try again soon ", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }

        } else {
            Call<User> call = (Call<User>) app.tweetService.follow(app.currentUser._id, userId);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Toast toast = Toast.makeText(UserList.this, "following " + user.firstName + " " + user.lastName, Toast.LENGTH_SHORT);
                    toast.show();
                    startActivity(new Intent(UserList.this, Timeline.class));
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast toast = Toast.makeText(UserList.this, "failed, try again soon ", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }
    }
}

class UserAdapter extends ArrayAdapter<User> {
    private Context context;

    public UserAdapter(Context context, List<User> users) {
        super(context, 0, users);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TweetApp app = TweetApp.getApp();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.user_list_item, null);
        }
        User user = getItem(position);
        TextView userName = (TextView) convertView.findViewById(R.id.userName);
        userName.setText(user.firstName + " " + user.lastName);

        TextView button = (TextView) convertView.findViewById(R.id.follow);
        if(app.currentUser.following != null) {
            if(app.currentUser.following.contains(user._id)){
                button.setText("unfollow");

            }
        }

        return convertView;
    }

}