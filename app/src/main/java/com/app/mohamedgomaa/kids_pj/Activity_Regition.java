package com.app.mohamedgomaa.kids_pj;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.mohamedgomaa.kids_pj.Anbyaa_Stories.Activity_Regition_Anbyaa;
import com.app.mohamedgomaa.kids_pj.Azkaar.Activity_Regition_Zakr;
import com.app.mohamedgomaa.kids_pj.Qaraan.Activity_Regition_Qaraan;
import com.app.mohamedgomaa.kids_pj.Qaraan.Activity_Regition_Qaraan_Show;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class Activity_Regition extends AppCompatActivity {
    AnimationDrawable anim_char;
    Animation anim, anim2, anim_Rotate;
    ImageView Img1_q, Img2_an, Img3_zak, Img4_t;
    MediaPlayer music, choice;
    private AdView mAdView;
    AlertDialog.Builder setting_dailog;

    public static boolean check_1 = true;
    ImageView im_char;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity__regition);
        initialize();
        setAnim();
        MobileAds.initialize(this, getResources().getString(R.string.ADMOB_APP_ID));
        mAdView = (AdView) findViewById(R.id.adView_Activity_Regition);
        if (new CheckConnection_Internet(Activity_Regition.this).IsConnection()) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }
    }
    InterstitialAd mInterstitialAd;
    void initialize() {
        ViewCompat.setLayoutDirection(findViewById(R.id.layer_id_relotion), ViewCompat.LAYOUT_DIRECTION_LTR);
        choice = MediaPlayer.create(this, R.raw.choose_sound);
        music = MediaPlayer.create(this, R.raw.app_music);
        im_char = (ImageView) findViewById(R.id.id_religion_char);
        Img1_q = (ImageView) findViewById(R.id.im_qaraan);
        Img2_an = (ImageView) findViewById(R.id.im_anbyaa);
        Img3_zak = (ImageView) findViewById(R.id.im_zakr);
        Img4_t = (ImageView) findViewById(R.id.im_test);
        im_char.setBackgroundResource(R.drawable.item_motion_religion_char);
        anim_char = (AnimationDrawable) im_char.getBackground();
        anim_char.start();
        MobileAds.initialize(getApplicationContext(),
                "ca-app-pub-2377269014702707~1992291077");
        AdRequest adRequest_inter = new AdRequest.Builder().build();
        mInterstitialAd = new InterstitialAd(Activity_Regition.this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2377269014702707/4668749587");
        mInterstitialAd.loadAd(adRequest_inter);
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    void setAnim() {
        anim = AnimationUtils.loadAnimation(this, R.anim.scale_arabic);
        anim_Rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        anim2 = AnimationUtils.loadAnimation(this, R.anim.scale);
        Img1_q.setAnimation(anim_Rotate);
        Img2_an.setAnimation(anim_Rotate);
        Img3_zak.setAnimation(anim_Rotate);
        Img4_t.setAnimation(anim_Rotate);

    }

    Thread Sound;
    boolean check_sound = false;

    @Override
    protected void onStart() {
        initialize();
        setAnim();
        super.onStart();
        Sound = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    while (check_1) {
                        sleep(100);
                        choice.start();
                        sleep(2100);
                        if (!check_sound) {
                            music.start();
                        }
                        sleep(38000);
                    }
                } catch (Exception E) {
                }

            }
        };
        Sound.start();
    }

    public void AlQaran_Action(View view) {
        method(Img1_q, Activity_Regition_Qaraan.class);
    }

    void method(ImageView img, final Class<?> clas) {
        img.startAnimation(anim);
        check_1 = false;
        music.stop();
        new CountDownTimer(1700, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                startActivity(new Intent(Activity_Regition.this, clas));
                finish();
            }
        }.start();
    }

    public void Zakr_Action(View view) {
        method(Img3_zak, Activity_Regition_Zakr.class);
    }

    public void Anbyaa_Action(View view) {
        method(Img2_an, Activity_Regition_Anbyaa.class);
    }

    @Override
    public void onBackPressed() {

    }

    AlertDialog ad;

    public void Setting_Action(View view) {
        initDailog();
        ad = setting_dailog.show();
    }

    public void Test_Action(View view) {
        method(Img4_t, AlwDaa.class);
    }

    void initDailog() {
        setting_dailog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.setting_dailog_alerm_main, null);
        final ImageView sound_act = (ImageView) view.findViewById(R.id.d_id_sound);
        ImageView rate_act = (ImageView) view.findViewById(R.id.d_id_rate);
        ImageView msg_act = (ImageView) view.findViewById(R.id.d_id_msg);
        ImageView close = (ImageView) view.findViewById(R.id.d_id_close);
        sound_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!check_sound) {
                    sound_act.setImageResource(R.drawable.audio2);
                    music.pause();
                    check_sound = true;
                } else {
                    sound_act.setImageResource(R.drawable.audio);
                    music.start();
                    check_sound = false;
                }
            }
        });
        rate_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent evaluate = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=com.app.mohamedgomaa.kids_project"));
                startActivity(evaluate);
            }
        });
        msg_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] Email = new String[]{"zeafanm@gmail.com"};
                Intent gmail = new Intent(Intent.ACTION_SEND);
                gmail.setType("text/plain");
                gmail.putExtra(Intent.EXTRA_EMAIL, Email);
                gmail.putExtra(Intent.EXTRA_SUBJECT, "عن روضة الأطفال ");
                startActivity(Intent.createChooser(gmail, "Send mail..."));
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.dismiss();
            }
        });
        setting_dailog.setCancelable(false);
        setting_dailog.setView(view);
    }

    public void Exit(View view) {
        final AlertDialog.Builder alerm_Exit = new AlertDialog.Builder(Activity_Regition.this);
        View view_Saved = getLayoutInflater().inflate(R.layout.dailog_alerm_exit_app, null);
        Button btn_yes = (Button) view_Saved.findViewById(R.id.id_yse2);
        Button btn_No = (Button) view_Saved.findViewById(R.id.btn_No2);
        alerm_Exit.setView(view_Saved);
        final AlertDialog ad = alerm_Exit.show();
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
                else {
                    finish();
                    System.exit(0);
                }

            }
        });
        btn_No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.dismiss();
            }
        });
    }
}
