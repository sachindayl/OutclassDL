package com.sportsoutclass.outclassdl;

import android.app.Application;

/**
 * Created by Sachinda on 8/12/2015.
 */
public class InningsOneComplete extends Application {

    private boolean innOneComplete = true;

    public boolean getInnOneComplete() {
        return innOneComplete;
    }

    public void setInnOneComplete(boolean innOneComplete) {
        this.innOneComplete = innOneComplete;
    }

}
