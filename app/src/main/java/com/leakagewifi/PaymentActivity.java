package com.leakagewifi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by KRISHNA on 2/12/2018.
 */

public class PaymentActivity extends AppCompatActivity {
    TextView dialog_Info;
    Button dialog_Cancel,dialog_Ok;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // load the layout
        setContentView(R.layout.activity_payment);

        dialog_Info=(TextView)findViewById(R.id.dialog_info);

        dialog_Cancel=(Button)findViewById(R.id.dialog_cancel);
        dialog_Ok=(Button)findViewById(R.id.dialog_ok);

        new AlertDialog.Builder(this)
                .setTitle("Closing application")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("No", null).show();
    }
    /*public void openDialog() {
        final Dialog dialog = new Dialog(PaymentAdctivity.this); // Context, this, etc.
        dialog.setContentView(R.layout.activity_payment);
        dialog.setTitle("hello");
        dialog.show();
    }*/

}
