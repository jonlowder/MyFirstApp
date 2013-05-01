/**
 * 
 */
package com.example.myfirstapp.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.PhoneLookup;

/**
 * @author jon.lowder
 *
 */
public class ContactUtils
{
    /**
     * A static method that checks if the given {@code phoneNumber} is associated
     * with a contact. If so, true is returned. If the {@code phoneNumber} is not
     * associated a contact or if it is null, false is returned.
     * @return true if the given {@code phoneNumber} is found in the contact
     * list; otherwise, false is returned
     */
    public static boolean isInContacts(Context context, String phoneNumber)
    {
        boolean result = false;
        if(null == phoneNumber)
        {
            return result;
        }
        
        // Create the lookup Uri
        Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        // Create a String array for the projection
        String[] projection = {PhoneLookup._ID,
                               PhoneLookup.NUMBER,
                               PhoneLookup.DISPLAY_NAME};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if(null != cursor)
        {
            result = cursor.moveToFirst();
        }
        return result;
    } // end method isInContact
} // end class 
