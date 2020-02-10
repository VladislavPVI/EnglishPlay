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

public class TwoLevel extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_startvideo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_two_level);
        final ImageView imageView = (ImageView)findViewById(R.id.image_two_level);
        imageView.post(new Runnable() {
            @Override
            public void run() {
                ((AnimationDrawable)imageView.getBackground()).start();
            }
        });
    }

    public void butHard11(View view) {
        Intent intent = new Intent(TwoLevel.this,HardLevel.class);
        startActivity(intent);
    }

    public void butHard33(View view) {

    Intent intent = new Intent(TwoLevel.this,SinAct.class);
    startActivity(intent);}

    public void butHard22(View view) {
        Intent intent = new Intent(TwoLevel.this,GameCatch.class);
        startActivity(intent);
    }
}
