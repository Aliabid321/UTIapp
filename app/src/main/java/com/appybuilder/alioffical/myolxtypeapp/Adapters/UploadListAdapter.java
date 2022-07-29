package com.appybuilder.alioffical.myolxtypeapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appybuilder.alioffical.myolxtypeapp.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class UploadListAdapter extends RecyclerView.Adapter<UploadListAdapter.MyviewHolder> {
    public List<String> fileNameList;
    public List<String> fileDoneList;
    Context context;

    public UploadListAdapter(List<String> fileNameList, List<String> fileDoneList, Context context) {
        this.fileNameList = fileNameList;
        this.fileDoneList = fileDoneList;
        this.context = context;
    }

    @NonNull
    @Override
    public UploadListAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selecteditemsresourcefile,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UploadListAdapter.MyviewHolder holder, int position) {
        String fileName = fileNameList.get(position);
        holder.fileName.setText(fileName);
        String fileDone = fileDoneList.get(position);
        if (fileDone.equals("Uploading")){
           // holder.fileDone.setImageResource(R.drawable.itembg);
            Glide.with(context).load(fileDone).into(holder.fileDone);
        } else {
            Glide.with(context).load(fileDone).into(holder.fileDone);
        }

    }

    @Override
    public int getItemCount() {
        return fileNameList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        public TextView fileName;
        public ImageView fileDone;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            fileName = itemView.findViewById(R.id.txtFilename);
            fileDone = itemView.findViewById(R.id.imgLoading);
        }
    }
}
