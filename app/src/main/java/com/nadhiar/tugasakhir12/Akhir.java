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

public class Akhir extends AppCompatActivity {

    private TextView nama;
    public SQLiteHandler db;
    public SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akhir);

        Typeface lobs = Typeface.createFromAsset(getAssets(),"fonts/Lobster.otf");
        nama = (TextView) findViewById(R.id.txnama);
        nama.setTypeface(lobs);

        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails();
        String name = user.get("name");
        nama.setText(name);
    }

    public void logout (View view){
        session.setLogin(false);
        db.deleteUsers();
        Intent intent = new Intent(Akhir.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
