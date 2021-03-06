package it.jaschke.alexandria.services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Vibrator;

/**
 * Created by lucagrazioli on 23/01/16.
 */
public class Utility {

    public static void vibrate(Context context, long millisecond){
        ((Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(millisecond);
    }

    public static boolean isDeviceConnected(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }
}
