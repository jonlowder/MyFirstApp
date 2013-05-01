package com.example.myfirstapp.utils;

import java.util.HashSet;
import java.util.Set;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.example.myfirstapp.data.ISMSMessenger;

/**
 * @author jon.lowder
 *
 */
//Obtained from 
//https://github.com/vivdub/DynamicSMSReceiver/tree/master/SMSReceiver
//H:\JON\School\EM360-ProgForMobileComputing-2013-WinterQtr\Project\SampleCode-SmsReceiver.txt
public class SMSReceiver extends BroadcastReceiver
{
    /** TAG for debugging purposes **/
    private static final String TAG = "com.example.myfirstapp.SMSReceiver";
    private static final String BACK_TALK = "BackTalk";
    private static final String BACK_TALK_TAG = "(" + BACK_TALK +")";
    private static final String DEFAULT_OUT_MSG = BACK_TALK_TAG + " I'm busy. " +
                                                    "I'll talk to you later.";
    
    private ISMSMessenger mMessenger;
    
    /**
     * Creates an SMSReceiver with the given out message.
     * @param message
     */
    public SMSReceiver(ISMSMessenger messenger)
    {
        mMessenger = messenger;
    }
    
    /**
     * A Set of phone numbers to not reply to.
     */
    private Set<String> doNotReplyToSet = new HashSet<String>();
    
    /**
     * Clears the Set of phone numbers that a reply have already been sent to.
     */
    private void clearDoNotReplyToSet()
    {
        if(null != doNotReplyToSet) // it should not be null, but just in case
        {
            Log.d(TAG, "clearDoNotReplyToSet(): clearing doNotReplyToSet");
            doNotReplyToSet.clear();
        }
        else
        {
            Log.d(TAG, "clearDoNotReplyToSet(): creating a new doNotReplyToSet");
            doNotReplyToSet = new HashSet<String>();
        }
    }
    
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.d(TAG, "onReceive(Context, Intent): ENTER");
        
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        if(null != bundle)
        {
            // pdus?
            Object[] pdus = (Object[])bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            String originatingAddress = "";
            boolean numIsInContacts = false;
            boolean numIsInDoNotReplySet = false;
            
            // We are only interested in obtaining
            // information from the first message
//            for(int i = 0; i < msgs.length; i++)
            for(int i = 0; i < 1; i++)
            {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
//                msg = msgs[i].getMessageBody();
                originatingAddress = msgs[i].getOriginatingAddress();
                numIsInContacts = ContactUtils.isInContacts(context, originatingAddress);
                numIsInDoNotReplySet = doNotReplyToSet.contains(originatingAddress);
            } // end msgs for loop
            
            // If originatingAddress is in contacts and not in doNotReplyToSet,
            if(numIsInContacts && !numIsInDoNotReplySet)
            {
                String outMsg = (null == mMessenger)
                        ? DEFAULT_OUT_MSG
                        : BACK_TALK_TAG + " " + mMessenger.getSMSMessage();
                
                Log.d(TAG, "onReceive(Context, Intent): SENDING MESSAGE:" +
                        " originatingAddress["+originatingAddress+"]" +
                        " out msg["+outMsg+"]");
                
                // Send message
                SMSSender.sendSMSMessage(context, originatingAddress, outMsg);
                // Add originatingAddress to doNotReplyToSet
                doNotReplyToSet.add(originatingAddress);
            }
            else
            {
                Log.d(TAG, "onReceive(Context, Intent): DO NOT SEND MESSAGE TO" +
                        " originatingAddress["+originatingAddress+"]" +
                        " numIsInContacts["+numIsInContacts+"]" +
                        " numIsInDoNotReplySet["+numIsInDoNotReplySet+"]");
            }
            
            
        } // end if bundle is not null
        
        Log.d(TAG, "onReceive(Context, Intent): EXIT");
    }

    public String getMessage()
    {
        return mMessenger.getSMSMessage();
    }
    
    public void setMessenger(ISMSMessenger messenger)
    {
        mMessenger = messenger;
        clearDoNotReplyToSet();
    }
}
