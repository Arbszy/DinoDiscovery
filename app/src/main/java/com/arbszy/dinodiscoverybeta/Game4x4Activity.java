package com.arbszy.dinodiscoverybeta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;

import java.util.Random;

public class Game4x4Activity extends AppCompatActivity {

    private int numOfElements;

    private MemoryButton[] buttons;

    private int[] buttonGraphicLocations;
    private int[] buttonGraphics;

    private MemoryButton selectedButton1;
    private MemoryButton selectedButton2;

    private boolean isBusy = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game4x4);

        GridLayout gridLayout = findViewById(R.id.grid_layout_4x4);

        int numColumns = gridLayout.getColumnCount();
        int numRows = gridLayout.getRowCount();

        numOfElements = numColumns + numRows;

        buttons = new MemoryButton[numOfElements];

        buttonGraphics = new int[numOfElements / 2];

        buttonGraphics[0] = R.drawable.tile_1;
        buttonGraphics[1] = R.drawable.tile_2;

        buttonGraphicLocations = new int[numOfElements];

        shuffleButtonGraphics();
    }

    protected void shuffleButtonGraphics() {
        Random random = new Random();

        for(int i = 0; i < numOfElements; i++) {
            buttonGraphicLocations[i] = i % numOfElements / 2;
        }

        for(int i = 0; i < numOfElements; i++)
        {

        }

    }
}
