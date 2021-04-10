package com.eclairios.CrossComps.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class UnSelectedTeamAdapter extends RecyclerView.Adapter<UnSelectedTeamAdapter.ViewHolder> {
    public static final int BlueType = 1;
    public static final int GreenType = 0;
    public static final int EditGreenType = 2;
    public static final int SubClassType = 3;

    private InterfaceForSetTeams interfaceForSetTeams;

    public LayoutInflater inflater;
    public ArrayList<MyCrossCompAllTeamsMainModel> crossCompAllTeamsMainModels;
    public Context context;
    public UnSelectedTeamAdapter( Context ctx, ArrayList<MyCrossCompAllTeamsMainModel> crossCompAllTeamsMainModels,InterfaceForSetTeams interfaceForSetTeams) {
        this.inflater = LayoutInflater.from(ctx);
        this.crossCompAllTeamsMainModels = crossCompAllTeamsMainModels;
        this.context = ctx;
        this.interfaceForSetTeams = interfaceForSetTeams;
    }
    @NonNull
    @Override
    public UnSelectedTeamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == GreenType){
            View view = inflater.inflate(R.layout.my_crosscomp_teams_main_row_green,parent,false);

            return new UnSelectedTeamAdapter.ViewHolder(view);
        }else if(viewType == EditGreenType){
            View view = inflater.inflate(R.layout.my_crosscomp_teams_main_row_green_edit,parent,false);
            return new UnSelectedTeamAdapter.ViewHolder(view);
        }else if(viewType == SubClassType){
            View view = inflater.inflate(R.layout.team_sub_class_row,parent,false);
            return new UnSelectedTeamAdapter.ViewHolder(view);
        }
        else{
            //my_crosscomp_teams_main_row_blue
            View view = inflater.inflate(R.layout.team_sub_class_row_for_join,parent,false);
            return new UnSelectedTeamAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull UnSelectedTeamAdapter.ViewHolder holder, int position) {
        holder.teamName.setText(crossCompAllTeamsMainModels.get(position).getTeamName());

        try{
            WaitDialog.hideDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.JoinImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(crossCompAllTeamsMainModels.get(position).getSelectedTeamOpenType().equals("WorldTeam")){
                    context.startActivity(new Intent(context, WorldTeamScoreActivity.class));
                }else if(crossCompAllTeamsMainModels.get(position).getSelectedTeamOpenType().equals("HomeTeam")){
                    context.startActivity(new Intent(context, MyFundraisingTeamDetailActivity.class));
                }else if(crossCompAllTeamsMainModels.get(position).getTeamType().equals("Unselected")){

                    if(crossCompAllTeamsMainModels.get(position).getTeamName().equals("Community")){

                        interfaceForSetTeams.SelectCommunity();
                    }else if(crossCompAllTeamsMainModels.get(position).getTeamName().equals("High School Class")){
                        interfaceForSetTeams.SelectHighSchool();
                    }else if(crossCompAllTeamsMainModels.get(position).getTeamName().equals("College/University Class")){
                        interfaceForSetTeams.CollegeUniversity();
                    }else if(crossCompAllTeamsMainModels.get(position).getTeamName().equals("Professional School Class")){
                        interfaceForSetTeams.ProfessionalSchool();
                    }else if(crossCompAllTeamsMainModels.get(position).getTeamName().equals("Local Faith Group")){
                        interfaceForSetTeams.Faith();
                    }else if(crossCompAllTeamsMainModels.get(position).getTeamName().equals("Local Gym")){
                        interfaceForSetTeams.GymBrand();
                    }else if(crossCompAllTeamsMainModels.get(position).getTeamName().equals("Local Company Group")){
                        interfaceForSetTeams.Company();
                    }else if(crossCompAllTeamsMainModels.get(position).getTeamName().equals("Local Occupation Group")){
                        interfaceForSetTeams.Occupation();
                    }else if(crossCompAllTeamsMainModels.get(position).getTeamName().equals("Local Military Group")){
                        interfaceForSetTeams.MilitaryBranch();
                    }else if(crossCompAllTeamsMainModels.get(position).getTeamName().equals("Friends & Family")){
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
        ImageView JoinImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teamName= itemView.findViewById(R.id.MyHomeTeam);
            JoinImage = itemView.findViewById(R.id.JoinTeamImage);

        }
    }
    @Override
    public int getItemViewType(int position) {
        //  return super.getItemViewType(position);

        //  return BlueType;
        if(crossCompAllTeamsMainModels.get(position).getTeamType().equals("Unselected")){
            return BlueType;
        }else{

            if(crossCompAllTeamsMainModels.get(position).getTeamCategory().equals("SubClass")){
                return SubClassType;
            }
            return GreenType;
        }

    }



}
