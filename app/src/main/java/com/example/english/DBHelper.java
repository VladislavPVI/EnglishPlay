package com.example.english;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DBHelper  extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    private static String DB_PATH; // полный путь к базе данных
    public static final String DATABASE_NAME = "dictionary.db";
    public static final String TABLE_Noun = "Noun";
    public static final String TABLE_Verb = "Verb";
    public static final String TABLE_Adj = "Adj";
    public static final String TABLE_Adv ="Adv";
    public static final String TABLE_Syn ="Synonyms";

    public static final String KEY_ID1 = "_id";
    public static final String KEY_ID2 = "_id";
    public static final String KEY_ID3 = "_id";
    public static final String KEY_ID4 = "_id";
    public static final String KEY_ID5 = "_id";

    public static final String KEY_VERB1 = "value";
    public static final String KEY_VERB2 = "verb2";
    public static final String KEY_VERB3 = "verb3";
    public static final String KEY_VERB4 = "verb4";
    public static final String KEY_VALUE1 = "value";
    public static final String KEY_VALUE2 = "value";
    public static final String KEY_VALUE4 = "value";

    public static final String KEY_TRANSLATE1 = "translate";
    public static final String KEY_TRANSLATE2 = "translate";
    public static final String KEY_TRANSLATE3 = "translate";
    public static final String KEY_TRANSLATE4 = "translate";

    public static final String KEY_PROPERTIES1 = "properties1";
    public static final String KEY_PROPERTIES2 = "properties2";
    public static final String KEY_IMG = "img";

    public static final String KEY_W1 = "word2";
    public static final String KEY_W2 = "word1";
    public static final String KEY_Trans = "trans";

    private Context myContext;


    public DBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext=context;
        DB_PATH =context.getFilesDir().getPath() + DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase dbl) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    void create_db(){
        InputStream myInput = null;
        OutputStream myOutput = null;
        try {
            File file = new File(DB_PATH);
            if (!file.exists()) {
                this.getReadableDatabase();
                //получаем локальную бд как поток
                myInput = myContext.getAssets().open(DATABASE_NAME);
                // Путь к новой бд
                String outFileName = DB_PATH;
                // Открываем пустую бд
                myOutput = new FileOutputStream(outFileName);
                // побайтово копируем данные
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
                myOutput.close();
                myInput.close();
            }
        }
        catch(IOException ex){
            Log.d("DatabaseHelper", ex.getMessage());
        }
    }
    public SQLiteDatabase open()throws SQLException {

        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }

}