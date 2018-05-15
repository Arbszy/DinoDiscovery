package com.arbszy.dinodiscoverybeta;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.GridLayout;

public class MemoryButton {

    protected int row;
    protected int column;
    protected int frontDrawableID;

    protected boolean isFlipped = false;
    protected boolean isMatched = false;

    protected Drawable front;
    protected Drawable back;

    public MemoryButton(Context context, int r, int c, int frontImageDrawableID) {
        //super(context);

        row = r;
        column = c;
        frontDrawableID = frontImageDrawableID;

        //front = AppCompatDrawableManager.get().getDrawable(context, frontImageDrawableID);
        //back = AppCompatDrawableManager.get().getDrawable(context, R.drawable.tile_2);

        // setBackground(back);

        GridLayout.LayoutParams tempParams = new GridLayout.LayoutParams(GridLayout.spec(r), GridLayout.spec(c));

        //tempParams.width = (int) getResources().getDisplayMetrics().density * 50;
        // tempParams.height = (int) getResources().getDisplayMetrics().density * 50;

        //setLayoutParams(tempParams);
    }

}
