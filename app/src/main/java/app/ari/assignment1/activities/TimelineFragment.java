package app.ari.assignment1.activities;

        import java.lang.reflect.Array;
        import java.util.ArrayList;
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

/**
 * Created by Ari on 10/10/16.
 */
public class TimelineFragment extends ListFragment implements OnItemClickListener, AbsListView.MultiChoiceModeListener {
    private List<Tweet> tweets;
    private TextView welcome;
    private TweetAdapter adapter;
    TweetApp app;
    public ListView timeline;
    private User user;
    private TweetList tweetList;

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
                for(Tweet t : tweetList.tweets){
                    tweetList.deleteTweet(t);
                }
                startActivity(new Intent(getActivity(), Timeline.class));
                return true;
            case R.id.newTweet:
                Tweet tweet = new Tweet();
                tweet._id = "123";
                tweetList.addTweet(tweet);
                Intent i = new Intent(getActivity(), TweeterPager.class);
                i.putExtra(TweeterFragment.EXTRA_TWEET_ID, tweet._id);
                startActivityForResult(i, 0);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Opens new TweeterPager,then TweeterFragment class with clicked on tweet
     * @param parent
     * @param view
     * @param position
     * @param id Tweet tweet = adapter.getItem(position);
        Helper.startActivityWithData(getActivity(), TweeterPager.class, TweeterFragment.EXTRA_TWEET_ID, tweet.id);
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Tweet tweet = adapter.getItem(position);
        Helper.startActivityWithData(getActivity(), TweeterPager.class, TweeterFragment.EXTRA_TWEET_ID, tweet._id);
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
}

       /**
        * a helper class to populate list view with arrays·
        */
        class TweetAdapter extends ArrayAdapter<Tweet> {
            private Context context;

            public TweetAdapter(Context context, List<Tweet> tweets) {
                super(context, 0, tweets);
                this.context = context;
            }

            @SuppressLint("InflateParams")
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                if (convertView == null) {
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
