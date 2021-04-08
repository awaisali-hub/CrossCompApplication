package com.eclairios.CrossComps.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eclairios.CrossComps.CustomLoader.WaitDialog;
import com.eclairios.CrossComps.Interface.InterfaceForSetTeams;
import com.eclairios.CrossComps.Model.MyCrossCompAllTeamsMainModel;
import com.eclairios.CrossComps.R;
import com.eclairios.CrossComps.Teams.MyFundraisingTeamDetailActivity;
import com.eclairios.CrossComps.Teams.TeamsScoreActivity;
import com.eclairios.CrossComps.Teams.WorldTeamScoreActivity;

import java.util.ArrayList;

public class MyCrossCompUserSelectedTeamAdapter extends RecyclerView.Adapter<MyCrossCompUserSelectedTeamAdapter.ViewHolder> {
    public static final int BlueType = 1;
    public static final int GreenType = 0;
    public static final int EditGreenType = 2;

    private InterfaceForSetTeams interfaceForSetTeams;

    public LayoutInflater inflater;
    public ArrayList<MyCrossCompAllTeamsMainModel> crossCompAllTeamsMainModels;
    public Context context;
    public MyCrossCompUserSelectedTeamAdapter( Context ctx, ArrayList<MyCrossCompAllTeamsMainModel> crossCompAllTeamsMainModels,InterfaceForSetTeams interfaceForSetTeams) {
        this.inflater = LayoutInflater.from(ctx);
        this.crossCompAllTeamsMainModels = crossCompAllTeamsMainModels;
        this.context = ctx;
        this.interfaceForSetTeams = interfaceForSetTeams;
    }
    @NonNull
    @Override
    public MyCrossCompUserSelectedTeamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == GreenType){
            View view = inflater.inflate(R.layout.my_crosscomp_teams_main_row_green,parent,false);
          //  View view = inflater.inflate(R.layout.team_sub_class_row,parent,false);
            return new MyCrossCompUserSelectedTeamAdapter.ViewHolder(view);
        }else if(viewType == EditGreenType){
            View view = inflater.inflate(R.layout.my_crosscomp_teams_main_row_green_edit,parent,false);
            return new MyCrossCompUserSelectedTeamAdapter.ViewHolder(view);
        }
        else{
            //my_crosscomp_teams_main_row_blue
            View view = inflater.inflate(R.layout.my_crosscomp_teams_main_row_blue,parent,false);
            return new MyCrossCompUserSelectedTeamAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyCrossCompUserSelectedTeamAdapter.ViewHolder holder, int position) {
        holder.teamName.setText(crossCompAllTeamsMainModels.get(position).getTeamName());

        try{
            WaitDialog.hideDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.teamName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(crossCompAllTeamsMainModels.get(position).getSelectedTeamOpenType().equals("WorldTeam")){
                    context.startActivity(new Intent(context, WorldTeamScoreActivity.class));
                }else if(crossCompAllTeamsMainModels.get(position).getSelectedTeamOpenType().equals("HomeTeam")){
                    context.startActivity(new Intent(context, MyFundraisingTeamDetailActivity.class));
                }else if(crossCompAllTeamsMainModels.get(position).getTeamType().equals("Unselected")){

                    if(crossCompAllTeamsMainModels.get(position).getTeamName().equals("My High School Class Team")){
                        interfaceForSetTeams.SelectHighSchool();
                    }else if(crossCompAllTeamsMainModels.get(position).getTeamName().equals("My College Class Team")){
                        interfaceForSetTeams.CollegeUniversity();
                    }else if(crossCompAllTeamsMainModels.get(position).getTeamName().equals("My Professional Class Team")){
                        interfaceForSetTeams.ProfessionalSchool();
                    }else if(crossCompAllTeamsMainModels.get(position).getTeamName().equals("My Faith Group Team")){
                        interfaceForSetTeams.Faith();
                    }else if(crossCompAllTeamsMainModels.get(position).getTeamName().equals("My Gym Team")){
                        interfaceForSetTeams.GymBrand();
                    }else if(crossCompAllTeamsMainModels.get(position).getTeamName().equals("My Company Team")){
                        interfaceForSetTeams.Company();
                    }else if(crossCompAllTeamsMainModels.get(position).getTeamName().equals("My Occupation Team")){
                        interfaceForSetTeams.Occupation();
                    }else if(crossCompAllTeamsMainModels.get(position).getTeamName().equals("My Military Group Team")){
                        interfaceForSetTeams.MilitaryBranch();
                    }else if(crossCompAllTeamsMainModels.get(position).getTeamName().equals("My Friends & Family Team")){
                        interfaceForSetTeams.FriendFamily();
                    }

                }else{
                    context.startActivity(new Intent(context, TeamsScoreActivity.class));
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

      //  return BlueType;
        if(crossCompAllTeamsMainModels.get(position).getTeamType().equals("Unselected")){
            return BlueType;
        }else{
            return GreenType;
        }

    }


}