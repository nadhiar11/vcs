package com.nadhiar.tugasakhir12;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nadhiar.tugasakhir12.algor.KriptografiVisual_teks;

import java.io.IOException;

public class Ujicoba extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;
    public static final int PICK_IMAGE_2 = 2;
    public int idx;
    private ImageView share1,share2,sharedek;
    private TextView upl1,dek,upl2;
    private Bitmap sh1,sh2,sh3;
    public String path1,path2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ujicoba);

        share1 = (ImageView)findViewById(R.id.share1);
        share2 = (ImageView)findViewById(R.id.share2);
        sharedek = (ImageView)findViewById(R.id.idekrip);
        upl1 =(TextView)findViewById(R.id.upload1);
        upl2 = (TextView)findViewById(R.id.upload2);
        dek = (TextView)findViewById(R.id.dekrip);

        //sh1 = getIntent().getParcelableExtra("kunci1");
        //share1.setImageBitmap(sh1);

        upl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pilih gambar"), PICK_IMAGE);
            }
        });

        upl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pilih gambar"), PICK_IMAGE_2);
            }
        });


        dek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KriptografiVisual_teks vcc = new KriptografiVisual_teks();
                //sh1 = BitmapFactory.decodeFile(path1);
                //sh2 = BitmapFactory.decodeFile(path2);
                sh3 = vcc.DekripsiKriptografiVisual(sh1,sh2);

                sharedek.setImageBitmap(sh3);
            }
        });

    }

    //Method Ambil Gambar dari Gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                sh1 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                share1.setImageBitmap(sh1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == PICK_IMAGE_2 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                sh2 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                share2.setImageBitmap(sh2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
