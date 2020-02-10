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

public class oneLevel extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_startvideo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_one_level);
        final ImageView imageView = (ImageView)findViewById(R.id.image_one_level);
        imageView.post(new Runnable() {
            @Override
            public void run() {
                ((AnimationDrawable)imageView.getBackground()).start();
            }
        });

    }

    public void butSimple11(View view) {
        Intent intent = new Intent(oneLevel.this,MainGame.class);
        startActivity(intent);
    }

    public void butSimple33(View view) {
        Intent intent = new Intent(oneLevel.this,Dict.class);
        startActivity(intent);
    }

    public void butSimple22(View view) {
        Intent intent = new Intent(oneLevel.this,GameCatch2.class);
        startActivity(intent);
    }
}
