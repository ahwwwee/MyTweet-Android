package app.ari.assignment1.activities;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static app.ari.assignment1.helper.FileIOHelper.writeBitmap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

import app.ari.assignment1.R;
import app.ari.assignment1.app.TweetApp;
import app.ari.assignment1.models.Tweet;

/**
 * Created by ictskills on 14/12/16.
 */
public class CameraActivity extends AppCompatActivity implements View.OnClickListener {

    private Button savePhoto;
    private Button takePhoto;
    private ImageView Image;
    private static  final int CAMERA_RESULT = 5;
    public static final String EXTRA_PHOTO_FILENAME = "app.ari.photo.filename";
    private Bitmap Photo;
    private TweetApp app;
    public String path;
    public static CameraActivity camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        app = TweetApp.getApp();

        Image = (ImageView) findViewById(R.id.Image);
        savePhoto = (Button)findViewById(R.id.savePhoto);
        takePhoto = (Button)findViewById(R.id.takePhoto);
        savePhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
    }

    public CameraActivity getIt(){
        camera = this;
        return camera;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.takePhoto     : onTakePhotoClicked(view);
                break;
            case R.id.savePhoto     :
                try {
                    onPictureTaken(Photo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void onTakePhotoClicked(View v)
    {
        // Check for presence of device camera. If not present advise user and quit method.
        PackageManager pm = getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(this, "Camera app not present on this device", Toast.LENGTH_SHORT).show();
            return;
        }
        // The device has a camera app ... so use it.
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent,CAMERA_RESULT);
        savePhoto.setEnabled(true);
    }

    private void onPictureTaken(Bitmap data) throws Exception {
        String filename = UUID.randomUUID().toString() + ".png";
        if (writeBitmap(this, filename, data) == true) {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_PHOTO_FILENAME, filename);
            setResult(Activity.RESULT_OK, intent);
            path = filename;
            setTweetPhoto(data);
        }
        else {
            setResult(Activity.RESULT_CANCELED);
        }
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case CameraActivity.CAMERA_RESULT :
                if(data != null) {
                    processImage(data);
                }
                else {
                    Toast.makeText(this, "Camera failure: check simulated camera present emulator advanced settings",
                            Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void processImage(Intent data) {
        Photo = (Bitmap) data.getExtras().get("data");
        if(Photo == null)
        {
            Toast.makeText(this, "Attempt to take photo did not succeed", Toast.LENGTH_SHORT).show();
        }
        Image.setImageBitmap(Photo);
    }

    public void setTweetPhoto(Bitmap data) throws JSONException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        data.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        JSONArray array = new JSONArray();
        array.put(byteArray);
        /*int i = 0;
        Byte[] byteByte = new Byte[byteArray.length];
        for (byte b : byteArray){
            byteByte[i++] = b;
        }
        */
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        JSONObject json = new JSONObject();
        json.put("data", byteArray);
        app.currentTweet.picture = json;
    }

    public Tweet nodeImageConvert(Tweet tweet){
        byte[] array = Base64.decode(tweet.picture.toString(), Base64.DEFAULT);
        Bitmap decoded = BitmapFactory.decodeByteArray(array, 0, array.length);
        String filename = UUID.randomUUID().toString() + ".png";
        if (writeBitmap(this, filename, decoded) == true) {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_PHOTO_FILENAME, filename);
            setResult(Activity.RESULT_OK, intent);
            tweet.path = filename;
        }
        else {
            setResult(Activity.RESULT_CANCELED);
        }
        return tweet;
    }

}
