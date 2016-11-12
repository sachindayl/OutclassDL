package com.sportsoutclass.outclassdl;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class AboutPage extends AppCompatActivity {

    private Toolbar toolbar;
    String[] titleValues, subTitleValues;
    RecyclerView aboutRecycler;
    private aboutPageRVAdapter adapter;
    LinearLayout ll;
    final Context context = StateClass.getContext();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        ll = (LinearLayout) findViewById(R.id.about_row_ll);
        aboutRecycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        aboutRecycler.setLayoutManager(llm);

        adapter = new aboutPageRVAdapter(context, titleValues, subTitleValues);
        aboutRecycler.setAdapter(adapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher); // Initialize this to whatever you want
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = getTheme();
            theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
            int color = typedValue.data;
            ActivityManager.TaskDescription description = new ActivityManager.TaskDescription(null, bm, color);
            setTaskDescription(description);
            bm.recycle();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.d("Home Pressed","Pressing home button in AboutPage");
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void init() {

        aboutRecycler = (RecyclerView) findViewById(R.id.about_recycler);
        String version = BuildConfig.VERSION_NAME;
        titleValues = new String[]{"Developer", "Version", "Feedback", "Rate", "Licences"};
        subTitleValues = new String[]{"Sachinda Liyanaarachchi", version, "Give your feedback at dlcalculatorapp@gmail.com", "Please rate this app on store", "Open source licences used"};
    }


}
