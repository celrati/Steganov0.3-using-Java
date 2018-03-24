package com.example.hp.steganographyfinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.hp.steganographyfinal.Stegano.Embed;
import com.example.hp.steganographyfinal.Stegano.Tools;

public class EmbedPage extends AppCompatActivity{

    ImageView coverImage = null;
    Button loadButton = null;
    Button embedButton = null;
    EditText message = null;
    EditText password = null;
    Uri imageUri = null;
    private static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.embed_activity);
        coverImage = (ImageView)findViewById(R.id.embedImage);
        loadButton = (Button)findViewById(R.id.embedLoad);
        embedButton = (Button)findViewById(R.id.embedButton);
        message = (EditText)findViewById(R.id.embedMessage);
        password = (EditText)findViewById(R.id.embedPassword);

        //LISTENERS
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        embedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageUri!=null) {
                    if(!message.getText().toString().equals("")) {
                        if(!password.getText().toString().equals("")){
                            Intent intent = new Intent(EmbedPage.this, EmbedResult.class);
                            intent.putExtra("imageUri", imageUri);
                            intent.putExtra("message", message.getText().toString());
                            intent.putExtra("key", password.getText().toString());
                            startActivity(intent);
                        }
                        else Toast.makeText(EmbedPage.this, "Please enter a key", Toast.LENGTH_LONG).show();
                    }
                    else Toast.makeText(EmbedPage.this, "Please enter a message", Toast.LENGTH_LONG).show();


                }
                else Toast.makeText(EmbedPage.this, "Please select an image", Toast.LENGTH_LONG).show();
            }
        });


    } //onCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==PICK_IMAGE){
            imageUri = data.getData();
            coverImage.setImageURI(imageUri);
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
            Intent i = new Intent(EmbedPage.this, MainActivity.class);
            finish();
            startActivity(i);
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