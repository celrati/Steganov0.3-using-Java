package com.example.hp.steganographyfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button embedButton = null;
    Button extractButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        embedButton = (Button)findViewById(R.id.main_embed);
        extractButton = (Button)findViewById(R.id.main_extract);

        //LISTENERS
        embedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, EmbedPage.class);
                startActivity(i);
            }
        });

        extractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ExtractPage.class);
                startActivity(i);
            }
        });
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
