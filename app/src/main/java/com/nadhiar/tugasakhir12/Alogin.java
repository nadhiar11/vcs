package com.nadhiar.tugasakhir12;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.nadhiar.tugasakhir12.helper.SQLiteHandler;
import com.nadhiar.tugasakhir12.helper.SessionManager;

import java.util.HashMap;

public class Alogin extends AppCompatActivity {
    private TextView anda, email, kuesioner;
    public SQLiteHandler db;
    public SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alogin);

        anda = (TextView) findViewById(R.id.txAnda);
        email = (TextView) findViewById(R.id.txEmail);
        kuesioner = (TextView) findViewById(R.id.txKuesioner);

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
}
