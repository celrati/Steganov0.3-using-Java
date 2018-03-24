package com.example.hp.steganographyfinal.Stegano;

import android.widget.Toast;

import com.example.hp.steganographyfinal.EmbedPage;

public class Embed {


    public static boolean encode(byte[] imageBytes,String message)
    {
        byte[] msg_bytes = message.getBytes();
        byte[] len_bytes = toByteArray(message.length());
        boolean a = add_data(imageBytes, len_bytes, 0);
        boolean b = add_data(imageBytes, msg_bytes, 16);
        if (a && b){
            return true;
        }
        return false;
    }

    public static boolean add_data(byte[] image, byte[] data, int offset) {
        if(data.length*8 + offset > image.length) {
            return false;
        }
        else {
            //for each byte
            for(int i=0; i<data.length; i++){
                int data_byte = data[i] & 0xFF;
                //for each bit in that byte (starting from the the most significant one (7)
                for(int bit=7; bit>=0; bit--, offset++){
                    int b = (data_byte >>> bit) & 1;
                    image[offset] = (byte)(image[offset]&0xFE | b);
                }
            }
            return true;
        }
    }

    public static byte[] toByteArray(int n) {
        byte byte1 = (byte)((n & 0xFF00) >>> 8);
        byte byte0 = (byte)((n & 0x00FF)      );
        return (new byte[]{byte1, byte0});
    }

}
