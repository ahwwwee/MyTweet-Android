package app.ari.assignment1.helper;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v4.app.NavUtils;

import java.io.InputStream;
import java.io.Serializable;

/**
 * Created by Ari on 03/10/16.
 */
public class Helper {

    /**
     * Start activity with an intent with extra data
     * @param parent
     * @param classname
     * @param extraID
     * @param extraData
     */
    public static void startActivityWithData (Activity parent, Class classname, String extraID, Serializable extraData) {
        Intent intent = new Intent(parent, classname);
        intent.putExtra(extraID, extraData);
        parent.startActivity(intent);
    }

    /**
     * to implement up button. Brings user back to parent activity
     * @param parent
     */
    public static void navigateUp(Activity parent){
        Intent upIntent = NavUtils.getParentActivityIntent(parent);
        NavUtils.navigateUpTo(parent, upIntent);
    }
}
