package com.shantanu.example.secondstorage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnFileExplorer,btnshare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFileExplorer=findViewById(R.id.btn_file);
        btnshare=findViewById(R.id.btn_share);

        btnFileExplorer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,FileExplorer.class);
                startActivity(intent);
            }
        });
        btnshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ShareImageActivity.class);
                startActivity(intent);
            }
        });
    }
}
