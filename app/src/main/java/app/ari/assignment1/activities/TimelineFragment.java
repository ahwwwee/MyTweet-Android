package app.ari.assignment1.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;

import app.ari.assignment1.activities.settings.SettingsActivity;
import app.ari.assignment1.R;
import app.ari.assignment1.app.TweetApp;
import app.ari.assignment1.models.TweetList;
import app.ari.assignment1.models.Tweet;
import app.ari.assignment1.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.ActionMode;
import android.widget.AbsListView;
import android.widget.ListView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.view.ViewGroup;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.Toast;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.content.BroadcastReceiver;

/**
 * Created by Ari on 10/10/16.
 */
public class TimelineFragment extends ListFragment implements AbsListView.MultiChoiceModeListener, Callback<List<Tweet>> {
    private List<Tweet> tweets;
    private TweetAdapter adapter;
    TweetApp app;
    public ListView timeline;
    private TweetList tweetList;
    protected static TimelineFragment timeFrag;
    public int timer;
    private Timer countdown;
    /**
     * Loads these when the activity is opened
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.app_name);

        app = TweetApp.getApp();
        if(app.currentUser.timer > 0) {
            timer = app.currentUser.timer;
        } else {
            app.currentUser.timer = 60000;
            app.tweetList.addUser(app.currentUser);
            timer = 60000;
        }
        countdown = new Timer();
        countdown.schedule(new TimerTask() {
            @Override
            public void run() {
                getTweets();
            }
        }, 0, timer);

        tweetList = app.tweetList;
        tweets = tweetList.tweets;
        timeFrag = this;
        adapter = new TweetAdapter(getActivity(), tweets);
        setListAdapter(adapter);
    }

    /**
     * this method is used whenever this activity is exited.
     * it makes sure that the activity is no longer on the stack
     * its needed to stop the timer when the user is nolonger on the page
     */
    @Override
    public void onStop(){
        getActivity().finish();
        super.onStop();
    }

    /**
     * to be called when refresh is needed
     */
    public void getTweets(){
        Call<List<Tweet>> call = (Call<List<Tweet>>) app.tweetService.getFollowing(app.currentUser._id);
        call.enqueue(this);
    }

    /**
     * Loads these when the activity is opened
     * loads the items on the page so that they are readable in other methods.
     * needs to be used when fragments are implemented
     * @param savedInstanceState
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);

        timeline = (ListView) v.findViewById(android.R.id.list);
        timeline.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        timeline.setMultiChoiceModeListener(this);
        return v;
    }

    /**
     * response from successful call with list of tweets.
     * @param call
     * @param response
     */
    public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {
        List<Tweet> array = new ArrayList<>();
        for(Tweet t : response.body()){
            if(t.picture != null){
                t = app.nodeImageConvert(t);
                array.add(t);
            }else{
                array.add(t);
            }
        }
        tweetList.refreshTweets(array);
        adapter.notifyDataSetChanged();
        app.tweetServiceAvailable = true;
    }

    /**
     * responce from an unsuccessful call for a list of tweets
     * @param call
     * @param t
     */
    @Override
    public void onFailure(Call<List<Tweet>> call, Throwable t) {
        Toast toast = Toast.makeText(getActivity(), "Connection error, unable to retrieve tweets", Toast.LENGTH_SHORT);
        toast.show();
        app.tweetServiceAvailable = false;
    }

    /**
     * Listening to when an individual item on the ListView has been clicked, Opens up a TweeterPager, and then a TweeterFragment page to view the tweet
     * @param l
     * @param v
     * @param position
     * @param id
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Tweet tweet = ((TweetAdapter) getListAdapter()).getItem(position);
        Intent i = new Intent(getActivity(), TweeterPager.class);
        i.putExtra(TweeterFragment.EXTRA_TWEET_ID, tweet._id);
        startActivityForResult(i, 0);
    }

    /**
     * when this activity is reopened this method is invoked refreshes list.
     */
    @Override
    public void onResume() {
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    /**
     * populates menu bar on this activity
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_timeline, menu);
    }

    /**
     * For menu Items, If the any options in the menu is selected or the + button clicked.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            case R.id.clear:
                tweetList.deleteTweets();
                startActivity(new Intent(getActivity(), Timeline.class));
                return true;
            case R.id.users:
                startActivity(new Intent(getActivity(), UserList.class));
                return true;
            case R.id.newTweet:
                if (app.tweetServiceAvailable == true) {
                    Tweet tweet = new Tweet();
                    app.currentTweet = tweet;
                    startActivity(new Intent(getActivity(), TweetActivity.class));
                } else {
                    Toast toast = Toast.makeText(getActivity(), "Cannot make a tweet while offline", Toast.LENGTH_SHORT);
                    toast.show();
                }
                return true;
            case R.id.refresh:
                if (app.tweetServiceAvailable){
                    getTweets();
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {

    }

    /**
     * for long hold tweet delete. points to appropriate menu.
     * @param actionMode
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.tweet_list_context, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    /**
     * action listener for delete tweet.
     * @param actionMode
     * @param menuItem
     * @return
     */
    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.delete_tweet:
                deleteTweet(actionMode);
                return true;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {

    }

    /**
     * method to delete tweet from arrays
     * @param actionMode
     */
    public void deleteTweet(ActionMode actionMode) {
        for (int i = adapter.getCount() - 1; i >= 0; i--) {
            if (timeline.isItemChecked(i)) {
                tweetList.deleteTweet(adapter.getItem(i));
            }
        }
        actionMode.finish();
        adapter.notifyDataSetChanged();
    }

    /**
     * to make methods from this class accessable elsewhere from a static context
     * @return
     */
    public static TimelineFragment getThis(){
        return timeFrag;
    }

    /**
     * refreshes tweetlist, needed to be used when tweets are updated and user is on this page
     */
    public void refresh(){
        adapter.notifyDataSetChanged();
    }

}

/**
 * a helper class to populate list view with arrays·
 */
class TweetAdapter extends ArrayAdapter<Tweet> {
    private Context context;
    List<Tweet> tweets;

    public TweetAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
        this.context = context;
        this.tweets = tweets;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.tweet_list_item, null);
        }
        Tweet tweet = getItem(position);
        TextView tweeter = (TextView) convertView.findViewById(R.id.tweeter);
        if(tweet.tweeter != null) {
            tweeter.setText(tweet.tweeter.firstName + " " + tweet.tweeter.lastName + " says...");
        } else {
            tweeter.setText(tweet.firstName + " " + tweet.lastName + " says...");
        }

        TextView listContent = (TextView) convertView.findViewById(R.id.listContent);
        listContent.setText(tweet.content);

        TextView listDate = (TextView) convertView.findViewById(R.id.listDate);
        listDate.setText(tweet.date + "");
        return convertView;
    }
}
