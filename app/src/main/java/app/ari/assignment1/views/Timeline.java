package app.ari.assignment1.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import app.ari.assignment1.R;
import app.ari.assignment1.app.TweetApp;
import app.ari.assignment1.models.Tweet;
import app.ari.assignment1.models.TweetList;

import static app.ari.assignment1.helper.Helper.startActivityWithDataForResults;

/**
 * Created by ictskills on 03/10/16.
 */
public class Timeline extends AppCompatActivity implements AdapterView.OnItemClickListener{

    public TweetApp app;
    public ListView timeline;
    public TweetList tweetList;
    public TweetAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        app = (TweetApp) getApplication();

        timeline = (ListView) findViewById(R.id.timeline);
        tweetList = app.tweetList;

        timeline.setOnItemClickListener(this);
        adapter = new TweetAdapter(this, TweetList.tweets);
        timeline.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_tweeter, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.settings: Toast toast = Toast.makeText(Timeline.this, "Setting pressed", Toast.LENGTH_SHORT);
                toast.show();
                return true;
            case R.id.clear: TweetList.tweets.clear();
                startActivity(new Intent(this, Timeline.class));
                return true;
            case R.id.newTweet:Tweet tweet = new Tweet();
                TweetList.addTweet(tweet);
                startActivityWithDataForResults(this, Tweeter.class, "Tweet_ID", tweet.id);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Tweet tweet = adapter.getItem(i);
        Intent intent = new Intent(this, Tweeter.class);
        intent.putExtra("Tweet_ID", tweet.id);
        startActivity(intent);
    }
}

class TweetAdapter extends ArrayAdapter<Tweet>{
    private Context context;

    public TweetAdapter(Context context, ArrayList<Tweet> tweets){
        super(context, 0, tweets);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.tweet_list_item, null);
        }

        Tweet tweet = getItem(position);
        TextView listContent = (TextView) convertView.findViewById(R.id.listContent);
        listContent.setText(tweet.content);

        TextView listDate = (TextView) convertView.findViewById(R.id.listDate);
        listDate.setText(tweet.date + "");

        return convertView;
    }
}
