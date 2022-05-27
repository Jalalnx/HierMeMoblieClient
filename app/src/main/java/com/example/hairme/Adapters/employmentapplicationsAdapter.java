package com.example.hairme.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hairme.Models.Job;
import com.example.hairme.Models.employmentapplications;
import com.example.hairme.Models.institutes;
import com.example.hairme.R;
import com.example.hairme.Services.TimeAgo2;
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
//
//        //get name
        Job job = currentItem.getJob();;
        holder.job_role.setText(job.getJob_role());

        String stautes = currentItem.getStatus();
        if(stautes.equals("0")){
            holder.status.setTextColor(Color.parseColor("#fcfcfa"));
            holder.sy_card.setCardBackgroundColor(Color.parseColor("#e1eb34"));
            holder.status.setText("تحت المعالجه");
        }else if(stautes.equals("1")){
            holder.status.setTextColor(Color.parseColor("#fafcfa"));
            holder.sy_card.setCardBackgroundColor(Color.parseColor("#34eb3a"));
            holder.status.setText("تم القبول ");
        }

        //set date
        String date=currentItem.getCreatedAt();
        String time = date;
        TimeAgo2 timeAgo2 = new TimeAgo2();
        String MyFinalValue = timeAgo2.covertTimeToText(time);
        holder.dataof_applay.setText(MyFinalValue);
//        holder.views.setText(views);

        String indistry= job.getIndustry();
        holder.industry.setText(indistry);

    }

    @Override
    public int getItemCount() {
        return employmentapplicationlist.size();
    }

    public class ExampleviewHolder extends  RecyclerView.ViewHolder{

        public TextView job_role;
        public TextView status;
        public TextView dataof_applay;
        public TextView industry;
        public CardView sy_card;


        public ExampleviewHolder(@NonNull View itemView) {
            super(itemView);
            job_role=itemView.findViewById(R.id.job_role);
            status=itemView.findViewById(R.id.status);
            dataof_applay=itemView.findViewById(R.id.data);
            industry=itemView.findViewById(R.id.industry);
            sy_card=itemView.findViewById(R.id.sy_card);

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
