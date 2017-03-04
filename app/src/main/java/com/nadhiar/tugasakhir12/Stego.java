package com.nadhiar.tugasakhir12;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nadhiar.tugasakhir12.helper.SQLiteHandler;
import com.nadhiar.tugasakhir12.helper.SessionManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class Stego extends AppCompatActivity {
    private ImageView shkom;
    private ImageView stegomed;
    private TextView txtCover;
    private Button sisip;
    public Bitmap BmpShare1, BmpCover, BmpStegoImage;
    public int indexingW, indexingH, idx;
    public String absoluteFilePathSource,name;
    public SQLiteHandler db;
    public SessionManager session;
    public static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stego);

        shkom = (ImageView) findViewById(R.id.sh1kom);
        stegomed = (ImageView) findViewById(R.id.imstego);
        txtCover = (TextView) findViewById(R.id.txCover);
        sisip = (Button) findViewById(R.id.sisip);

        BmpShare1 = getIntent().getParcelableExtra("kunci2");
        shkom.setImageBitmap(BmpShare1);

        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        name = user.get("name");

        sisip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                Toast.makeText(getApplicationContext(), "Berhasil disisipkan", Toast.LENGTH_LONG).show();

                //save hasil stego
                try {
                    BmpStegoImage.compress(Bitmap.CompressFormat.PNG, 0, new FileOutputStream(new File("/sdcard/" + name + "_stego.png")));
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

    public void pilih(View view) {
        Intent AmbilFoto = new Intent(Intent.ACTION_PICK);
        AmbilFoto.setType("image/*");
        startActivityForResult(AmbilFoto, PICK_IMAGE);
    }

    public void lanjut(View view) {
        Intent i = new Intent(Stego.this, Akhir.class);
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
                            stegomed.setImageBitmap(BitmapFactory.decodeFile(absoluteFilePathSource));
                        //txtCover.setText(absoluteFilePathSource);

                    }
                }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
