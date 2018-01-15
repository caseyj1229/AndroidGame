package com.example.jackc.androidgame;

/**
 * Created by jackc on 1/12/2018.
 */

import android.graphics.Bitmap;

public abstract class GameObject {
    protected Bitmap image;
    protected final int imageRows;
    protected final int imageCols;

    protected final int imgHeight;
    protected final int imgWidth;

    protected final int spriteWidth;
    protected final int spriteHeight;

    protected int xPos;
    protected int yPos;

    public GameObject(Bitmap image, int imageRows, int imageCols,int xPos,int yPos){
        this.image = image;

        this.imageRows = imageRows;
        this.imageCols = imageCols;
        this.xPos = xPos;
        this.yPos = yPos;

        this.imgHeight = image.getHeight();
        this.imgWidth  = image.getWidth();

        this.spriteWidth = imgWidth/imageCols;
        this.spriteHeight = imgHeight/imageRows;
    }

    protected Bitmap getSubImageAtPosition(int imageRow,int imageCol){
        Bitmap subImage = Bitmap.createBitmap(image,imageCol*spriteWidth,imageRow*spriteHeight,spriteWidth,spriteHeight);
        return subImage;
    }

    public int getXPos(){
        return this.xPos;
    }

    public int getYPos(){
        return this.yPos;
    }

    public int getImgWidth(){
        return this.getImgWidth();
    }

    public int getImgHeight(){
        return this.imgHeight;
    }
}
