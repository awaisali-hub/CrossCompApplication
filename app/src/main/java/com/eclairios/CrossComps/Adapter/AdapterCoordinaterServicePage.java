package com.eclairios.CrossComps.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eclairios.CrossComps.CustomLoader.WaitDialog;
import com.eclairios.CrossComps.Dashboard;
import com.eclairios.CrossComps.Model.ModelCoordinaterServicePage;
import com.eclairios.CrossComps.Interface.MyInterface;
import com.eclairios.CrossComps.Model.ModelWeekDays;
import com.eclairios.CrossComps.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterCoordinaterServicePage extends RecyclerView.Adapter<AdapterCoordinaterServicePage.ViewHolder> {

    List<ModelCoordinaterServicePage> listCoordinater;
    Context context;
    private MyInterface listener;

    public AdapterCoordinaterServicePage(List<ModelCoordinaterServicePage> listCoordinater, Context context, MyInterface listener) {
        this.listCoordinater = listCoordinater;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterCoordinaterServicePage.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_facility_row,parent,false);
        AdapterCoordinaterServicePage.ViewHolder viewHolder = new AdapterCoordinaterServicePage.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCoordinaterServicePage.ViewHolder holder, int position) {

        WaitDialog.hideDialog();
        holder.serviceDay.setText(listCoordinater.get(position).getService_facility_name());


    }
    @Override
    public int getItemCount() {
        Log.e("sizecheck", "getItemCount: "+listCoordinater.size() );
        return listCoordinater.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        TextView serviceDay;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceDay = itemView.findViewById(R.id.service_facility);
            context = itemView.getContext();

        }}
}
