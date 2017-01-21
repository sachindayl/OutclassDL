package com.sportsoutclass.outclassdl;

import android.util.Log;

import com.google.android.gms.analytics.HitBuilders;

class Tracking {
    private StateClass state;
    private String className;

    Tracking(String className, StateClass state){
        this.state = state;
     this.className = className;
    }

    void doTracking(){
        //TODO: Uncomment Track before publishing
//        com.google.android.gms.analytics.Tracker mTracker = state.getDefaultTracker();
//        Log.i("TAG", "Setting screen name: " + className);
//        mTracker.setScreenName(className);
//        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }


}
