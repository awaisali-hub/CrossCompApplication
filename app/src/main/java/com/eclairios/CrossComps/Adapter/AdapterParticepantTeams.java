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

import com.eclairios.CrossComps.Model.ModelParticipentTeams;
import com.eclairios.CrossComps.R;
import com.eclairios.CrossComps.TeamScore;

import java.util.List;



public class AdapterParticepantTeams extends RecyclerView.Adapter<AdapterParticepantTeams.ViewHolder> {

    List<ModelParticipentTeams> chatItemm;
    Context context;

    public AdapterParticepantTeams(List<ModelParticipentTeams> chatItemm, Context context) {
        this.chatItemm = chatItemm;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowcrosscompteams,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Text111.setText(chatItemm.get(position).getTeamName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TeamScore.class);
                intent.putExtra("teamID",chatItemm.get(position).getTeamID());

                Log.e("fggfgf", "onClick: "+ chatItemm.get(position).getTeamID());

                intent.putExtra("showFloatingButton",chatItemm.get(position).getActiveUserTeamOrAllTeams());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        Log.e( "vjnvjbjccxcznjn", "getItemCount: "+chatItemm.size()  );
        return chatItemm.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        TextView Text111;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Text111 = itemView.findViewById(R.id.teamsname);

            context = itemView.getContext();
        }}}
