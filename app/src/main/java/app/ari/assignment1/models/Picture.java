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
 * Created by ictskills on 22/12/16.
 */
public class Picture {
    public Object picData;
    public HashMap data;
    public String contentType;

    public Picture(){
    }
}
