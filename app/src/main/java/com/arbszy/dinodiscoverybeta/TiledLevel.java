package com.arbszy.dinodiscoverybeta;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.renderscript.ScriptGroup;
import android.util.DisplayMetrics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class TiledLevel {

    private final Context context;
    int x;
    int y;

    private Bitmap bitmap;
    private Bitmap tileAtlas;
    int width;
    int height;
    private int rows;
    private int cols;
    private int tileSize;
    private int[][] tiles;
    private ArrayList<Rect> rects;

    private boolean inScaled;
    private float scalar;

    TiledLevel (Context context, boolean inScaled) {
        this.context = context;
        this.inScaled = inScaled;
        x = y = 0;
    }

    public void draw(Canvas canvas) {
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bitmap, null, dst, null);
    }

    public void load(String filename, DisplayMetrics displayMetrics) {
        try {
            InputStream file = context.getAssets().open(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(file));

            if (inScaled) {
                scalar = displayMetrics.scaledDensity;
            }
            else {
                scalar = 1;
            }

            while(reader.ready()) {
                String line = reader.readLine();

                if (line.isEmpty()) {
                    continue;
                }
                else if (line.charAt(0) == '#') {
                    continue;
                }

                StringTokenizer tokens = new StringTokenizer(line, " ");
                String token = tokens.nextToken();

                switch(token) {
                    case "cols":
                        cols = Integer.parseInt(tokens.nextToken());
                        break;
                    case "rows":
                        rows = Integer.parseInt(tokens.nextToken());
                        tiles = new int[cols][rows];
                        break;
                    case "tileSize":
                        tileSize = (int)(Integer.parseInt(tokens.nextToken()) * scalar);
                        width = cols * tileSize;
                        height = rows * tileSize;
                        break;
                    case "tileSheetFilename":
                        loadBitmap(tokens.nextToken());
                        break;
                    case "tileRectFilename":
                        createRects(tokens.nextToken());
                        break;
                    case "levelTilesFilename":
                        createBitmap(tokens.nextToken());
                        break;

                }

            }

            reader.close();
            file.close();
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void loadBitmap(String filename) {
        int id = context.getResources().getIdentifier(filename, "drawable", "com.trios.canvassprites");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = inScaled;
        tileAtlas = BitmapFactory.decodeResource(context.getResources(), id, options);
    }

    private void createRects(String filename) {
        try {
            InputStream file = context.getAssets().open(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(file));

            rects = new ArrayList<>();

            while(reader.ready()) {
                String line = reader.readLine();
                if (line.isEmpty()) {
                    continue;
                }
                else if (line.charAt(0) == '#') {
                    continue;
                }

                Rect rect = new Rect();
                StringTokenizer tokens = new StringTokenizer(line, " ");
                while(tokens.hasMoreElements()) {
                    String token = tokens.nextToken();

                    switch (token) {
                        case "x":
                            rect.left = (int) (Integer.parseInt(tokens.nextToken()) * scalar);
                            break;
                        case "y":
                            rect.top = (int) (Integer.parseInt(tokens.nextToken()) * scalar);
                            break;
                        case "w":
                            rect.right = rect.left + (int) (Integer.parseInt(tokens.nextToken()) * scalar);
                            break;
                        case "h":
                            rect.bottom = rect.top + (int) (Integer.parseInt(tokens.nextToken()) * scalar);
                    }
                }
                rects.add(rect);
            }
            reader.close();
            file.close();
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void createBitmap(String filename) {
        try {
            InputStream file = context.getAssets().open(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(file));

            int row = 0;

            while(reader.ready()) {
                int col = 0;
                String line = reader.readLine();

                if (line.isEmpty()) {
                    continue;
                }
                else if (line.charAt(0) == '#') {
                    continue;
                }

                StringTokenizer tokens = new StringTokenizer(line, " ");
                while(tokens.hasMoreElements()) {
                    tiles[col++][row] = Integer.parseInt(tokens.nextToken());
                }
                row++;
            }

            reader.close();
            file.close();

        }
        catch (IOException exception) {
            exception.printStackTrace();
        }

        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);

        for (int r = 0; r < cols; r++) {
            for (int c = 0; c < cols; c++) {
                int type = tiles[c][r];

                int x = c * tileSize;
                int y = r * tileSize;

                Rect src = rects.get(type);
                Rect dst = new Rect(x, y, x+tileSize, y+tileSize);

                canvas.drawBitmap(tileAtlas, src, dst, null);
            }
        }
        rects.clear();
        rects = null;
        tileAtlas.recycle();
        tileAtlas = null;

    }
}
