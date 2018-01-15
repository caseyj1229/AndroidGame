package com.example.jackc.androidgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.provider.Settings;

/**
 * Created by jackc on 1/12/2018.
 */

public class GameCharacter extends GameObject {
    private static final int LOOK_DOWN = 0;
    private static final int LOOK_LEFT = 1;
    private static final int LOOK_RIGHT = 2;
    private static final int LOOK_UP = 3;

    //Index of the current row to use for correct sprite
    private int currentRow = LOOK_RIGHT;

    private int currentCol;

    //Arrays of Bitmaps that hold each of the different character looks
    private Bitmap[] lookRights;
    private Bitmap[] lookLefts;
    private Bitmap[] lookDowns;
    private Bitmap[] lookUps;

    //Speed character moves
    public static final float SPEED = 0.1f;

    //Sets move values for the x and y coordiantes
    private int movingVectorX = 10;
    private int movingVectorY = 5;

    //Last time draw was called
    private long lastDrawTime = -1;

    private GameSurface gameSurface;

    //Create a GameCharacter
    public GameCharacter(GameSurface gameSurface, Bitmap image, int x, int y){
        super(image,4,3,x,y);

        this.gameSurface = gameSurface;

        //All Bitmap Arrays of size 3, as there
        //are 3 columns of 4 rows for each look
        this.lookRights = new Bitmap[imageCols];
        this.lookLefts = new Bitmap[imageCols];
        this.lookDowns = new Bitmap[imageCols];
        this.lookUps = new Bitmap[imageCols];

        //Populate the Bitmap Arrays
        for(int i = 0; i<this.imageCols; i++){
            this.lookDowns[i] = this.getSubImageAtPosition(LOOK_DOWN,i);
            this.lookLefts[i] = this.getSubImageAtPosition(LOOK_LEFT,i);
            this.lookRights[i] = this.getSubImageAtPosition(LOOK_RIGHT,i);
            this.lookUps[i] = this.getSubImageAtPosition(LOOK_UP,i);
        }
    }

    //Which move Bitmap is needed by getCurrentMoveBitmap
    public Bitmap[] getMoveBitmaps(){
        switch(currentRow){
            case(LOOK_DOWN): return this.lookDowns;
            case(LOOK_LEFT): return this.lookLefts;
            case(LOOK_RIGHT): return this.lookRights;
            case(LOOK_UP): return this.lookUps;

            default: return null;
        }
    }

    //Which member of the bitmap array is needed to draw
    public Bitmap getCurrentMoveBitmap(){
        Bitmap[] bitmaps = this.getMoveBitmaps();
        return bitmaps[this.currentCol];
    }

    public void update(){
        this.currentCol++;
        if(currentCol>this.imageCols){
            this.currentCol = 0;
        }

        //What Time is It in Nanoseconds
        long currentTime = System.nanoTime();

        //Check if draw has been called by seeing if it is
        //equal to the -1 value drawTime was initialized too
        if(lastDrawTime == -1){
            lastDrawTime = currentTime;
        }

        int deltaTime = (int)((currentTime-lastDrawTime)/1000000);

        float distance = deltaTime*SPEED;

        //Get length of move vector (SQRT(x^2 + y^2))
        double moveVector = Math.sqrt((movingVectorX*movingVectorX)+(movingVectorY*movingVectorY));

        //Get Characters new position
        this.xPos = xPos + (int)(distance*movingVectorX/moveVector);
        this.yPos = yPos + (int)(distance*movingVectorY/moveVector);

        //Handle when character hits screen edge
        //On x-axis
        if(this.xPos<0){
            this.xPos = 0;
            this.movingVectorX = movingVectorX*-1;
        }
        else if(this.xPos>this.gameSurface.getWidth()-imgWidth){
            this.xPos = this.gameSurface.getWidth()-imgWidth;
            this.movingVectorX = movingVectorX*-1;
        }

        //On y-axis
        if(this.yPos<0){
            this.yPos = 0;
            this.movingVectorY = movingVectorY*-1;
        }
        else if(this.yPos>this.gameSurface.getHeight()-imgHeight){
            this.yPos = this.gameSurface.getHeight()-imgHeight;
            this.movingVectorY = movingVectorY*-1;
        }

        //Determine the character image needed (row)
        if( movingVectorX > 0 )  {
            if(movingVectorY > 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                this.currentRow = LOOK_DOWN;
            }else if(movingVectorY < 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                this.currentRow = LOOK_UP;
            }else  {
                this.currentRow = LOOK_RIGHT;
            }
        } else {
            if(movingVectorY > 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                this.currentRow = LOOK_DOWN;
            }else if(movingVectorY < 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                this.currentRow = LOOK_UP;
            }else  {
                this.currentRow =LOOK_LEFT ;
            }
        }
    }

    //Draw the character on screen
    public void draw(Canvas canvas){
        Bitmap bitmap = this.getCurrentMoveBitmap();
        canvas.drawBitmap(bitmap,xPos,yPos,null);

        this.lastDrawTime = System.nanoTime();
    }

    //Set the moving vector (speed and direction)
    public void setMovingVector(int movingVectorX, int movingVectorY){
        this.movingVectorX = movingVectorX;
        this.movingVectorY = movingVectorY;
    }
}
