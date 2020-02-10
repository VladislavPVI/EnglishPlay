package com.example.english;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChangeLevel extends Activity {

    private int CounterWin=0;
    private int CounterLost=0;

    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_WIN = "Предложении_win";
    public static final String APP_PREFERENCES_LOST = "Предложении_lost";
    public SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_MULTI_PROCESS);

        setContentView(R.layout.activity_change_level);
        final ImageView imageView = (ImageView)findViewById(R.id.image1level);
        imageView.post(new Runnable() {
            @Override
            public void run() {
                ((AnimationDrawable)imageView.getBackground()).start();
            }
        });

    }

    public void Hard(View view) {
        if(CounterWin>500)
        {
            Intent intent = new Intent(ChangeLevel.this,TwoLevel.class);
            startActivity(intent);
        }
        else Toast.makeText(getApplicationContext(),
                "Набери очки в простом уровне!", Toast.LENGTH_SHORT).show();

    }

    public void Simple(View view) {
        Intent intent = new Intent(ChangeLevel.this,oneLevel.class);
        startActivity(intent);
    }

    public void vid1(View view) {
        Intent intent = new Intent(ChangeLevel.this, videoA2.class);
        startActivity(intent);
    }

    public void vid2(View view) {
        if(CounterWin>500)
        {
            Intent intent = new Intent(ChangeLevel.this, videoA3.class);
            startActivity(intent);
        }
        else Toast.makeText(getApplicationContext(),
                "Набери очки в простом уровне!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Date d = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("d.M.yyyy");
        String my=APP_PREFERENCES_WIN+" "+format1.format(d);
        if (mSettings.contains(my)) {
            // Получаем число из настроек
            CounterWin = mSettings.getInt(my, 0);
        }

    }
}
