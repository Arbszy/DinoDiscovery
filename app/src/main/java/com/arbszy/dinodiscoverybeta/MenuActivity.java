package com.arbszy.dinodiscoverybeta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;

public class MenuActivity extends AppCompatActivity {

    private ImageButton buttonPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


       buttonPlay = findViewById(R.id.buttonPlay);

        buttonPlay.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
               Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);


            }
        });
    }
}

