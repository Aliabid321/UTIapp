package com.appybuilder.alioffical.myolxtypeapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.appybuilder.alioffical.myolxtypeapp.Apis.ClickListner;
import com.appybuilder.alioffical.myolxtypeapp.Models.UtiHomeModel;
import com.appybuilder.alioffical.myolxtypeapp.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.MyViewHolder> {
    List<UtiHomeModel.Datum> homeModelList;
    //    List<DummyModel>dummyModelList;
    Context context;
    ClickListner clickListnerCallBack;

    public FavAdapter(List<UtiHomeModel.Datum> homeModelList, Context context) {
        this.homeModelList = homeModelList;
        this.context = context;
    }

    public void setClickCallBack(ClickListner clickCallBack) {
        clickListnerCallBack = clickCallBack;
    }
    @NonNull
    @Override
    public FavAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_resourcefile, parent, false);
        return new MyViewHolder(MyItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavAdapter.MyViewHolder holder, int position) {
        UtiHomeModel.Datum homeModel = homeModelList.get(position);
        try {
            try {
                    Glide.with(context).load(homeModel.getAdsImages().get(0).getImageUrl()).into(holder.img);
                    holder.txt_nameItem.setText(homeModel.getAdsInfo().getItemName());
                    holder.txt_DesItem.setText(homeModel.getAdsInfo().getItemDescription());
                    holder.txt_dateItem.setText(homeModel.getAdsInfo().getCreatedAt());
                    holder.txt_loc_Item.setText(homeModel.getAdsInfo().getLocation());
                    holder.txt_nameItem.setText(homeModel.getAdsInfo().getItemName());
                    Glide.with(context).load(homeModel.getAdsImages().get(position).getImageUrl()).into(holder.img);


            }catch (NullPointerException e){
                Log.d("Null Pointer Exception", e.getMessage());
            }

        } catch (IndexOutOfBoundsException e) {
            Log.d("Error", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return homeModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_nameItem, txt_price, txt_dateItem, txt_DesItem, txt_loc_Item;
        ImageView img;
        CardView btnItemclick;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_nameItem = itemView.findViewById(R.id.name_id);
            txt_price = itemView.findViewById(R.id.price);
            txt_DesItem = itemView.findViewById(R.id.des_id);
            txt_loc_Item = itemView.findViewById(R.id.location);
            txt_dateItem = itemView.findViewById(R.id.date);
            img = itemView.findViewById(R.id.img_idItem);
            btnItemclick = itemView.findViewById(R.id.cardView);
            btnItemclick.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.cardView) {
                if (clickListnerCallBack != null) {
                    int pos = getAdapterPosition();
                    clickListnerCallBack.onItemClickListner(pos);
                }
            }
        }
    }

}
