package app.ari.assignment1.activities;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import static app.ari.assignment1.helper.FileIOHelper.writeBitmap;

import java.util.UUID;

import app.ari.assignment1.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Image = (ImageView) findViewById(R.id.Image);
        savePhoto = (Button)findViewById(R.id.savePhoto);
        takePhoto = (Button)findViewById(R.id.takePhoto);
        savePhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
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
            case R.id.savePhoto     : onPictureTaken(Photo);
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

    private void onPictureTaken(Bitmap data) {
        String filename = UUID.randomUUID().toString() + ".png";
        if (writeBitmap(this, filename, data) == true) {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_PHOTO_FILENAME, filename);
            setResult(Activity.RESULT_OK, intent);
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

    private void processImage(Intent data)
    {
        Photo = (Bitmap) data.getExtras().get("data");
        if(Photo == null)
        {
            Toast.makeText(this, "Attempt to take photo did not succeed", Toast.LENGTH_SHORT).show();
        }
        Image.setImageBitmap(Photo);
    }

}
