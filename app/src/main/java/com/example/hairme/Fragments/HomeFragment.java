package com.example.hairme.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hairme.Models.UserModle;
import com.example.hairme.R;
import com.example.hairme.Services.SharedPrefmanager;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

//https://www.simplifiedcoding.net/upload-pdf-file-server-android/
public class HomeFragment extends Fragment {


    TextView Wellecomigng;
    ShapeableImageView User;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Wellecomigng=root.findViewById(R.id.Wellecomigng);
        User=root.findViewById(R.id.userImg);

        UserModle user = SharedPrefmanager.getInstance(getActivity()).getUser();
        Date dt = new Date(); int hours = dt.getHours(); int min = dt.getMinutes(); int am_pm = Calendar.AM_PM;
        if(hours>=1 || hours<=3 && am_pm == Calendar.AM){
            Wellecomigng.setText(user.getF_name()+"عمت مساء   ");
        }else if(hours>=4 || hours<=12 && am_pm == Calendar.AM){
            Wellecomigng.setText(user.getF_name()+"صباح الخير   ");
        }else if(hours>=13 || hours<=17 && am_pm == Calendar.PM){
            Wellecomigng.setText(user.getF_name()+"نهارك سعيد");
        }else if(hours>=18 || hours<=20 && am_pm == Calendar.PM){
            Wellecomigng.setText(user.getF_name()+"صباح الخير ");
        }else if(hours>=21 || hours<=24 && am_pm == Calendar.PM) {
            Wellecomigng.setText(user.getF_name()+"أحلام سعيده ");
        }
        Picasso.get().load(user.getPhoto()).fit().centerInside().into(User);


return root;
    }
}