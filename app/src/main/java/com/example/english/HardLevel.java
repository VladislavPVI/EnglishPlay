package com.example.english;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


public class HardLevel extends AppCompatActivity {
    Button[] buttons;
    TextView textView;
    private int CounterWin=0;
    private int CounterLost=0;
    private TextView InfoText1;
    private TextView InfoText2;

    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_WIN = "Предложении_win";
    public static final String APP_PREFERENCES_LOST = "Предложении_lost";
    public SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_hard_level);
        final Button btn1 = (Button) findViewById(R.id.hard_button);
        final Button btn2 = (Button) findViewById(R.id.hard_button2);
        final Button btn3 = (Button) findViewById(R.id.hard_button3);
        final Button btn4 = (Button) findViewById(R.id.hard_button4);
        final Button btn5 = (Button) findViewById(R.id.hard_button5);
        final Button btn6 = (Button) findViewById(R.id.hard_button6);
        final Button btn7 = (Button) findViewById(R.id.hard_button7);
        final Button btn8 = (Button) findViewById(R.id.dict3);
        final Button btn9 = (Button) findViewById(R.id.dict2);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_MULTI_PROCESS);
        InfoText1 = (TextView) findViewById(R.id.hlwin);
        InfoText2 = (TextView) findViewById(R.id.hllost);

        buttons = new Button[]{btn1, btn2, btn3, btn7, btn8, btn9, btn4, btn5, btn6 };
        final Random random = new Random();
        String[] allPhrases = getResources().getStringArray(R.array.phrases);
        Integer tmp = random.nextInt(allPhrases.length);
        final String myPhrase = allPhrases[tmp];

        textView = (TextView) findViewById(R.id.hard_textView2);


        final String[] myPhase2 = myPhrase.split(" ");
        ArrayList<String> forDelete = new ArrayList<String>();
        for (int ik = 0; ik < myPhase2.length; ik++) {
            forDelete.add(myPhase2[ik]);
        }


        for (int ik = 0; ik < myPhase2.length; ik++) {
            tmp = random.nextInt(forDelete.size());
            buttons[ik].setText(forDelete.get(tmp));
            forDelete.remove(forDelete.get(tmp));
        }

        for (int i = 0; i <= 8; i++) {
            if (buttons[i].getText().equals("New Button"))
                buttons[i].setVisibility(View.INVISIBLE);
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.hard_button:
                        textView.setText(textView.getText() + " " + btn1.getText());
                        btn1.setEnabled(false);
                        break;
                    case R.id.hard_button2:
                        textView.setText(textView.getText() + " " + btn2.getText());
                        btn2.setEnabled(false);
                        break;
                    case R.id.hard_button3:
                        textView.setText(textView.getText() + " " + btn3.getText());
                        btn3.setEnabled(false);
                        break;
                    case R.id.hard_button4:
                        textView.setText(textView.getText() + " " + btn4.getText());
                        btn4.setEnabled(false);
                        break;
                    case R.id.hard_button5:
                        textView.setText(textView.getText() + " " + btn5.getText());
                        btn5.setEnabled(false);
                        break;
                    case R.id.hard_button6:
                        textView.setText(textView.getText() + " " + btn6.getText());
                        btn6.setEnabled(false);
                        break;
                    case R.id.hard_button7:
                        textView.setText(textView.getText() + " " + btn7.getText());
                        btn7.setEnabled(false);
                        break;
                    case R.id.dict3:
                        textView.setText(textView.getText() + " " + btn8.getText());
                        btn8.setEnabled(false);
                        break;
                    case R.id.dict2:
                        textView.setText(textView.getText() + " " + btn9.getText());
                        btn9.setEnabled(false);
                        break;
                }
            }
        };
        for ( int i =0; i<=8; i++)
            buttons[i].setOnClickListener(onClickListener);
        final Toast toast = Toast.makeText(getApplicationContext(),"True!", Toast.LENGTH_SHORT);
        final Toast toast2 = Toast.makeText(getApplicationContext(),"Wrong!", Toast.LENGTH_SHORT);
        Button buttonk = (Button)findViewById(R.id.hard_button10);
        buttonk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textJoke=textView.toString();
                String[] tmp2 = textJoke.split(" ");

                if(textView.getText().equals("  "+myPhrase)) {
                    CounterWin+=150;
                    toast.show();
                }
                else {
                    CounterLost+=150;
                    toast2.show();
                }
                reset();
            }
        });
    }

    protected void reset() {
        finish();
        startActivity(new Intent(HardLevel.this, HardLevel.class));
    }

    public void cancel(View view) {
        for (int ik = 0; ik < buttons.length; ik++) {
            buttons[ik].setEnabled(true);;
        }
        textView.setText("");
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
