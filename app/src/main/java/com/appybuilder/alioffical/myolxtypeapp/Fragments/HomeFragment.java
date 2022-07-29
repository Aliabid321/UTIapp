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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.appybuilder.alioffical.myolxtypeapp.Activitise.ShowItemDetailActivity;
import com.appybuilder.alioffical.myolxtypeapp.Adapters.HomeAdapter;
import com.appybuilder.alioffical.myolxtypeapp.Apis.APIService;
import com.appybuilder.alioffical.myolxtypeapp.Apis.ApiClient;
import com.appybuilder.alioffical.myolxtypeapp.Apis.ClickListner;
import com.appybuilder.alioffical.myolxtypeapp.Models.DummyModel;
import com.appybuilder.alioffical.myolxtypeapp.Models.MyPrefrence;
import com.appybuilder.alioffical.myolxtypeapp.Models.SampleModel;
import com.appybuilder.alioffical.myolxtypeapp.Models.UtiHomeModel;
import com.appybuilder.alioffical.myolxtypeapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements ClickListner {
    public View view;
    public RecyclerView reclerHome;
    LinearLayoutManager layoutManager;
    HomeAdapter homeAdapter;
    ProgressBar home_progressbar;
    RequestQueue vollyRequesQueu;
    MyPrefrence myPrefrence;
    String strToken;
    List<UtiHomeModel.Datum> homeAdsList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        init_Views();//intilization of views......
        return view;
    }

    public void init_Views() {
        myPrefrence = new MyPrefrence(getContext());
        vollyRequesQueu = Volley.newRequestQueue(getContext());
        reclerHome = view.findViewById(R.id.home_recler);
        home_progressbar = view.findViewById(R.id.home_progressbar);
        home_progressbar.setVisibility(View.VISIBLE);
        showAllAds();//calling from database....
        //getData();

    }
    public void showAllAds() {
        strToken = MyPrefrence.getStr("token");
        Call<UtiHomeModel.NewHomeModel> call;
        APIService service = ApiClient.getClient().create(APIService.class);
        call = service.showAllAdsfunData("Bearer " + strToken);
        call.enqueue(new Callback<UtiHomeModel.NewHomeModel>() {
            @Override
            public void onResponse(Call<UtiHomeModel.NewHomeModel> call, Response<UtiHomeModel.NewHomeModel> response) {
                homeAdsList = response.body().getData();
                home_progressbar.setVisibility(View.INVISIBLE);
                layoutManager = new GridLayoutManager(getContext(), 2);
                homeAdapter = new HomeAdapter(homeAdsList, getContext());
                reclerHome.setLayoutManager(layoutManager);
                reclerHome.setItemAnimator(new DefaultItemAnimator());
                reclerHome.setAdapter(homeAdapter);
                homeAdapter.setClickCallBack(HomeFragment.this);
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