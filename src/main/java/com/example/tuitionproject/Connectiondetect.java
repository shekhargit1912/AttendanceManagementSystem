package com.example.tuitioproject;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Connectiondetect {
    Context context;
    public Connectiondetect(Context context){
        this.context=context;

    }
    public boolean isConnected()
    {
        ConnectivityManager manager=(ConnectivityManager)context.getSystemService(Service.CONNECTIVITY_SERVICE);
        if (manager != null)
        {
            NetworkInfo info =manager.getActiveNetworkInfo();
            if (info != null)
            {
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    return true;
                }
            }
        }
        return false;
    }
}
