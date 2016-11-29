package app.ari.assignment1.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import app.ari.assignment1.R;
import app.ari.assignment1.app.TweetApp;
import app.ari.assignment1.models.Tweet;
import app.ari.assignment1.models.TweetList;
import app.ari.assignment1.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ictskills on 29/11/16.
 */
public class UserList extends AppCompatActivity implements AdapterView.OnItemClickListener, Callback<List<User>> {
    public TweetList tweetList;
    public TweetApp app;
    public List<User> users;
    public UserAdapter adapter;
    public ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.activity_userlist);

        listView = (ListView) findViewById(R.id.userlist);

        app = TweetApp.getApp();
        users = tweetList.users;
        Call<List<User>> call = (Call<List<User>>) app.tweetService.getAllUsers();
        call.enqueue(this);
        adapter = new UserAdapter(this, users);
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        //put input onto page to pass in UsersId for follow method
        //Call<User> call = (Call<User>) app.tweetService.follow(userId);
    }

    @Override
    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
        /*for(User u: response.body()){
            tweetList.addUser(u);
        }*/
        Toast toast = Toast.makeText(this, "Successfully retrieved users", Toast.LENGTH_SHORT);
        toast.show();
        app.tweetServiceAvailable = true;
    }

    @Override
    public void onFailure(Call<List<User>> call, Throwable t) {
        Toast toast = Toast.makeText(this, "Connection error, unable to retrieve users", Toast.LENGTH_SHORT);
        toast.show();
        app.tweetServiceAvailable = false;
    }
}

class UserAdapter extends ArrayAdapter<User> {
    private Context context;

    public UserAdapter(Context context, List<User> users) {
        super(context, 0, users);
        this.context = context;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.user_list_item, null);
        }
        User user = getItem(position);
        TextView userName = (TextView) convertView.findViewById(R.id.userName);
        userName.setText(user.firstName + " " + user.lastName);

        return convertView;
    }
}
