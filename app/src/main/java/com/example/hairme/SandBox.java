package com.example.hairme;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.hairme.Fragments.HomeFragment;
import com.example.hairme.Fragments.MyAppliction;
import com.example.hairme.Fragments.NotificatinFragment;
import com.example.hairme.Fragments.ProfileFragment;


public class SandBox extends AppCompatActivity {

private MeowBottomNavigation meowBottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN, android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sand_box);

        meowBottomNavigation= findViewById(R.id.bottom_navigation);
        meowBottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_baseline_account_circle_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_baseline_account_circle_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_account_circle_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_baseline_account_circle_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.ic_baseline_account_circle_24));




        meowBottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment ;
                switch (item.getId()){
                    case  1:
                       fragment =new ProfileFragment();
                        break;
                    case 2:
                        fragment =new NotificatinFragment();
                        break;
                    case 3:
                        fragment = new HomeFragment();
                        break;
                    case 4 :
                        fragment = new MyAppliction();
                        break;
                    default:
                        fragment = new HomeFragment();
                }
            replace(fragment);
            }

        });

        meowBottomNavigation.setCount(2 ,"10");
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