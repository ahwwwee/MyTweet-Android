package app.ari.assignment1.views;

        import java.util.ArrayList;

        import app.ari.assignment1.helper.Helper;
        import app.ari.assignment1.R;
        import app.ari.assignment1.app.TweetApp;
        import app.ari.assignment1.models.TweetFragment;
        import app.ari.assignment1.models.TweetList;
        import app.ari.assignment1.models.Tweet;

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
        import android.widget.CheckBox;
        import android.annotation.SuppressLint;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v4.app.ListFragment;
        import android.widget.AdapterView.OnItemClickListener;
        import android.widget.Toast;

        import static app.ari.assignment1.helper.Helper.startActivityWithDataForResults;

/**
 * Created by ictskills on 10/10/16.
 */
public class TimelineFragment extends ListFragment implements OnItemClickListener {
        private ArrayList<Tweet> tweets;
        private TweetList tweetList;
        private TweetAdapter adapter;
        TweetApp app;

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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
            View v = super.onCreateView(inflater, parent, savedInstanceState);
            return v;
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            Tweet tweet = ((TweetAdapter) getListAdapter()).getItem(position);
            Intent i = new Intent(getActivity(), Tweeter.class);
            i.putExtra(TweetFragment.EXTRA_TWEET_ID, tweet.id);
            startActivityForResult(i, 0);
        }

        @Override
        public void onResume() {
            super.onResume();
            ((TweetAdapter) getListAdapter()).notifyDataSetChanged();
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            super.onCreateOptionsMenu(menu, inflater);
            inflater.inflate(R.menu.menu_tweeter, menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch(item.getItemId()){
                case R.id.settings: Toast toast = Toast.makeText(getActivity(), "Setting pressed", Toast.LENGTH_SHORT);
                    toast.show();
                    return true;
                case R.id.clear: TweetList.tweets.clear();
                    startActivity(new Intent(getActivity(), Timeline.class));
                    return true;
                case R.id.newTweet:Tweet tweet = new Tweet();
                    TweetList.addTweet(tweet);
                    startActivityWithDataForResults(getActivity(), Tweeter.class, "Tweet_ID", tweet.id);
                    return true;
            }

            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Tweet tweet = adapter.getItem(position);
            Helper.startActivityWithData(getActivity(), Tweeter.class, "Tweet_ID", tweet.id);
        }

        class TweetAdapter extends ArrayAdapter<Tweet> {
            private Context context;

            public TweetAdapter(Context context, ArrayList<Tweet> tweets){
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
    }
