package com.example.hairme.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hairme.Models.Job;
import com.example.hairme.Models.employmentapplications;
import com.example.hairme.Models.institutes;
import com.example.hairme.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class employmentapplicationsAdapter extends RecyclerView.Adapter<employmentapplicationsAdapter.ExampleviewHolder>{
    private Context mcontex;
    private ArrayList<employmentapplications> employmentapplicationlist;

    ///lisner for on click
    private jobAdapter.OnItemClickListener mListener;

    public interface  OnItemClickListener{
        void onItemClick(int postion);
    }

    public void setOnItemClickListener(jobAdapter.OnItemClickListener listener){
        mListener = listener;

    }

    public employmentapplicationsAdapter(Context contex , ArrayList<employmentapplications> employmentapplicationlist){
        mcontex=contex;
        this.employmentapplicationlist=employmentapplicationlist;

    }

    @NonNull
    @Override
    public employmentapplicationsAdapter.ExampleviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mcontex).inflate(R.layout.employmentapplicationcard,parent,false);
        return  new employmentapplicationsAdapter.ExampleviewHolder(v);

    }


    @Override
    public void onBindViewHolder(@NonNull employmentapplicationsAdapter.ExampleviewHolder holder, int position) {
        ///hoooks
        employmentapplications currentItem=employmentapplicationlist.get(position);
//
//        String date=currentItem.getCreatedAt();
//        //get name
//        String name = instanc.getCompanyName();;
//        holder.data.setText(date);
//        holder.views.setText(views);

    }

    @Override
    public int getItemCount() {
        return employmentapplicationlist.size();
    }

    public class ExampleviewHolder extends  RecyclerView.ViewHolder{

//        public TextView cat;
//

        public ExampleviewHolder(@NonNull View itemView) {
            super(itemView);
//            in_imag=itemView.findViewById(R.id.in_imf);
//            des=itemView.findViewById(R.id.des);
//
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
    }
}
