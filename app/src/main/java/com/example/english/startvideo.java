package com.example.english;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class startvideo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_startvideo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        VideoView videoView = (VideoView) findViewById(R.id.videoStart);
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() +"/"+R.raw.starter));
        videoView.setMediaController(new MediaController(this));
        videoView.requestFocus(0);
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                Intent intent = new Intent(startvideo.this,menu.class);
                startActivity(intent);
            }
        });
    }

    public void onSkin(View view) {
        Intent intent = new Intent(startvideo.this,menu.class);
        startActivity(intent);
    }
}
