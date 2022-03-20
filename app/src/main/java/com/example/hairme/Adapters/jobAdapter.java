package com.example.hairme.Adapters;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hairme.Models.Job;
import com.example.hairme.Models.institutes;
import com.example.hairme.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


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
         ///hoooks
        Job currentItem=mExamplelist.get(position);

        String date=currentItem.getCreatedAt();
        String job_role=currentItem.getJob_role();
        String industry=currentItem.getIndustry();
        String views=currentItem.getViews();
        //get compeny detalis
        institutes instanc   =currentItem.getInstitutes();
        ///get logo

        //get name
        String name = instanc.getCompanyName();



//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
//        Date c = Calendar.getInstance().getTime();
//        String formattedDate = df.format(c);
//        String formattedDate2 = df.format(date);
       ///set data
        holder.des.setText(job_role);
        holder.compny.setText(name);
        holder.cat.setText(industry);
        holder.data.setText(date);
        holder.views.setText(views);






        try {
            String imag = instanc.getLogo();
            Picasso.get().load(imag).fit().centerInside().into(holder.in_imag);
        }catch (Exception e){
            Toast.makeText(mcontex.getApplicationContext(), "Response:  " + instanc.toString(), Toast.LENGTH_SHORT).show();


        }
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
        public TextView compny;

        public ExampleviewHolder(@NonNull View itemView) {
            super(itemView);
            in_imag=itemView.findViewById(R.id.in_imf);
            des=itemView.findViewById(R.id.des);
            cat=itemView.findViewById(R.id.cat);
            data=itemView.findViewById(R.id.data);
            views=itemView.findViewById(R.id.views);
            compny=itemView.findViewById(R.id.compny);
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
