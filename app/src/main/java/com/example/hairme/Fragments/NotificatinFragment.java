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
import com.example.hairme.Adapters.jobAdapter;
import com.example.hairme.Adapters.notifyAdapter;
import com.example.hairme.Models.Job;
import com.example.hairme.Models.UserModle;
import com.example.hairme.Models.notify;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificatinFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificatinFragment extends Fragment {

    RecyclerView JobRecyclerView;
    private notifyAdapter JobAdapter;
    private ArrayList<notify> notifyList;
    private RequestQueue mRequestQueue;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotificatinFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificatinFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificatinFragment newInstance(String param1, String param2) {
        NotificatinFragment fragment = new NotificatinFragment();
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
        View root = inflater.inflate(R.layout.fragment_notificatin, container, false);

        JobRecyclerView =root.findViewById(R.id.NotifyList);
        JobRecyclerView.setHasFixedSize(true);
        JobRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        notifyList = new ArrayList<notify>();

        JobAdapter = new notifyAdapter(getContext(), notifyList);
        JobRecyclerView.setAdapter(JobAdapter);
        mRequestQueue = Volley.newRequestQueue(getContext());

        parseJSON();
        return root;
    }
    private void parseJSON() {

        UserModle user = SharedPrefmanager.getInstance(getActivity()).getUser();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userId", user.getId());


        JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, URLs.notifyUser , new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if (response.getBoolean("error")) {
                        ///replace tosat with dilog
                        Toast.makeText(getActivity(), "Response:  " + response.toString(), Toast.LENGTH_SHORT).show();
                    } else {

                        //replace with jsonobject
                        JSONArray array = response.getJSONArray("data");
                        Gson gson = new Gson();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            notify notify = gson.fromJson(object.toString(), notify.class);
                            notifyList.add(notify);
                        }
                        JobAdapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (getActivity() != null) {
                    if (error instanceof NetworkError) {
                    } else if (error instanceof ServerError) {
                        Toast.makeText(getActivity(),
                                "ServerError!",
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(getActivity(),
                                "AuthFailureError!",
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof ParseError) {
                        Toast.makeText(getActivity(),
                                "ParseError!",
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof NoConnectionError) {
                        Toast.makeText(getContext(),
                                "NoConnectionError!",
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof TimeoutError) {
                        Toast.makeText(getActivity(),
                                "Oops. Timeout error!",
                                Toast.LENGTH_LONG).show();
                    }


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
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        jsonOblect.setRetryPolicy(new com.android.volley.DefaultRetryPolicy
                (30000, com.android.volley.DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        com.android.volley.DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Mysingleton.getInstance(getActivity()).addToReguestQueu(jsonOblect);
    }

}