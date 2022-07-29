package com.appybuilder.alioffical.myolxtypeapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.toolbox.Volley;
import com.appybuilder.alioffical.myolxtypeapp.Activitise.ShowItemDetailActivity;
import com.appybuilder.alioffical.myolxtypeapp.Adapters.FavAdapter;
import com.appybuilder.alioffical.myolxtypeapp.Adapters.HomeAdapter;
import com.appybuilder.alioffical.myolxtypeapp.Apis.APIService;
import com.appybuilder.alioffical.myolxtypeapp.Apis.ApiClient;
import com.appybuilder.alioffical.myolxtypeapp.Apis.ClickListner;
import com.appybuilder.alioffical.myolxtypeapp.Models.ConstantModel;
import com.appybuilder.alioffical.myolxtypeapp.Models.MyPrefrence;
import com.appybuilder.alioffical.myolxtypeapp.Models.UtiHomeModel;
import com.appybuilder.alioffical.myolxtypeapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavFragment extends Fragment implements ClickListner {

    List<UtiHomeModel.Datum> homeAdsList = new ArrayList<>();
    RecyclerView recyclerViewFav;
    LinearLayoutManager layoutManager;
    MyPrefrence myPrefrence;
    ProgressBar home_progressbar;
    String strToken;
    View view;
    FavAdapter homeAdapter;
    public FavFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_fav, container, false);
        ConstantModel.chek_for_fav=true;
        init_Views();
        return view;
    }
    public void init_Views() {
        myPrefrence = new MyPrefrence(getContext());
       // vollyRequesQueu = Volley.newRequestQueue(getContext());
        recyclerViewFav = view.findViewById(R.id.fav_recler_items);
        home_progressbar = view.findViewById(R.id.fav_progressbar);
        home_progressbar.setVisibility(View.VISIBLE);
        showAllAds();//calling from database....
        //getData();

    }
    public void showAllAds() {
        strToken = MyPrefrence.getStr("token");
        Call<UtiHomeModel.NewHomeModel> call;
        APIService service = ApiClient.getClient().create(APIService.class);
        call = service.showAllfav_Ads("Bearer " + strToken);
        call.enqueue(new Callback<UtiHomeModel.NewHomeModel>() {
            @Override
            public void onResponse(Call<UtiHomeModel.NewHomeModel> call, Response<UtiHomeModel.NewHomeModel> response) {
                homeAdsList = response.body().getData();
                home_progressbar.setVisibility(View.INVISIBLE);
                layoutManager = new GridLayoutManager(getContext(), 2);
                homeAdapter = new FavAdapter(homeAdsList, getContext());
                recyclerViewFav.setLayoutManager(layoutManager);
                recyclerViewFav.setItemAnimator(new DefaultItemAnimator());
                recyclerViewFav.setAdapter(homeAdapter);
                homeAdapter.setClickCallBack(FavFragment.this);
            }


            @Override
            public void onFailure(Call<UtiHomeModel.NewHomeModel> call, Throwable t) {
                Log.d("Data", t.getMessage());
            }
        });
    }
    //callback listner....
    @Override
    public void onItemClickListner(int posistion) {
        Intent intent=new Intent(getContext(), ShowItemDetailActivity.class);
        intent.putExtra("id",homeAdsList.get(posistion).getAdsInfo().getId());
        startActivity(intent);
    }
}