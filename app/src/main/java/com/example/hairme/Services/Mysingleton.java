package com.example.hairme.Services;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.DiskBasedCache;

public class Mysingleton {
    private static Mysingleton mInstance;
    private RequestQueue mRequestQueu;
    private Context mCtx;

    //https://diligent-bails.000webhostapp.com/load.php


    ////INSERT INTO `emg_phone`
    // (`id`, `departmen`, `phone_number`, `image`) VALUES ('1', 'الشرطه النجده', '999',
    // 'https://diligent-bails.000webhostapp.com/image/3.png'),
    // ('2', 'عمليات المرور', '77777', 'https://diligent-bails.000webhostapp.com/image/4.png');

    public Mysingleton(Context mCtx) {
        this.mCtx = mCtx;
        mRequestQueu= getmRequestQueue();
    }

    public  RequestQueue getmRequestQueue() {
        if( mRequestQueu == null ){
            Cache cache=new DiskBasedCache(mCtx.getCacheDir(),1024*1024);
            com.android.volley.toolbox.BasicNetwork network = new com.android.volley.toolbox.BasicNetwork(new com.android.volley.toolbox.HurlStack());
            mRequestQueu=new RequestQueue(cache, (com.android.volley.Network) network);
            mRequestQueu = com.android.volley.toolbox.Volley.newRequestQueue(mCtx.getApplicationContext());

        }
        return mRequestQueu;
    }
    public static synchronized Mysingleton getInstance(Context context){
        if(mInstance == null){
            mInstance =new Mysingleton(context);
        }
        return mInstance;
    }
    public <T> void  addToReguestQueu(com.android.volley.Request<T> request){
        mRequestQueu.add(request);
    }
}

