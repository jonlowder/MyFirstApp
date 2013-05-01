package com.example.myfirstapp;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myfirstapp.data.IProfile;
import com.example.myfirstapp.data.Profile;
import com.example.myfirstapp.display.ProfileRadioButton;
import com.example.myfirstapp.utils.SMSReceiver;

/**
 * @author jon.lowder
 *
 */
public class MainActivity extends Activity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    
    private static final String TAG = "com.example.myfirstapp.MainActivity";
    
    private SMSReceiver mReceiver = null;
    private IntentFilter mFilter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        Log.d(TAG, "onCreate(Bundle)");
        
        // This is the file activity_main.xml
        setContentView(R.layout.activity_main);
        
        // Create an array of Profiles to add to the profileButtonGroup
        IProfile[] profiles = {new Profile("Movie", "At the movies. I will get back to you later"),
                new Profile("Driving", "I am driving. I will get back to you later"),
                new Profile("Work", "I am at work. I will get back to you later"),
                new Profile("Meeting", "I am in a meeting. I will get back to you later"),
                new Profile("In class", "I am in class. I will get back to you later"),
                new Profile("Studying", "I am studying. I will get back to you later"),
                new Profile("Just Busy", "Just Busy. I will get back to you later"),
                new Profile("Profile 1", "Profile 1"),
                new Profile("Profile 2", "Profile 2"),
                new Profile("Profile 3", "Profile 3"),
                new Profile("Profile 4", "Profile 4"),
                new Profile("Profile 5", "Profile 5"),
                new Profile("Profile 6", "Profile 6"),
                new Profile("Profile 7", "Profile 7"),
                new Profile("Profile 8", "Profile 8"),
                new Profile("Profile 9", "Profile 9")};
        
        // Get the profileButtonGroup and add buttons to it
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.profileButtonGroup);
        // TODO: Implement RadioGroup.OnCheckedChangeListener for MainActivity and move code into onCheckedChanged method.
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                ProfileRadioButton profileRadioButton = (ProfileRadioButton)group.findViewById(checkedId);
                Button cancelButton = (Button) findViewById(R.id.cancelbutton);
                if(null != profileRadioButton && profileRadioButton.isChecked())
                {
                    // Lazily load SMSReceiver
                    if(null == mReceiver)
                    {
                        mReceiver = new SMSReceiver(profileRadioButton.getMessenger());
                    }
                    else
                    {
                        // Get the messenger and set it on the SMSReceiver.
                        mReceiver.setMessenger(profileRadioButton.getMessenger());
                    }
                    
                    // Lazily load IntentFilter
                    if(null == mFilter)
                    {
                        mFilter = new IntentFilter();
                        mFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
                    }
                    
                    // Announce registering SMSReceiver for the selected Profile. 
                    Toast.makeText(MainActivity.this, "Enabling \"" +
                        profileRadioButton.getName() + "\" profile", Toast.LENGTH_LONG).show();
                    // TODO: Registering the BroadcastReceive should only happen once.
                    MainActivity.this.registerReceiver(mReceiver, mFilter);
                    // Add notification
                    buildNotification(profileRadioButton.getName(), profileRadioButton.getMessage());
                    cancelButton.setClickable(true);
                } // end if radioButton is checked
            } // end method onCheckedChanged
        }); // end anonymous class RadioGroup.OnCheckedChangeListener
        
        // temp radio button to add to radio group
        ProfileRadioButton profileRadioButton;
        for(int i = 0; i < profiles.length; i++)
        {
            profileRadioButton = new ProfileRadioButton(this, profiles[i]);
            radioGroup.addView(profileRadioButton);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    // TODO: WHAT DOES THIS DO? Posts a notification in the status bar.
    // This is from http://developer.android.com/guide/topics/ui/notifiers/notifications.html
    private void buildNotification(String title, String text)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.ic_launcher)
            .setContentTitle(title)
            .setContentText(text);
        
        // Create an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, MainActivity.class);
        
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(001, builder.build());
    } // end method buildNotification
    
    public void unregisterReceiver(View view)
    {
        if(null != mReceiver)
        {
            RadioGroup radioGroup = (RadioGroup)findViewById(R.id.profileButtonGroup);
            int checkedId = radioGroup.getCheckedRadioButtonId();
            ProfileRadioButton profileRadioButton = 
                    (ProfileRadioButton)radioGroup.findViewById(checkedId);
            String profileName = profileRadioButton.getName();
            String text = (null == profileName) ? "" : profileName;
            
            Button cancelButton = (Button) findViewById(R.id.cancelbutton);
            
            radioGroup.clearCheck();
            cancelButton.setClickable(false);
            
            Toast.makeText(MainActivity.this, "Disabling \"" + text + "\" profile", Toast.LENGTH_LONG).show();
            this.unregisterReceiver(mReceiver);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(001);
        }
    }
} // end class MainActivity
