package com.example.hp.steganographyfinal;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.hp.steganographyfinal.Stegano.Extract;
import com.example.hp.steganographyfinal.Stegano.Tools;

import java.nio.ByteBuffer;


public class ExtractPage extends AppCompatActivity {

    ImageView stegoImage = null;
    Button loadButton = null;
    Button extractButton = null;
    EditText password = null;
    Uri imageUri = null; //IMAGE'S URI IN DEVICE STORAGE
    private static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extract_activity);
        stegoImage = (ImageView)findViewById(R.id.extractImage);
        loadButton = (Button)findViewById(R.id.extractLoad);
        extractButton = (Button)findViewById(R.id.extractButton);
        password = (EditText)findViewById(R.id.extractPassword);

        //LISTENER
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        extractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageUri!=null) {
                    if(!password.getText().toString().equals("")){
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            String key = password.getText().toString();
                            String text = Extract.decode(bitmap);
                            text = Tools.XORcode(text, key);
                            Intent intent = new Intent(ExtractPage.this, ExtractResult.class);
                            intent.putExtra("text", text);
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    else Toast.makeText(ExtractPage.this, "Please enter a key", Toast.LENGTH_LONG).show();
                }
                else Toast.makeText(ExtractPage.this, "Please select an image", Toast.LENGTH_LONG).show();
            }
        });


    } //onCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            stegoImage.setImageURI(imageUri);
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
            Intent i = new Intent(ExtractPage.this, MainActivity.class);
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
