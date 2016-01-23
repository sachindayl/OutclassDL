package com.sportsoutclass.outclassdl;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Sachinda on 1/22/2016.
 * Adapter for Recycler View
 */
public class aboutPageRVAdapter extends RecyclerView.Adapter<aboutPageRVAdapter.aboutPageViewHolder> {

    private String[] tDataset;
    private String[] stDataset;

    public static class aboutPageViewHolder extends RecyclerView.ViewHolder {
        public TextView about_Title;
        public TextView about_subTitle;

        public aboutPageViewHolder(View v) {
            super(v);
            about_Title = (TextView) v.findViewById(R.id.row_mainLine);
            about_subTitle = (TextView) v.findViewById(R.id.row_subLine);
        }
    }

    public aboutPageRVAdapter(String[] titleDataset, String[] subTitleDataset) {
        this.tDataset = titleDataset;
        this.stDataset = subTitleDataset;
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
