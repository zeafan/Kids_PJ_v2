package com.app.mohamedgomaa.kids_pj.Qaraan;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.mohamedgomaa.kids_pj.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
    class Record_class extends BaseAdapter {
        DatabaseHelper_RecordList db_record;
        Resources resources;
        LayoutInflater layoutInflater;
        static MediaPlayer mediaPlayer=new MediaPlayer();
        ArrayList<Activity_Regition_Qaraan_Record_item> List_item = new ArrayList<>();
        Context _con;
        public Record_class(Context con,Resources res) {
            db_record=new DatabaseHelper_RecordList(con);
            List_item = db_record.get_All_Data();
            _con=con;
            this.resources=res;
        }

        @Override
        public int getCount() {
            return List_item.size();
        }

        @Override
        public Object getItem(int position) {
            return List_item.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        boolean check_Play = true;

        @Override
        public View getView(int position, final View convertView, ViewGroup parent) {
            final int pos = position;
            LinearLayout layout;
            layoutInflater= (LayoutInflater)_con.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view_Row = layoutInflater.inflate(R.layout.activity__regition__qaraan_record_rows_item, null);
            TextView name_soora_txtV = (TextView) view_Row.findViewById(R.id.id_soora_name);
            layout = (LinearLayout) view_Row.findViewById(R.id.layer_id_relotion_qaraan_row_record);
            Random r = new Random();
            int num = r.nextInt(6);
            layout.setBackgroundResource(resources.getIdentifier("row_soora" + String.valueOf(num + 1), "drawable",_con.getPackageName()));
            Typeface T_face2 = Typeface.createFromAsset(_con.getAssets(), "font1.otf");
            name_soora_txtV.setTypeface(T_face2);
            name_soora_txtV.setText(List_item.get(position).name_Soora);
            TextView number_soora = (TextView) view_Row.findViewById(R.id.id_soora_id);
            number_soora.setText(String.valueOf(List_item.get(position).id));
            final Button btn_Play = (Button) view_Row.findViewById(R.id.play_pause_record);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (check_Play) {
                        check_Play = false;
                        btn_Play.setBackgroundResource(android.R.drawable.ic_media_play);
                        mediaPlayer = new MediaPlayer();
                        try {
                            mediaPlayer.setDataSource(List_item.get(pos).Path_Soora);
                            mediaPlayer.prepare();
                        } catch (IOException e) {
                            Toast.makeText(_con, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        mediaPlayer.start();

                    } else {
                        check_Play = true;
                        btn_Play.setBackgroundResource(android.R.drawable.ic_media_pause);
                        if (mediaPlayer != null) {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                        }
                    }
                }
            });
            return view_Row;
        }
    }
