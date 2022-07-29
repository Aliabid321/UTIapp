package com.appybuilder.alioffical.myolxtypeapp.Activitise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appybuilder.alioffical.myolxtypeapp.Adapters.HomeAdapter;
import com.appybuilder.alioffical.myolxtypeapp.Apis.APIService;
import com.appybuilder.alioffical.myolxtypeapp.Apis.ApiClient;
import com.appybuilder.alioffical.myolxtypeapp.Fragments.HomeFragment;
import com.appybuilder.alioffical.myolxtypeapp.MainActivity;
import com.appybuilder.alioffical.myolxtypeapp.Models.ChatModel;
import com.appybuilder.alioffical.myolxtypeapp.Models.ConstantModel;
import com.appybuilder.alioffical.myolxtypeapp.Models.MyPrefrence;
import com.appybuilder.alioffical.myolxtypeapp.Models.UtiHomeModel;
import com.appybuilder.alioffical.myolxtypeapp.R;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowItemDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String SHARED_PREFS_FILE = "MyChatPreference";
    CarouselView carouselView;
    TextView txt_name, txt_des, txt_price, txt_location, txt_type, profile_txt;
    ImageView img;
    String strToken;
    List<UtiHomeModel.Datum> homeAdsList = new ArrayList<>();
    Intent intent;
    String id;
    String strItemName, strItemDes, strLocation, strCreatorName, strPhoneNo;
    MyPrefrence myPrefrence;
    LinearLayout btnwhatsapp, btnphonesms, btnphonecalll;
    int[] sampleImages = {R.drawable.flag_pakistan, R.drawable.flag_pakistan, R.drawable.flag_pakistan, R.drawable.flag_pakistan, R.drawable.flag_pakistan};
    String[] sampleImages1;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    List<ChatModel> chatString = new ArrayList<>();
    ChatModel chatModel = new ChatModel();
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item_detail);
        init_views();//intilization of views........
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);
        getItemData();

    }

    public void init_views() {
        carouselView = (CarouselView) findViewById(R.id.crousalview_id);

        btnwhatsapp = findViewById(R.id.whatapp_btn);
        btnwhatsapp.setOnClickListener(this);
        btnphonecalll = findViewById(R.id.btncall);
        btnphonecalll.setOnClickListener(this);
        btnphonesms = findViewById(R.id.btnsms);
        btnphonesms.setOnClickListener(this);
        intent = getIntent();
        id = intent.getExtras().get("id").toString();
        carouselView = findViewById(R.id.crousalview_id);
        txt_name = findViewById(R.id.id_name);
        txt_des = findViewById(R.id.id_des);
        txt_price = findViewById(R.id.price);
        txt_location = findViewById(R.id.location);
        profile_txt = findViewById(R.id.show_profiletxt_user);
        img = findViewById(R.id.profile_image_id);
        sharedPreferences = getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
    }

    public void getItemData() {
        strToken = MyPrefrence.getStr("token");
        Call<UtiHomeModel.NewHomeModel> call;
        APIService service = ApiClient.getClient().create(APIService.class);
        call = service.showSingleAd("Bearer " + strToken, id);
        call.enqueue(new Callback<UtiHomeModel.NewHomeModel>() {
            @Override
            public void onResponse(Call<UtiHomeModel.NewHomeModel> call, Response<UtiHomeModel.NewHomeModel> response) {
                if (response.isSuccessful()) {
                    try {
                        homeAdsList = response.body().getData();
                        strItemName = homeAdsList.get(0).getAdsInfo().getItemName();
                        strItemDes = homeAdsList.get(0).getAdsInfo().getItemDescription();
                        strLocation = homeAdsList.get(0).getAdsInfo().getLocation();
                        image=homeAdsList.get(0).getAdsImages().get(0).getImageUrl().toString();
                        strPhoneNo = MyPrefrence.getStr("phone");
                        strCreatorName = MyPrefrence.getStr("name");
                        ConstantModel.strCreatorName = strCreatorName;
                        setViewData();
                    }catch (NullPointerException e){
                        Log.d("Exception",e.getMessage());
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "SomeThing went Wrong", Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onFailure(Call<UtiHomeModel.NewHomeModel> call, Throwable t) {
                Log.d("Data", t.getMessage());
            }
        });

    }

    public void setViewData() {
        txt_name.setText(strItemName);
        txt_des.setText(strItemDes);
        //  txt_location.setText(strLocation);
        profile_txt.setText(strCreatorName);


    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.whatapp_btn) {
            open_whatsapp();
        }
        if (view.getId() == R.id.btnsms) {
            open_sms();
        }
        if (view.getId() == R.id.btncall) {
            callIntentfun();
        }
    }

    private void open_sms() {
        setChatDataFun();
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", "03400212190");
        smsIntent.putExtra("sms_body", "Body of Message");
        startActivity(smsIntent);
    }

    public void callIntentfun() {
        setChatDataFun();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "03400212190", null));
        startActivity(intent);
    }

    public void open_whatsapp() {
        setChatDataFun();
        Uri uri = Uri.parse("smsto:" + "03400212190");
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.setPackage("com.whatsapp");
        startActivity(Intent.createChooser(i, ""));
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
          Glide.with(getApplicationContext()).load(sampleImages[position]).into(imageView);
        }
    };

    public void setChatDataFun() {
        editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String s = gson.toJson(chatString);
        chatModel.setStrPhoneNo(strPhoneNo);
        chatString.add(chatModel);
        chatModel.setStrReciver(ConstantModel.strCreatorName);
        String json = gson.toJson(chatString);
        editor.putString(ConstantModel.strCreatorName,json);
        editor.putString("KEY",ConstantModel.strCreatorName);
        editor.apply();
        editor.commit();

    }
}