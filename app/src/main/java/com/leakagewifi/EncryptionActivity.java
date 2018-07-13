package com.leakagewifi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by KRISHNA on 12/23/2017.
 */

public class EncryptionActivity extends AppCompatActivity {
    EditText edtPass;
    TextView textOutput,textInput;
    Button btnEncrypt, btnDecrypt;

    String ouputString;
    String setpassword,setdecrypt;
    String AES = "AES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encryption);


        edtPass = (EditText) findViewById(R.id.edtpass);
        textInput = (TextView) findViewById(R.id.textinput);

        textOutput = (TextView) findViewById(R.id.textoutput);

        btnEncrypt = (Button) findViewById(R.id.btnencrypt);
        btnDecrypt = (Button) findViewById(R.id.btndecrypt);

        setpassword=edtPass.getText().toString();
        setdecrypt=btnDecrypt.getText().toString();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*edtPass.setTransformationMethod(new PasswordTransformationMethod());*/

        btnEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ouputString = encrypt(textInput.getText().toString(), edtPass.getText().toString());
                    textOutput.setText(ouputString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });


        btnDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ouputString =decrypt(ouputString,edtPass.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                textOutput.setText(ouputString);
            }
        });

        //.setText(getIntent().getStringExtra("address"));

        Intent intent = getIntent();
        String str = intent.getStringExtra("message");
        textInput.setText(str);

        if(setpassword.equals(setdecrypt))
        {

        }else
        {
            Toast.makeText(getApplicationContext(),"Enter the correct password",Toast.LENGTH_LONG).show();
        }
    }

    private String decrypt(String ouputString, String password) throws Exception {
        SecretKeySpec key = generatekey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodevalue=Base64.decode(ouputString,Base64.DEFAULT);
        byte[] decvalue=c.doFinal(decodevalue);
        String decryptedvalue=new String(decvalue);
        return decryptedvalue;
    }

    private String encrypt(String Data, String password) throws Exception{
        SecretKeySpec key = generatekey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal =c.doFinal(Data.getBytes());
        String encryptedValue = Base64.encodeToString(encVal,Base64.DEFAULT);
        return encryptedValue;

    }


    private SecretKeySpec generatekey(String password) throws Exception{
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes=password.getBytes("UTF-8");
        digest.update(bytes, 0,bytes.length);
        byte[] key= digest.digest();
        SecretKeySpec secretKeySpec=new SecretKeySpec(key,"AES");
        return secretKeySpec;
    }



    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(getApplicationContext(), ResourcesActivity.class);
                startActivity(i);
                finish();
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), ResourcesActivity.class);
        startActivity(i);
        finish();
    }
}
