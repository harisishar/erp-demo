package com.app.crmapp.Utils;

/**
 * Created by LBMSOLUTIONS on 05-11-2019.
 */

public class Application extends android.app.Application {
    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }

    public static synchronized Application getInstance() {
        return instance;
    }
}
