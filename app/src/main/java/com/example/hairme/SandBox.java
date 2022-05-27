package com.example.hairme;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.hairme.Fragments.HomeFragment;
import com.example.hairme.Fragments.MyAppliction;
import com.example.hairme.Fragments.NotificatinFragment;
import com.example.hairme.Fragments.ProfileFragment;
import com.example.hairme.Models.UserModle;
import com.example.hairme.Models.notify;
import com.example.hairme.Services.Mysingleton;
import com.example.hairme.Services.SharedPrefmanager;
import com.example.hairme.Services.URLs;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class SandBox extends AppCompatActivity {

private MeowBottomNavigation meowBottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sand_box);


        meowBottomNavigation= findViewById(R.id.bottom_navigation);
        meowBottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.logout));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.applictios));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.home));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.notifications_none_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.pin_24));




        meowBottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment ;
                switch (item.getId()){
                    case  1:
                     //////////logout logic
                        SharedPrefmanager.getInstance(getApplicationContext()).logout();
                        break;
                    case 2:
                        fragment = new MyAppliction();
                        replace(fragment);
                        break;
                    case 3:
                        fragment = new HomeFragment();
                        replace(fragment);
                        break;
                    case 4 :
                        fragment =new NotificatinFragment();

                        replace(fragment);
                        break;
                    case 5:
                        fragment =new ProfileFragment();
                        replace(fragment);
                        break;
                    default:
                        fragment = new HomeFragment();
                        replace(fragment);
                }
            }

        });

//        meowBottomNavigation.setCount(4 ,"10");
        meowBottomNavigation.show(3,true);
        meowBottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                Toast.makeText(SandBox.this,"You allrady Select "+item.getId(),Toast.LENGTH_LONG).show();
                          }
        });

        meowBottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                Toast.makeText(SandBox.this,"You allrady Select ",Toast.LENGTH_LONG).show();
            }
        });
//        replace(new HomeFragment());

    }

    private void replace(Fragment Fragment) {
       getSupportFragmentManager().beginTransaction().replace(R.id.Sandbox,Fragment).commit();
    }



}