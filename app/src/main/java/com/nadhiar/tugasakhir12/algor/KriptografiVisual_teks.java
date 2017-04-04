package com.nadhiar.tugasakhir12.algor;

/**
 * Created by 1312100999 on 1/25/2017.
 */
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;

import java.util.Random;

public class KriptografiVisual_teks extends Activity {
    public Bitmap KonversiStringKeBitmap(String teks, int panjang, int lebar) {
        Bitmap proses = Bitmap.createBitmap(panjang, lebar, Bitmap.Config.ARGB_8888);
        Canvas text = new Canvas(proses);
        TextPaint textPaint = new TextPaint();
        //Typeface nike = Typeface.createFromAsset(getAssets(), "fonts/Nike.ttf"); // Custom font untuk kriptografi visual
        textPaint.setTextSize(40);
        textPaint.setTextScaleX(1.f);
        textPaint.setAlpha(0);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        text.drawText(teks, panjang / 2, lebar * 3 / 4, textPaint);
        return proses;
    }

    public Bitmap[] EknripsiKriptografiVisual (Bitmap InputGambar){
        Bitmap[] share = new Bitmap[2];
        share[0] = Bitmap.createBitmap(InputGambar.getWidth(),InputGambar.getHeight(),InputGambar.getConfig());
        share[1] = Bitmap.createBitmap(InputGambar.getWidth(),InputGambar.getHeight(),InputGambar.getConfig());
        for(int i=0; i<InputGambar.getWidth()-1; i++){
            for(int j=0; j<InputGambar.getHeight()-1; j++ ){
                //Nilai indexing random untuk pilihan pixel
                Random generator = new Random();
                int indexing = generator.nextInt(6);
                if(i%2 == 0 && j%2 == 0){
                    if (indexing == 0){
                        share[0].setPixel(i,j,Color.WHITE);
                        share[0].setPixel(i,j+1,Color.WHITE);
                        share[0].setPixel(i+1,j,Color.BLACK);
                        share[0].setPixel(i+1,j+1,Color.BLACK);
                    }

                    if (indexing == 1){
                        share[0].setPixel(i,j,Color.BLACK);
                        share[0].setPixel(i,j+1,Color.BLACK);
                        share[0].setPixel(i+1,j,Color.WHITE);
                        share[0].setPixel(i+1,j+1,Color.WHITE);
                    }

                    if (indexing == 2){
                        share[0].setPixel(i,j,Color.BLACK);
                        share[0].setPixel(i,j+1,Color.WHITE);
                        share[0].setPixel(i+1,j,Color.BLACK);
                        share[0].setPixel(i+1,j+1,Color.WHITE);
                    }

                    if (indexing == 3){
                        share[0].setPixel(i,j,Color.WHITE);
                        share[0].setPixel(i,j+1,Color.BLACK);
                        share[0].setPixel(i+1,j,Color.WHITE);
                        share[0].setPixel(i+1,j+1,Color.BLACK);
                    }

                    if (indexing == 4){
                        share[0].setPixel(i,j,Color.WHITE);
                        share[0].setPixel(i,j+1,Color.BLACK);
                        share[0].setPixel(i+1,j,Color.BLACK);
                        share[0].setPixel(i+1,j+1,Color.WHITE);
                    }

                    if (indexing == 5){
                        share[0].setPixel(i,j,Color.BLACK);
                        share[0].setPixel(i,j+1,Color.WHITE);
                        share[0].setPixel(i+1,j,Color.WHITE);
                        share[0].setPixel(i+1,j+1,Color.BLACK);
                    }

                    //Pixel hitam akan menghasilkan share 1
                    //berbeda dengan share 2 dan sebaliknya

                    if (InputGambar.getPixel(i,j)==Color.BLACK){
                        if (share[0].getPixel(i,j)==Color.BLACK)
                            share[1].setPixel(i,j,Color.WHITE);
                        else
                            share[1].setPixel(i,j,Color.BLACK);
                    }
                    //memberikan noise
                    else {
                        if (share[0].getPixel(i,j)==Color.BLACK)
                            share[1].setPixel(i,j,Color.BLACK);
                        else
                            share[1].setPixel(i,j,Color.WHITE);
                    }

                }
            }
        }
        return share;
    }

    public Bitmap DekripsiKriptografiVisual (Bitmap share1, Bitmap share2){
        Bitmap HasilDekripsi = Bitmap.createBitmap(share1.getWidth(),share1.getHeight(),share1.getConfig());
        for(int i=0; i<HasilDekripsi.getWidth(); i++){
            for(int j=0; j<HasilDekripsi.getHeight(); j++){
                //Asumsikan pixel putih transparan
                if(share2.getPixel(i,j)!=Color.BLACK && share1.getPixel(i,j)!=Color.BLACK){
                    HasilDekripsi.setPixel(i,j,Color.WHITE);
                }
                else {
                    HasilDekripsi.setPixel(i,j,Color.BLACK);
                }
            }
        }
        return HasilDekripsi;
    }
}
