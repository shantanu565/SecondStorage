package com.shantanu.example.secondstorage;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileExplorer extends AppCompatActivity implements FileAdapter.OnItemClickListener {
    RecyclerView filesRV;
    FileAdapter adapter;
    List<File> files;
    String currentDir;
    File rootDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_explorer);

        rootDir = Environment.getExternalStorageDirectory();

        currentDir = rootDir.getAbsolutePath();
        filesRV = findViewById(R.id.rv);
        filesRV.setLayoutManager(new LinearLayoutManager(this));

        files = Arrays.asList(rootDir.listFiles());
        adapter = new FileAdapter(files, this);
        filesRV.setAdapter(adapter);

    }
    @Override
    public void onItemClick(String filePath, File file) {
        if (file.isDirectory()) {
            currentDir = filePath;
            files = Arrays.asList(file.listFiles());
            if (files != null) {
                if (files.size() != 0) {
                    adapter = new FileAdapter(files, FileExplorer.this);
                    filesRV.setAdapter(adapter);
                } else {
                    Toast.makeText(this, file.getName() + " EMPTY", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            String mimeType = getMimeType(currentDir);
            Uri apkURI = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
            i.setDataAndType(apkURI, mimeType);
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                startActivity(i);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "No app can open this type of file.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public String getMimeType(String url) {
        String mimeType = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return mimeType;
    }


}