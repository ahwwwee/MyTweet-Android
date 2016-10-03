package app.ari.assignment1.helper;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;

/**
 * Created by ictskills on 03/10/16.
 */
public class Helper {

    public static void navigateUp(Activity parent){
        Intent upIntent = NavUtils.getParentActivityIntent(parent);
        NavUtils.navigateUpTo(parent, upIntent);
    }
}
