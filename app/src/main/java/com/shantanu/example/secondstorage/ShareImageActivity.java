package com.shantanu.example.secondstorage;

import android.content.Intent;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShareImageActivity extends AppCompatActivity {
    Button btnCamera,btn_sharePrivate,btnShareInternal;
    private File file1, file2;
    private int REQUEST_CODE = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_image);

        btnCamera=findViewById(R.id.btn_camera);
        btn_sharePrivate=findViewById(R.id.btn_shareprivate);
        btnShareInternal=findViewById(R.id.btn_shareinternal);

    }
    public void takepic(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file2 = getOutputMediaFile();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this,
                        getPackageName() + ".provider", file2));
        startActivityForResult(intent, REQUEST_CODE);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show();
            file1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/" + getPackageName() + "/images/" + file2.getName());
            try {
                FileUtils.copyFile(file2, file1);
            } catch (IOException e) {
              e.printStackTrace();
            }
        }
    }

    public void shareprivate(View v){


    }

    public void shareinternal(View v){
        if(file2==null){
            Toast.makeText(this, "Please Click a Picture First", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent privateIntent = new Intent();
        privateIntent.setAction(Intent.ACTION_SEND);
        privateIntent.setType("image/*");
        privateIntent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(this,
                getPackageName() + ".provider",file2));
        privateIntent.putExtra(Intent.EXTRA_TEXT, "Internal Memory");
        privateIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(privateIntent, "Send via"));

    }
    private File getOutputMediaFile() {
        File mediaStorageDir1 = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        if (!mediaStorageDir1.exists()) {
            if (!mediaStorageDir1.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir1.getPath() + File.separator +
                "Img_" + timeStamp + ".jpg");
    }
}
