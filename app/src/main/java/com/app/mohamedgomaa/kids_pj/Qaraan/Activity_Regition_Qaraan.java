package com.app.mohamedgomaa.kids_pj.Qaraan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.mohamedgomaa.kids_pj.Activity_Regition;
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
import java.util.Random;

public class Activity_Regition_Qaraan extends AppCompatActivity {
    GridView gridView;
    DatabaseHelper db;
    ArrayList<Activity_Regition_Qaraan_items> myItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity__regition__qaraan);
        Activity_Regition.check_1=false;
        db = new DatabaseHelper(this);
        ViewCompat.setLayoutDirection(findViewById(R.id.layer_id_relotion_qaraan), ViewCompat.LAYOUT_DIRECTION_LTR);
        File database = getApplicationContext().getDatabasePath(db.DBNAME);
        if (false == database.exists()) {
            db.getReadableDatabase();
            copyDatabase(this);
        }
        gridView = (GridView) findViewById(R.id.id_item_sora);
        myItems = db.get_All_Data();
        AdapterGridView adapterGridView = new AdapterGridView();
        gridView.setAdapter(adapterGridView);
        MediaPlayer m2=getMedia("sooar_main/sooarM_sorah.mp3");
        m2.start();
        MobileAds.initialize(this,getResources().getString(R.string.ADMOB_APP_ID));
        AdView mAdView = (AdView)findViewById(R.id.adView_Activity_qaraan_list);
        if(new CheckConnection_Internet(Activity_Regition_Qaraan.this).IsConnection())
        {
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }
    }
    public void Return(View view) {

        startActivity(new Intent(this,Activity_Regition.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,Activity_Regition.class));
        finish();
    }

    AlertDialog.Builder getRecord_AlermD;
    public void Setting_Record(View view) {
        getRecord_AlermD = new AlertDialog.Builder(this);
        View view_list_record = getLayoutInflater().inflate(R.layout.activity__regition__qaraan_record_view, null);
        ListView listView = (ListView) view_list_record.findViewById(R.id.id_list_record_items);
        ImageView imageView=(ImageView)view_list_record.findViewById(R.id.close_record_list);
        Record_class adapter_recordList = new Record_class(this,getResources());
        listView.setAdapter(adapter_recordList);
        getRecord_AlermD.setView(view_list_record);
        getRecord_AlermD.setCancelable(false);
        final AlertDialog ad=getRecord_AlermD.show();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Record_class.mediaPlayer.isPlaying()) {
                    Record_class.mediaPlayer.stop();
                }
                ad.dismiss();
            }
        });

    }

    class AdapterGridView extends BaseAdapter {

        @Override
        public int getCount() {
            return myItems.size();
        }

        @Override
        public Object getItem(int position) {
            return myItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.activity__regition__qaraan_item, null);
            LinearLayout item_backG = (LinearLayout) view.findViewById(R.id.id_item_sora_background);
            ImageView Soora_save = (ImageView) view.findViewById(R.id.id_item_sora_save);
            ImageView Soora_star1 = (ImageView) view.findViewById(R.id.id_item_sora_star1);
            ImageView Soora_star2 = (ImageView) view.findViewById(R.id.id_item_sora_star2);
            ImageView Soora_star3 = (ImageView) view.findViewById(R.id.id_item_sora_star3);
            TextView num_Soora = (TextView) view.findViewById(R.id.id_item_sora_number);
            TextView name_Soora = (TextView) view.findViewById(R.id.id_item_sora_name);

           final int num_ayaa = myItems.get(position).num_ayaa;
            final int num_views = myItems.get(position).num_views;
            final int num_saved = myItems.get(position).soora_saved;
          final  String name_Sora = myItems.get(position).name_Soora;
            Typeface T_face = Typeface.createFromAsset(getAssets(), "buble.ttf");
            Typeface T_face2 = Typeface.createFromAsset(getAssets(), "font1.otf");
            Random ran = new Random();
            int num = ran.nextInt(14);
            name_Soora.setTypeface(T_face2);
            num_Soora.setTypeface(T_face);
            /////////////////////
            item_backG.setBackgroundResource(getResources().getIdentifier("items_sora_background" + String.valueOf(num + 1), "drawable", getPackageName()));
            num_Soora.setText(String.valueOf(num_ayaa));
            if (num_saved == 0) {
                Soora_save.setImageResource(R.drawable.items_sora_save_false);
            } else if (num_saved == 1) {
                Soora_save.setImageResource(R.drawable.items_sora_save);
            }
            name_Soora.setText(name_Sora);
            switch (num_views) {
                case 1:
                    Soora_star1.setImageResource(R.drawable.items_sora_star);
                    break;
                case 2:
                    Soora_star1.setImageResource(R.drawable.items_sora_star);
                    Soora_star2.setImageResource(R.drawable.items_sora_star);
                    break;
                case 3:
                    Soora_star1.setImageResource(R.drawable.items_sora_star);
                    Soora_star2.setImageResource(R.drawable.items_sora_star);
                    Soora_star3.setImageResource(R.drawable.items_sora_star);
                    break;
                default:
                    Soora_star1.setImageResource(R.drawable.items_sora_star_false);
                    Soora_star2.setImageResource(R.drawable.items_sora_star_false);
                    Soora_star3.setImageResource(R.drawable.items_sora_star_false);

            }
            final Activity_Regition_Qaraan_items My_class=myItems.get(position);
            final int id = myItems.get(position).id;
          //  final int id_img = myItems.get(position).Id_img;
            item_backG.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MediaPlayer m=new MediaPlayer();
                    if(position>=1) {
                        int id_sound = 39 - id;
                        m=getMedia("sooar_main/sooarM_s"+String.valueOf(id_sound)+".mp3");
                    }
                    else if(position==0)
                    {
                        int id_sound=1;
                        m=getMedia("sooar_main/sooarM_s"+String.valueOf(id_sound)+".mp3");
                    }
                   final MediaPlayer m2=m;
                    Intent intent = new Intent(Activity_Regition_Qaraan.this, Activity_Regition_Qaraan_Show.class);
                    intent.putExtra("class1",My_class);
                            m2.start();
                    startActivity(intent);
                    finish();

                }
            });

            return view;
        }
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
MediaPlayer getMedia(String path)
{
    MediaPlayer m = new MediaPlayer();
    try {
        AssetFileDescriptor descriptor = getAssets().openFd(path);
        m.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
        descriptor.close();
        m.prepare();
        m.setVolume(1f, 1f);
    } catch (IOException e) {
        e.printStackTrace();
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
    }
    return m;
}
}
