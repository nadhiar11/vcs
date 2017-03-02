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
import android.widget.ImageView;
import android.widget.Toast;

import com.nadhiar.tugasakhir12.helper.SQLiteHandler;
import com.nadhiar.tugasakhir12.helper.SessionManager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class Stego extends AppCompatActivity {
    private ImageView shkom;
    private ImageView stegomed;
    public int indexingW, indexingH;
    public String picturePath;
    public String label;
    public SQLiteHandler db;
    public SessionManager session;
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stego);

        shkom = (ImageView) findViewById(R.id.sh1kom);
        stegomed = (ImageView) findViewById(R.id.imstego);

        final Bitmap stego = getIntent().getParcelableExtra("kunci2");
        shkom.setImageBitmap(stego);
    }

    public void pilih(View view) {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    public void sisip(View view) {
        Bitmap BmpCover = BitmapFactory.decodeFile(picturePath); // Gambar bebas
        Bitmap BmpStegoImage = Bitmap.createBitmap(BmpCover.getWidth(), BmpCover.getHeight(), Bitmap.Config.ARGB_8888);

        final Bitmap BmpShare1 = getIntent().getParcelableExtra("kunci2"); // share
        indexingW = BmpStegoImage.getWidth() / BmpShare1.getWidth();
        indexingH = BmpStegoImage.getHeight() / BmpShare1.getHeight();

        // YASS
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

        //ambil dari db
        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        String name = user.get("name");

        //simpan hasil penyisipan
        label = name + "_stego.png";
        saveBitmap(BmpStegoImage, label);
    }

    public void lanjut (View view){
        Intent i = new Intent(Stego.this, Akhir.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            stegomed.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

    private String saveBitmap(Bitmap bm, String filename) {
        try {
            String filepath = filename;
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                AlertDialog ad = new AlertDialog.Builder(this).create();
                ad.setTitle("Error");
                ad.setMessage("External Storage is not mounted. The image was not saved");
                ad.show();
                Log.e("EncodeActivity", "Could not save image as " + filename);
                return null;
            }
            String fname = Environment.getExternalStorageDirectory() + "/" + filepath;
            FileOutputStream fos = new FileOutputStream(fname);
            bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            return fname;
        } catch (FileNotFoundException e) {
            Log.e("EncodeActivity", e.getMessage());
        } catch (IOException e) {
            Log.e("EncodeActivity", e.getMessage());
        }
        return null;
    }

}
