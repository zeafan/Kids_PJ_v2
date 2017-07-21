package com.app.mohamedgomaa.kids_pj;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

public class Activity_Regition_Zakr extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity__regition__zakr);
        ViewCompat.setLayoutDirection(findViewById(R.id.layer_id_relotion_zakr), ViewCompat.LAYOUT_DIRECTION_LTR);

    }
}
