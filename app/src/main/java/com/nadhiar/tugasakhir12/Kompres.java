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

public class Kompres extends AppCompatActivity {
    private ImageView share, shareKom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kompres);

        share = (ImageView) findViewById(R.id.imgsh1);
        shareKom = (ImageView) findViewById(R.id.imgsh1kom);

        Bitmap bm = getIntent().getParcelableExtra("kunci1");
        share.setImageBitmap(bm);
    }

    public void kompresi(View view) {
        Bitmap sh1 = getIntent().getParcelableExtra("kunci1");
        ByteArrayOutputStream sh1out = new ByteArrayOutputStream();
        sh1.compress(Bitmap.CompressFormat.PNG, 100, sh1out);
        Bitmap sh1dec = BitmapFactory.decodeStream(new ByteArrayInputStream(sh1out.toByteArray()));
        shareKom.setImageBitmap(sh1dec);
    }

    public void terus(View view) {
        //passing ke activity selanjutnya
        shareKom.setDrawingCacheEnabled(true);
        Bitmap d = shareKom.getDrawingCache();
        Intent intent = new Intent(Kompres.this, Stegano.class);
        intent.putExtra("kunci2", d);
        startActivity(intent);
        //finish();
    }

}
