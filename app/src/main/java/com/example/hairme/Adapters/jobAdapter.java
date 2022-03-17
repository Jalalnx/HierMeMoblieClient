package com.example.hairme.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hairme.Models.Job;
import com.example.hairme.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class jobAdapter  extends RecyclerView.Adapter<jobAdapter.ExampleviewHolder> {
    private Context mcontex;
    private ArrayList<Job> mExamplelist;

    ///lisner for on click
    private  OnItemClickListener mListener;

    public interface  OnItemClickListener{
        void onItemClick(int postion);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;

    }

    public jobAdapter(Context contex , ArrayList<Job> examplelist){
        mcontex=contex;
        mExamplelist=examplelist;

    }

    @NonNull
    @Override
    public ExampleviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mcontex).inflate(R.layout.jobcard,parent,false);
        return  new ExampleviewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ExampleviewHolder holder, int position) {

//        Emg_Numbers_Modle currentItem=mExamplelist.get(position);
//        String imageUrl=currentItem.getImageUrl();
//        String depart_name=currentItem.getDeprment_name();
//        int likecount=currentItem.getNumber();
//
//
//        holder.mTextviewCreator.setText(depart_name);
//        holder.mtextviewlikes.setText("Phone :"+likecount);
//
//        Picasso.with(mcontex).load(imageUrl).fit().centerInside().into(holder.mImageview);

    }

    @Override
    public int getItemCount() {
        return mExamplelist.size();
    }

    public class ExampleviewHolder extends  RecyclerView.ViewHolder{
        public ShapeableImageView in_imag;
        public TextView des;
        public TextView cat;
        public TextView data;
        public TextView views;

        public ExampleviewHolder(@NonNull View itemView) {
            super(itemView);
            in_imag=itemView.findViewById(R.id.in_imf);
            des=itemView.findViewById(R.id.des);
            cat=itemView.findViewById(R.id.cat);
            data=itemView.findViewById(R.id.data);
            views=itemView.findViewById(R.id.views);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int postion= getAdapterPosition();
                        if(postion != RecyclerView.NO_POSITION){
                            mListener.onItemClick(postion);
                        }
                    }
                }
            });


        }
    }}
