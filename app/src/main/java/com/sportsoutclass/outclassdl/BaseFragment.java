package com.sportsoutclass.outclassdl;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by sachinda on 1/14/17.
 */

public abstract class BaseFragment extends Fragment {
    public ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    public void replaceFragment(Fragment someFragment, int containerId) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(containerId, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}