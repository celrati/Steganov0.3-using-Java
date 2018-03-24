package com.example.hp.steganographyfinal.Stegano;

import android.graphics.Bitmap;

public class Extract {

    public static String decode(Bitmap im) {
        byte[] arr = Tools.bitmapToBytes(im);
        int length = 0;
        int offset = 16;

        for(int i=0; i<offset; i++) {
            length = (length << 1) | (arr[i] & 1);
        }

        byte[] result = new byte[length];

        for(int b=0; b<result.length; b++) {
            for(int i=0; i<8; i++,offset++) {
                result[b] = (byte)((result[b] << 1) | (arr[offset] & 1));
            }
        }

        String str = new String(result);
        return str;
    }

}
