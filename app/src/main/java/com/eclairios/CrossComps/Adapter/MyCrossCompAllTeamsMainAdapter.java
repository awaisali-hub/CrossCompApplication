package com.eclairios.CrossComps.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eclairios.CrossComps.Interface.InterfaceForSetTeams;
import com.eclairios.CrossComps.Model.MyCrossCompAllTeamsMainModel;
import com.eclairios.CrossComps.R;
import com.eclairios.CrossComps.Teams.TeamsScoreActivity;

import java.util.ArrayList;

public class MyCrossCompAllTeamsMainAdapter extends RecyclerView.Adapter<MyCrossCompAllTeamsMainAdapter.ViewHolder> {
    public static final int BlueType = 1;
    public static final int GreenType = 0;
    public static final int EditGreenType = 2;

    private InterfaceForSetTeams interfaceForSetTeams;

    public LayoutInflater inflater;
    public ArrayList<MyCrossCompAllTeamsMainModel> crossCompAllTeamsMainModels;
    public Context context;
    public MyCrossCompAllTeamsMainAdapter( Context ctx, ArrayList<MyCrossCompAllTeamsMainModel> crossCompAllTeamsMainModels,InterfaceForSetTeams interfaceForSetTeams) {
        this.inflater = LayoutInflater.from(ctx);
        this.crossCompAllTeamsMainModels = crossCompAllTeamsMainModels;
        this.context = ctx;
        this.interfaceForSetTeams = interfaceForSetTeams;
    }
    @NonNull
    @Override
    public MyCrossCompAllTeamsMainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == GreenType){
            View view = inflater.inflate(R.layout.my_crosscomp_teams_main_row_green,parent,false);
            return new MyCrossCompAllTeamsMainAdapter.ViewHolder(view);
        }else if(viewType == EditGreenType){
            View view = inflater.inflate(R.layout.my_crosscomp_teams_main_row_green_edit,parent,false);
            return new MyCrossCompAllTeamsMainAdapter.ViewHolder(view);
        }
        else{
            View view = inflater.inflate(R.layout.my_crosscomp_teams_main_row_blue,parent,false);
            return new MyCrossCompAllTeamsMainAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(crossCompAllTeamsMainModels.get(position).getTeamID().equals("1")){
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            params.height = 0;
            params.width = 0;
            holder.itemView.setLayoutParams(params);
            holder.itemView.setVisibility(View.INVISIBLE);
        }else{
            holder.teamName.setText(crossCompAllTeamsMainModels.get(position).getTeamName());
        }

        holder.teamName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(crossCompAllTeamsMainModels.get(position).getTeamID().equals("8")){
                    interfaceForSetTeams.TeamsChurch(crossCompAllTeamsMainModels.get(position).getTeamID());
                }else{
                    interfaceForSetTeams.TeamsPostalCode(crossCompAllTeamsMainModels.get(position).getTeamID());
                }
                }
        });
    }


    @Override
    public int getItemCount() {
        return crossCompAllTeamsMainModels.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{
        TextView teamName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teamName= itemView.findViewById(R.id.MyHomeTeam);

        }
    }
    @Override
    public int getItemViewType(int position) {
        //  return super.getItemViewType(position);

        return BlueType;

      //  return EditGreenType;
    }


}