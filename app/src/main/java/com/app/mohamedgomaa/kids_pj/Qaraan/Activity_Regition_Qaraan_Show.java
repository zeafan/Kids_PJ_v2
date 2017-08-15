package com.app.mohamedgomaa.kids_pj.Qaraan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.airbnb.lottie.LottieAnimationView;
import com.app.mohamedgomaa.kids_pj.Azkaar.Activity_Regition_Zakr;
import com.app.mohamedgomaa.kids_pj.CheckConnection_Internet;
import com.app.mohamedgomaa.kids_pj.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Activity_Regition_Qaraan_Show extends AppCompatActivity {
    ImageView Img_eye, Img_prev_ayaa, Img_next_ayaa, Img_main_show, Img_Record, Img_Play, Img_Reload, Img_Sound;
    ImageSwitcher Img_Show_Ayaa;
    TextView Txt_soora_name, Txt_num_soora, Txt_tafseer;
    int id, id_img, num_Ayaa, num_views, Soora_saved;
    String name_Soora;
    ConstraintLayout layer;
    String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder;
    Random random;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    public static final int RequestPermissionCode = 1;
    boolean check_Play_btn = true;
    MediaPlayer Ayaa_Sound;
    List<MediaPlayer> my_list_Ayaas_sound = new ArrayList<>();
    List<Drawable> my_list_Ayaas_Img = new ArrayList<>();
    int i = 0;
    List<Activity_Regition_Qaraan_items_tafseer> list;
    DatabaseHelper db;
    DatabaseHelper_RecordList db_record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity__regition__qaraan__show);
        ViewCompat.setLayoutDirection(findViewById(R.id.layer_show_soora), ViewCompat.LAYOUT_DIRECTION_LTR);
        db = new DatabaseHelper(this);
        db_record = new DatabaseHelper_RecordList(this);
        File database = getApplicationContext().getDatabasePath(db.DBNAME);
        if (false == database.exists()) {
            db.getReadableDatabase();
            copyDatabase(this);
        }
        init();
        getDataFromIntent();
        addFont();
        change_Design();
        initImageSwitcher();
        MobileAds.initialize(this,getResources().getString(R.string.ADMOB_APP_ID));
        AdView mAdView = (AdView)findViewById(R.id.adView_Activity_qaraan_show);
        if(new CheckConnection_Internet(Activity_Regition_Qaraan_Show.this).IsConnection())
        {
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }
        my_list_Ayaas_sound.add(getMedia("sound/sooarl_s0.mp3"));
        my_list_Ayaas_Img.add(getDrawable("images/besm_allah.png"));
        list = new ArrayList<>();
        list = db.get_All_Data_tafseer(id_img);
        for (int no_ayaa = 1; no_ayaa <= num_Ayaa; no_ayaa++) {
            my_list_Ayaas_sound.add(getMedia("sound/sooarI_s" + String.valueOf(id) + "_" + String.valueOf(no_ayaa) + ".mp3"));
            my_list_Ayaas_Img.add(getDrawable("images/" + String.valueOf(id_img) + "/s_" + String.valueOf(id_img) + "_"
                    + String.valueOf(no_ayaa) + ".png"));
        }
        Ayaa_Sound = my_list_Ayaas_sound.get(0);
        Ayaa_Sound.start();
        Img_Show_Ayaa.setImageDrawable(my_list_Ayaas_Img.get(0));
        num_views++;
        switch (num_views)
        {
            case 1:
                createToast(1);
                break;
            case 2:
                createToast(2);
                break;
            default:
                createToast(3);

        }
        db.updata_numView(num_views, id);
        Count();
    }
    void createToast(int i) {
        ImageView imageView=new ImageView(this);
        imageView.setImageResource(getResources().getIdentifier("star"+String.valueOf(i),"drawable",getPackageName()));
        Toast toast = new Toast(Activity_Regition_Qaraan_Show.this);
        toast.setView(imageView);
        toast.setGravity(Gravity.TOP,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    private boolean copyDatabase(Context context) {
        try {
            InputStream inputStream = context.getAssets().open(db.DBNAME);
            String outFileName = db.DBLOCATION + db.DBNAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    int y;
    boolean check_star = true;
    boolean check_Reload_btn = true;
    CountDownTimer countDownTimer;
    void Count() {
         countDownTimer=new CountDownTimer(my_list_Ayaas_sound.get(i).getDuration(), my_list_Ayaas_sound.get(i).getDuration()) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (check_star) {
                    Ayaa_Sound = my_list_Ayaas_sound.get(i);
                    if (Ayaa_Sound != null) {
                        if (!check_Sound) {
                            Ayaa_Sound.setVolume(0, 0);
                        }
                        Ayaa_Sound.start();
                        String tafseer = "";
                        for (int u = 0; u < list.size(); u++) {
                            if (i == list.get(u).Id_Ayah) {
                                tafseer += list.get(u).title + " :" + list.get(u).content + "\n";
                            }

                        }
                        if (!tafseer.equals("")) {
                            Txt_tafseer.setText(tafseer);
                        }
                        Txt_num_soora.setText(String.valueOf(i));
                        Img_Show_Ayaa.setImageDrawable(my_list_Ayaas_Img.get(i));
                    }
                }
            }

            @Override
            public void onFinish() {
                my_list_Ayaas_sound.get(y).stop();
                if (check_Play_btn && check_Reload_btn && chech_Record) {
                    if (i < num_Ayaa) {
                        i++;
                        Count();
                    } else if (i == num_Ayaa) {
                        if (Soora_saved == 0) {
                            final AlertDialog.Builder alerm_Ayaa_Save = new AlertDialog.Builder(Activity_Regition_Qaraan_Show.this);
                            View view_Saved = getLayoutInflater().inflate(R.layout.dailog_alerm_saved_soora, null);
                            Button btn_yes=(Button)view_Saved.findViewById(R.id.id_yse);
                            Button btn_No=(Button)view_Saved.findViewById(R.id.btn_No);
                            Button close=(Button)view_Saved.findViewById(R.id.close);
                            alerm_Ayaa_Save.setView(view_Saved);
                            final AlertDialog ad= alerm_Ayaa_Save.show();
                            btn_yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new CountDownTimer(4000, 3800) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                            ad.dismiss();
                                            db.updata_Saved(1, id);
                                            MediaPlayer mediaPlayer123 = MediaPlayer.create(Activity_Regition_Qaraan_Show.this, R.raw.mabrook2);
                                            View view_mabrook = getLayoutInflater().inflate(R.layout.view_mabrook, null);
                                            Toast toast = new Toast(Activity_Regition_Qaraan_Show.this);
                                            toast.setView(view_mabrook);
                                            toast.setDuration(Toast.LENGTH_LONG);
                                            toast.show();
                                            mediaPlayer123.start();
                                        }
                                        @Override
                                        public void onFinish() {
                                            startActivity(new Intent(Activity_Regition_Qaraan_Show.this, Activity_Regition_Qaraan.class));
                                            finish();
                                        }
                                    }.start();
                                }
                            });
                            btn_No.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ad.dismiss();
                                    i = 0;
                                    Count();
                                }
                            });
                            close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ad.dismiss();
                                }
                            });
                        } else {
                            i = 0;
                            Count();
                        }
                    }
                }
            }
        };
        countDownTimer.start();
    }


    public void NextAyaa(View view) {
        if (i < num_Ayaa) {
            Ayaa_Sound.stop();
            i++;
        }
    }

    public void Prev_Ayaa(View view) {
        if (i > 1) {
            Ayaa_Sound.stop();
            i--;
        }
    }

    boolean Reload_Check = true;

    public void Reload(View view) {
        if (Reload_Check) {
            check_Reload_btn = false;
            Reload_Check = false;
            Img_Reload.setImageResource(R.drawable.reload2);
            Ayaa_Sound.setLooping(true);
            Img_Play.setEnabled(false);
        } else {
            check_Reload_btn = true;
            Reload_Check = true;
            Count();
            Ayaa_Sound.setLooping(false);
            Img_Reload.setImageResource(R.drawable.reload1);
            Img_Play.setEnabled(true);
        }
    }

    boolean Play_Check = true;

    public void Play_Pause(View view) {
        if (Play_Check) {
            Img_Play.setImageResource(R.drawable.btn_pause);
            check_Play_btn = false;
            Play_Check = false;
            Img_Reload.setEnabled(false);
            Img_prev_ayaa.setEnabled(false);
            Img_next_ayaa.setEnabled(false);
            countDownTimer.cancel();
            Ayaa_Sound.stop();
        } else if (!Play_Check) {
            Img_Play.setImageResource(R.drawable.btn_play);
            check_Play_btn = true;
            Img_Reload.setEnabled(true);
            Img_prev_ayaa.setEnabled(true);
            Img_next_ayaa.setEnabled(true);
            Play_Check = true;
            Count();
        }
    }

    @Override
    public void onBackPressed() {
    }

    void initImageSwitcher() {

        Img_Show_Ayaa.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView myView = new ImageView(getApplicationContext());
                myView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                myView.setLayoutParams(new ImageSwitcher.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
                return myView;
            }
        });
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        Img_Show_Ayaa.setInAnimation(in);
        Img_Show_Ayaa.setOutAnimation(out);
    }

    void change_Design() {
        Random ran = new Random();
        int num = ran.nextInt(5);
        Img_main_show.setImageResource(getResources().getIdentifier("items_sora_show_item" +
                String.valueOf(num + 1), "drawable", getPackageName()));
        layer.setBackgroundResource(getResources().getIdentifier("items_sora_show_back"
                        + String.valueOf(num + 1),
                "drawable", getPackageName()));

    }

    void addFont() {
        // Typeface T_face = Typeface.createFromAsset(getAssets(), "buble.ttf");
        Typeface T_face2 = Typeface.createFromAsset(getAssets(), "font1.otf");
        Txt_soora_name.setTypeface(T_face2);
        // Txt_num_soora.setTypeface(T_face);
        Txt_soora_name.setText(name_Soora);
        Txt_num_soora.setText(String.valueOf(num_Ayaa));

    }

    void getDataFromIntent() {
        id = getIntent().getExtras().getInt("id");
        id_img = getIntent().getExtras().getInt("id_img");
        num_Ayaa = getIntent().getExtras().getInt("num_ayaa");
        name_Soora = getIntent().getExtras().getString("name_soora");
        num_views = getIntent().getExtras().getInt("num_views");
        Soora_saved = getIntent().getExtras().getInt("Soora_saved");
    }

    void init() {
        layer = (ConstraintLayout) findViewById(R.id.layer_show_soora);
        Img_eye = (ImageView) findViewById(R.id.imageView4);
        Img_prev_ayaa = (ImageView) findViewById(R.id.imageView8);
        Img_next_ayaa = (ImageView) findViewById(R.id.imageView7);
        Img_main_show = (ImageView) findViewById(R.id.imageView);
        Img_Show_Ayaa = (ImageSwitcher) findViewById(R.id.imageView6);
        Img_Record = (ImageView) findViewById(R.id.imageView10);
        Img_Play = (ImageView) findViewById(R.id.imageView9);
        Img_Reload = (ImageView) findViewById(R.id.imageView11);
        Img_Sound = (ImageView) findViewById(R.id.imageView12);
        Txt_soora_name = (TextView) findViewById(R.id.imageView5);
        Txt_num_soora = (TextView) findViewById(R.id.imageView3);
        Txt_tafseer = (TextView) findViewById(R.id.textView);
    }

    MediaPlayer getMedia(String path) {
        MediaPlayer m = new MediaPlayer();
        try {
            AssetFileDescriptor descriptor = getAssets().openFd(path);
            m.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(),
                    descriptor.getLength());
            descriptor.close();
            m.prepare();
            m.setVolume(1f, 1f);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
        return m;
    }

    Drawable getDrawable(String path) {

        InputStream inputStream = null;
        try {
            inputStream = getAssets().open(path);
        } catch (IOException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        Drawable drawable = Drawable.createFromStream(inputStream, null);
        return drawable;
    }

    public void Back(View view) {
        check_star = false;
        Ayaa_Sound.setLooping(false);
        Ayaa_Sound.stop();
        startActivity(new Intent(this, Activity_Regition_Qaraan.class));
        finish();
    }

    boolean check = false;

    public void Eye(View view) {
        if (check) {
            Img_Show_Ayaa.setVisibility(View.VISIBLE);
            check = false;
            Img_eye.setImageResource(R.drawable.sura_eye_open);
        } else {
            Img_Show_Ayaa.setVisibility(View.INVISIBLE);
            check = true;
            Img_eye.setImageResource(R.drawable.sura_eye_close);

        }

    }

    boolean check_Sound = true;

    public void Sound(View view) {
        if (check_Sound) {
            Img_Sound.setImageResource(R.drawable.audio);
            Ayaa_Sound.setVolume(0, 0);
            check_Sound = false;
        } else {
            Img_Sound.setImageResource(R.drawable.audio2);
            Ayaa_Sound.setVolume(1, 1);
            check_Sound = true;
        }
    }


    AlertDialog.Builder Record_AlermD;
    boolean chech_Record = true;

    public void Record(View view) {
        Record_AlermD = new AlertDialog.Builder(this);
        View view_record = getLayoutInflater().inflate(R.layout.setting_dailog_alerm_record, null);
        final ImageView imageView = (ImageView) view_record.findViewById(R.id.id_record_dialog);
        ImageView image_cloase=(ImageView)view_record.findViewById(R.id.close_record_Dailog);
        final LottieAnimationView animationView = (LottieAnimationView)view_record.findViewById(R.id.animation_view);
        animationView.setAnimation("equal.json");
        animationView.loop(true);
        Record_AlermD.setView(view_record);
        Record_AlermD.setCancelable(false);
        final AlertDialog ad=Record_AlermD.show();
        image_cloase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.dismiss();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chech_Record) {
                    imageView.setImageResource(R.drawable.btn_record_stop);
                    Ayaa_Sound.stop();
                    countDownTimer.cancel();
                    chech_Record = false;
                    if (checkPermission()) {
                        AudioSavePathInDevice =
                                Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                                        CreateRandomAudioFileName(5) + "AudioRecording.3gp";
                        MediaRecorderReady();
                        boolean result = db_record.Insert_Record(name_Soora, AudioSavePathInDevice);
                        try {
                            mediaRecorder.prepare();
                            animationView.playAnimation();
                            mediaRecorder.start();
                            animationView.playAnimation();

                        } catch (IllegalStateException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        if (result) {
                            Toast.makeText(Activity_Regition_Qaraan_Show.this, "الأن بدأ التسجيل",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Activity_Regition_Qaraan_Show.this, "هناك عطل فى التسجيل",
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        requestPermission();
                    }


                } else {
                    imageView.setImageResource(R.drawable.btn_record_star);
                    chech_Record = true;
                    Count();
                    mediaRecorder.stop();
                    animationView.pauseAnimation();
                    Toast.makeText(Activity_Regition_Qaraan_Show.this, "تم أيقاف التسجيل ونقل إلى قائمة التسجيلات",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public String CreateRandomAudioFileName(int string) {
        random = new Random();
        StringBuilder stringBuilder = new StringBuilder(string);
        int i = 0;
        while (i < string) {
            stringBuilder.append(RandomAudioFileName.
                    charAt(random.nextInt(RandomAudioFileName.length())));

            i++;
        }
        return stringBuilder.toString();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(Activity_Regition_Qaraan_Show.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length > 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(Activity_Regition_Qaraan_Show.this, "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Activity_Regition_Qaraan_Show.this, "Permission Denied", Toast.LENGTH_LONG).show();

                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }

    public void MediaRecorderReady() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }
}
