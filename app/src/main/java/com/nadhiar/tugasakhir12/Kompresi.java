package com.nadhiar.tugasakhir12;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class Kompresi extends AppCompatActivity {

    private ImageView sh1;
    private ImageView sh1kom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kompresi);

        sh1 = (ImageView) findViewById(R.id.imgsh1);
        sh1kom = (ImageView) findViewById(R.id.imgsh1kom);

        final Bitmap bm = getIntent().getParcelableExtra("kunci1");
        sh1.setImageBitmap(bm);
    }

    public void kompresi(View view){
        final Bitmap sh1 = getIntent().getParcelableExtra("kunci1");
        ByteArrayOutputStream sh1out = new ByteArrayOutputStream();
        sh1.compress(Bitmap.CompressFormat.PNG, 100, sh1out);
        Bitmap sh1dec = BitmapFactory.decodeStream(new ByteArrayInputStream(sh1out.toByteArray()));
        sh1kom.setImageBitmap(sh1dec);
    }

    public void lanjut (View view){
        //passing ke activity selanjutnya
        sh1kom.setDrawingCacheEnabled(true);
        Bitmap d = sh1kom.getDrawingCache();
        Intent i = new Intent(Kompresi.this, Stego.class);
        i.putExtra("kunci2",d);
        startActivity(i);
        finish();
    }

}
