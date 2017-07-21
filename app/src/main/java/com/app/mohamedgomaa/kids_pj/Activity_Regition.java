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

import com.app.mohamedgomaa.kids_pj.Anbyaa_Stories.Activity_Regition_Anbyaa;
import com.app.mohamedgomaa.kids_pj.Qaraan.Activity_Regition_Qaraan;

public class Activity_Regition extends AppCompatActivity {
    AnimationDrawable anim_char;
    Animation anim,anim2,anim_Rotate;
    ImageView Img1_q,Img2_an,Img3_zak,Img4_t;
    MediaPlayer choice;
    public static boolean check_1=true;
    ImageView im_char;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity__regition);
        initialize();
        setAnim();
    }
    void initialize()
    {
        ViewCompat.setLayoutDirection(findViewById(R.id.layer_id_relotion), ViewCompat.LAYOUT_DIRECTION_LTR);
        choice = MediaPlayer.create(this, R.raw.choose_sound);
        im_char = (ImageView) findViewById(R.id.id_religion_char);
        Img1_q = (ImageView) findViewById(R.id.im_qaraan);
        Img2_an = (ImageView) findViewById(R.id.im_anbyaa);
        Img3_zak = (ImageView) findViewById(R.id.im_zakr);
        Img4_t = (ImageView) findViewById(R.id.im_test);
        im_char.setBackgroundResource(R.drawable.item_motion_religion_char);
        anim_char=(AnimationDrawable)im_char.getBackground();
        anim_char.start();
    }

    void setAnim(){
        anim= AnimationUtils.loadAnimation(this,R.anim.scale_arabic);
        anim_Rotate= AnimationUtils.loadAnimation(this,R.anim.rotate);
        anim2= AnimationUtils.loadAnimation(this,R.anim.scale);
        Img1_q.setAnimation(anim_Rotate);
        Img2_an.setAnimation(anim_Rotate);
        Img3_zak.setAnimation(anim_Rotate);
        Img4_t.setAnimation(anim_Rotate);

    }

    @Override
    protected void onStart() {
        initialize();
        setAnim();
        super.onStart();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    while (check_1) {
                        sleep(100);
                        choice.start();
                        sleep(20000);
                    }
                } catch (Exception E) {
                }

            }
        }.start();
    }

    public void AlQaran_Action(View view) {
        Img1_q.startAnimation(anim);
        check_1=false;
        new CountDownTimer(1700,1000)
        {
            @Override
            public void onTick(long millisUntilFinished) {
           }

            @Override
            public void onFinish() {
                startActivity(new Intent(Activity_Regition.this,Activity_Regition_Qaraan.class));
                finish();
            }
        }.start();
    }

    public void Zakr_Action(View view) {
        Img3_zak.startAnimation(anim2);
        new CountDownTimer(1100,1000)
        {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(Activity_Regition.this,Activity_Regition_Zakr.class));

            }
        }.start();
    }

    public void Test_Action(View view) {
        Img4_t.startAnimation(anim);
        new CountDownTimer(1700,1000)
        {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(Activity_Regition.this,Activity_Regition_Test.class));
            }
        }.start();
    }

    public void Anbyaa_Action(View view) {
        Img2_an.startAnimation(anim2);
        new CountDownTimer(1100,1000)
        {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(Activity_Regition.this,Activity_Regition_Anbyaa.class));
            }
        }.start();
    }
}
