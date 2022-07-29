package com.appybuilder.alioffical.myolxtypeapp.Activitise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appybuilder.alioffical.myolxtypeapp.Adapters.HomeAdapter;
import com.appybuilder.alioffical.myolxtypeapp.Apis.APIService;
import com.appybuilder.alioffical.myolxtypeapp.Apis.ApiClient;
import com.appybuilder.alioffical.myolxtypeapp.Models.AdsCategoryModel;
import com.appybuilder.alioffical.myolxtypeapp.Models.MyPrefrence;
import com.appybuilder.alioffical.myolxtypeapp.Models.UtiHomeModel;
import com.appybuilder.alioffical.myolxtypeapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowCategoryActivity extends AppCompatActivity {
    RecyclerView showcatRecler;
    ProgressBar progressBar;
    List<AdsCategoryModel.Datum>categoryModelList=new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_category);
        init_views();
    }
    public void init_views() {
        progressBar=findViewById(R.id.progressbar_browsecate);
        progressBar.setVisibility(View.VISIBLE);
        showcatRecler = findViewById(R.id.showcategory_recler);
         prePareData();
        layoutManager=new GridLayoutManager(this,2);
        categoryAdapter = new CategoryAdapter(categoryModelList, this);


    }

    private void prePareData() {
        String strToken = MyPrefrence.getStr("token");
        Call<AdsCategoryModel.AdsCatModel> call;
        APIService service = ApiClient.getClient().create(APIService.class);
        call = service.showAllAdsCategoryfunData("Bearer " + strToken);
        call.enqueue(new Callback<AdsCategoryModel.AdsCatModel>() {
            @Override
            public void onResponse(Call<AdsCategoryModel.AdsCatModel> call, Response<AdsCategoryModel.AdsCatModel> response) {
                categoryModelList=response.body().getData();
                Log.d("Message",response.message());
                 progressBar.setVisibility(View.INVISIBLE);
                layoutManager = new GridLayoutManager(getApplicationContext(), 2);
                categoryAdapter = new CategoryAdapter(categoryModelList, getApplicationContext());
                showcatRecler.setLayoutManager(layoutManager);
                showcatRecler.setItemAnimator(new DefaultItemAnimator());
                showcatRecler.setAdapter(categoryAdapter);
            }

            @Override
            public void onFailure(Call<AdsCategoryModel.AdsCatModel> call, Throwable t) {
                Log.d("Data", t.getMessage());
            }
        });
    }


}