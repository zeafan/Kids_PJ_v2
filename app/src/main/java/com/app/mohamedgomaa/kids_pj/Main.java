package com.app.mohamedgomaa.kids_pj;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Main extends AppCompatActivity {
    AlertDialog.Builder setting_dailog;
    MediaPlayer music, choice;
    Thread Sound;
    boolean check_1 = true;
    AnimationDrawable anim_arabic, anim_reg, anim_math, anim_computer, anim_english, anim_game;
    ImageView im_arabic, im_reg, im_math, im_computer, im_english, im_game;
    Animation animation;
    void setAnimation()
    {
        animation = AnimationUtils.loadAnimation(this, R.anim.scale_arabic);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        music = MediaPlayer.create(this, R.raw.music_main);
        choice = MediaPlayer.create(this, R.raw.choose_sound);
        setting_dailog = new AlertDialog.Builder(this);
        setting_dailog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });

        Sound = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    while (check_1) {
                        sleep(100);
                            choice.start();
                        sleep(2100);
                    if(!check_sound) {
                        music.start();
                    }
                        sleep(58000);
                    }
                } catch (Exception E) {
                }

            }
        };
        Sound.start();
    }

    @Override
    protected void onStart() {
        initAnimationDraw();
        setAnimation();
        super.onStart();
    }

    void initAnimationDraw() {
        im_arabic = (ImageView) findViewById(R.id.id_arabic);
        im_reg = (ImageView) findViewById(R.id.id_religion);
        im_math = (ImageView) findViewById(R.id.id_math);
        im_computer = (ImageView) findViewById(R.id.id_computer);
        im_english = (ImageView) findViewById(R.id.id_english);
        im_game = (ImageView) findViewById(R.id.id_game);

        im_arabic.setBackgroundResource(R.drawable.item_motion_arabic);
        im_reg.setBackgroundResource(R.drawable.item_motion_regition);
        im_math.setBackgroundResource(R.drawable.item_motion_math);
        im_computer.setBackgroundResource(R.drawable.item_motion_computer);
        im_english.setBackgroundResource(R.drawable.item_motion_english);
        im_game.setBackgroundResource(R.drawable.item_motion_games);

        anim_arabic = (AnimationDrawable) im_arabic.getBackground();
        anim_reg = (AnimationDrawable) im_reg.getBackground();
        anim_math = (AnimationDrawable) im_math.getBackground();
        anim_computer = (AnimationDrawable) im_computer.getBackground();
        anim_english = (AnimationDrawable) im_english.getBackground();
        anim_game = (AnimationDrawable) im_game.getBackground();
        anim_game.start();
        anim_english.start();
        anim_computer.start();
        anim_math.start();
        anim_reg.start();
        anim_arabic.start();
    }

    boolean check_sound = false;

    void initDailog() {
        setting_dailog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.setting_dailog_alerm_main, null);
        final ImageView sound_act = (ImageView) view.findViewById(R.id.d_id_sound);
        ImageView about_act = (ImageView) view.findViewById(R.id.d_id_about);
        ImageView rate_act = (ImageView) view.findViewById(R.id.d_id_rate);
        ImageView msg_act = (ImageView) view.findViewById(R.id.d_id_msg);
        sound_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!check_sound) {
                    sound_act.setImageResource(R.drawable.audio2);
                    music.pause();
                    check_sound=true;
                } else {
                    sound_act.setImageResource(R.drawable.audio);
                    music.start();
                    check_sound=false;
                }
            }
        });
        about_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

        setting_dailog.setCancelable(true);
        setting_dailog.setView(view);
    }


    public void Setting_Action(View view) {
        initDailog();
        setting_dailog.show();
    }


    public void Arabic_Action(View view) {
        im_arabic.startAnimation(animation);
        new CountDownTimer(1800,1000)
        {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                check_1=false;
                music.pause();
                startActivity(new Intent(Main.this,Activity_Arabic.class));

            }
        }.start();

    }

    public void Game_Action(View view) {
        im_game.startAnimation(animation);
        new CountDownTimer(1800,1000)
        {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish()
            {
                check_1=false;
                music.pause();
                startActivity(new Intent(Main.this,Activity_Game.class));

            }
        }.start();
    }

    public void English_Action(View view) {
        im_english.startAnimation(animation);
        new CountDownTimer(1800,1000)
        {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish()
            {
                check_1=false;
                music.pause();
                startActivity(new Intent(Main.this,Activity_English.class));
            }
        }.start();
    }

    public void Computer_Action(View view) {
        im_computer.startAnimation(animation);
        new CountDownTimer(1800,1000)
        {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                check_1=false;
                music.pause();
                                startActivity(new Intent(Main.this,Activity_Computer.class));
            }
        }.start();
    }

    public void Math_Action(View view) {
        im_math.startAnimation(animation);
        new CountDownTimer(1800,1000)
        {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                check_1=false;
                music.pause();
                startActivity(new Intent(Main.this,Activity_Math.class));
            }
        }.start();
    }

    public void Reg_Action(View view) {
        im_reg.startAnimation(animation);
        new CountDownTimer(1800,1000)
        {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                check_1=false;
                music.pause();
                startActivity(new Intent(Main.this, Activity_Regition.class));
            }
        }.start();
    }
}
