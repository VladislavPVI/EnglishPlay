package com.example.english;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class SinAct extends AppCompatActivity implements View.OnTouchListener,View.OnDragListener {

    DBHelper dbHelper;
    SQLiteDatabase database;
    Button Hint;
    private TextView text1,text2,text3,text4,text5,text6;
    ArrayMap<String, String> arrayMap = new ArrayMap<>();
    int countWin=0;
    private boolean flag = false;
    private int CounterWin=0;
    private int CounterLost=0;
    private TextView InfoText1;
    private TextView InfoText2;

    // имя файла настройки
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_WIN = "Синонимы_win";
    public static final String APP_PREFERENCES_LOST = "Синонимы_lost";
    public SharedPreferences mSettings;

    //When touched text gets dropped into either text4 or text5 or text6 then this method will be called
    @Override
    public boolean onDrag(View v, DragEvent event) {
        if (event.getAction()==DragEvent.ACTION_DROP) {
            //handle the dragged view being dropped over a target view
            TextView dropped = (TextView) event.getLocalState();
            TextView dropTarget = (TextView) v;
            //stop displaying the view where it was before it was dragged
            dropped.setVisibility(View.INVISIBLE);

            //if an item has already been dropped here, there will be different string
            String text = dropTarget.getText().toString();
            //if there is already an item here, set it back visible in its original place


            //update the text and color in the target view to reflect the data being dropped
            //   dropTarget.setText(dropped.getText());
            if (flag) {
                if (text.equals(arrayMap.get(dropped.getText().toString()))) {
                    dropTarget.setBackgroundColor(Color.GREEN);
                    countWin++;
                } else dropTarget.setBackgroundColor(Color.RED);
            } else {
                dropTarget.setBackgroundColor(Color.GRAY);
                if (text.equals(arrayMap.get(dropped.getText().toString()))) {
                    countWin++;
                }
            }
        }
        return true;
    }

    protected  void reset(){
        finish();
        startActivity(new Intent(SinAct.this, SinAct.class));
    }

    //When text1 or text2 or text3 gets clicked or touched then this method will be called
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN)
        {
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(null, shadowBuilder, v, 0);
            return true;
        }
        else return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_sin);

        ArrayList<Integer> count = new ArrayList<>();
        count.add(0); count.add(1); count.add(2);
        Collections.shuffle(count);
        dbHelper = new DBHelper(this);
        dbHelper.create_db();
        database = dbHelper.open();


        Cursor cur = database.query(DBHelper.TABLE_Syn, null, null, null, null, null, null, null);
        while (arrayMap.size()<3)
        {
            cur.moveToPosition((int) (Math.random() * cur.getCount()));
            arrayMap.put(cur.getString(1), cur.getString(2));
        }
        dbHelper.close();

        text1 = (TextView)findViewById(R.id.qw1);
        text2 = (TextView)findViewById(R.id.qw2);
        text3 = (TextView)findViewById(R.id.qw3);
        text4 = (TextView)findViewById(R.id.ans1);
        text5 = (TextView)findViewById(R.id.ans2);
        text6 = (TextView)findViewById(R.id.ans3);


        text1.setText(arrayMap.keyAt(count.get(0)));
        text2.setText(arrayMap.keyAt(count.get(1)));
        text3.setText(arrayMap.keyAt(count.get(2)));
        Collections.shuffle(count);
        text4.setText(arrayMap.valueAt(count.get(0)));
        text5.setText(arrayMap.valueAt(count.get(1)));
        text6.setText(arrayMap.valueAt(count.get(2)));

        //Setting touch and drag listeners
        text1.setOnTouchListener(this);
        text2.setOnTouchListener(this);
        text3.setOnTouchListener(this);
        text4.setOnDragListener(this);
        text5.setOnDragListener(this);
        text6.setOnDragListener(this);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_MULTI_PROCESS);
        InfoText1 = (TextView) findViewById(R.id.pWin);
        InfoText2 = (TextView) findViewById(R.id.pLost);
    }

    public void cancel(View view) {
        text1.setVisibility(View.VISIBLE);
        text2.setVisibility(View.VISIBLE);
        text3.setVisibility(View.VISIBLE);
        text4.setBackgroundColor(Color.YELLOW);
        text5.setBackgroundColor(Color.YELLOW);
        text6.setBackgroundColor(Color.YELLOW);
        countWin =0;
    }

    public void skip(View view) {
        if (countWin==3)  {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "You Won!", Toast.LENGTH_SHORT);
            toast.show();
            CounterWin+=100;
            reset();
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "You Lost!", Toast.LENGTH_SHORT);
            toast.show();
            CounterLost+=100;
            reset();
        }
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

    public void hints(View view) {
        flag = true;
        this.findViewById(R.id.hintS).setVisibility(View.GONE);
    }
}
