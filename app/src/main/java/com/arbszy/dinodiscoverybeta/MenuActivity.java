package com.arbszy.dinodiscoverybeta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

public class MenuActivity extends AppCompatActivity {

    private Button button4x4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        //button4x4 = (Button) findViewById(R.id.button_4x4_game);

        button4x4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, Game4x4Activity.class);
                startActivity(intent);


            }

            //variables for the greatest game ever


        });
    }
}

