package com.nadhiar.tugasakhir12;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
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
    private TextView txstego;
    public int idx;
    public String absoluteFilePathSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        ekstrak = (ImageView) findViewById(R.id.imEkstrak);
        shsistem = (ImageView) findViewById(R.id.imSistem);
        dekrip = (ImageView) findViewById(R.id.imDekrip);
        ekstraksi = (Button) findViewById(R.id.btEkstraksi);
        txstego = (TextView) findViewById(R.id.txStegomedia);

        ekstraksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                            //stegomed.setImageBitmap(BitmapFactory.decodeFile(absoluteFilePathSource));
                            txstego.setText(absoluteFilePathSource);

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
                        absoluteFilePathSource = cursor.getString(idx);
                        shsistem.setImageBitmap(BitmapFactory.decodeFile(absoluteFilePathSource));

                    }
                }
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
