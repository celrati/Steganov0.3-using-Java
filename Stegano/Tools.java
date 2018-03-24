package com.example.hp.steganographyfinal.Stegano;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class Tools {

    public static byte[] bitmapToBytes(Bitmap bitmap)
    {
        ByteBuffer buffer = ByteBuffer.allocate(bitmap.getByteCount());
        bitmap.copyPixelsToBuffer(buffer);
        byte[] bytesRGBA = buffer.array();
        return bytesRGBA;
    }

    public static Bitmap bytesToBitmap(byte[] bytes, int width, int height)
    {
        Buffer buf = ByteBuffer.wrap(bytes);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        buf.position(0);
        bitmap.copyPixelsFromBuffer(buf);
        return bitmap;
    }

    public static double msePixel(Bitmap image1, Bitmap image2){
        int dr, dg, db, da; //differences de niveau de couleur sur chaque canal (r, g, b et a)
        int sum_r=0, sum_g=0, sum_b=0, sum_a=0;
        for(int i=0; i<image1.getWidth(); i++){
            for(int j=0; j<image1.getHeight();j++){
                dr = Color.red(image1.getPixel(i,j))-Color.red(image2.getPixel(i,j));
                dg = Color.green(image1.getPixel(i,j))-Color.green(image2.getPixel(i,j));
                db = Color.blue(image1.getPixel(i,j))-Color.blue(image2.getPixel(i,j));
                da = Color.alpha(image1.getPixel(i,j))-Color.alpha(image2.getPixel(i,j));
                sum_r += dr*dr;
                sum_g += dg*dg;
                sum_b += db*db;
                sum_a += da*da;
            }
        }
        double mse = (1.0 / (image1.getWidth() * image1.getHeight() * 4)) * (sum_r+sum_g+sum_b+sum_a);
        return mse;
    }

    public static double mseByte(byte[] image1, byte[] image2){
        int dr, dg, db, da;
        int sum_r=0, sum_g=0, sum_b=0, sum_a=0;
        for(int i=0; i<image1.length; i+=4){
            dr = image1[i]   - image2[i];
            dg = image1[i+1] - image2[i+1];
            db = image1[i+2] - image2[i+2];
            da = image1[i+3] - image2[i+3];
            sum_r += dr*dr;
            sum_g += dg*dg;
            sum_b += db*db;
            sum_a += da*da;
        }
        double mse = (1.0 / (image1.length/4 * 4)) * (sum_r+sum_g+sum_b+sum_a);
        return mse;
    }

    public static double psnr(double mse){
        return 10*Math.log10(255*255/mse);
    }

    public static String XORcode(String message, String key) {
        // String -> char[]
        char[] keyArray = key.toCharArray();
        StringBuilder output = new StringBuilder();

        for(int i = 0; i < message.length(); i++) {
            /*Different variations of the XOR code*/

            //output.append((char) (message.charAt(i) ^ (keyArray[i % keyArray.length]))); //a XOR b
            //output.append((char) (message.charAt(i) ^ (keyArray[i % keyArray.length] & keyArray[(i+1) % keyArray.length]))); //a XOR (b AND c)
            output.append((char) (message.charAt(i) ^ (keyArray[i%keyArray.length] & keyArray[(i+1)%keyArray.length] ^ keyArray[(i+1)%keyArray.length]))); //a XOR (b AND c XOR d)
        }
        return output.toString();
    }

}

