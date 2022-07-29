package com.appybuilder.alioffical.myolxtypeapp.Activitise;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appybuilder.alioffical.myolxtypeapp.Models.AdsCategoryModel;
import com.appybuilder.alioffical.myolxtypeapp.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    List<AdsCategoryModel.Datum>adsCatModelList;
    Context context;

    public CategoryAdapter(List<AdsCategoryModel.Datum> adsCatModelList, Context context) {
        this.adsCatModelList = adsCatModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.showcateresourcefile, parent, false);
        return new MyViewHolder(MyItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder holder, int position) {
         AdsCategoryModel.Datum obj =adsCatModelList.get(position);
         holder.txtCate.setText(obj.getCategoryName());

    }

    @Override
    public int getItemCount() {
        return adsCatModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView catImageUrl;
        TextView txtCate;
        LinearLayout btnCateClick;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            catImageUrl=itemView.findViewById(R.id.Item_Image);
            txtCate=itemView.findViewById(R.id.txt_item_name);
            btnCateClick=itemView.findViewById(R.id.item_1);
            btnCateClick.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId()==R.id.item_1){
                String id=adsCatModelList.get(getAdapterPosition()).getId().toString();
                Intent intent=new Intent(context,ProductDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("add_id",id);
                context.startActivity(intent);
            }
        }
    }
}
