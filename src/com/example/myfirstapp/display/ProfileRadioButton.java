/**
 * 
 */
package com.example.myfirstapp.display;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RadioButton;

import com.example.myfirstapp.data.IProfile;
import com.example.myfirstapp.data.ISMSMessenger;

/**
 * @author jon.lowder
 *
 */
public class ProfileRadioButton extends RadioButton
{
    private static final String TAG = "com.example.myfirstapp.display.ProfileRadioButton";
    
    private IProfile mProfile;
    
    /**
     * @param context
     */
    public ProfileRadioButton(Context context)
    {
        super(context);
        Log.d(TAG, "ProfileRadioButton(Context)");
    }

    /**
     * @param context
     */
    public ProfileRadioButton(Context context, IProfile profile)
    {
        super(context);
        Log.d(TAG, "ProfileRadioButton(Context, IProfile)");
        setProfile(profile);
    }

    /**
     * @param context
     * @param attrs
     */
    public ProfileRadioButton(Context context, AttributeSet attrs, IProfile profile)
    {
        super(context, attrs);
        Log.d(TAG, "ProfileRadioButton(Context, AttributeSet, IProfile)");
        setProfile(profile);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public ProfileRadioButton(Context context, AttributeSet attrs, int defStyle, IProfile profile)
    {
        super(context, attrs, defStyle);
        Log.d(TAG, "ProfileRadioButton(Context, AttributeSet, int, IProfile)");
        setProfile(profile);
    }

    private void setProfile(IProfile profile)
    {
        mProfile = profile;
        setText(mProfile.getName());
    }
    
    public String getName()
    {
        return mProfile.getName();
    }
    
    public void setName(String name)
    {
        mProfile.setName(name);
    }
    
    public String getMessage()
    {
        return mProfile.getMessage();
    }
    
    public void setMessage(String message)
    {
        mProfile.setMessage(message);
    }
    
    public ISMSMessenger getMessenger()
    {
        return (ISMSMessenger)mProfile;
    }
} // end class ProfileRadioButton 
