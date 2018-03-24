package com.example.hp.steganographyfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class ExtractResult extends AppCompatActivity {

    TextView message = null;
    Button saveButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extract_result);
        message = (TextView)findViewById(R.id.messageRes);
        saveButton = (Button)findViewById(R.id.button);

        final String text = getIntent().getExtras().getString("text");
        message.setText(text);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savetext(text,"message");
            }
        });
    }

    public void savetext(String text, String fname){
        try {
            int n = new File("/storage/emulated/0/Lissarif/Text").listFiles().length;
            File file = new File("/storage/emulated/0/Lissarif/Text", fname+String.valueOf(n)+".txt");
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file));
            out.write(text);
            out.close();
            Toast.makeText(ExtractResult.this, "Text saved", Toast.LENGTH_SHORT).show();
        }
        catch (Throwable t) {
            Toast.makeText(ExtractResult.this, "Exception: "+t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.home_button){
            Intent i = new Intent(ExtractResult.this, MainActivity.class);
            startActivity(i);
            finish();
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
