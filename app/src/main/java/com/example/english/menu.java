package com.example.english;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class menu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_menu);
        startService(new Intent(this, BackM.class));
        final ImageView imageView = (ImageView)findViewById(R.id.imageMen);
        imageView.post(new Runnable() {
            @Override
            public void run() {
                ((AnimationDrawable)imageView.getBackground()).start();
            }
        });

    }
    public void onBackPressed() {
        stopService(new Intent(this, BackM.class));
        finishAffinity();
    }
    public void onButton1(View view) {
        Intent intent = new Intent(menu.this,Main2Activity.class);
        startActivity(intent);
    }

    public void statist(View view) {
        Intent intent = new Intent(menu.this,Statistic.class);
        startActivity(intent);
    }

    public void levels(View view) {
        Intent intent = new Intent(menu.this,ChangeLevel.class);
        startActivity(intent);
    }

    public void bonus(View view) {
        Intent intent = new Intent(menu.this,bonus.class);
        startActivity(intent);
    }

    public void about(View view) {
        Intent intent = new Intent(menu.this,about.class);
        startActivity(intent);
    }

    public void history(View view) {
        Intent intent = new Intent(menu.this, videoA1.class);
        startActivity(intent);
    }
}
