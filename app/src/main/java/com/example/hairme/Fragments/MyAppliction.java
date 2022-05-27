package com.example.hairme.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hairme.Adapters.employmentapplicationsAdapter;
import com.example.hairme.Adapters.jobAdapter;
import com.example.hairme.Job_Details;
import com.example.hairme.Models.Job;
import com.example.hairme.Models.UserModle;
import com.example.hairme.Models.employmentapplications;
import com.example.hairme.R;
import com.example.hairme.Services.Mysingleton;
import com.example.hairme.Services.SharedPrefmanager;
import com.example.hairme.Services.URLs;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MyAppliction extends Fragment {

    RecyclerView JobRecyclerView;
    private employmentapplicationsAdapter employmentapplicationsAdapter;
    private ArrayList<employmentapplications> JobList;
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_appliction, container, false);

        JobRecyclerView = root.findViewById(R.id.app_recyclerview);
        JobRecyclerView.setHasFixedSize(true);
        JobRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        JobList = new ArrayList<employmentapplications>();

        employmentapplicationsAdapter = new employmentapplicationsAdapter(getContext(), JobList);
        JobRecyclerView.setAdapter(employmentapplicationsAdapter);
        mRequestQueue = Volley.newRequestQueue(getContext());


        getApp();
        return root;
    }

    private void getApp() {
        com.kaopiz.kprogresshud.KProgressHUD prog = com.kaopiz.kprogresshud.KProgressHUD.create(getContext())
                .setStyle(com.kaopiz.kprogresshud.KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("الرجاء الانتظار ")
                .setDetailsLabel("يتم مزامنة بياناتك ")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setSize(200, 200)
                .show();

        UserModle user = SharedPrefmanager.getInstance(getContext()).getUser();


        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userId", user.getId());
        Toast.makeText(getActivity(), user.getId(), Toast.LENGTH_SHORT).show();


        JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, URLs.URL_app, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                prog.dismiss();
                try {
                    if (response.getBoolean("error")) {
                        ///replace tosat with dilog
                        Toast.makeText(getActivity(), "Response:  " + response.getString("masseg"), Toast.LENGTH_SHORT).show();
                          } else {
                            //replace with jsonobject
                            JSONArray array = response.getJSONArray("data");
                            Gson gson = new Gson();
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                employmentapplications job = gson.fromJson(object.toString(), employmentapplications.class);
                                JobList.add(job);
                            }
                            employmentapplicationsAdapter.notifyDataSetChanged();
                            prog.dismiss();
    //                        employmentapplicationsAdapter.setOnItemClickListener(MyAppliction.this::onItemClick);
                    }
                } catch (JSONException e) {
                    prog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                prog.dismiss();
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                    Toast.makeText(getActivity(),
                            "ServerError!",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(getContext(),
                            "AuthFailureError!",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(getActivity(),
                            "ParseError!",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getActivity(),
                            "NoConnectionError!",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(getActivity(),
                            "Oops. Timeout error!",
                            Toast.LENGTH_LONG).show();
                }


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
                        getApp();
                        dailog.dismiss();
                    }
                });
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json" );//put your token here
                headers.put("Content-Type", "application/json" );//put your token here
                headers.put("Connection", "keep-alive" );//put your token here
                return headers;
            }
        };

        Mysingleton.getInstance(getActivity()).addToReguestQueu(jsonOblect);
    }
}