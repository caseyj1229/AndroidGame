package com.example.jackc.androidgame;

/**
 * Created by jackc on 1/13/2018.
 */

import android.graphics.Canvas;
import android.provider.Settings;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
    private boolean running;
    private GameSurface gameSurface;
    private SurfaceHolder surfaceHolder;

    //Creates thread
    public GameThread(GameSurface gameSurface, SurfaceHolder surfaceHolder){
        this.gameSurface = gameSurface;
        this.surfaceHolder = surfaceHolder;
    }

    @Override
    public void run(){
        long startTime = System.nanoTime();

        //While the gameThread is running
        while(running){
            Canvas canvas = null;
            try{
                // Get Canvas from Holder and lock it.
                canvas = this.surfaceHolder.lockCanvas();

                // Synchronized
                synchronized (canvas)  {
                    this.gameSurface.update();
                    this.gameSurface.draw(canvas);
                }
            }
            catch (Exception e){
                System.err.print(e.getCause());
            }
            finally{
                if(canvas != null){
                    this.surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
        long currentTime = System.nanoTime();

        //Need Interval to redraw the game in milliseconds
        long timeToWait = (currentTime-startTime)/1000000;

        if(timeToWait < 25){
            timeToWait = 25;
        }

        try{
            this.sleep(timeToWait);
        }
        catch(Exception e){
            System.out.print(e.getCause());
        }
        startTime = System.nanoTime();
    }

    public void setRunning(boolean running){
        this.running = running;
    }
}
