package app.ari.assignment1.models;

import android.graphics.Bitmap;
import android.util.Base64;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Ari on 22/12/16.
 * class to represent the data being taken from the mongoose database
 */
public class Picture {
    public HashMap data;
    public String contentType;

    /**
     * constructor for a new picture
     */
    public Picture(){
    }
}
