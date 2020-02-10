package com.example.english;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
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
import java.util.Locale;

import static com.example.english.DBHelper.*;
import static com.example.english.R.id.*;


public class MainGame extends Activity implements
        TextToSpeech.OnInitListener  {
    DBHelper dbHelper;
    SQLiteDatabase database;
    ArrayList<Integer> answer = new ArrayList<>();
    ArrayList<Integer> sent = new ArrayList<>();
    ArrayList<String> output = new ArrayList<>();
    String hint ="";
    boolean zn = false;
    ArrayList<Integer> answer1 = new ArrayList<>();

    static int flag;
    private TextToSpeech mTTS;
    private int CounterWin=0;
    private int CounterLost=0;
    private TextView InfoText1;
    private TextView InfoText2;

    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_WIN = "Предложении_win";
    public static final String APP_PREFERENCES_LOST = "Предложении_lost";
    public SharedPreferences mSettings;


    private static final int[][][] lexan = {
            {{1, 8, 11, 15, 17}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {8}, {8}, {0}, {4}, {0}, {0}, {4}},
            {{8, 11, 3, 15, 17}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {8}, {8}, {0}, {0}, {0}, {4}, {0}},
            {{11, 2, 13, 16, 17}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {8}, {8}, {0}, {4}, {0}, {4}},
            {{19}, {9}, {9}, {9}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}},
            {{21}, {4}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}},
            {{24}, {4}, {4}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}},
            {{22}, {0}, {4}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}},
            {{23}, {0}, {0}, {4}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}},
            {{23}, {0}, {0}, {4}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}},
            {{0}, {0}, {0}, {0}, {0}, {0}, {0}, {4}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}},
            {{0}, {0}, {0}, {0}, {6}, {6}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}},
            {{0}, {0}, {0}, {0}, {5}, {0}, {0}, {0}, {9}, {0}, {0}, {12}, {0}, {14}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}},
            {{0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {10}, {10}, {0}, {0}, {10}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}},
            {{0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {10}, {10}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}},
            {{0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {10}, {10}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}},
            {{18}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}},
            {{18}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {8}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}},
            {{20}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}},
            {{20}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {8}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}},
            {{19}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}},
            {{19}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {8}, {0}, {0}, {0}, {0}, {0}, {0}, {0}},
            {{0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {10}, {10}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}},


    };

    private static boolean checkZn(int oneS, boolean zn) {
        if (oneS == 3 | oneS == 4 | oneS == 5 | oneS == 6 | oneS == 7 | oneS == 8 | oneS == 15 | oneS == 16 | oneS == 17 | oneS == 18 | oneS == 19 | oneS == 20) {
            return zn;
        } else return !zn;
    }

    private static boolean syntAN(int[][][] lexan, ArrayList<Integer> answer) {
        int[] newSost;
        int goingSostD;
        int newSostD = 0;
        int numOfLexType = 0;
        while (numOfLexType < answer.size()) {
            int check = 0;
            goingSostD = newSostD;
            newSost = lexan[answer.get(numOfLexType)][goingSostD];
            for (int aNewSost : newSost) {
                int k = 0;
                while (k < lexan[answer.get(numOfLexType)][aNewSost].length) {
                    if (numOfLexType + 1 == answer.size()) return aNewSost == 6 || aNewSost == 10;
                    if (lexan[answer.get(numOfLexType + 1)][aNewSost][k] != 0) {
                        newSostD = aNewSost;
                        check = 1;
                    }
                    k++;
                }
            }
            if (check == 0) return false;
            numOfLexType++;
        }
        return false;
    }

    private ArrayList<Integer> generationOfSent(int[][][] lexan) {
        ArrayList<Integer> sent = new ArrayList<>();
        int goingW;
        int type = 9;
        while (lexan[type][0][0] == 0)
            type = (int) (Math.random() * lexan.length);
        sent.add(type);
        int[] newSost;
        goingW = lexan[type][0][(int) (Math.random() * lexan[type][0].length)];
        while (goingW != 10 && goingW != 6) {
            type = (int) (Math.random() * lexan.length);
            newSost = lexan[type][goingW];
            while (newSost[0] == 0) {
                type = (int) (Math.random() * lexan.length);
                newSost = lexan[type][goingW];
            }
            goingW = newSost[(int) (Math.random() * newSost.length)];
            sent.add(type);
            //buttToText.put(type, return_of_word(type));
        }
        //buttToText.se
        flag = sent.size();
        while (sent.size() < 8) {
            sent.add((int) (Math.random() * lexan.length));
            while (Collections.frequency(sent, sent.get(sent.size()-1)) > 1)
                sent.set(sent.size()-1,(int) (Math.random() * lexan.length));
        }
        //Collections.shuffle(sent);
        return sent;
    }

    private void inst_text(int name_of_button, String text) {
        TextView mHelloTextView = (TextView) findViewById(name_of_button);
        mHelloTextView.setText(text);
    }

    private String return_of_word(int id) {
        switch (id) {
            case 0:
                return "i";

            case 1:
                switch ((int) (Math.random() * 3)) {
                    case 0:
                        return "you";
                    case 1:
                        return "we";
                    case 2:
                        return "they";
                }
            case 2:
                switch ((int) (Math.random() * 3)) {
                    case 0:
                        return "he";
                    case 1:
                        return "she";
                    case 2:
                        return "it";
                }
            case 3:
                switch ((int) (Math.random() * 2)) {
                    case 0:
                        return "will";
                    case 1:
                        return "won't";
                }
            case 4:
                switch ((int) (Math.random() * 2)) {
                    case 0:
                        return "am";
                    case 1:
                        return "am not";
                }
            case 5:
                switch ((int) (Math.random() * 2)) {
                    case 0:
                        return "was";
                    case 1:
                        return "wasn't";
                }
            case 6:
                switch ((int) (Math.random() * 2)) {
                    case 0:
                        return "is";
                    case 1:
                        return "isn't";
                }
            case 7:
                switch ((int) (Math.random() * 2)) {
                    case 0:
                        return "are";
                    case 1:
                        return "aren't";
                }
            case 8:
                switch ((int) (Math.random() * 2)) {
                    case 0:
                        return "were";
                    case 1:
                        return "weren't";
                }
            case 9:
                return "be";
            case 10:
                Cursor cur = database.query(DBHelper.TABLE_Adj, null, null, null, null, null, null, null);
                cur.moveToPosition((int) (Math.random() * cur.getCount()));
                return cur.getString(1);
            case 11:
                Cursor cur1 = database.query(TABLE_Adv, null, null, null, null, null, null, null);
                cur1.moveToPosition((int) (Math.random() * cur1.getCount()));
                return cur1.getString(1);
            case 12:
                Cursor cur2 = database.query(TABLE_Verb, null, null, null, null, null, null, null);
                cur2.moveToPosition((int) (Math.random() * cur2.getCount()));
                return cur2.getString(1);
            case 13:
                Cursor cur3 = database.query(TABLE_Verb, null, null, null, null, null, null, null);
                cur3.moveToPosition((int) (Math.random() * cur3.getCount()));
                return cur3.getString(5);
            case 14:
                Cursor cur4 = database.query(TABLE_Verb, null, null, null, null, null, null, null);
                cur4.moveToPosition((int) (Math.random() * cur4.getCount()));
                return cur4.getString(3);
            case 15:
                return "do";
            case 16:
                return "don't";
            case 17:
                return "does";
            case 18:
                return "doesn't";
            case 19:
                return "did";
            case 20:
                return "didn't";
            case 21:
                Cursor cur5 = database.query(TABLE_Verb, null, null, null, null, null, null, null);
                cur5.moveToPosition((int) (Math.random() * cur5.getCount()));
                return cur5.getString(3);


        }
        return null;
    }

    protected void transLtoword(ArrayList<Integer> sent) {
        for (int i=0;i<8;i++){
            output.add(return_of_word(sent.get(i)));
            answer1.add(i);
            if (i<flag) hint += output.get(i)+" ";
        }

        Collections.shuffle(answer1);
       for (int i =0; i <8; i++)
        inst_text(button59, output.get(answer1.get(0)));
        inst_text(button58, output.get(answer1.get(1)));
        inst_text(button57, output.get(answer1.get(2)));
        inst_text(button56, output.get(answer1.get(3)));
        inst_text(button55, output.get(answer1.get(4)));
        inst_text(button54, output.get(answer1.get(5)));
        inst_text(button51, output.get(answer1.get(6)));
        inst_text(button50, output.get(answer1.get(7)));
    }

    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main_game);
        dbHelper = new DBHelper(this);
        dbHelper.create_db();
        findViewById(Output).setClickable(false);
        database = dbHelper.open();
        sent = generationOfSent(lexan);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_MULTI_PROCESS);
        InfoText1 = (TextView) findViewById(R.id.ppWin);
        InfoText2 = (TextView) findViewById(R.id.ppLost);


        transLtoword(sent);
        dbHelper.close();
        mTTS = new TextToSpeech(this, this);

    }

protected  void reset(){
    finish();
    startActivity(new Intent(MainGame.this, MainGame.class));
}
    public void onButton1(View view) {
        Button butTEXT = (Button) findViewById(Output);
        inst_text(Output, butTEXT.getText().toString() + output.get(answer1.get(0)) + " ");
        answer.add(sent.get(answer1.get(0)));
        mTTS.speak(output.get(answer1.get(0)), TextToSpeech.QUEUE_FLUSH, null);
        view.setEnabled(false);
    }

    public void onButton2(View view) {
        Button butTEXT = (Button) findViewById(Output);
        inst_text(Output, butTEXT.getText().toString() + output.get(answer1.get(1)) + " ");
        answer.add(sent.get(answer1.get(1)));
        mTTS.speak(output.get(answer1.get(1)), TextToSpeech.QUEUE_FLUSH, null);
        view.setEnabled(false);
    }

    public void onButton3(View view) {
        Button butTEXT = (Button) findViewById(Output);
        inst_text(Output, butTEXT.getText().toString() + output.get(answer1.get(2)) + " ");
        answer.add(sent.get(answer1.get(2)));
        mTTS.speak(output.get(answer1.get(2)), TextToSpeech.QUEUE_FLUSH, null);
        view.setEnabled(false);

    }

    public void onButton4(View view) {
        Button butTEXT = (Button) findViewById(Output);
        inst_text(Output, butTEXT.getText().toString() + output.get(answer1.get(3)) + " ");
        answer.add(sent.get(answer1.get(3)));
        mTTS.speak(output.get(answer1.get(3)), TextToSpeech.QUEUE_FLUSH, null);
        view.setEnabled(false);
    }

    public void onButton5(View view) {
        Button butTEXT = (Button) findViewById(Output);
        inst_text(Output, butTEXT.getText().toString() + output.get(answer1.get(4)) + " ");
        answer.add(sent.get(answer1.get(4)));
        mTTS.speak(output.get(answer1.get(4)), TextToSpeech.QUEUE_FLUSH, null);
        view.setEnabled(false);
    }

    public void onButton6(View view) {
        Button butTEXT = (Button) findViewById(Output);
        inst_text(Output, butTEXT.getText().toString() + output.get(answer1.get(5)) + " ");
        answer.add(sent.get(answer1.get(5)));
        mTTS.speak(output.get(answer1.get(5)), TextToSpeech.QUEUE_FLUSH, null);
        view.setEnabled(false);
    }

    public void onButton7(View view) {
        Button butTEXT = (Button) findViewById(Output);
        inst_text(Output, butTEXT.getText().toString() + output.get(answer1.get(6)) + " ");
        answer.add(sent.get(answer1.get(6)));
        mTTS.speak(output.get(answer1.get(6)), TextToSpeech.QUEUE_FLUSH, null);
        view.setEnabled(false);
    }

    public void onButton8(View view) {
        Button butTEXT = (Button) findViewById(Output);
        inst_text(Output, butTEXT.getText().toString() + output.get(answer1.get(7)) + " ");
        answer.add(sent.get(answer1.get(7)));
        mTTS.speak(output.get(answer1.get(7)), TextToSpeech.QUEUE_FLUSH, null);
        view.setEnabled(false);
    }

    public void Que(View view) {
        Button butTEXT = (Button) findViewById(Output);
        inst_text(Output, butTEXT.getText().toString() + "?");
        zn = true;
        view.setEnabled(false);
    }

    public void Point(View view) {
        Button butTEXT = (Button) findViewById(Output);
        inst_text(Output, butTEXT.getText().toString() + ".");
        view.setEnabled(false);
    }


    public void Delete(View view) {
        inst_text(Output, "");
        answer.clear();
        findViewById(button59).setEnabled(true);
        findViewById(button58).setEnabled(true);
        findViewById(button57).setEnabled(true);
        findViewById(button56).setEnabled(true);
        findViewById(button55).setEnabled(true);
        findViewById(button54).setEnabled(true);
        findViewById(button51).setEnabled(true);
        findViewById(button50).setEnabled(true);
        findViewById(button53).setEnabled(true);
        findViewById(button52).setEnabled(true);

    }

    public void Check(View view) {
        String voice="";
        Toast toast = Toast.makeText(getApplicationContext(),
                "Молодец!", Toast.LENGTH_SHORT);

        Toast toast1 = Toast.makeText(getApplicationContext(),
                "Ты сделал ошибку!", Toast.LENGTH_SHORT);


        if (!answer.isEmpty()) {
            if (syntAN(lexan, answer) && checkZn(answer.get(0), zn)) {
                toast.show();
                voice="Well done!";
                CounterWin+=100;
            }
            else {
                toast1.show();
                voice="You are wrong!";
                CounterLost+=100;
            }
            mTTS.speak(voice, TextToSpeech.QUEUE_FLUSH, null);
            reset();
        }


    }

    public void Restart(View view) {
        onDestroy();
        reset();
    }

    public void Hint(View view) {

        Toast toast = Toast.makeText(getApplicationContext(),
                hint, Toast.LENGTH_SHORT);
        toast.show();
        view.setEnabled(false);
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown mTTS!
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            Locale locale = new Locale("eng");
            int result = mTTS.setLanguage(locale);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Извините, этот язык не поддерживается");
            }

        } else {
            Log.e("TTS", "Ошибка!");
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
}

