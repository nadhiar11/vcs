package com.nadhiar.tugasakhir12;

/**
 * Created by 1312100999 on 1/25/2017.
 */


import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nadhiar.tugasakhir12.helper.SQLiteHandler;
import com.nadhiar.tugasakhir12.helper.SessionManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import com.nadhiar.tugasakhir12.algor.lzw.Compressor;

public class Kompresi extends AppCompatActivity {
    private ImageView sh1kom, stegomedia;
    private Bitmap a, BmpShare1, BmpCover, BmpStegoImage;
    private TextView txSisip;
    public static final int PICK_IMAGE = 1;
    public int idx, ukur, indexingW, indexingH;
    public String absoluteFilePathSource, name;
    public SQLiteHandler db;
    public SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kompresi);

        Typeface xtra = Typeface.createFromAsset(getAssets(), "fonts/Lobster.otf");

        sh1kom = (ImageView) findViewById(R.id.imsharekom);
        stegomedia = (ImageView) findViewById(R.id.stegomed);
        txSisip = (TextView) findViewById(R.id.txSisip);
        txSisip.setTypeface(xtra);


        a = getIntent().getParcelableExtra("kunci1"); // ini bentuknya bitmap bukan file !!
        sh1kom.setImageBitmap(a);

        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        name = user.get("name");
    }

    public void ambil(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, PICK_IMAGE);
    }

    public void kompres(View view) {
        File f = bitmapToFile(a);
        BmpShare1 = Compressor.getDefault(this).compressToBitmap(f);
        Toast.makeText(getApplicationContext(), "Share berhasil dikompresi", Toast.LENGTH_LONG).show();
    }


    public void sisip(View view) {
        BmpCover = BitmapFactory.decodeFile(absoluteFilePathSource); // Gambar bebas
        BmpStegoImage = Bitmap.createBitmap(BmpCover.getWidth(), BmpCover.getHeight(), Bitmap.Config.ARGB_8888);
        indexingW = BmpStegoImage.getWidth() / BmpShare1.getWidth();
        indexingH = BmpStegoImage.getHeight() / BmpShare1.getHeight();

        for (int i = 0; i < BmpStegoImage.getWidth(); i++) {
            for (int j = 0; j < BmpStegoImage.getHeight(); j++) {

                int p = BmpCover.getPixel(i, j);
                int r = Color.red(p);
                int g = Color.green(p);
                int b = Color.blue(p);
                int a = Color.alpha(p);

                if (i < BmpShare1.getWidth() && j < BmpShare1.getHeight()) {
                    int p1 = BmpCover.getPixel(i * indexingW + 1, j * indexingH + 1);
                    int r1 = Color.red(p1);
                    int g1 = Color.green(p1);
                    int x1 = r1 + 1;
                    int b1 = Color.blue(p1);
                    int a1 = Color.alpha(p1);

                    int p2 = BmpCover.getPixel(i * indexingW + 2, j * indexingH + 2);
                    int r2 = Color.red(p2);
                    int g2 = Color.green(p2);
                    int x2 = r2 + 1;
                    int b2 = Color.blue(p2);
                    int a2 = Color.alpha(p2);

                    int p3 = BmpCover.getPixel(i * indexingW + 3, j * indexingH + 3);
                    int r3 = Color.red(p3);
                    int g3 = Color.green(p3);
                    int x3 = r3 + 1;
                    int b3 = Color.blue(p3);
                    int a3 = Color.alpha(p3);

                    int p4 = BmpCover.getPixel(i * indexingW + 4, j * indexingH + 4);
                    int r4 = Color.red(p4);
                    int g4 = Color.green(p4);
                    int x4 = r4 + 1;
                    int b4 = Color.blue(p4);
                    int a4 = Color.alpha(p4);

                    if (BmpShare1.getPixel(i, j) == Color.BLACK) {
                        if (i <= BmpShare1.getWidth() / 2 && j <= BmpShare1.getHeight() / 2) {
                            if (r1 % 2 == 0) r1 = x1;
                            BmpStegoImage.setPixel(i * indexingW + 1, j * indexingH + 1, Color.argb(a1, r1, g1, b1));
                        }
                        if (i <= BmpShare1.getWidth() / 2 && j > BmpShare1.getHeight() / 2) {
                            if (r2 % 2 == 0) r2 = x2;
                            BmpStegoImage.setPixel(i * indexingW + 2, j * indexingH + 2, Color.argb(a2, r2, g2, b2));
                        }
                        if (i > BmpShare1.getWidth() / 2 && j <= BmpShare1.getHeight() / 2) {
                            if (r3 % 2 == 0) r3 = x3;
                            BmpStegoImage.setPixel(i * indexingW + 3, j * indexingH + 3, Color.argb(a3, r3, g3, b3));
                        }
                        if (i > BmpShare1.getWidth() / 2 && j > BmpShare1.getHeight() / 2) {
                            if (r4 % 2 == 0) r4 = x4;
                            BmpStegoImage.setPixel(i * indexingW + 4, j * indexingH + 4, Color.argb(a4, r4, g4, b4));
                        }
                    } else {
                        if (i <= BmpShare1.getWidth() / 2 && j <= BmpShare1.getHeight() / 2) {
                            if (r1 % 2 == 1) {
                                x1 = r1 - 1;
                                r1 = x1;
                            }
                            BmpStegoImage.setPixel(i * indexingW + 1, j * indexingH + 1, Color.argb(a1, r1, g1, b1));
                        }
                        if (i <= BmpShare1.getWidth() / 2 && j > BmpShare1.getHeight() / 2) {
                            if (r2 % 2 == 1) {
                                x2 = r2 - 1;
                                r2 = x2;
                            }
                            BmpStegoImage.setPixel(i * indexingW + 2, j * indexingH + 2, Color.argb(a2, r2, g2, b2));
                        }
                        if (i > BmpShare1.getWidth() / 2 && j <= BmpShare1.getHeight() / 2) {
                            if (r3 % 2 == 1) {
                                x3 = r3 - 1;
                                r3 = x3;
                            }
                            BmpStegoImage.setPixel(i * indexingW + 3, j * indexingH + 3, Color.argb(a3, r3, g3, b3));
                        }
                        if (i > BmpShare1.getWidth() / 2 && j > BmpShare1.getHeight() / 2) {
                            if (r4 % 2 == 1) {
                                x4 = r4 - 1;
                                r4 = x4;
                            }
                            BmpStegoImage.setPixel(i * indexingW + 4, j * indexingH + 4, Color.argb(a4, r4, g4, b4));
                        }
                    }
                }
                if (BmpStegoImage.getPixel(i, j) == 0)
                    BmpStegoImage.setPixel(i, j, Color.argb(a, r, g, b));
            }
        }
        Toast.makeText(getApplicationContext(), "Berhasil disisipkan . . . . .", Toast.LENGTH_LONG).show();

        //save hasil stego
        try {
            BmpStegoImage.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(new File("/sdcard/" + name + "_stego.png")));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void next(View view) {
        Intent i = new Intent(Kompresi.this, Akhir.class);
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
                        absoluteFilePathSource = cursor.getString(idx);

                        if (BitmapFactory.decodeFile(absoluteFilePathSource).getWidth() <= 350 && BitmapFactory.decodeFile(absoluteFilePathSource).getHeight() <= 150) {
                            Toast.makeText(getApplicationContext(),
                                    "Ukuran gambar terlalu kecil", Toast.LENGTH_LONG)
                                    .show();
                        } else
                            stegomedia.setImageBitmap(BitmapFactory.decodeFile(absoluteFilePathSource));
                        //txtCover.setText(absoluteFilePathSource);
                    }
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public File bitmapToFile(Bitmap bmp) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(ukur);
            bmp.compress(Bitmap.CompressFormat.PNG, 0, bos);
            byte[] bArr = bos.toByteArray();
            bos.flush();
            bos.close();

            FileOutputStream fos = openFileOutput("kom.png", Context.MODE_WORLD_WRITEABLE);
            fos.write(bArr);
            fos.flush();
            fos.close();

            File mFile = new File(getFilesDir().getAbsolutePath(), "kom.png");
            return mFile;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
