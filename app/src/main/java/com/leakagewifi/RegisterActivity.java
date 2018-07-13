package com.leakagewifi;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RegisterActivity extends AppCompatActivity {

    public TextView txtReg;
    public EditText edtName,edtPass,edtEmail,edtMobile,edtCity;
    public Button btnReg;

    String strName,strPass,strEmail,strMobile,strCity;
    Constants con;
    ConnectionDetector Constant;
    JSONParser jsonParser;
    public static Boolean isInternetPresent = false;
    String REGISTER_URL = "userregister.php";

    private static String resultdata = "data";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        txtReg = (TextView) findViewById(R.id.txtreg);

        edtName = (EditText) findViewById(R.id.edtname);
        edtPass = (EditText) findViewById(R.id.edtpass);
        edtEmail = (EditText) findViewById(R.id.edtemail);
        edtMobile = (EditText) findViewById(R.id.edtmobile);
        edtCity = (EditText) findViewById(R.id.edtcity);

        btnReg = (Button) findViewById(R.id.btnreg);

        con					    = new Constants();
        Constant 				= new ConnectionDetector(getApplicationContext());
        isInternetPresent 		= Constant.isConnectingToInternet();
        jsonParser              = new JSONParser();




        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strName = edtName.getText().toString();
                strPass = edtPass.getText().toString();
                strEmail = edtEmail.getText().toString();
                strMobile = edtMobile.getText().toString();
                strCity = edtCity.getText().toString();

                if (strName.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Enter User Name", Toast.LENGTH_LONG).show();
                }else if (strPass.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_LONG).show();
                }else if (strEmail.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Enter Email", Toast.LENGTH_LONG).show();
                }else if (strMobile.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Enter Mobile Number", Toast.LENGTH_LONG).show();
                }else if (strCity.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Enter City", Toast.LENGTH_LONG).show();
                }else if (isInternetPresent) {
                    new Load().execute();
                } else {
                    Toast.makeText(getApplicationContext(),"NO INTERNET CONNECTION",Toast.LENGTH_LONG).show();
                }
                /*Intent i = new Intent(RegisterActivity.this, LoginpassActivity.class);
                startActivity(i);
                finish();*/
            }

        });


    }

    public class Load extends AsyncTask<String, String, String> {
        String strRegStatus;

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("username", strName));
            params.add(new BasicNameValuePair("password", strPass));
            params.add(new BasicNameValuePair("email", strEmail));
            params.add(new BasicNameValuePair("mobile_no", strMobile));
            params.add(new BasicNameValuePair("city", strCity));
            Log.i("dewy",strName);

            JSONObject jsonReg = jsonParser.makeHttpRequest(con.Api_Url + REGISTER_URL, "POST", params);
            Log.i("kkkk", String.valueOf(jsonReg));

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
                Intent menupage = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(menupage);
                finish();
            }
            if(strRegStatus.equals("faild")){
                Toast.makeText(getApplicationContext(),"Already Register in Email Address",Toast.LENGTH_LONG).show();
                Intent menupage = new Intent(RegisterActivity.this,RegisterActivity.class);
                startActivity(menupage);
                finish();
            }


            else {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }
        }
    }
}
