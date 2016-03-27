package com.sportsoutclass.outclassdl;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Sachinda on 1/22/2016.
 * Adapter for Recycler View
 */
public class aboutPageRVAdapter extends RecyclerView.Adapter<aboutPageRVAdapter.aboutPageViewHolder> {

    private String[] tDataset;
    private String[] stDataset;
    private static Context ctx;
    private static final String GooglePlayStorePackageNameOld = "com.google.market";
    private static final String GooglePlayStorePackageNameNew = "com.android.vending";

    public static class aboutPageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView about_Title;
        public TextView about_subTitle;

        public aboutPageViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            about_Title = (TextView) v.findViewById(R.id.row_mainLine);
            about_subTitle = (TextView) v.findViewById(R.id.row_subLine);
        }

        @Override
        public void onClick(View v) {
            /**
             * If position 3, goes to email intent for user feedback
             * If position 4, goes to Play Store or Amazon Appstore for rating
             */
            int position = getAdapterPosition();
            if (position == 0) {
                Intent howTo = new Intent(ctx, HowToPage.class);
                howTo.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(howTo);

            } else if (position == 3) {
                Intent sendEmail = new Intent(Intent.ACTION_SEND);
                sendEmail.setType("message/rfc822");
                sendEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{"dlcalculatorapp@gmail.com"});
                sendEmail.putExtra(Intent.EXTRA_SUBJECT, "Sending Feedback");
                sendEmail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    ctx.startActivity(sendEmail);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ctx, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            } else if (position == 4) {
                boolean googlePlay = checkWhichStore();
                Log.v("googlePlay: ", String.valueOf(googlePlay));
                final String appPackageName = ctx.getPackageName(); // getPackageName() from Context or Activity object
                Uri uri = Uri.parse((googlePlay ? "market://details?id=" : "amzn://apps/android?p=") + appPackageName);
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    ctx.startActivity(goToMarket);
                } catch (android.content.ActivityNotFoundException anfe) {
                    try {
                        ctx.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse((googlePlay ? "https://play.google.com/store/apps/details?id=" : "http://www.amazon.com/gp/mas/dl/android?p=") + appPackageName)));
                    } catch (ActivityNotFoundException e2) {
                        Toast.makeText(ctx, "You don't have any app that can open this link", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        /**
         * Checks if Google play is installed or not in the device
         *
         * @return true if installed
         */
        private boolean checkWhichStore() {
            PackageManager packageManager = ctx.getPackageManager();
            boolean googlePlayStoreInstalled = false;
            List<PackageInfo> packages = packageManager.getInstalledPackages(0);
            for (PackageInfo packageInfo : packages) {
                if (packageInfo.packageName.equals(GooglePlayStorePackageNameOld) ||
                        packageInfo.packageName.equals(GooglePlayStorePackageNameNew)) {
                    googlePlayStoreInstalled = true;
                    break;
                }
            }
            return googlePlayStoreInstalled;
        }
    }

    public aboutPageRVAdapter(Context context, String[] titleDataset, String[] subTitleDataset) {

        this.tDataset = titleDataset;
        this.stDataset = subTitleDataset;
        ctx = context;
    }

    @Override
    public aboutPageRVAdapter.aboutPageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row1, parent, false);
        return new aboutPageRVAdapter.aboutPageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(aboutPageViewHolder holder, int position) {
        holder.about_Title.setText(tDataset[position]);
        holder.about_subTitle.setText(stDataset[position]);
    }

    @Override
    public int getItemCount() {
        return tDataset.length;
    }


}
