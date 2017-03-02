package com.nadhiar.tugasakhir12;

/**
 * Created by 1312100999 on 1/25/2017.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nadhiar.tugasakhir12.algor.KriptografiVisual_teks;
import com.nadhiar.tugasakhir12.app.AppConfig;
import com.nadhiar.tugasakhir12.app.AppController;
import com.nadhiar.tugasakhir12.helper.SQLiteHandler;
import com.nadhiar.tugasakhir12.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Register extends Activity {

    private static final String TAG = Register.class.getSimpleName();
    private Button btnLinkToLogin;
    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private ImageView share1, share2;
    private SessionManager session;
    private SQLiteHandler db;
    public String seed;
    public Bitmap BmpTeksGambar;
    public Bitmap[] BmpShare = new Bitmap[2];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputFullName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        share1 = (ImageView) findViewById(R.id.share1);
        share2 = (ImageView) findViewById(R.id.share2);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(Register.this,
                    Utama.class);
            startActivity(intent);
            finish();
        }

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    public void daftar(View view) {

        String name = inputFullName.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            registerUser(name, email, password);

            //==============================================
            seed = inputPassword.getText().toString().trim();
            KriptografiVisual_teks vcc = new KriptografiVisual_teks();
            BmpTeksGambar = vcc.KonversiStringKeBitmap(seed, 300, 100);
            BmpShare = vcc.EknripsiKriptografiVisual(BmpTeksGambar);
            //share1.setImageBitmap(BmpTeksGambar);
            //share1.setVisibility(View.VISIBLE);
            //===========================================
            //nanti imageview nya dibuat invisible saja
            //===========================================
            share1.setImageBitmap(BmpShare[0]); //utk user
            share1.setVisibility(View.INVISIBLE);
            share2.setImageBitmap(BmpShare[1]);//utk sistem
            share2.setVisibility(View.INVISIBLE);

            //fungsi untuk upload share milik sistem
            Bitmap sh2 = BmpShare[1];
            try {
                sh2.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(new File("/sdcard/" + name + "_sistem.png")));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(),
                    "Please enter your details!", Toast.LENGTH_LONG)
                    .show();
        }

    }

    private void registerUser(final String name, final String email, final String password) {
        String tag_string_req = "req_register";
        pDialog.setMessage("Registering ...");
        showDialog();
        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        String uid = jObj.getString("uid");
                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user.getString("created_at");

                        // Inserting row in users table
                        db.addUser(name, email, uid, created_at);
                        share1.setDrawingCacheEnabled(true);
                        Bitmap b = share1.getDrawingCache();
                        Intent i = new Intent(Register.this, Utama.class);
                        i.putExtra("kunci", b);
                        startActivity(i);
                        //finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}