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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    ShapeableImageView userImge;
    TextView name,profi,email,phone,edu,gender1;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_profile, container, false);
         userImge =root.findViewById(R.id.userprofile);
         name =root.findViewById(R.id.profile_name);
        profi =root.findViewById(R.id.profissions);
        email =root.findViewById(R.id.email);
        phone =root.findViewById(R.id.phone);
        edu =root.findViewById(R.id.edu);
        gender1 =root.findViewById(R.id.gender1);


        UserModle user = SharedPrefmanager.getInstance(getActivity()).getUser();
        Picasso.get().load(user.getPhoto()).fit().centerInside().into(userImge);
        name.setText(user.getF_name()+" "+user.getL_name());
        profi.setText(user.getProfession());
        email.setText(new StringBuilder().append("البريد الاكتروني :").append(user.getEmail()).toString());
        phone.setText(new StringBuilder().append(" رقم الهاتف :").append(user.getPhone_Number()).toString() );
        edu.setText(new StringBuilder().append(" المؤهل العلمي :").append(user.getEducation_level()).toString() );
        gender1.setText(new StringBuilder().append(" الجنس :").append(user.getGender()).toString() );
        return root;
    }
}