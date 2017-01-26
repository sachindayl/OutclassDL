package com.sportsoutclass.outclassdl;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by sachinda on 1/15/17.
 * Adapter to connect innings picker recycler view
 */

class InningsPickerRVAdapter extends RecyclerView.Adapter<InningsPickerRVAdapter.inningsPickerViewHolder> {
    private String[] titles;
    private String[] subTitles;

    InningsPickerRVAdapter(String[] tSet, String[] sTSet) {
        this.titles = tSet;
        this.subTitles = sTSet;
    }

    class inningsPickerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView inning_Title;
        TextView inning_subTitle;

        inningsPickerViewHolder(View v) {
            super(v);

            v.setOnClickListener(this);
            inning_Title = (TextView) v.findViewById(R.id.row_mainLine);
            inning_subTitle = (TextView) v.findViewById(R.id.row_subLine);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            TeamSelection teamSelect = (TeamSelection) v.getContext();
            switch (position) {
                case 0:
                    Fragment firstInnings = new FirstInnings();
                    teamSelect.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, firstInnings, "firstInnings").addToBackStack("inningsPick").commit();
                    break;
                case 1:
                    Fragment secondInnings = new SecondInnings();
                    teamSelect.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, secondInnings, "secondInnings").addToBackStack("inningsPick").commit();
                    break;
//                case 2:
//                    Fragment rateTable = new RateTable();
//                    teamSelect.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, rateTable, "rateTable").addToBackStack("inningsPick").commit();
//                    break;
            }

        }
    }

    @Override
    public InningsPickerRVAdapter.inningsPickerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row1, parent, false);
        return new InningsPickerRVAdapter.inningsPickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(inningsPickerViewHolder holder, int position) {
        holder.inning_Title.setText(titles[position]);
        holder.inning_subTitle.setText(subTitles[position]);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
