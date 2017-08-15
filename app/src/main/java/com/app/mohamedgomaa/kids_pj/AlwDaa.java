package com.app.mohamedgomaa.kids_pj;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class AlwDaa extends AppCompatActivity {
VideoView VW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_alw_daa);
        VW=(VideoView)findViewById(R.id.videoView);
        MobileAds.initialize(this,getResources().getString(R.string.ADMOB_APP_ID));
        AdView mAdView = (AdView)findViewById(R.id.adView__Alwdaa);
        if(new CheckConnection_Internet(AlwDaa.this).IsConnection())
        {
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }
        String Path="android.resource://com.app.mohamedgomaa.kids_pj/"+R.raw.video;
        VW.setVideoURI(Uri.parse(Path));
        VW.setMediaController(new MediaController(this));
        VW.requestFocus();
        VW.start();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AlwDaa.this,Activity_Regition.class));
        finish();
    }
}
