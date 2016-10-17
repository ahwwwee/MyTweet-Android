package app.ari.assignment1.helper;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

/**
 * Created by Ari on 04/10/16.
 */
public class ContactHelper {

    /**
     * extracts email from phones contacts
     * @param context
     * @param data
     * @return
     */
    public static String getEmail(Context context, Intent data){
        String email = "no email";
        Uri contactUri = data.getData();
        ContentResolver cr = context.getContentResolver();

        Cursor cur = cr.query(contactUri, null, null, null, null);

        if(cur.getCount() > 0 ) {
            try {
                cur.moveToFirst();
                String contactId = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor emails = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId, null, null);
                emails.moveToFirst();
                email = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                emails.close();
            } catch (Exception e) {
            }
        }
        return email;
    }

    /**
     * Get contacts name from contacts.
     * @param context
     * @param data
     * @return
     */
    public static String getContact(Context context, Intent data){
        String contact = "unable to find contact";
        Uri contactUri = data.getData();
        String[] queryFields = new String[] {ContactsContract.Contacts.DISPLAY_NAME};
        Cursor c = context.getContentResolver().query(contactUri, queryFields, null, null, null);
        if(c.getCount() == 0){
            c.close();
            return contact;
        }
        c.moveToFirst();
        contact = c.getString(0);
        c.close();

        return contact;
    }

    /**
     * Send the email using a 3rd party app on the users device
     * @param context
     * @param email
     * @param subject
     * @param body
     */
    public static void sendEmail(Context context, String email, String subject, String body){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);
        context.startActivity(Intent.createChooser(emailIntent, "Sending Email"));
    }
}
