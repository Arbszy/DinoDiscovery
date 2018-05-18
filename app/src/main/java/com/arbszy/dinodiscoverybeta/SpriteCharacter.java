package com.arbszy.dinodiscoverybeta;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.HashMap;

public class SpriteCharacter {
    int x = 0;
    int y = 0;
    int width;
    int height;

    int sheet_rows = 4;
    int sheet_cols = 3;

    private final Bitmap bmp;
    private int srcX = 0;
    private int srcY = 0;

    HashMap<String, Animation> animations;
    private Animation currentAnimation = null;
    private int currentFrame = 0;
    private long currentFrameTime = 0;
    private long frameTime = 0;
    private boolean playing = false;

    public SpriteCharacter(Bitmap bmp) {
        this.bmp = bmp;

        width = bmp.getWidth() / sheet_cols;
        height = bmp.getHeight() / sheet_rows;

        updateSrc();
        animations = new HashMap<String, Animation>();
    }

    public void update(long deltaTime) {
        if (playing) {
            currentFrameTime += deltaTime;

            if (currentFrameTime > frameTime) {
                currentFrameTime = 0;
                currentFrame++;

                if (currentFrame > currentAnimation.startFrame + currentAnimation.frameCount -1) {
                    currentFrame = currentAnimation.startFrame;
                    if (!currentAnimation.looping) {
                        playing = false;
                    }
                }

                updateSrc();
            }
        }
    }


    public void draw(Canvas canvas) {
        Rect srcRect = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dstRect = new Rect(x, y, x + width, y + height);

        canvas.drawBitmap(bmp, srcRect, dstRect, null);
    }

    public void addAnimation(String name, int startFrame, int frameCount, int fps, int cellWidth, int cellHeight, boolean looping) {
        animations.put(name, new Animation(name, startFrame, frameCount, fps, cellWidth, cellHeight, looping));
    }

    public boolean setAnimations(String name) {
        currentAnimation = animations.get(name);
        if (currentAnimation == null) {
            currentFrame =0;
            playing = false;
            updateSrc();
            return false;
        }
        currentFrame = currentAnimation.startFrame;
        frameTime = 1000 / currentAnimation.fps;
        currentFrameTime = 0;
        playing = true;
        updateSrc();
        return true;
    }

    private void updateSrc() {

        srcX = currentFrame % sheet_cols * width;
        srcY = currentFrame / sheet_cols * height;
    }
}
