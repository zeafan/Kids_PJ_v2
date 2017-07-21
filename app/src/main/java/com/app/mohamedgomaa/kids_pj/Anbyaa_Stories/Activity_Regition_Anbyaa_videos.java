package com.app.mohamedgomaa.kids_pj.Anbyaa_Stories;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.mohamedgomaa.kids_pj.CheckConnection_Internet;
import com.app.mohamedgomaa.kids_pj.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Activity_Regition_Anbyaa_videos extends AppCompatActivity {
    RecyclerView recyclerView;
   ProgressDialog build;
    List<item_video> _mylist=new ArrayList<>();
    final String url="https://zeafancom.000webhostapp.com/kids_religion_getAllmovies.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.
                FLAG_FULLSCREEN);
        setContentView(R.layout.activity__regition__anbyaa_videos);
        ViewCompat.setLayoutDirection(findViewById(R.id.Activity_Regition_Anbyaa_video), ViewCompat.LAYOUT_DIRECTION_LTR);

        recyclerView=(RecyclerView)findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
        recyclerView.setHasFixedSize(true);
        if (new CheckConnection_Internet(this).IsConnection()) {
            build=new ProgressDialog(this);
            build.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            build.setMessage("أستنى ياحبيبى شويه بيحمل من النت");
            build.setCancelable(false);
            build.show();
            Singleton singleton = new Singleton(this);
            StringRequest StringReq = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        JSONArray JsonArry=jsonObject.getJSONArray("serverResponse_AllMovies");
                        for(int i=0;i<JsonArry.length();i++)
                        {
                            JSONObject J_Ob=JsonArry.getJSONObject(i);
                            _mylist.add(new item_video(J_Ob.getInt("id"),J_Ob.getString("photoLink"),J_Ob.getString("videoLink")));
                        }
                        RecyleAdapter_videos recyleAdapterVideos =new RecyleAdapter_videos(_mylist,Activity_Regition_Anbyaa_videos.this);
                        build.dismiss();
                        recyclerView.setAdapter(recyleAdapterVideos);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            singleton.addToRequestQueue(StringReq);
        } else {
            ImageView img=new ImageView(this);
            img.setImageResource(R.drawable.connect_internet);
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setView(img);
            builder.setCancelable(true);
            builder.show();
        }
    }

    public void Back_Main(View view) {
        startActivity(new Intent(Activity_Regition_Anbyaa_videos.this,Activity_Regition_Anbyaa.class));
        finish();
    }
}
