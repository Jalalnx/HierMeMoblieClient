package com.example.hairme.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hairme.Adapters.jobAdapter;
import com.example.hairme.LoginActivity;
import com.example.hairme.Models.Job;
import com.example.hairme.Models.UserModle;
import com.example.hairme.R;
import com.example.hairme.Services.Mysingleton;
import com.example.hairme.Services.Network_connectivety;
import com.example.hairme.Services.SharedPrefmanager;
import com.example.hairme.Services.URLs;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

//https://www.simplifiedcoding.net/upload-pdf-file-server-android/
@SuppressWarnings("ALL")
public class HomeFragment extends Fragment implements jobAdapter.OnItemClickListener {

    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 1;
    RecyclerView JobRecyclerView;
    private jobAdapter JobAdapter;
    private ArrayList<Job> JobList;
    private RequestQueue mRequestQueue;

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
        Date dt = new Date();
        int hours = dt.getHours();
        int min = dt.getMinutes();
        int am_pm = Calendar.AM_PM;
        if(hours<=1 || hours<=3 && am_pm == Calendar.AM){
            Wellecomigng.setText(user.getF_name()+"عمت مساء   ");
        }else if(hours<=4 || hours<=12 && am_pm == Calendar.AM){
            Wellecomigng.setText(user.getF_name()+"صباح الخير   ");
        }else if(hours<=13 || hours<=17 && am_pm == Calendar.PM){
            Wellecomigng.setText(user.getF_name()+"نهارك سعيد");
        }
        Picasso.get().load(user.getPhoto()).fit().centerInside().into(User);
        if (!isNetworkConnected(getActivity())) {
            Network_connectivety network_connectivety = new Network_connectivety(getActivity());
        root.findViewById(R.id.text_feedbake).setVisibility(View.VISIBLE);
        root.findViewById(R.id.img_feedback).setVisibility(View.VISIBLE);
        root.findViewById(R.id.recyclerview).setVisibility(View.GONE);
        }else
        {
            root.findViewById(R.id.text_feedbake).setVisibility(View.GONE);
            root.findViewById(R.id.img_feedback).setVisibility(View.GONE);
            root.findViewById(R.id.recyclerview).setVisibility(View.VISIBLE);
        }

        JobRecyclerView =root.findViewById(R.id.recyclerview);
        JobRecyclerView.setHasFixedSize(true);
        JobRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        JobList = new ArrayList<Job>();

        JobAdapter = new jobAdapter(getContext(), JobList);
        JobRecyclerView.setAdapter(JobAdapter);
        mRequestQueue = Volley.newRequestQueue(getContext());

return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!SharedPrefmanager.getInstance(getContext()).isLoggedIn()) {
            startActivity(new Intent(getContext() , LoginActivity.class));
        }

    }

    public boolean isNetworkConnected(Context context) {
        try {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiConn = manager.getNetworkInfo(manager.TYPE_WIFI);
            NetworkInfo mobileConn = manager.getNetworkInfo(manager.TYPE_MOBILE);
            ;


            if ((wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected())) {
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }

    }

    private void parseJSON() {
        com.kaopiz.kprogresshud.KProgressHUD prog = com.kaopiz.kprogresshud.KProgressHUD.create(getContext())
                .setStyle(com.kaopiz.kprogresshud.KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("Validate the information")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setSize(200, 200)
                .show();


        StringRequest request = new StringRequest(Request.Method.GET, URLs.URL_List_Jobs, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    //replace with jsonobject
                    JSONArray array = jsonObject.getJSONArray("dep");
                    Gson gson = new Gson();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        Job job = gson.fromJson(object.toString(), Job.class);
                        JobList.add(job);
                    }
                    JobAdapter.notifyDataSetChanged();
                    prog.dismiss();
                    JobAdapter.setOnItemClickListener(HomeFragment.this::onItemClick);

                } catch (JSONException e) {
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    //e.printStackTrace();
                    prog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                prog.dismiss();

                final AlertDialog dailog = new AlertDialog.Builder(getContext())
                        .setTitle("Oops Check you Internet connection")
                        .setMessage(error.toString())
                        .setPositiveButton("Reload", null)
                        .setNegativeButton("Cancel", null)
                        .show();
                Button Retry = dailog.getButton(AlertDialog.BUTTON_POSITIVE);
                Retry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        parseJSON();
                        dailog.dismiss();
                    }
                });
                //  dailog.dismiss();
            }
        });
//        request.setRetryPolicy(new com.android.volley.DefaultRetryPolicy
//                (30000, com.android.volley.DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                        com.android.volley.DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Mysingleton.getInstance(getContext()).addToReguestQueu(request);


    }

    @Override
    public void onItemClick(int postion) {
        Job clickedItem = JobList.get(postion);
 Toast.makeText(getActivity(),"clicked",Toast.LENGTH_LONG).show();

    }

}