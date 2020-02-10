package com.example.english;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Statistic extends AppCompatActivity {
    String selectedDate="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_statistic);
        TextView Textz = (TextView) findViewById(R.id.status0);
        Textz.setText("select the date for which you want to know the statistics");

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendar);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year,
                                            int month, int dayOfMonth) {
                String APP_PREFERENCES = "mysettings";
                SharedPreferences mSettings;
                mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_MULTI_PROCESS);

                String APP_PREFERENCES_WIN = "Словарь_win";
                String APP_PREFERENCES_LOST = "Словарь_lost";
                String info="";
                selectedDate = dayOfMonth+"."+(month+1)+"."+year;
                TextView Text0 = (TextView) findViewById(R.id.status0);
                Text0.setText("selectedDate "+selectedDate);
                String my = APP_PREFERENCES_WIN +" "+ selectedDate;
                if (mSettings.contains(my)) {
                    // Получаем число из настроек
                    int Win = mSettings.getInt(my, 0);
                    // Выводим на экран данные из настроек
                    info=APP_PREFERENCES_WIN+" "+Win+" points";
                }
                else info=APP_PREFERENCES_WIN+" "+" Null";
                TextView Text1 = (TextView) findViewById(R.id.status1);
                Text1.setText(info);
                info="";
                selectedDate = dayOfMonth+"."+(month+1)+"."+year;
                info=info+" "+selectedDate;
                my = APP_PREFERENCES_LOST +" "+ selectedDate;
                if (mSettings.contains(my)) {
                    // Получаем число из настроек
                    int Lost = mSettings.getInt(my, 0);
                    info=APP_PREFERENCES_LOST+" "+Lost+" points";
                }
                else info=APP_PREFERENCES_LOST+" "+" Null";
                TextView Text2 = (TextView) findViewById(R.id.status2);
                Text2.setText(info);

                APP_PREFERENCES_WIN = "Синонимы_win";
                APP_PREFERENCES_LOST = "Синонимы_lost";
                info="";
                my = APP_PREFERENCES_WIN +" "+ selectedDate;
                if (mSettings.contains(my)) {
                    // Получаем число из настроек
                    int Win = mSettings.getInt(my, 0);
                    // Выводим на экран данные из настроек
                    info=APP_PREFERENCES_WIN+" "+Win+" points";
                }
                else info=APP_PREFERENCES_WIN+" "+" Null";
                TextView Text3 = (TextView) findViewById(R.id.status3);
                Text3.setText(info);
                info="";
                selectedDate = dayOfMonth+"."+(month+1)+"."+year;
                my = APP_PREFERENCES_LOST +" "+ selectedDate;
                if (mSettings.contains(my)) {
                    // Получаем число из настроек
                    int Lost = mSettings.getInt(my, 0);
                    info=APP_PREFERENCES_LOST+" "+Lost+" points";
                }
                else info=APP_PREFERENCES_LOST+" "+" Null";
                TextView Text4 = (TextView) findViewById(R.id.status4);
                Text4.setText(info);


                APP_PREFERENCES_WIN = "Предложении_win";
                APP_PREFERENCES_LOST = "Предложении_lost";
                info="";
                my = APP_PREFERENCES_WIN +" "+ selectedDate;
                if (mSettings.contains(my)) {
                    // Получаем число из настроек
                    int Win = mSettings.getInt(my, 0);
                    // Выводим на экран данные из настроек
                    info=APP_PREFERENCES_WIN+" "+Win+" points";
                }
                else info=APP_PREFERENCES_WIN+" "+" Null";
                TextView Text5 = (TextView) findViewById(R.id.status5);
                Text5.setText(info);
                info="";
                selectedDate = dayOfMonth+"."+(month+1)+"."+year;
                my = APP_PREFERENCES_LOST +" "+ selectedDate;
                if (mSettings.contains(my)) {
                    // Получаем число из настроек
                    int Lost = mSettings.getInt(my, 0);
                    info=APP_PREFERENCES_LOST+" "+Lost+" points";
                }
                else info=APP_PREFERENCES_LOST+" "+" Null";
                TextView Text6 = (TextView) findViewById(R.id.status6);
                Text6.setText(info);
            }
        });
    }
}
