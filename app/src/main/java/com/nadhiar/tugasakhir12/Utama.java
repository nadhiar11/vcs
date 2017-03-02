package com.nadhiar.tugasakhir12;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nadhiar.tugasakhir12.helper.SQLiteHandler;
import com.nadhiar.tugasakhir12.helper.SessionManager;

import java.util.HashMap;

public class Utama extends AppCompatActivity {

    private TextView txname;
    private TextView txemail;
    private Button next;
    private Button logout;
    private ImageView imshare;

    public SQLiteHandler db;
    public SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama);

        txname = (TextView) findViewById(R.id.name);
        txemail = (TextView) findViewById(R.id.email);
        next = (Button) findViewById(R.id.btnNext);
        logout = (Button) findViewById(R.id.logout);
        imshare = (ImageView) findViewById(R.id.share);

        final Bitmap bm = getIntent().getParcelableExtra("kunci");
        imshare.setImageBitmap(bm);
        imshare.setVisibility(View.INVISIBLE);

        db = new SQLiteHandler(getApplicationContext());

        session = new SessionManager(getApplicationContext());
        // if (!session.isLoggedIn()) {
        //    logoutUser();
        // }

        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        txname.setText(name);
        txemail.setText(email);

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //pasing ke activity selanjutnya
                imshare.setDrawingCacheEnabled(true);
                Bitmap c = imshare.getDrawingCache();
                Intent intent = new Intent(Utama.this, Kompresi.class);
                intent.putExtra("kunci1", c);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                session.setLogin(false);
                db.deleteUsers();
                Intent intent = new Intent(Utama.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
