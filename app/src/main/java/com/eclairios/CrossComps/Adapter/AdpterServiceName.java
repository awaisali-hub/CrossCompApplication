package com.eclairios.CrossComps.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eclairios.CrossComps.EventAndServices.CrossComp;
import com.eclairios.CrossComps.Model.ModelServiceName;
import com.eclairios.CrossComps.R;

import java.util.ArrayList;
import java.util.List;

public class AdpterServiceName extends RecyclerView.Adapter<AdpterServiceName.ViewHolder> {

    List<ModelServiceName> serviceNameListt;
    Context context;

    public AdpterServiceName(ArrayList<ModelServiceName> serviceNameListt, Context context) {
        this.serviceNameListt = serviceNameListt;
        this.context = context;

    }



    @NonNull
    @Override
    public AdpterServiceName.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowservicename,parent,false);
        AdpterServiceName.ViewHolder viewHolder = new AdpterServiceName.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CrossComp.class);
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        Log.e( "vjnvjbjcnjn", "getItemCount: "+serviceNameListt.size()  );
        return serviceNameListt.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        TextView serviceName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.service_Name);
            context = itemView.getContext();


        }}
}
