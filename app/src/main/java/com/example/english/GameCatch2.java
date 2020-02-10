package com.example.english;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.CYAN;

public class GameCatch2 extends Activity implements View.OnTouchListener,
        SurfaceHolder.Callback {
    ArrayMap<String, String> arrayMap = new ArrayMap<>();
    private SurfaceView mSurface;
    private DrawingThread mThread;
    DBHelper dbHelper;
    SQLiteDatabase database;
    float mX;
    float mY;
    int speed = 5;
    float textSize;
    int currentWord;
    boolean watch = false;
    String currentWordS=null;
    Toast win;
    Toast lose;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_game_catch);

        mSurface = (SurfaceView) findViewById(R.id.surface);
        mSurface.setOnTouchListener(this);
        mSurface.getHolder().addCallback(this);
        dbHelper = new DBHelper(this);
        dbHelper.create_db();
        database = dbHelper.open();
        addText();
        win= Toast.makeText(getApplicationContext(),
                "Well done!", Toast.LENGTH_SHORT);
        lose= Toast.makeText(getApplicationContext(),
                "Bad!", Toast.LENGTH_SHORT);



    }

    public void onClick(View v) {
        mThread.clearItems();
    }

    public static int rnd(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mX = event.getX();
            mY = event.getY();
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            mX = event.getX();
            mY = event.getY();
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mThread = new DrawingThread(holder, BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_launcher));
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        mThread.updateSize(width, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mThread.quit();
        mThread = null;
    }

    public void onClick1(View view) {

        addW();
        this.findViewById(R.id.startCatch).setVisibility(View.GONE);
        this.findViewById(R.id.textView4).setVisibility(View.GONE);

    }

    public void addW() {
        mThread.addItem();
        //mThread.addItem(1);
        //mThread.addItem(2);
    }

    public void addText() {
        Cursor cur = database.query(DBHelper.TABLE_Noun, null, null, null, null, null, null, null);
        while (arrayMap.size() < 3) {
            cur.moveToPosition((int) (Math.random() * cur.getCount()));
            arrayMap.put(cur.getString(1), cur.getString(2));
        }

        currentWord = (int) (Math.random() * 3);
        currentWordS = arrayMap.valueAt(currentWord);

    }

    private class DrawingThread extends HandlerThread implements
            Handler.Callback {
        private static final int MSG_ADD = 100;
        private static final int MSG_MOVE = 101;
        private static final int MSG_CLEAR = 102;
        private int mDrawingWidth, mDrawingHeight;
        private SurfaceHolder mDrawingSurface;
        private Paint mPaint;
        private Handler mReceiver;
        private Bitmap mIcon;
        private ArrayList<DrawingItem> mLocations;
        Bitmap imgm = BitmapFactory.decodeResource(getResources(), R.drawable.ic);

        private class DrawingItem {
            // Current location marker
            int x, y;
            // Direction markers for motion
            boolean horizontal, vertical;

            public DrawingItem(int x, int y, boolean horizontal,
                               boolean vertical) {
                this.x = x;
                this.y = y;
                this.horizontal = horizontal;
                this.vertical = vertical;
            }
        }

        public DrawingThread(SurfaceHolder holder, Bitmap icon) {
            super("DrawingThread");
            mDrawingSurface = holder;
            mLocations = new ArrayList<DrawingItem>();
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mIcon = icon;

        }

        @Override
        protected void onLooperPrepared() {
            mReceiver = new Handler(getLooper(), this);
            // Start the rendering
            mReceiver.sendEmptyMessage(MSG_MOVE);

        }

        @Override
        public boolean quit() {
            // Clear all messages before dying
            mReceiver.removeCallbacksAndMessages(null);
            return super.quit();
        }

        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_ADD:
                    // Create a new item at the touch location,
                    // with a randomized start direction
                    DrawingItem newItem = new DrawingItem(msg.arg1, msg.arg2,
                            Math.round(Math.random()) == 0, Math.round(Math
                            .random()) == 0);
                    mLocations.add(newItem);
                    break;
                case MSG_CLEAR:
                    // Remove all objects
                    break;
                case MSG_MOVE:
                    // Render a frame

                    Canvas c = mDrawingSurface.lockCanvas();
                    if (c == null) {
                        break;
                    }

                    // Clear Canvas first
                    //c.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.bg),0,0,null);
                    c.drawColor(Color.parseColor("#B3E5FC"));
                    // Draw each item

                    for (int i = 0; i < mLocations.size(); i++) {

                        // Update location
                        //item.x += (item.horizontal ? 5 : -5);
                        //if (item.x >= (mDrawingWidth - mIcon.getWidth())) {
                        //     item.horizontal = false;
                        //  } else if (item.x <= 0) {
                        //     item.horizontal = true;
                        // }
                        //k=0;
                        mLocations.get(i).y += speed;

                        // Draw to the Canvas
                        // mLocations.get(i).y>=(mDrawingHeight-imgm.getHeight())&&mLocations.get(i).y<=(mDrawingHeight-imgm.getHeight()+5)

                        c.drawBitmap(mIcon, mLocations.get(i).x, mLocations.get(i).y, mPaint);
                        textSize = mPaint.getTextSize();
                        mPaint.setTextSize(mDrawingWidth/18);
                        c.drawText(arrayMap.keyAt(i), mLocations.get(i).x + mIcon.getWidth() / 8, mLocations.get(i).y + mIcon.getHeight() / 2, mPaint);
                        mPaint.setTextSize(textSize);
                        if (watch){
                            if (mLocations.get(i).y>=mDrawingHeight-imgm.getHeight()-mIcon.getHeight()&&mLocations.get(i).x+mIcon.getWidth()/2>=mX-imgm.getWidth()/2-mIcon.getWidth()/2&&mLocations.get(i).x+mIcon.getWidth()/2<=mX+imgm.getWidth()/2+mIcon.getWidth()/2) {
                                if (i == currentWord){
                                    win.show();
                                    }
                                else lose.show();
                                watch = false;
                                mLocations.remove(i);
                                arrayMap.removeAt(i);

                            }}
                        if (mLocations.get(0).y >= mDrawingHeight && mLocations.get(1).y >= mDrawingHeight) {
                            arrayMap.clear();
                            addText();
                            mLocations.clear();
                            addW();}


                        // if(mLocations.isEmpty())
                        // {
                        //     for (int k=0; k<3; k++)
                        //         mReceiver.sendMessage(Message.obtain(mReceiver, MSG_ADD, ((mDrawingWidth/3+80)*k), -rnd(mIcon.getHeight(),mIcon.getHeight()*3)));
                        //   }

                    }

                    c.drawBitmap(imgm, mX - imgm.getWidth() / 2, mDrawingHeight - imgm.getHeight(), mPaint);
                    //mPaint.setTextSize(textSize * 7);
                    textSize = mPaint.getTextSize();
                    mPaint.setTextSize(mDrawingWidth/15);
                    c.drawText(currentWordS, mX - imgm.getWidth() / 2 + imgm.getWidth() / 4, mDrawingHeight-imgm.getHeight()/4, mPaint);
                    mPaint.setTextSize(textSize);
                    // mPaint.setTextSize(textSize);
                    // mPaint.setTextSize(textSize * 7);
                    // Release to be rendered to the screen
                    mDrawingSurface.unlockCanvasAndPost(c);

                    break;
            }
            // Post the next frame

            mReceiver.sendEmptyMessage(MSG_MOVE);
            return true;
        }

        public void updateSize(int width, int height) {
            mDrawingWidth = width;
            mDrawingHeight = height;
        }

        public void addItem() {
            // Pass the location into the Handler using Message arguments
            watch = true;
            Message msg = Message.obtain(mReceiver, MSG_ADD, 0, -rnd(mIcon.getHeight(),mIcon.getHeight()*4));
            mReceiver.sendMessage(msg);
            Message msg2 = Message.obtain(mReceiver, MSG_ADD, (mDrawingWidth-mIcon.getWidth())/2, -rnd(mIcon.getHeight(),mIcon.getHeight()*4));
            mReceiver.sendMessage(msg2);
            Message msg3 = Message.obtain(mReceiver, MSG_ADD, mDrawingWidth-mIcon.getWidth(), -rnd(mIcon.getHeight(),mIcon.getHeight()*4));
            mReceiver.sendMessage(msg3);
        }

        public void clearItems() {
            mReceiver.sendEmptyMessage(MSG_CLEAR);
        }
    }
}