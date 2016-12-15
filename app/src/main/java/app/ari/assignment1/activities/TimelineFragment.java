package app.ari.assignment1.activities;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import app.ari.assignment1.activities.settings.SettingsActivity;
import app.ari.assignment1.helper.Helper;
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
import android.widget.AdapterView;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.AdapterView.OnItemClickListener;
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
    private User user;
    private TweetList tweetList;
    public static final String BROADCAST_ACTION = "app.ari.assignment1.activities.TimelineFragment";
    private IntentFilter intentFilter;
    protected static TimelineFragment timeFrag;



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
        tweetList = app.tweetList;
        tweets = tweetList.tweets;
        if (app.tweetServiceAvailable) {
            Call<List<Tweet>> call = (Call<List<Tweet>>) app.tweetService.getFollowing(app.currentUser._id);
            call.enqueue(this);
        }
        registerBroadcastReceiver();
        timeFrag = this;
        adapter = new TweetAdapter(getActivity(), tweets);
        setListAdapter(adapter);
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

    private void registerBroadcastReceiver()
    {
        intentFilter = new IntentFilter(BROADCAST_ACTION);
        ResponseReceiver responseReceiver = new ResponseReceiver();
        // Registers the ResponseReceiver and its intent filters
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(responseReceiver, intentFilter);
    }

    public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {
        tweetList.refreshTweets(response.body());
        refresh();
        app.tweetServiceAvailable = true;
    }

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
        super.onResume();
        refresh();
        ((TweetAdapter) getListAdapter()).notifyDataSetChanged();
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
                    tweet._id = "123";
                    tweetList.addTweet(tweet);
                    Intent i = new Intent(getActivity(), TweeterPager.class);
                    i.putExtra(TweeterFragment.EXTRA_TWEET_ID, tweet._id);
                    startActivityForResult(i, 0);
                } else {
                    Toast toast = Toast.makeText(getActivity(), "Cannot make a tweet while offline", Toast.LENGTH_SHORT);
                    toast.show();
                }
                return true;
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
        refresh();
    }

    public void refresh(){
        adapter.notifyDataSetChanged();
    }

    public static TimelineFragment getThis(){
        return timeFrag;
    }

    //Broadcast receiver for receiving status updates from the IntentService
    private class ResponseReceiver extends BroadcastReceiver
    {
        //private void ResponseReceiver() {}
        // Called when the BroadcastReceiver gets an Intent it's registered to receive
        @Override
        public void onReceive(Context context, Intent intent)
        {
            //refreshDonationList();
            adapter.tweets = app.tweetList.tweets;
            refresh();
        }
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
