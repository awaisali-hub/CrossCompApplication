package com.eclairios.CrossComps.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eclairios.CrossComps.BackgroundTaskClasses.BackgroundTaskForJoinTeams;
import com.eclairios.CrossComps.CustomLoader.WaitDialog;
import com.eclairios.CrossComps.Interface.InterfaceForSetTeams;
import com.eclairios.CrossComps.Model.MyCrossCompAllTeamsMainModel;
import com.eclairios.CrossComps.R;
import com.eclairios.CrossComps.Teams.JoinTeamsActivity;
import com.eclairios.CrossComps.Teams.MyFundraisingTeamDetailActivity;
import com.eclairios.CrossComps.Teams.TeamsScoreActivity;
import com.eclairios.CrossComps.Teams.WorldTeamScoreActivity;

import java.util.ArrayList;

public class MyCrossCompUserSelectedTeamAdapter extends RecyclerView.Adapter<MyCrossCompUserSelectedTeamAdapter.ViewHolder> {
    public static final int BlueType = 1;
    public static final int GreenType = 0;
    public static final int EditGreenType = 2;
    public static final int SubClassType = 3;

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

            return new MyCrossCompUserSelectedTeamAdapter.ViewHolder(view);
        }else if(viewType == EditGreenType){
            View view = inflater.inflate(R.layout.my_crosscomp_teams_main_row_green_edit,parent,false);
            return new MyCrossCompUserSelectedTeamAdapter.ViewHolder(view);
        }else if(viewType == SubClassType){
              View view = inflater.inflate(R.layout.team_sub_class_row,parent,false);
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
                }else{
                    context.startActivity(new Intent(context, TeamsScoreActivity.class));
                }
            }
        });

        if(crossCompAllTeamsMainModels.get(position).getTeamCategory().equals("SubClass")){
            holder.unJoinImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                //    Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show();

                    new AlertDialog.Builder(context)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Confirm UnJoin")
                            .setMessage("Are you sure you want to UnJoin Team?")
                            .setPositiveButton("Yes, UNJOIN NOW", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                                    String currentUserID = preferences.getString("CurrentUserId", "");

                                    String method = "unJoinTeams";
                                    String teamType;
                                    BackgroundTaskForJoinTeams backgroundTask = new BackgroundTaskForJoinTeams(context);
                                    if(crossCompAllTeamsMainModels.get(position).getSelectedTeamOpenType().equals("HighSchoolClassTeam")){
                                        teamType = "HighSchool";
                                        backgroundTask.execute(method,currentUserID, teamType,"0","0");
                                    }else if(crossCompAllTeamsMainModels.get(position).getSelectedTeamOpenType().equals("CollegeSubTeam")){
                                        teamType = "CollegeUniversity";
                                        backgroundTask.execute(method,currentUserID, teamType,"0","0");
                                    }else if(crossCompAllTeamsMainModels.get(position).getSelectedTeamOpenType().equals("ProfessionalClassTeam")){
                                        teamType = "ProfessionalSchool";
                                        backgroundTask.execute(method,currentUserID, teamType,"0","0");
                                    }else if(crossCompAllTeamsMainModels.get(position).getSelectedTeamOpenType().equals("FaithLocalTeam")){
                                        teamType = "FaithGroup";
                                        backgroundTask.execute(method,currentUserID, teamType,"0","0");
                                    }else if(crossCompAllTeamsMainModels.get(position).getSelectedTeamOpenType().equals("GymLocalTeam")){
                                        teamType = "GymBrand";
                                        backgroundTask.execute(method,currentUserID, teamType,"0","0");
                                    }else if(crossCompAllTeamsMainModels.get(position).getSelectedTeamOpenType().equals("CompanyLocalTeam")){
                                        teamType = "CompanyGroup";
                                        backgroundTask.execute(method,currentUserID, teamType,"0","0");
                                    }else if(crossCompAllTeamsMainModels.get(position).getSelectedTeamOpenType().equals("OccupationLocalTeam")){
                                        teamType = "OccupationGroup";
                                        backgroundTask.execute(method,currentUserID, teamType,"0","0");
                                    }else if(crossCompAllTeamsMainModels.get(position).getSelectedTeamOpenType().equals("MilitaryLocalTeam")){
                                        teamType = "MilitaryBranch";
                                        backgroundTask.execute(method,currentUserID, teamType,"0","0");
                                    }else if(crossCompAllTeamsMainModels.get(position).getSelectedTeamOpenType().equals("FriendTeam")){
                                        teamType = "PersonalTeams";
                                        backgroundTask.execute(method,currentUserID, teamType,"0","0");
                                    }else if(crossCompAllTeamsMainModels.get(position).getSelectedTeamOpenType().equals("CommunityTeam")){
                                        teamType = "CommunityTeams";
                                        backgroundTask.execute(method,currentUserID, teamType,"0","0");
                                    }



                                }

                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return crossCompAllTeamsMainModels.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{
        TextView teamName;
        ImageView unJoinImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teamName= itemView.findViewById(R.id.MyHomeTeam);
            unJoinImage = itemView.findViewById(R.id.unJoinImage);

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