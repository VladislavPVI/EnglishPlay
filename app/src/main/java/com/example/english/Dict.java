package com.example.english;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Dict extends AppCompatActivity {

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;

    ImageView imageView;
    DBHelper dbHelper;
    SQLiteDatabase database;
    ArrayList<String> vars = new ArrayList<>();
    ArrayList<String> adrs = new ArrayList<>();
    int randval;
    private int CounterWin=0;
    private int CounterLost=0;
    private TextView InfoText1;
    private TextView InfoText2;

    // имя файла настройки
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_WIN = "Словарь_win";
    public static final String APP_PREFERENCES_LOST = "Словарь_lost";
    public SharedPreferences mSettings;

    private void inst_text(int name_of_button, String text) {
        TextView mTextView = (TextView) findViewById(name_of_button);
        mTextView.setText(text);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_dict);

        imageView = (ImageView) this.findViewById(R.id.imageView);

        this.button1 = (Button) this.findViewById(R.id.button1);
        this.button2 = (Button) this.findViewById(R.id.button2);
        this.button3 = (Button) this.findViewById(R.id.button3);
        this.button4 = (Button) this.findViewById(R.id.butres);

        dbHelper = new DBHelper(this);
        dbHelper.create_db();
        database = dbHelper.open();


        inst_text(R.id.button1, return_of_word());
        inst_text(R.id.button2, return_of_word());
        inst_text(R.id.button3, return_of_word());

        this.button1.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                showImage1();
            }
        });

        this.button2.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                showImage2();
            }
        });

        this.button3.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImage3();
            }
        });

        this.button4.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatingDialog();
            }
        });

        randval=(int)(Math.random() * 3);
        showImage(randval);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_MULTI_PROCESS);
        InfoText1 = (TextView) findViewById(R.id.progressWin);
        InfoText2 = (TextView) findViewById(R.id.progressLost);
    }


    private String return_of_word() {
        Cursor cur = database.query(DBHelper.TABLE_Noun, null, null, null, null, null, null, null);
        cur.moveToPosition((int) (Math.random() * cur.getCount()));
        if(vars.size()!=0) {
            boolean flag=false;
            while (!flag) {
                for (String lol : vars) {
                    if (lol.equals(cur.getString(1))) {
                        flag = false;
                        cur.moveToPosition((int) (Math.random() * cur.getCount()));
                        break;
                    } else flag = true;
                }
            }
        }
        vars.add(cur.getString(1));
        adrs.add(cur.getString(5));
        dbHelper.close();
        return cur.getString(1);
    }

    public void showRatingDialog() {
        final AlertDialog.Builder ratingdialog = new AlertDialog.Builder(this);
        ratingdialog.setTitle("Рейтинг!");

        View linearlayout = getLayoutInflater().inflate(R.layout.ratingdialog, null);
        ratingdialog.setView(linearlayout);

        final RatingBar rating = (RatingBar)linearlayout.findViewById(R.id.ratingbar);
        rating.setVisibility(View.VISIBLE);
        if(CounterWin!=0) {
            double res = (int)(((CounterWin) * 5.0) / ((CounterWin + CounterLost))*10)*0.1;

            rating.setRating((float)(res));
            Toast toast = Toast.makeText(getApplicationContext(),
                    " рейтинг "+ ((float)res), Toast.LENGTH_SHORT);
            toast.show();
        }
        else rating.setRating(0);
        ratingdialog.create();
        ratingdialog.show();
    }

    private void showImage(int id) {
        if(adrs.get(id).equals(vars.get(id))) {
            String filename = adrs.get(id);
            int resID = getResources().getIdentifier(filename, "drawable", getPackageName());
            imageView.setImageResource(resID);
        }
        else
        {
            Bitmap bitmap1 = null;
            Uri uri = Uri.parse(adrs.get(id));
            try {
                bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {

            }
            imageView.setImageBitmap(bitmap1);
        }
    }
    private void showImage1() {
        if(randval==0)
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "You Won!", Toast.LENGTH_SHORT);
            toast.show();
            CounterWin+=100;
        }
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "You Lost!", Toast.LENGTH_SHORT);
            toast.show();
            CounterLost+=100;
        }
        reset();
    }

    private void showImage2() {
        if(randval==1)
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "You Won!", Toast.LENGTH_SHORT);
            toast.show();
            CounterWin+=100;
        }
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "You Lost!", Toast.LENGTH_SHORT);
            toast.show();
            CounterLost+=100;
        }
        reset();
    }

    private void showImage3() {
        if(randval==2)
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "You Won!", Toast.LENGTH_SHORT);
            toast.show();
            CounterWin+=100;
        }
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "You Lost!", Toast.LENGTH_SHORT);
            toast.show();
            CounterLost+=100;
        }
        reset();
    }
    protected  void reset(){
        finish();
        startActivity(new Intent(Dict.this, Dict.class));
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
            // Выводим на экран данные из настроек
            InfoText1.setText("Today WIN points " + CounterWin );
        }
        my=APP_PREFERENCES_LOST+" "+format1.format(d);
        if (mSettings.contains(my)) {
            // Получаем число из настроек
            CounterLost = mSettings.getInt(my, 0);
            // Выводим на экран данные из настроек
            InfoText2.setText("Today LOST points " + CounterLost );
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Запоминаем данные
        Date d = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("d.M.yyyy");
        SharedPreferences.Editor editor = mSettings.edit();
        String my=APP_PREFERENCES_WIN+" "+format1.format(d);
        editor.putInt(my, CounterWin);
        my=APP_PREFERENCES_LOST+" "+format1.format(d);
        editor.putInt(my, CounterLost);
        editor.apply();
    }
}