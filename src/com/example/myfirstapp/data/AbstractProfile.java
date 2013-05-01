/**
 * 
 */
package com.example.myfirstapp.data;
import android.util.Log;

/**
 * @author jon.lowder
 *
 */
public abstract class AbstractProfile implements IProfile, ISMSMessenger
{
    private static final String TAG = "com.example.myfirstapp.data.AbstractProfile";
    private String mName = "";
    private String mMessage = "";
    
    public AbstractProfile(String name, String message)
    {
        Log.d(TAG, "AbstractProfile(String, String)");
        setName(name);
        setMessage(message);
    }
    
    @Override
    public String getName()
    {
        Log.d(TAG, "getName()");
        return mName;
    }
    @Override
    public void setName(String name)
    {
        Log.d(TAG, "setName(String)");
        mName = (null != name) ? name : "";
    }
    @Override
    public String getMessage()
    {
        Log.d(TAG, "getMessage()");
        return mMessage;
    }
    @Override
    public void setMessage(String message)
    {
        Log.d(TAG, "setMessage(String)");
        mMessage = (null != message) ? message : "";
    }

    /* (non-Javadoc)
     * @see com.example.myfirstapp.data.ISMSMessenger#getOutMessage()
     */
    @Override
    public String getSMSMessage()
    {
        return mMessage;
    }
} // end class AbstractProfile