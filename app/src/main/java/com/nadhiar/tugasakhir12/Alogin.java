package com.nadhiar.tugasakhir12;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.nadhiar.tugasakhir12.helper.SQLiteHandler;
import com.nadhiar.tugasakhir12.helper.SessionManager;

import java.util.HashMap;

public class Alogin extends AppCompatActivity {
    private TextView anda, email, kuesioner, txTrims;
    public SQLiteHandler db;
    public SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alogin);

        Typeface kau = Typeface.createFromAsset(getAssets(), "fonts/KaushanScript.otf");
        Typeface lobs = Typeface.createFromAsset(getAssets(), "fonts/Lobster.otf");

        anda = (TextView) findViewById(R.id.txAnda);
        anda.setTypeface(lobs);
        email = (TextView) findViewById(R.id.txEmail);
        email.setTypeface(lobs);
        kuesioner = (TextView) findViewById(R.id.txKuesioner);
        kuesioner.setTypeface(kau);
        txTrims = (TextView)findViewById(R.id.txTrims);
        txTrims.setTypeface(kau);

        db = new SQLiteHandler(getApplicationContext());

        session = new SessionManager(getApplicationContext());
        // if (!session.isLoggedIn()) {
        //    logoutUser();
        // }

        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String mail = user.get("email");

        anda.setText(name);
        email.setText(mail);

        kuesioner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //====================================
                //kasih intent ke halaman isi kueioner
            }
        });
    }

    public void keluar(View view){
        session.setLogin(false);
        db.deleteUsers();
        Intent intent = new Intent(Alogin.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
