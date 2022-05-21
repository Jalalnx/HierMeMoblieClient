package com.example.hairme.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hairme.Models.Job;
import com.example.hairme.Models.notify;
import com.example.hairme.R;

import java.util.ArrayList;

public class notifyAdapter extends RecyclerView.Adapter<notifyAdapter.ExampleviewHolder> {
    private Context mcontex;
    private ArrayList<notify> notifylist;

    ///lisner for on click
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int postion);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;

    }

    public notifyAdapter(Context contex, ArrayList<notify> notifylist) {
        mcontex = contex;
        this.notifylist = notifylist;

    }

    @NonNull
    @Override
    public notifyAdapter.ExampleviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mcontex).inflate(R.layout.notifycard,parent,false);
        return  new notifyAdapter.ExampleviewHolder(v);

    }


    @Override
    public void onBindViewHolder(@NonNull ExampleviewHolder holder, int position) {
        ///hoooks
        notify currentItem = notifylist.get(position);

        String date = currentItem.getNotify();
        //get compeny detalis
//        Job job = currentItem.getJob();
        ///get logo

        holder.message.setText(date);
//        holder.compny.setText(name);

    }

    @Override
    public int getItemCount() {
        return notifylist.size();
    }

    public class ExampleviewHolder extends RecyclerView.ViewHolder {
        public TextView message;


        public ExampleviewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int postion = getAdapterPosition();
                        if (postion != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(postion);
                        }
                    }
                }
            });


        }
    }
}
