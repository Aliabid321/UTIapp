package com.appybuilder.alioffical.myolxtypeapp.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appybuilder.alioffical.myolxtypeapp.Activitise.SplashActivity;
import com.appybuilder.alioffical.myolxtypeapp.Apis.APIService;
import com.appybuilder.alioffical.myolxtypeapp.Apis.ApiClient;
import com.appybuilder.alioffical.myolxtypeapp.Models.AdsCategoryModel;
import com.appybuilder.alioffical.myolxtypeapp.Models.MyPrefrence;
import com.appybuilder.alioffical.myolxtypeapp.R;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment implements View.OnClickListener {
    FirebaseAuth auth;
    TextView txtname, txtemail, txtphone, txtadd;
    View view;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseRefrence = database.getReference();
    String strname,stremail,strphone;
    MyPrefrence myPrefrence;
    Button btnlogout;
    String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account, container, false);
        inti_views();
        return view;
    }
    public void inti_views() {
        myPrefrence = new MyPrefrence(getContext());
        txtname=view.findViewById(R.id.txtname);
        txtemail =view.findViewById(R.id.txtemail);
        txtphone=view.findViewById(R.id.txtphone);
        txtadd=view.findViewById(R.id.txtaddress);
        auth=FirebaseAuth.getInstance();
        getDatafun();
        btnlogout=view.findViewById(R.id.btnlog_out);
        btnlogout.setOnClickListener(this);
        token=MyPrefrence.getStr("token");
    }
    public void getDatafun(){
        strname= myPrefrence.getStr("name");
        stremail= myPrefrence.getStr("email");
        strphone= myPrefrence.getStr("phone");
        //setting data to textview....
        txtname.setText(strname);
        txtemail.setText(stremail);
        txtphone.setText(strphone);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btnlog_out){
            showCustomeLogOutDialog();

        }
    }
    private void showCustomeLogOutDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Button btnContinue = (Button) dialog.findViewById(R.id.btnContinue);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        LinearLayout uperlayout = dialog.findViewById(R.id.uperlayout);
        RelativeLayout confirm_layout = dialog.findViewById(R.id.confirm_layout);
        TextView txt_ok = dialog.findViewById(R.id.ok);
        ImageView img = dialog.findViewById(R.id.foodImg);
        //hare simple displaying the imge of specific items....

        btnCancel.setEnabled(true);
        btnContinue.setEnabled(true);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                dialog.hide();
                logoutfun();
            }
        });
        confirm_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });

        dialog.show();
    }
    public void logoutfun(){
        Call<AdsCategoryModel.AdsCatModel> call;
        APIService service = ApiClient.getClient().create(APIService.class);
        // call = service.create_Ads_2("Bearer " + strToken,hashMap);
        call = service.logout("Bearer " +token);
        call.enqueue(new Callback<AdsCategoryModel.AdsCatModel>() {
            @Override
            public void onResponse(Call<AdsCategoryModel.AdsCatModel> call, Response<AdsCategoryModel.AdsCatModel> response) {
                if (response.isSuccessful()){
                    MyPrefrence.setStr("session","");
                    getActivity().finish();
                    startActivity(new Intent(getContext(), SplashActivity.class));
                }

            }

            @Override
            public void onFailure(Call<AdsCategoryModel.AdsCatModel> call, Throwable t) {
                Log.d("Tag",t.getMessage());
            }
        });

    }
}