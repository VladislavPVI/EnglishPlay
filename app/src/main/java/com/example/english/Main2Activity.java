package com.example.english;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static android.widget.ImageView.ScaleType.CENTER_INSIDE;
import static com.example.english.DBHelper.TABLE_Adj;
import static com.example.english.DBHelper.TABLE_Adv;
import static com.example.english.DBHelper.TABLE_Noun;
import static com.example.english.DBHelper.TABLE_Syn;
import static com.example.english.DBHelper.TABLE_Verb;



public class Main2Activity extends AppCompatActivity implements View.OnClickListener {


    Button btnAdd, btninfo,btnsort,btnsort1,btnsort2,btndel,btnimg;
    EditText etValue, etTranslate,etVerb2,etVerb3,etVerb4;
    Spinner con1,Speech,con2;
    TableLayout table;
    DBHelper dbHelper;
    ImageView imageView;
    String imgadr="";
    String mytranslate="";
    static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main2);

        imageView = (ImageView) findViewById(R.id.imageView);


        btndel = (Button) findViewById(R.id.btDel);
        btndel.setOnClickListener(this);

        btnsort = (Button) findViewById(R.id.btnSort);
        btnsort.setOnClickListener(this);

        btnsort1 = (Button) findViewById(R.id.btnSort1);
        btnsort1.setOnClickListener(this);

        btnsort2 = (Button) findViewById(R.id.btnSort2);
        btnsort2.setOnClickListener(this);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btninfo = (Button) findViewById(R.id.btninfo);
        btninfo.setOnClickListener(this);

        btnimg = (Button) findViewById(R.id.btnimg);
        btnimg.setOnClickListener(this);

        etValue = (EditText) findViewById(R.id.etValue);
        etTranslate = (EditText) findViewById(R.id.etTranslate);
        etVerb2 = (EditText) findViewById(R.id.etVerb2);
        etVerb3 = (EditText) findViewById(R.id.etVerb3);
        etVerb4 = (EditText) findViewById(R.id.etVerb4);

        etVerb2.setVisibility(View.GONE);
        etVerb3.setVisibility(View.GONE);
        etVerb4.setVisibility(View.GONE);

        Speech = (Spinner) findViewById(R.id.PartOfSpeech);
        con1 = (Spinner) findViewById(R.id.con1);
        con2 = (Spinner) findViewById(R.id.con2);

        con1.setVisibility(View.GONE);
        con2.setVisibility(View.GONE);

        table = (TableLayout) findViewById(R.id.table);

        dbHelper = new DBHelper(this);
        // создаем базу данных
        dbHelper.create_db();
        setupSpinner();
        Toast toast = Toast.makeText(getApplicationContext(),
                "ВНИМАНИЕ!\nИзменения могут повлиять на работу программы!", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }

    public void MyCout1(Cursor cur) {
        int idIndex = cur.getColumnIndex(DBHelper.KEY_ID1); //вывод ID
        int valueIndex = cur.getColumnIndex(DBHelper.KEY_VALUE1);
        int translateIndex = cur.getColumnIndex(DBHelper.KEY_TRANSLATE1);
        int properties1 = cur.getColumnIndex(DBHelper.KEY_PROPERTIES1);
        int properties2 = cur.getColumnIndex(DBHelper.KEY_PROPERTIES2);
        int myimg = cur.getColumnIndex(DBHelper.KEY_IMG);
        imgadr="";
        if (cur.moveToFirst()) { //перемещение курсора на первую считанную строку

            do {
                TableRow row = new TableRow(getApplicationContext());
                TextView textName = new TextView(getApplicationContext());
                textName.setText(cur.getInt(idIndex) +
                        " , " + cur.getString(valueIndex) +
                        " , " + cur.getString(translateIndex) +
                        " ( " + cur.getString(properties1) +
                        " , " + cur.getString(properties2) + " )"
                );
                mytranslate=cur.getString(valueIndex);
                imgadr=cur.getString(myimg);
                row.addView(textName);
                table.addView(row);
            } while (cur.moveToNext());
        }
    }

    public void MyCout2(Cursor cur) {
        int idIndex = cur.getColumnIndex(DBHelper.KEY_ID2); //вывод ID
        int valueIndex = cur.getColumnIndex(DBHelper.KEY_VALUE2);
        int translateIndex = cur.getColumnIndex(DBHelper.KEY_TRANSLATE2);
        if (cur.moveToFirst()) {
            do {
                TableRow row = new TableRow(getApplicationContext());
                TextView textName = new TextView(getApplicationContext());
                textName.setText(cur.getInt(idIndex) +
                        " , " + cur.getString(valueIndex) +
                        " , " + cur.getString(translateIndex)
                );
                row.addView(textName);
                table.addView(row);
            } while (cur.moveToNext());
        }
    }

    public void MyCout4(Cursor cur) {
        int idIndex = cur.getColumnIndex(DBHelper.KEY_ID4); //вывод ID
        int valueIndex = cur.getColumnIndex(DBHelper.KEY_VALUE4);
        int translateIndex = cur.getColumnIndex(DBHelper.KEY_TRANSLATE4);
        if (cur.moveToFirst()) {
            do {
                TableRow row = new TableRow(getApplicationContext());
                TextView textName = new TextView(getApplicationContext());
                textName.setText(cur.getInt(idIndex) +
                        " , " + cur.getString(valueIndex) +
                        " , " + cur.getString(translateIndex)
                );
                row.addView(textName);
                table.addView(row);
            } while (cur.moveToNext());
        }
    }

    public void MyCout3(Cursor cur) {

        int idIndex = cur.getColumnIndex(DBHelper.KEY_ID3); //вывод ID
        int valueIndex = cur.getColumnIndex(DBHelper.KEY_VERB1);
        int translateIndex = cur.getColumnIndex(DBHelper.KEY_TRANSLATE1);
        int value2Index = cur.getColumnIndex(DBHelper.KEY_VERB2);
        int value3Index = cur.getColumnIndex(DBHelper.KEY_VERB3);
        int value4Index = cur.getColumnIndex(DBHelper.KEY_VERB4);
        if (cur.moveToFirst()) { //перемещение курсора на первую считанную строку
            do {
                TableRow row = new TableRow(getApplicationContext());
                TextView textName = new TextView(getApplicationContext());
                textName.setText(cur.getInt(idIndex) +
                        " , " + cur.getString(valueIndex) +
                        " , " + cur.getString(translateIndex) +
                        " ( " + cur.getString(value2Index) +
                        " , " + cur.getString(value3Index)+
                        " , " + cur.getString(value4Index)+" )"
                );
                row.addView(textName);
                table.addView(row);
            } while (cur.moveToNext());
        }
    }

    public void MyCout5(Cursor cur) {

        int idIndex = cur.getColumnIndex(DBHelper.KEY_ID5); //вывод ID
        int word1Index = cur.getColumnIndex(DBHelper.KEY_W1);
        int word2Index = cur.getColumnIndex(DBHelper.KEY_W2);
        int transIndex = cur.getColumnIndex(DBHelper.KEY_Trans);
        if (cur.moveToFirst()) { //перемещение курсора на первую считанную строку
            do {
                TableRow row = new TableRow(getApplicationContext());
                TextView textName = new TextView(getApplicationContext());
                textName.setText(cur.getInt(idIndex) +
                        " , " + cur.getString(word1Index) +
                        " , " + cur.getString(word2Index) +
                        " , " + cur.getString(transIndex)
                );
                row.addView(textName);
                table.addView(row);
            } while (cur.moveToNext());
        }
    }

    private int mSpeech = 4;
    private int mNumber = 2;
    private int mIsch = 2;

    private void setupSpinner() {
        ArrayAdapter numberSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_number_options, android.R.layout.simple_spinner_item);

        numberSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        con1.setAdapter(numberSpinnerAdapter);
        con1.setSelection(0);
        con1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.number_single))) {
                        mNumber = 0;
                    } else if (selection.equals(getString(R.string.number_plural))) {
                        mNumber = 1;
                    } else if (selection.equals(getString(R.string.number_unknown))) {
                        mNumber = 2;}
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mNumber = 2; // Unknown
            }
        });

        ArrayAdapter numeratSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_numerat_options, android.R.layout.simple_spinner_item);
        numeratSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        con2.setAdapter(numeratSpinnerAdapter);
        con2.setSelection(0);
        con2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.numerat_isch))) {
                        mIsch = 0;
                    } else if (selection.equals(getString(R.string.numerat_nisch))) {
                        mIsch = 1;
                    } else if (selection.equals(getString(R.string.number_unknown))) {
                        mIsch = 2;}
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mIsch = 2; // Unknown
            }
        });

        ArrayAdapter speechSpinnerAdapter= ArrayAdapter.createFromResource(this,
                R.array.array_speech_options2, android.R.layout.simple_spinner_item);
        speechSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        Speech.setAdapter(speechSpinnerAdapter);
        Speech.setSelection(0);
        Speech.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                etVerb2.setVisibility(View.GONE);
                etVerb3.setVisibility(View.GONE);
                etVerb4.setVisibility(View.GONE);
                con1.setVisibility(View.GONE);
                con2.setVisibility(View.GONE);
                btnimg.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.Speech_Noun))) {
                        mSpeech = 0;
                        con1.setVisibility(View.VISIBLE);
                        con2.setVisibility(View.VISIBLE);
                        btnimg.setVisibility(View.VISIBLE);
                    } else if (selection.equals(getString(R.string.Speech_adjective))) {
                        mSpeech = 1;
                    } else if (selection.equals(getString(R.string.Speech_adverb))) {
                        mSpeech = 2;
                    }else if (selection.equals(getString(R.string.Speech_verb))) {
                        mSpeech = 3;
                        etVerb2.setVisibility(View.VISIBLE);
                        etVerb3.setVisibility(View.VISIBLE);
                        etVerb4.setVisibility(View.VISIBLE);
                    }
                    else {
                        mSpeech = 4; // Не определен
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSpeech = 4; // Unknown
            }
        });
    }


    @Override
    public void onClick(View v) {

        String value = etValue.getText().toString();
        String translate = etTranslate.getText().toString();
        ContentValues contentValues = new ContentValues(); //одна строка таблицы
        SQLiteDatabase database;
        database = dbHelper.open();
        switch (v.getId()) {
            case R.id.btnimg:
                imageView.setVisibility(View.VISIBLE);
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
                break;
            case R.id.btnAdd:
                if (value.equals("") || translate.equals("") || mSpeech == 4 ){ // проверяем заполненность полей
                    Toast.makeText(getApplicationContext(), "Не заполнены поля!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (Speech.getSelectedItem().toString().equals(getString(R.string.Speech_Noun)) && (mIsch != 2 && mNumber != 2)&&!imgadr.equals("")) {
                        contentValues.put(DBHelper.KEY_VALUE1, value);
                        contentValues.put(DBHelper.KEY_TRANSLATE1, translate);
                        contentValues.put(DBHelper.KEY_PROPERTIES1, con1.getSelectedItem().toString());
                        contentValues.put(DBHelper.KEY_PROPERTIES2, con2.getSelectedItem().toString());
                        contentValues.put(DBHelper.KEY_IMG, imgadr);
                        database.insert(DBHelper.TABLE_Noun, null, contentValues);//добавляем значения в БД
                        con1.setSelection(0);
                        con2.setSelection(0);
                        etTranslate.setText("");
                        etValue.setText("");
                        Speech.setSelection(0);
                        imgadr="";
                        imageView.setVisibility(View.GONE);
                    }
                    else {
                        if (Speech.getSelectedItem().toString().equals(getString(R.string.Speech_verb)) &&
                                !etVerb2.getText().toString().equals("") && !etVerb3.getText().toString().equals("")
                                && !etVerb4.getText().toString().equals("")) {
                            contentValues.put(DBHelper.KEY_VERB1, value);
                            contentValues.put(DBHelper.KEY_TRANSLATE3, translate);
                            contentValues.put(DBHelper.KEY_VERB2, etVerb2.getText().toString());
                            contentValues.put(DBHelper.KEY_VERB3, etVerb3.getText().toString());
                            contentValues.put(DBHelper.KEY_VERB4, etVerb4.getText().toString());
                            database.insert(TABLE_Verb, null, contentValues);//добавляем значения в БД
                            etVerb2.setText("");
                            etVerb3.setText("");
                            etVerb4.setText("");
                            etTranslate.setText("");
                            etValue.setText("");
                            Speech.setSelection(0);
                        } else if (Speech.getSelectedItem().toString().equals(getString(R.string.Speech_adjective))) {
                            contentValues.put(DBHelper.KEY_VALUE2, value);
                            contentValues.put(DBHelper.KEY_TRANSLATE2, translate);
                            database.insert(DBHelper.TABLE_Adj, null, contentValues);//добавляем значения в БД
                            etTranslate.setText("");
                            etValue.setText("");
                            Speech.setSelection(0);
                        } else if (Speech.getSelectedItem().toString().equals(getString(R.string.Speech_adverb))) {
                            contentValues.put(DBHelper.KEY_VALUE4, value);
                            contentValues.put(DBHelper.KEY_TRANSLATE4, translate);
                            database.insert(DBHelper.TABLE_Adj, null, contentValues);//добавляем значения в БД
                            etTranslate.setText("");
                            etValue.setText("");
                            Speech.setSelection(0);
                        } else
                            Toast.makeText(getApplicationContext(), "Не заполнены поля!", Toast.LENGTH_SHORT).show();
                    }
                }

                break;

            case R.id.btnSort:
                imageView.setVisibility(View.GONE);
                if (mSpeech != 4) {
                    switch(mSpeech)
                    {
                        case 0:
                            table.removeAllViews();
                            Cursor curs1 = database.query(DBHelper.TABLE_Noun,  null, null, null, null, null, null);
                            MyCout1(curs1);
                            curs1.close();
                            break;
                        case 1:

                            table.removeAllViews();
                            Cursor curs2 = database.query(DBHelper.TABLE_Adj,  null, null, null, null, null, null);
                            MyCout2(curs2);
                            curs2.close();

                            break;
                        case 2:

                            table.removeAllViews();
                            Cursor curs4 = database.query(DBHelper.TABLE_Adv,  null, null, null, null, null, null);
                            MyCout4(curs4);
                            curs4.close();

                            break;
                        case 3:
                            table.removeAllViews();
                            Cursor curs3 = database.query(TABLE_Verb,  null, null, null, null, null, null);
                            MyCout3(curs3);
                            curs3.close();
                            break;
                    }
                }
                break;

            case R.id.btnSort1:
                table.removeAllViews();
                if (!(value.equals(""))) {
                    Cursor cur1 = database.query(DBHelper.TABLE_Noun, null, "value = ?", new String[]{value}, null, null, null);
                    Cursor cur2 = database.query(DBHelper.TABLE_Adj, null, "value = ?", new String[]{value}, null, null, null);
                    Cursor cur3 = database.query(TABLE_Verb, null, "value = ?", new String[]{value}, null, null, null);//чтение таблицы (можно с сортировкой)
                    Cursor cur4 = database.query(TABLE_Adv, null, "value = ?", new String[]{value}, null, null, null);
                    MyCout1(cur1);
                    cur1.close();
                    MyCout2(cur2);
                    cur2.close();
                    MyCout3(cur3);
                    cur3.close();
                    MyCout4(cur4);
                    cur4.close();
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setMaxHeight(400);
                    imageView.setMaxWidth(500);
                    if(imgadr.equals(value)) {
                        String filename = value;
                        int resID = getResources().getIdentifier(filename, "drawable", getPackageName());
                        imageView.setImageResource(resID);
                    }
                    else
                    {
                        Bitmap bitmap1 = null;
                        Uri uri = Uri.parse(imgadr);
                        try {
                            bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        } catch (IOException e) {

                        }
                        imageView.setImageBitmap(bitmap1);
                    }
                    imgadr="";

                }
                break;
            case R.id.btnSort2:
                imageView.setVisibility(View.GONE);
                table.removeAllViews();
                if (!(translate.equals(""))) {
                    Cursor cur1 = database.query(DBHelper.TABLE_Noun, null, "translate = ?", new String[]{translate}, null, null, null);
                    Cursor cur2 = database.query(DBHelper.TABLE_Adj, null, "translate = ?", new String[]{translate}, null, null, null);
                    Cursor cur3 = database.query(TABLE_Verb, null, "translate = ?", new String[]{translate}, null, null, null);//чтение таблицы (можно с сортировкой)
                    Cursor cur4 = database.query(DBHelper.TABLE_Adv, null, "translate = ?", new String[]{translate}, null, null, null);
                    MyCout1(cur1);
                    cur1.close();
                    MyCout2(cur2);
                    cur2.close();
                    MyCout3(cur3);
                    cur3.close();
                    MyCout4(cur4);
                    cur4.close();
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setMaxHeight(400);
                    imageView.setMaxWidth(500);
                    if(imgadr.equals(mytranslate)) {
                        String filename = mytranslate;
                        int resID = getResources().getIdentifier(filename, "drawable", getPackageName());
                        imageView.setImageResource(resID);
                    }
                    else
                    {
                        Bitmap bitmap1 = null;
                        Uri uri = Uri.parse(imgadr);
                        try {
                            bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        } catch (IOException e) {

                        }
                        imageView.setImageBitmap(bitmap1);
                    }
                    imgadr="";
                    mytranslate="";
                }
                break;

            case R.id.btninfo:
                imageView.setVisibility(View.GONE);
                table.removeAllViews();
                Cursor curs1 = database.query(DBHelper.TABLE_Noun,  null, null, null, null, null, null);
                Cursor curs2 = database.query(DBHelper.TABLE_Adj, null ,null, null, null, null, null, null);
                Cursor curs3 = database.query(TABLE_Verb, null,  null, null, null, null, null, null);
                Cursor curs4 = database.query(TABLE_Adv, null,  null, null, null, null, null, null);
                Cursor curs5 = database.query(TABLE_Syn, null,  null, null, null, null, null, null);
                MyCout1(curs1);
                curs1.close();
                MyCout2(curs2);
                curs2.close();
                MyCout3(curs3);
                curs3.close();
                MyCout4(curs4);
                curs4.close();
                MyCout5(curs5);
                curs5.close();
                break;
            case R.id.btDel:
                imageView.setVisibility(View.GONE);
                database.delete(TABLE_Noun, "value = ?", new String[]{etValue.getText().toString()});
                database.delete(TABLE_Verb, "value = ?", new String[]{etValue.getText().toString()});
                database.delete(TABLE_Adj, "value = ?", new String[]{etValue.getText().toString()});
                database.delete(TABLE_Adv, "value = ?", new String[]{etValue.getText().toString()});
                break;
        }
        dbHelper.close();//закрываем соединение с БД
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        Bitmap bitmap = null;
        switch(requestCode) {
            case GALLERY_REQUEST:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    imgadr=selectedImage.getScheme()+":"+selectedImage.getSchemeSpecificPart();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageView.setImageBitmap(bitmap);
                }
        }
    }
}
