package com.shantanu.example.secondstorage;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder>{

    private List<File> files;
    private OnItemClickListener onItemClickListener;


    public FileAdapter(List<File> files, OnItemClickListener onItemClickListener) {
        this.files = files;
        this.onItemClickListener = onItemClickListener;
    }

    public void setFiles(List<File> files) {
        this.files = files;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rowlayout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final File currentFile = files.get(position);
       viewHolder.fileName.setText(currentFile.getName());
        if (currentFile.isDirectory()) {
           viewHolder.fileIcon.setImageResource(R.drawable.folder);
        } else if (currentFile.isFile()) {
            viewHolder.fileIcon.setImageResource(R.drawable.file);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(currentFile.getAbsolutePath(), currentFile);
            }
        });

    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView fileName;
        ImageView fileIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fileName = itemView.findViewById(R.id.txt_name);
            fileIcon = itemView.findViewById(R.id.img_folder);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String filePath, File file);
    }
}
