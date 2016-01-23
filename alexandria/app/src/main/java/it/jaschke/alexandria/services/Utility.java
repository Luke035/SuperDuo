package it.jaschke.alexandria.services;

import android.content.Context;
import android.os.Vibrator;

/**
 * Created by lucagrazioli on 23/01/16.
 */
public class Utility {

    public static void vibrate(Context context, long millisecond){
        ((Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(millisecond);
    }
}
