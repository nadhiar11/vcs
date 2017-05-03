package com.nadhiar.tugasakhir12;

/**
 * Created by 1312100999 on 1/25/2017.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nadhiar.tugasakhir12.algor.KriptografiVisual_teks;
import com.nadhiar.tugasakhir12.app.AppConfig;
import com.nadhiar.tugasakhir12.app.AppController;
import com.nadhiar.tugasakhir12.helper.SQLiteHandler;
import com.nadhiar.tugasakhir12.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

public class Register extends Activity {

    private static final String TAG = Register.class.getSimpleName();
    private Button btnLinkToLogin;
    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private String UPLOAD_URL = "http://192.168.43.45/api_ta/include/upload.php"; // alamat server upload
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    public Bitmap BmpTeksGambar, BmpShare1, BmpShare2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Typeface kau = Typeface.createFromAsset(getAssets(), "fonts/KaushanScript.otf");

        inputFullName = (EditText) findViewById(R.id.name);
        inputFullName.setTypeface(kau);
        inputEmail = (EditText) findViewById(R.id.email);
        inputEmail.setTypeface(kau);
        inputPassword = (EditText) findViewById(R.id.password);
        inputPassword.setTypeface(kau);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        btnLinkToLogin.setTypeface(kau);


        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        session = new SessionManager(getApplicationContext());
        db = new SQLiteHandler(getApplicationContext());
        if (session.isLoggedIn()) {
            Intent intent = new Intent(Register.this,
                    Utama.class);
            startActivity(intent);
            finish();
        }


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

        if (!name.isEmpty() && !email.isEmpty() && password.length() >= 6) {
            registrasiUser(name, email, password);

            KriptografiVisual_teks oke = new KriptografiVisual_teks();
            BmpTeksGambar = oke.KonversiStringKeBitmap(password, 300, 100);

            BmpShare1 = Bitmap.createBitmap(BmpTeksGambar.getWidth(), BmpTeksGambar.getHeight(), BmpTeksGambar.getConfig());
            BmpShare2 = Bitmap.createBitmap(BmpTeksGambar.getWidth(), BmpTeksGambar.getHeight(), BmpTeksGambar.getConfig());

            for (int i = 0; i < BmpTeksGambar.getWidth() - 1; i++) {
                for (int j = 0; j < BmpTeksGambar.getHeight() - 1; j++) {
                    Random generator = new Random();
                    int indexing = generator.nextInt(6);
                    if (i % 2 == 0 && j % 2 == 0) {
                        if (indexing == 0) {
                            BmpShare1.setPixel(i, j, Color.WHITE);
                            BmpShare1.setPixel(i, j + 1, Color.WHITE);
                            BmpShare1.setPixel(i + 1, j, Color.BLACK);
                            BmpShare1.setPixel(i + 1, j + 1, Color.BLACK);
                        }
                        if (indexing == 1) {
                            BmpShare1.setPixel(i, j, Color.BLACK);
                            BmpShare1.setPixel(i, j + 1, Color.BLACK);
                            BmpShare1.setPixel(i + 1, j, Color.WHITE);
                            BmpShare1.setPixel(i + 1, j + 1, Color.WHITE);
                        }
                        if (indexing == 2) {
                            BmpShare1.setPixel(i, j, Color.BLACK);
                            BmpShare1.setPixel(i, j + 1, Color.WHITE);
                            BmpShare1.setPixel(i + 1, j, Color.BLACK);
                            BmpShare1.setPixel(i + 1, j + 1, Color.WHITE);
                        }
                        if (indexing == 3) {
                            BmpShare1.setPixel(i, j, Color.WHITE);
                            BmpShare1.setPixel(i, j + 1, Color.BLACK);
                            BmpShare1.setPixel(i + 1, j, Color.WHITE);
                            BmpShare1.setPixel(i + 1, j + 1, Color.BLACK);
                        }
                        if (indexing == 4) {
                            BmpShare1.setPixel(i, j, Color.WHITE);
                            BmpShare1.setPixel(i, j + 1, Color.BLACK);
                            BmpShare1.setPixel(i + 1, j, Color.BLACK);
                            BmpShare1.setPixel(i + 1, j + 1, Color.WHITE);
                        }
                        if (indexing == 5) {
                            BmpShare1.setPixel(i, j, Color.BLACK);
                            BmpShare1.setPixel(i, j + 1, Color.WHITE);
                            BmpShare1.setPixel(i + 1, j, Color.WHITE);
                            BmpShare1.setPixel(i + 1, j + 1, Color.BLACK);
                        }
                    }

                    // Untuk Pixel Hitam
                    if (BmpTeksGambar.getPixel(i, j) == Color.BLACK) {
                        if (BmpShare1.getPixel(i, j) == Color.BLACK)
                            BmpShare2.setPixel(i, j, Color.WHITE);
                        else
                            BmpShare2.setPixel(i, j, Color.BLACK);
                    }

                    //Untuk pixel Putih (berikan Noise)
                    else {
                        if (BmpShare1.getPixel(i, j) == Color.BLACK)
                            BmpShare2.setPixel(i, j, Color.BLACK);
                        else
                            BmpShare2.setPixel(i, j, Color.WHITE);
                    }

                }
            }

            //share1.setImageBitmap(BmpShare1); //------------------------------------>> share utk user
            //share1.setVisibility(View.INVISIBLE);
            //share2.setImageBitmap(BmpShare2); //------------------------------------>> share untuk sistem
            //share2.setVisibility(View.INVISIBLE);
            try {
                uploadShare();
            } catch (Exception e) {
                //TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else {
            Toast.makeText(getApplicationContext(),
                    "Mohon masukkan data dengan benar!", Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void registrasiUser(final String name, final String email, final String password) {
        String tag_string_req = "req_register";
        pDialog.setMessage("Sedang mendaftarkan");
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

                        // masukkan data
                        //======================================
                        // passing ke activity selanjutnya
                        //======================================

                        db.addUser(name, email, uid, created_at);
                        //share1.setDrawingCacheEnabled(true);
                        Bitmap b = BmpShare1;
                        Intent i = new Intent(Register.this, Utama.class);
                        i.putExtra("kunci", b);
                        startActivity(i);
                        //finish();
                    } else {
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
                // Posting params ke register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };
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

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 0, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadShare() {
        //final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //loading.dismiss();
                Toast.makeText(Register.this, s, Toast.LENGTH_LONG).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //loading.dismiss();
                        Toast.makeText(Register.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Convert Bitmap ke String
                String image = getStringImage(BmpShare2);
                String name = inputEmail.getText().toString().trim();
                Map<String, String> params = new Hashtable<String, String>();

                //Menambahkan parameter
                params.put(KEY_IMAGE, image);
                params.put(KEY_NAME, name);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}