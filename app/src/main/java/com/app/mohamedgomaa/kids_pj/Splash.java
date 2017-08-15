package com.app.mohamedgomaa.kids_pj;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class Splash extends AppCompatActivity {
    AnimationDrawable animation_Boy, animationBus, animation_speak;
    ImageView imageBoy, image_Bus, Go_btn, image_wel;
    MediaPlayer m_btn, bus_alerm, bus_move, yay, welcame, go_audio;
    Animation boy_anim, bus_anim, action_btn, bus_anim2;
    boolean check_btn_press = false,check_go=true;
    private AdView mAdView;
    Thread thread1,thread2;
    void initilazationUI() {
        image_Bus = (ImageView) findViewById(R.id.bus);
        imageBoy = (ImageView) findViewById(R.id.boy);
        image_wel = (ImageView) findViewById(R.id.image_welcome);
        image_Bus.setBackgroundResource(R.drawable.move_bus);
        imageBoy.setBackgroundResource(R.drawable.move_boy);
        image_wel.setBackgroundResource(R.drawable.speak_boy);
        animation_Boy = (AnimationDrawable) imageBoy.getBackground();
        animationBus = (AnimationDrawable) image_Bus.getBackground();
        animation_speak = (AnimationDrawable) image_wel.getBackground();
        mAdView = (AdView) findViewById(R.id.adView_splash);
        if(new CheckConnection_Internet(Splash.this).IsConnection())
        {
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }
        Go_btn = (ImageView) findViewById(R.id.go);
        m_btn = MediaPlayer.create(this, R.raw.fav_btn);
        bus_alerm = MediaPlayer.create(this, R.raw.car);
        bus_move = MediaPlayer.create(this, R.raw.bus_sound_3);
        yay = MediaPlayer.create(this, R.raw.yay);
        welcame = MediaPlayer.create(this, R.raw.slpash);
        go_audio = MediaPlayer.create(this, R.raw.go);
        imageBoy.setVisibility(View.INVISIBLE);
        image_Bus.setVisibility(View.INVISIBLE);
        image_wel.setVisibility(View.INVISIBLE);

    }

    void setAnimation() {
        boy_anim = AnimationUtils.loadAnimation(this, R.anim.translate_boy);
        bus_anim = AnimationUtils.loadAnimation(this, R.anim.translate_bus);
        bus_anim2 = AnimationUtils.loadAnimation(this, R.anim.translate_bus2);
        animation_Boy.start();
        animation_speak.start();
        animationBus.start();

        imageBoy.setAnimation(boy_anim);
        image_Bus.setAnimation(bus_anim);
        imageBoy.setVisibility(View.VISIBLE);
        image_Bus.setVisibility(View.VISIBLE);


        thread1=new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(2000);
                    bus_alerm.start();
                    animationBus.stop();
                    sleep(2000);
                    animation_Boy.stop();
                    image_wel.setVisibility(View.VISIBLE);
                    // imageBoy.clearAnimation();
                } catch (Exception E) {
                }

            }
        };
        thread1.start();

        thread2=new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(4200);
                    welcame.start();
                    sleep(welcame.getDuration());
                    animation_speak.stop();
                    while(check_go) {
                        go_audio.start();
                        sleep(10000);
                    }

                } catch (Exception E) {
                }

            }
        };
        thread2.start();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ViewCompat.setLayoutDirection(findViewById(R.id.layer_id_splash), ViewCompat.LAYOUT_DIRECTION_LTR);
        MobileAds.initialize(this,getResources().getString(R.string.ADMOB_APP_ID));
        initilazationUI();
        setAnimation();
    }
    void Action_Go()
    {

        welcame.stop();
        image_wel.setVisibility(View.INVISIBLE);
        animation_speak.start();
        m_btn.start();
        new CountDownTimer(500,500) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                yay.start();

            }
        }.start();
        imageBoy.setVisibility(View.GONE);
        image_Bus.setImageResource(R.drawable.bus);
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                bus_move.start();
                Go_btn.setAnimation(action_btn);
                image_Bus.setAnimation(bus_anim2);
                image_Bus.setVisibility(View.GONE);
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            sleep(3000);
                            startActivity(new Intent(Splash.this, Activity_Regition.class));
                            finish();
                        } catch (Exception E) {
                        }

                    }
                }.start();
            }
        }.start();

    }
    public void go(View view) {
        check_go=false;
      //  thread2.stop();
        Action_Go();
        Go_btn.setVisibility(View.INVISIBLE);

    }
}