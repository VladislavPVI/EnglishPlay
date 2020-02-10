package com.example.english;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView)findViewById(R.id.textStart);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.myanim);
        textView.startAnimation(anim);
    }

    public void bStart(View view) {
        Intent intent = new Intent(MainActivity.this,startvideo.class);
        startActivity(intent);
    }
    public void onBackPressed() {
        stopService(new Intent(this, BackM.class));
        finish();
    }
}
