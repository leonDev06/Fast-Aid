package com.capstone.fastaid.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.fastaid.R;
import com.capstone.fastaid.activities.InjuryDetail;

public class InjuriesListAdapter extends RecyclerView.Adapter<InjuriesListAdapter.MyViewHolder> {
    // Properties
    private final String[] injuriesList;

    // Context
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    // Constructor
    public InjuriesListAdapter(String[] injuriesList, Context context){
        this.injuriesList = injuriesList;
        InjuriesListAdapter.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.injuries_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mInjuryName.setText(injuriesList[position]);
    }

    @Override
    public int getItemCount() {
        return injuriesList.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mInjuryName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // Bind Views
            mInjuryName = itemView.findViewById(R.id.mInjuryNameList);

            // On-Click
            itemView.setOnClickListener(listener ->{
                // Update Injury TextView
                mInjuryName = listener.findViewById(R.id.mInjuryNameList);
                String injuryName = mInjuryName.getText().toString();

                // Go to injury details
                Intent intent = new Intent(context, InjuryDetail.class);
                intent.putExtra("NAME", injuryName);
                context.startActivity(intent);
            });
        }

    }
}
