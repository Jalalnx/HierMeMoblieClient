package com.example.hairme;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.hairme.Fragments.HomeFragment;
import com.example.hairme.Fragments.MyAppliction;
import com.example.hairme.Fragments.NotificatinFragment;
import com.example.hairme.Fragments.ProfileFragment;
import com.example.hairme.Models.UserModle;
import com.example.hairme.Services.SharedPrefmanager;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;


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

        meowBottomNavigation.setCount(4 ,"10");
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