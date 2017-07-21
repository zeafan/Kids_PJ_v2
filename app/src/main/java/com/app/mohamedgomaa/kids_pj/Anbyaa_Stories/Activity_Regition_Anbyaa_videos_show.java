package com.app.mohamedgomaa.kids_pj.Anbyaa_Stories;

import android.support.v4.view.ViewCompat;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import com.app.mohamedgomaa.kids_pj.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
public class Activity_Regition_Anbyaa_videos_show extends YouTubeBaseActivity {
     YouTubePlayerView youtubeV;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.
                FLAG_FULLSCREEN);
        setContentView(R.layout.activity__regition__anbyaa_videos_show);
        ViewCompat.setLayoutDirection(findViewById(R.id.Activity_Regition_Anbyaa_video_show), ViewCompat.LAYOUT_DIRECTION_LTR);
        youtubeV=(YouTubePlayerView)findViewById(R.id.youtube_video);
        onInitializedListener=new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
            String id_video=getIntent().getExtras().getString("id_video");
                youTubePlayer.loadVideo(id_video);
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        youtubeV.initialize(getResources().getString(R.string.API_key),onInitializedListener);
    }
    }
