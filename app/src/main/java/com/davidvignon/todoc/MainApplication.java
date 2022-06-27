package com.davidvignon.todoc;

import android.app.Application;
//todo david faire les string pour les trad avec APPlication
//todo david faire le tri par time
//todo david warning
//todo david test unit
//todo david test instru
//todo david gérer les nullable et nonnull

public class MainApplication extends Application {

    private static Application sApplication;

    @Override
    public void onCreate() {
        super.onCreate();

        sApplication = this;
    }

    public static Application getInstance(){
        return sApplication;
    }
}
