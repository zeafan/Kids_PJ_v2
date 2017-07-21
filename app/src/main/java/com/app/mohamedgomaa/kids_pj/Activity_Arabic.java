package com.app.mohamedgomaa.kids_pj;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class Activity_Arabic extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this,new Crashlytics());
        // TODO: Move this to where you establish a user session
        logUser();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity__arabic);
        ViewCompat.setLayoutDirection(findViewById(R.id.layer_id_arabic), ViewCompat.LAYOUT_DIRECTION_LTR);

    }
    private void logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        Crashlytics.setUserIdentifier("12345");
        Crashlytics.setUserEmail("user@fabric.io");
        Crashlytics.setUserName("Test User");
    }

    public void forceCrash(View view) {
        throw new RuntimeException("This is a crash");
    }

}
