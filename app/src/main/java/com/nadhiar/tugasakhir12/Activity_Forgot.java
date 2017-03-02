package com.nadhiar.tugasakhir12;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.nadhiar.tugasakhir12.algor.KriptografiVisual_teks;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.FileUtil;

public class Activity_Forgot extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    ImageView ishare1, ishare2, imDekrip;
    private File share1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        ishare1 = (ImageView) findViewById(R.id.imShare1);
        ishare2 = (ImageView) findViewById(R.id.imShare2);
        imDekrip = (ImageView) findViewById(R.id.imDekrip);
    }

    public void upl_share1() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    public void upl_share2(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    public void upl_dekrip(View view) {
        KriptografiVisual_teks vcc = new KriptografiVisual_teks();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (data == null) {
                showError("Gagal membuka gambar!");
                return;
            }
            try {
                share1 = FileUtil.from(this, data.getData());
                ishare1.setImageBitmap(BitmapFactory.decodeFile(share1.getAbsolutePath()));
            } catch (IOException e) {
                showError("Gagal membaca data gambar!");
                e.printStackTrace();
            }
        }
    }

    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

}
