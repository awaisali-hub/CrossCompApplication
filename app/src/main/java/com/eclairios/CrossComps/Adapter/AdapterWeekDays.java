package com.eclairios.CrossComps.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eclairios.CrossComps.Interface.BasicTeamChallenge;
import com.eclairios.CrossComps.Model.ModelAllTeams;
import com.eclairios.CrossComps.Model.ModelCoordinaterServicePage;
import com.eclairios.CrossComps.Model.ModelWeekDays;
import com.eclairios.CrossComps.R;

import java.util.List;

public class AdapterWeekDays extends RecyclerView.Adapter<AdapterWeekDays.ViewHolder> {
    List<ModelWeekDays> weekDaysList;
    Context context;

    public AdapterWeekDays(List<ModelWeekDays> weekDaysList, Context context) {
        this.weekDaysList = weekDaysList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterWeekDays.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.week_days_row_item,parent,false);
        AdapterWeekDays.ViewHolder viewHolder = new AdapterWeekDays.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterWeekDays.ViewHolder holder, int position) {

        holder.weekDays.setText(weekDaysList.get(position).getMonday());

    }

    @Override
    public int getItemCount() {
        Log.e("size", "getItemCount: "+weekDaysList.size());
        return weekDaysList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        TextView weekDays;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            weekDays = itemView.findViewById(R.id.monday);
            context = itemView.getContext();
        }
    }
}