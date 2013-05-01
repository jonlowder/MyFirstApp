/**
 * 
 */
package com.example.myfirstapp.utils;

import java.util.ArrayList;

import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

/**
 * @author jon.lowder
 *
 */
public class SMSSender
{
    private static final String TAG = "com.example.myfirstapp.SMSSender";
    
    public static void sendSMSMessage(Context context, String destination, String message)
    {
        // Get the SmsManager
        SmsManager smsManager = SmsManager.getDefault();
        
        if(null != smsManager)
        {
            // Parse the message into fragments
            ArrayList<String> fragmentList = smsManager.divideMessage(message);
            int fragmentSize = fragmentList == null ? 0 : fragmentList.size();
            
//          String infoMsg = "msg:" + message + "dest: " + destination;
            // "Sent auto reply to [destination]"
            String toastMsg = "Sent auto reply to " + destination;
            String debugMsg = "";
            if(fragmentSize > 1)
            {
                debugMsg = "Sending " + fragmentSize + " parts SMS Message.";
                smsManager.sendMultipartTextMessage(destination, null, fragmentList, null, null);
            }
            else if(fragmentSize == 0)
            {
                debugMsg = "No message to send!";
            }
            else
            {
                debugMsg = "Sending 1 part SMS Message.";
                smsManager.sendTextMessage(destination, null, message, null, null);
            }
            Log.d(TAG, debugMsg);
            Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show();
            
            // TODO: Now add the message to the receipent's history
            // ContentProvider
//            Uri uri = Uri.parse("content://sms/sent");
//            ContentProviderClient contentProviderClient = getContentResolver().acquireContentProviderClient(uri);
//            if(null != contentProviderClient)
//            {
////                contentProviderClient
//            }
            // ContentProviderClient.release()
//            Toast.makeText(MainActivity.this, "MainActivity.sendSMSMessage(View)", Toast.LENGTH_SHORT).show();
      } // end if smsManager is not null
  } // end method sendSMSMessage
} // end class SMSSender