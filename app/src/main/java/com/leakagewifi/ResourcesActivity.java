package com.leakagewifi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by KRISHNA on 2/9/2018.
 */

public class ResourcesActivity extends AppCompatActivity {
    public Button Storagebtn,Historybtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);

        Storagebtn = (Button) findViewById(R.id.btnstorage);
        Historybtn = (Button) findViewById(R.id.btnhistory);



         Storagebtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(ResourcesActivity.this, StorageActivity.class);
            startActivity(i);
            finish();
             }
    });

        Historybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ResourcesActivity.this, HistoryActivity.class);
                startActivity(i);
                finish();
            }
        });

        /*Storagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str  = "STORAGE";

                Intent i = new Intent(ResourcesActivity.this, EncryptionActivity.class);
                //String strnew = Storagebtn.getText().toString();
                i.putExtra("message", str);
                startActivity(i);
                finish();

            }
        });*/
}

}

