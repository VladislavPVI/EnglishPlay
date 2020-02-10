package com.example.english;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class videoA1 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        stopService(new Intent(this, BackM.class));
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_a1);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        VideoView videoView = (VideoView) findViewById(R.id.videoP);
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() +"/"+R.raw.vid1));
        videoView.setMediaController(new MediaController(this));
        videoView.requestFocus(0);
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                Intent intent = new Intent(videoA1.this,ChangeLevel.class);
                startActivity(intent);
            }
        });
    }
}
