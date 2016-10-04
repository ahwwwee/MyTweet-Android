package app.ari.assignment1.helper;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;

import java.io.Serializable;

/**
 * Created by ictskills on 03/10/16.
 */
public class Helper {

    public static void startActivityWithDataForResults(Activity parent, Class name, String extraID, Serializable extraData){
        Intent intent = new Intent(parent, name);
        intent.putExtra(extraID, extraData);
        parent.startActivity(intent);
    }

    public static void navigateUp(Activity parent){
        Intent upIntent = NavUtils.getParentActivityIntent(parent);
        NavUtils.navigateUpTo(parent, upIntent);
    }
}
