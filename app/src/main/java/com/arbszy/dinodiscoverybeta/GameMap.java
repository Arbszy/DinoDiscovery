package com.arbszy.dinodiscoverybeta;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameMap extends AppCompatActivity {

    Canvas canvas;
    SpriteView spriteView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        spriteView = new SpriteView(this);
        setContentView(spriteView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        spriteView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        spriteView.resume();
    }

    class SpriteView extends SurfaceView implements Runnable {

        //System elements
        boolean running = true;
        Thread thread = null;
        Display display;
        int screenWidth;
        int screenHeight;

        // Canvas elements
        final SurfaceHolder holder;
        final Paint paint;

        //Sprite Elements
        Bitmap bmp;
        Sprite character;
        TiledLevel level1;
        int vx = 0;
        int vy = 0;

        //Time Elements
        long lastFrameTime;
        long deltaTime;
        int fps;

        public SpriteView(Context context) {
            super(context);

            holder = getHolder();
            paint = new Paint();
            paint.setColor(Color.argb(255, 255, 255, 255));

            display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
            screenWidth = size.x;

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.chara_sprite);

            character = new Sprite(bmp);
            character.addAnimation("walkDown", 6, 3, 7, 72, 96, true);
            character.addAnimation("walkUp", 0, 3, 7, 72, 96, true);
            character.addAnimation("idle", 7, 1, 1, 72, 96, false);
            character.setAnimations("idle");

            character.x = screenWidth / 2 - character.width / 2;
            character.y = 20;

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            level1 = new TiledLevel(context, false);
            level1.load("level1Info.dat", displayMetrics);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch(event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    if (event.getY() >= screenHeight / 2) {
                        vy = 5;
                        character.setAnimations("walkDown");
                    }
                    else {
                        vy = -5;
                        character.setAnimations("walkUp");
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    vy = 0;
                    character.setAnimations("idle");
                    break;
            }

            return true;
        }

        private void updateLogic() {
            character.y += vy;
            character.update(deltaTime);
        }

        private void drawCanvas() {
            if (holder.getSurface().isValid()) {
                canvas = holder.lockCanvas();
                level1.draw(canvas);
                //character.draw(canvas);
                paint.setColor(Color.argb(255, 255, 255, 255));
                paint.setTextSize(45);
                canvas.drawText("FPS: " + fps, 10, 40, paint);
                holder.unlockCanvasAndPost(canvas);
            }
        }

        public void controlFPS() {
            long timeThisFrame = (System.nanoTime() / 1000000 - lastFrameTime);
            long timeToSleep = 15 - timeThisFrame;
            deltaTime = 0;

            if (timeThisFrame > 0) {
                fps = (int) (100 / timeThisFrame);
                deltaTime = timeThisFrame;
            }

            if (timeToSleep > 0) {
                try {
                    thread.sleep(timeToSleep);
                } catch (InterruptedException e) {

                }
            }

            lastFrameTime = System.nanoTime() / 1000000;
        }

        @Override
        public void run() {
            while(running) {
                updateLogic();
                drawCanvas();
                controlFPS();
            }
        }

        public void resume() {
            thread = new Thread(this);
            thread.start();
            running = true;
        }
        public void pause() {
            running = false;
            try {
                thread.join();
            }
            catch(InterruptedException e) {

            }
        }
    }
}
