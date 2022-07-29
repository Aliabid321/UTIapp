package com.appybuilder.alioffical.myolxtypeapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appybuilder.alioffical.myolxtypeapp.Models.ChatModel;
import com.appybuilder.alioffical.myolxtypeapp.R;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    List<ChatModel> modelList;
    Context context;

    public ChatAdapter(List<ChatModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_resourcefile, parent, false);
        return new MyViewHolder(MyItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MyViewHolder holder, int position) {
        ChatModel model=modelList.get(position);
        holder.txt_senderName.setText(model.getStrReciver());
        holder.txt_phone_no.setText(model.getStrPhoneNo());

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_senderName,txt_phone_no;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_senderName=itemView.findViewById(R.id.sender_name);
            txt_phone_no=itemView.findViewById(R.id.phone_no);
        }
    }
}
