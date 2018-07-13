package com.leakagewifi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leakagewifi.Class.Constants;
import com.leakagewifi.Connection.ConnectionDetector;
import com.leakagewifi.Connection.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KRISHNA on 12/20/2017.
 */

public class LoginActivity extends AppCompatActivity {
    public Button btnLogin;
    public TextView txtNewuser;
    public ImageView imageLogo;
    public EditText edtUsername,edtPassword;


    String strUsername,strPassword;
    Constants con;
    ConnectionDetector Constant;
    JSONParser jsonParser;
    public static Boolean isInternetPresent = false;
    String REGISTER_URL = "login.php";

    private static String resultdata = "data";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btnlogin);
        txtNewuser = (TextView) findViewById(R.id.txtnewuser);

        edtUsername = (EditText) findViewById(R.id.edtusername);
        edtPassword = (EditText) findViewById(R.id.edtpassword);

        imageLogo = (ImageView) findViewById(R.id.imagelogo);

        con = new Constants();
        Constant = new ConnectionDetector(getApplicationContext());
        isInternetPresent = Constant.isConnectingToInternet();
        jsonParser = new JSONParser();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);

                // Setting Dialog Title
                alertDialog.setTitle("Enablewifi");

                // Setting Dialog Message
                alertDialog.setMessage("Are you sure you want to Exit?");

                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        // Write your code here to invoke YES event
                        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        wifi.setWifiEnabled(false);

                    }
                });

                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();*/

                strUsername = edtUsername.getText().toString();
                strPassword = edtPassword.getText().toString();

                if (strUsername.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Enter User Name", Toast.LENGTH_LONG).show();
                }else if (strPassword.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_LONG).show();
                }else if (isInternetPresent) {
                    //new Load().execute();
                } else {
                    Toast.makeText(getApplicationContext(), "NO INTERNET CONNECTION", Toast.LENGTH_LONG).show();
                }
                Intent i = new Intent(LoginActivity.this, ResourcesActivity.class);
                startActivity(i);
                finish();
            }

        });
        txtNewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
            });

    }

    public class Load extends AsyncTask<String, String, String> {
        String strRegStatus;

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("username", strUsername));
            params.add(new BasicNameValuePair("password", strPassword));

            Log.i("dewy",strUsername);

            JSONObject jsonReg = jsonParser.makeHttpRequest(con.Api_Url + REGISTER_URL, "POST", params);
            /*Log.i("kkkk", String.valueOf(jsonReg));*/

            try {
                strRegStatus = jsonReg.getString("success");
                Log.i("gggg",strRegStatus);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(strRegStatus.equals("True")){
                Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
                Intent menupage = new Intent(LoginActivity.this,ResourcesActivity.class);
                startActivity(menupage);
                finish();
            } else {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }
        }
    }
}
