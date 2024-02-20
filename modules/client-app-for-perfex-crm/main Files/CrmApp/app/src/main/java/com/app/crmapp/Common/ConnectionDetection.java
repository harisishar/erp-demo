package com.app.crmapp.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class ConnectionDetection {
	private static final String TAG = ConnectionDetection.class.getSimpleName();

	/******* static method called from activity or fragment
	 * to check internet connection *********/
    public static boolean isInternetAvailable(Context context) {
	    NetworkInfo info = (NetworkInfo) ((ConnectivityManager)
	    context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
	    if (info == null) {
	         Log.d(TAG,"no internet connection");
	         return false;
	    }
	    else {
	        if(info.isConnected()) {
	            Log.d(TAG," internet connection available...");
	            return true;
	        }
	        else if(info.isRoaming()){
	            //here is the roaming option you can change it if you want to disable internet while roaming, just return false
	            return true;
	        }
	        else {
	            Log.d(TAG," internet connection");
	            return true;
	        }
	    }
	}
}

