package com.appybuilder.alioffical.myolxtypeapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.appybuilder.alioffical.myolxtypeapp.Apis.APIService;
import com.appybuilder.alioffical.myolxtypeapp.Apis.ApiClient;
import com.appybuilder.alioffical.myolxtypeapp.Apis.ClickListner;
import com.appybuilder.alioffical.myolxtypeapp.Models.AdsCategoryModel;
import com.appybuilder.alioffical.myolxtypeapp.Models.ConstantModel;
import com.appybuilder.alioffical.myolxtypeapp.Models.MyPrefrence;
import com.appybuilder.alioffical.myolxtypeapp.Models.UpdateAdsModel;
import com.appybuilder.alioffical.myolxtypeapp.Models.UtiHomeModel;
import com.appybuilder.alioffical.myolxtypeapp.R;
import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyviewHolder> {
    List<UtiHomeModel.Datum> homeModelList;
    //    List<DummyModel>dummyModelList;
    Context context;
    ClickListner clickListnerCallBack;
    String strToken;
    String itemId;

    public void setClickCallBack(ClickListner clickCallBack) {
        clickListnerCallBack = clickCallBack;
    }

    public HomeAdapter(List<UtiHomeModel.Datum> homeModelList, Context context) {
        this.homeModelList = homeModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View MyItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.homeresourcefile, parent, false);
        return new MyviewHolder(MyItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.MyviewHolder holder, int position) {
        UtiHomeModel.Datum homeModel = homeModelList.get(position);
        try {
            Glide.with(context).load(homeModel.getAdsImages().get(0).getImageUrl()).into(holder.img);
            holder.txt_nameItem.setText(homeModel.getAdsInfo().getItemName());
            holder.txt_DesItem.setText(homeModel.getAdsInfo().getItemDescription());
            holder.txt_dateItem.setText(homeModel.getAdsInfo().getCreatedAt());
            holder.txt_loc_Item.setText(homeModel.getAdsInfo().getLocation());
            holder.txt_nameItem.setText(homeModel.getAdsInfo().getItemName());
            Glide.with(context).load(homeModel.getAdsImages().get(position).getImageUrl()).into(holder.img);
        } catch (IndexOutOfBoundsException e) {
            Log.d("Error", e.getMessage());
        }


    }

    @Override
    public int getItemCount() {
        return homeModelList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_nameItem, txt_price, txt_dateItem, txt_DesItem, txt_loc_Item;
        ImageView img;
        CardView btnItemclick;
        CheckBox chek_for_fav;


        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            strToken = MyPrefrence.getStr("token");
            txt_nameItem = itemView.findViewById(R.id.name_id);
            txt_price = itemView.findViewById(R.id.price);
            txt_DesItem = itemView.findViewById(R.id.des_id);
            txt_loc_Item = itemView.findViewById(R.id.location);
            txt_dateItem = itemView.findViewById(R.id.date);
            img = itemView.findViewById(R.id.img_idItem);
            btnItemclick = itemView.findViewById(R.id.cardView);
            btnItemclick.setOnClickListener(this);
            chek_for_fav = itemView.findViewById(R.id.chek_for_fav);
            chek_for_fav.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.cardView) {
                if (clickListnerCallBack != null) {
                    int pos = getAdapterPosition();
                    clickListnerCallBack.onItemClickListner(pos);
                }
            }
            if (view.getId() == R.id.chek_for_fav) {

                int pos = getAdapterPosition();
                itemId = homeModelList.get(pos).getAdsInfo().getId().toString();
            //    clickListnerCallBack.onItemClickListner(pos);
                boolean checked = ((CheckBox) view).isChecked();
                if (checked) {
                    setfav_function("favorite");
                } else {
                    setfav_function("un-favorite");
                }
            }
        }
    }

    public void setfav_function(String fav_unfav) {
        Call<UpdateAdsModel.UpdateModel> call;
        APIService service = ApiClient.getClient().create(APIService.class);
        // call = service.create_Ads_2("Bearer " + strToken,hashMap);
        call = service.update_fav_item("Bearer " + strToken, itemId, fav_unfav);
        call.enqueue(new Callback<UpdateAdsModel.UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateAdsModel.UpdateModel> call, Response<UpdateAdsModel.UpdateModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UpdateAdsModel.UpdateModel> call, Throwable t) {
                Toast.makeText(context, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
