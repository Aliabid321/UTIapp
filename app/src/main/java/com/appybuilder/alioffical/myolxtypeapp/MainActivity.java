package com.appybuilder.alioffical.myolxtypeapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.appybuilder.alioffical.myolxtypeapp.Activitise.ShowCategoryActivity;
import com.appybuilder.alioffical.myolxtypeapp.Fragments.AccountFragment;
import com.appybuilder.alioffical.myolxtypeapp.Fragments.FavFragment;
import com.appybuilder.alioffical.myolxtypeapp.Fragments.HomeFragment;
import com.appybuilder.alioffical.myolxtypeapp.Fragments.SellFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
     BottomNavigationView bottomNavigationView;
     AppBarConfiguration mAppBarConfiguration;
     FloatingActionButton btnfloating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HomeFragment homeFragment=new HomeFragment();
        callFragment(homeFragment);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(this);
        btnfloating=findViewById(R.id.fab);
        btnfloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ShowCategoryActivity.class));
            }
        });



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.home){
            HomeFragment homeFragment=new HomeFragment();
            callFragment(homeFragment);
        }
        if (item.getItemId()==R.id.sell){
            SellFragment sellFragment=new SellFragment();
            callFragment(sellFragment);
        }if (item.getItemId()==R.id.favads){
            FavFragment favFragment=new FavFragment();
            callFragment(favFragment);
        }if (item.getItemId()==R.id.Profile){
            AccountFragment profile=new AccountFragment();
            callFragment(profile);
        }

        return true;
    }
    private void callFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frag, fragment);
        // ft.addToBackStack(String.valueOf(fragment));
        ft.commit();
    }
}