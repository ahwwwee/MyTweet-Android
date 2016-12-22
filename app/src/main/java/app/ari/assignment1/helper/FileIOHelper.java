package app.ari.assignment1.helper;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import android.graphics.Bitmap;

/**
 * Created by Ari on 14/12/16.
 */
public class FileIOHelper {

    /**
     * checks that the photo can be accessed
     * @param context
     * @param filename
     * @param data
     * @return
     */
    public static boolean write(Context context, String filename, byte[] data)
    {
        FileOutputStream os = null;
        boolean success = true;
        try
        {
            os = context.openFileOutput(filename, Context.MODE_PRIVATE);
            os.write(data);
        }
        catch (Exception e)
        {
            success = false;
        }
        finally
        {
            try
            {
                if (os != null)
                    os.close();
            }
            catch (Exception e)
            {
                success = false;
            }
        }
        return success;
    }

    /**
     * returns a byte array for the bitmap that is passed in
     */
    public static byte[] byteArray(Bitmap bmp)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    /**
     * checks that the photo is accessable
     * @param context
     * @param filename
     * @param bmp
     * @return
     */
    public static boolean writeBitmap(Context context, String filename, Bitmap bmp)
    {
        return write(context, filename, byteArray(bmp));
    }
}
