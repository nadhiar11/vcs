package com.nadhiar.tugasakhir12;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_Forgot extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;
    public static final int PICK_IMAGE_2 = 2;
    private ImageView ekstrak, shsistem, dekrip;
    private Button ekstraksi;
    private TextView txstego,txReko;
    public Bitmap stegoimage,stegoimageload,hasil,shareSistem,shareSistemload,share1;
    public int idx,indexingW, indexingH;
    public String path1,path2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        Typeface kau = Typeface.createFromAsset(getAssets(), "fonts/KaushanScript.otf");

        ekstrak = (ImageView) findViewById(R.id.imEkstrak);
        shsistem = (ImageView) findViewById(R.id.imSistem);
        dekrip = (ImageView) findViewById(R.id.imDekrip);
        ekstraksi = (Button) findViewById(R.id.btEkstraksi);
        txstego = (TextView) findViewById(R.id.txStegomedia);
        txReko = (TextView)findViewById(R.id.txReko);
        txReko.setTypeface(kau);

        ekstraksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //========================
                //Ekstraksi share
                //========================

                shareSistemload = BitmapFactory.decodeFile(path2);
                shareSistem = Bitmap.createBitmap(shareSistemload.getWidth(),shareSistemload.getHeight(), Bitmap.Config.ARGB_8888);

                stegoimageload = BitmapFactory.decodeFile(path1);
                stegoimage = Bitmap.createBitmap(stegoimageload.getWidth(), stegoimageload.getHeight(), Bitmap.Config.ARGB_8888);
                indexingW = stegoimage.getWidth()/shareSistem.getWidth();
                indexingH = stegoimage.getHeight()/shareSistem.getHeight();

                share1 = Bitmap.createBitmap(300, 100, Bitmap.Config.ARGB_8888);
                int q = 0;
                int r = 0;

                for (int i=0;i<share1.getWidth();i++){
                    for (int j=0;j<share1.getHeight();j++){


                        //lihat sampel nilai sebelum diekstraksi
                        // if (i==20 && j>10 && j <20)	System.out.println("r=" +r +" g=" +g +" b=" +b);

                        if (i <= share1.getWidth()/2 && j <= share1.getHeight()/2){
                            q = stegoimageload.getPixel(i*indexingW+1, j*indexingH+1);
                            r = Color.red(q);
                        }
                        if (i <= share1.getWidth()/2 && j >share1.getHeight()/2){
                            q = stegoimageload.getPixel(i*indexingW+2, j*indexingH+2);
                            r = Color.red(q);
                        }
                        if (i > share1.getWidth()/2 && j <= share1.getHeight()/2){
                            q = stegoimageload.getPixel(i*indexingW+3, j*indexingH+3);
                            r = Color.red(q);
                        }
                        if (i > share1.getWidth()/2 && j >share1.getHeight()/2){
                            q = stegoimageload.getPixel(i*indexingW+4, j*indexingH+4);
                            r = Color.red(q);
                        }


                        if (r%2 == 1 ) share1.setPixel(i, j, Color.BLACK);
                        else share1.setPixel(i, j,Color.WHITE );
                    }
                }

                hasil = Bitmap.createBitmap(300, 100, Bitmap.Config.ARGB_8888);

                //looping pixel
                for(int i=0; i<hasil.getWidth(); i++){
                    for(int j=0; j<hasil.getHeight(); j++){

                        //asumsikan pixel putih transparan
                        if (share1.getPixel(i, j)!=Color.BLACK && shareSistemload.getPixel(i, j)!=Color.BLACK){
                            hasil.setPixel(i,j,Color.WHITE);
                        }
                        else{
                            hasil.setPixel(i,j,Color.BLACK);
                        }
                    }
                }
                //tampilkan hasil dekripsi ke layar
                dekrip.setImageBitmap(hasil);

            }
        });
    }

    public void upstego(View view) {
        Intent AmbilFoto = new Intent(Intent.ACTION_GET_CONTENT);
        AmbilFoto.setType("image/*");
        startActivityForResult(AmbilFoto, PICK_IMAGE);
    }

    public void uploadsist(View view) {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i, PICK_IMAGE_2);
    }

    public void login(View view){
        Intent i = new Intent(Activity_Forgot.this,LoginActivity.class);
        startActivity(i);
        finish();
    }

    //Method Ambil Gambar dari Gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case (PICK_IMAGE):
                if (resultCode == RESULT_OK) {
                    Uri photoUri = data.getData();

                    if (photoUri != null) {

                        Cursor cursor = getContentResolver().query(photoUri, null, null, null, null);
                        cursor.moveToFirst();
                        idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                        path1 = cursor.getString(idx);

                        if (BitmapFactory.decodeFile(path1).getWidth() <= 350 && BitmapFactory.decodeFile(path1).getHeight() <= 150) {
                            Toast.makeText(getApplicationContext(),
                                    "Ukuran gambar terlalu kecil", Toast.LENGTH_LONG)
                                    .show();
                        } else
                            ekstrak.setImageBitmap(BitmapFactory.decodeFile(path1));
                            txstego.setText(path1);

                    }
                }
                break;

            case (PICK_IMAGE_2):
                if (resultCode == RESULT_OK) {
                    Uri photoUri = data.getData();

                    if (photoUri != null) {

                        Cursor cursor = getContentResolver().query(photoUri, null, null, null, null);
                        cursor.moveToFirst();
                        idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                        path2 = cursor.getString(idx);
                        shsistem.setImageBitmap(BitmapFactory.decodeFile(path2));

                    }
                }
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
