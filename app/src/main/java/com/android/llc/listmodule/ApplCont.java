package com.android.llc.listmodule;

import android.app.Application;

/**
 * Created by Bhaiya on 8/14/2017.
 */

public class ApplCont extends Application {
    private static  ApplCont ourInstance = null;

    public static ApplCont getInstance() {
        return ourInstance;
    }

    private ApplCont() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ourInstance=this;
    }


}
