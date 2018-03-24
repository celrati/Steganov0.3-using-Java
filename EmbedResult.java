package com.example.hp.steganographyfinal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.hp.steganographyfinal.Stegano.Embed;
import com.example.hp.steganographyfinal.Stegano.Tools;
import java.io.File;
import java.io.FileOutputStream;


public class EmbedResult extends AppCompatActivity{

    ImageView stegoImage = null;
    Button saveButton = null;
    TextView info = null;
    Uri imageUri = null;
    Bitmap bitmap = null;
    Bitmap encodedImage = null;
    String message = null;
    String key = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.embed_result);
        stegoImage = (ImageView)findViewById(R.id.embedImageRes);
        saveButton = (Button)findViewById(R.id.saveImage);
        info = (TextView)findViewById(R.id.information);

        //RECIEVING THE DATA
        imageUri = getIntent().getParcelableExtra("imageUri");
        message = getIntent().getStringExtra("message");
        key = getIntent().getStringExtra("key");

        try{
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            byte[] imageBytes = Tools.bitmapToBytes(bitmap);
            message = Tools.XORcode(message, key);

            if(Embed.encode(imageBytes,message)){
                encodedImage = Tools.bytesToBitmap(imageBytes, bitmap.getWidth(), bitmap.getHeight());
                stegoImage.setImageBitmap(encodedImage);
                //double mse = Tools.msePixel(bitmap, encodedImage);
                double mse = Tools.mseByte(Tools.bitmapToBytes(bitmap), imageBytes);
                if(mse==0){
                    info.setText("MSE :" + String.format("%.3f", mse)+ "\n" + "PSNR: inf");
                }
                else{
                    double psnr = Tools.psnr(mse);
                    info.setText("MSE\t:\t" + String.format("%.6f", mse)+ "\n" + "PSNR\t:\t" + String.format("%.3f", psnr)+"db");
                }
            }
            else Toast.makeText(EmbedResult.this, "Image too small", Toast.LENGTH_LONG).show();

        }catch(Exception e){
            e.printStackTrace();
        }


        //LISTENER
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage(encodedImage,"stego");
            }
        });

    }//onCreate


    public void saveImage(Bitmap image, String fname){

        FileOutputStream out;
        //INDEX OF THE IMAGE IN THE FOLDER
        int n = new File("/storage/emulated/0/Lissarif/Pictures").listFiles().length;
        File f = new File("/storage/emulated/0/Lissarif/Pictures", fname+String.valueOf(n)+".png");

        try{
            out = new FileOutputStream(f);
            image.compress(Bitmap.CompressFormat.PNG,100,out);
            out.flush();
            out.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        //SCAN THE NEW FILE
        this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(f)));

        Toast.makeText(EmbedResult.this, "Image saved", Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.home_button){
            Intent i = new Intent(EmbedResult.this, MainActivity.class);
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
        //stegoImage.setImageURI(null);
        finish();
    }

}