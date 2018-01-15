package com.example.jackc.androidgame;

/**
 * Created by jackc on 1/13/2018.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback{
    private GameThread gameThread;
    private GameCharacter char1;

    public GameSurface(Context context){
        super(context);
        //Set Focusble to true to handle events
        this.setFocusable(true);
        //Set the callback
        this.getHolder().addCallback(this);
    }

    public void update(){
        this.char1.update();
    }

    //Handle Screen taps
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            int x = (int)event.getX();
            int y = (int)event.getY();

            int xVector = x-this.char1.getXPos();
            int yVector = y-this.char1.getYPos();

            this.char1.setMovingVector(xVector,yVector);
            return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        this.char1.draw(canvas);
    }

    //Called when Game surface is created
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder){
        Bitmap characterBitmap1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.char1);
        this.char1 = new GameCharacter(this, characterBitmap1,100,50);

        this.gameThread = new GameThread(this,surfaceHolder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder){
        boolean retry= true;
        while(retry) {
            try {
                this.gameThread.setRunning(false);
                this.gameThread.join();
            }
            catch(InterruptedException e)  {
                e.printStackTrace();
            }
            retry= true;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height){

    }
}
