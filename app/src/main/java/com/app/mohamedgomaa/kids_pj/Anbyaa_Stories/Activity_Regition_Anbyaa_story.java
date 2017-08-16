package com.app.mohamedgomaa.kids_pj.Anbyaa_Stories;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.app.mohamedgomaa.kids_pj.CheckConnection_Internet;
import com.app.mohamedgomaa.kids_pj.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

public class Activity_Regition_Anbyaa_story extends AppCompatActivity {
List<item_story> _myList=new ArrayList<>();
    RecyclerView recyclerView;
    static RecyleAdapter_story recyleAdapter_story;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.
                FLAG_FULLSCREEN);
        setContentView(R.layout.activity__regition__anbyaa_story);
        ViewCompat.setLayoutDirection(findViewById(R.id.Activity_Regition_Anbyaa_story), ViewCompat.LAYOUT_DIRECTION_LTR);
        recyclerView=(RecyclerView)findViewById(R.id.story_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
        recyclerView.setHasFixedSize(true);
        SharedPreferences db_download=getSharedPreferences("my_File",MODE_PRIVATE);
        for (int i=0;i<=13;i++)
        {
            _myList.add(new item_story(i,db_download.getInt("db_file_"+String.valueOf(i),0),getResources().getIdentifier("file_"+String.valueOf(i),"drawable",getPackageName())));
        }
       recyleAdapter_story=new RecyleAdapter_story(_myList,Activity_Regition_Anbyaa_story.this);
        recyclerView.setAdapter(recyleAdapter_story);
        MobileAds.initialize(this,getResources().getString(R.string.ADMOB_APP_ID));
        AdView mAdView = (AdView) findViewById(R.id.adView_Activity_Anbyaa_Story2);
        if(new CheckConnection_Internet(Activity_Regition_Anbyaa_story.this).IsConnection())
        {
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }

    }

    public void Back_Main(View view) {
        startActivity(new Intent(Activity_Regition_Anbyaa_story.this,Activity_Regition_Anbyaa.class));
        finish();
    }
}
