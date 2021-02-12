package com.eclairios.CrossComps.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eclairios.CrossComps.Interface.BasicTeamChallenge;
import com.eclairios.CrossComps.Interface.IndividualChallengeInterface;
import com.eclairios.CrossComps.Model.ModelAllTeams;
import com.eclairios.CrossComps.R;

import java.util.List;

public class AdapterAllTeams extends RecyclerView.Adapter<AdapterAllTeams.ViewHolder> {
    List<ModelAllTeams> modelAllTeams;
    Context context;
    private BasicTeamChallenge listener;

    public AdapterAllTeams(List<ModelAllTeams> modelAllTeams, Context context, BasicTeamChallenge listener) {
        this.modelAllTeams = modelAllTeams;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterAllTeams.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        AdapterAllTeams.ViewHolder viewHolder = new AdapterAllTeams.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAllTeams.ViewHolder holder, int position) {

        holder.teamName.setText(modelAllTeams.get(position).getTeamName());
        holder.teamScore.setText(modelAllTeams.get(position).getTeamScore());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.MakeTeamChallenge(modelAllTeams.get(position).getTeamID(),modelAllTeams.get(position).getTeamName(),modelAllTeams.get(position).getTeamScore(),modelAllTeams.get(position).getTeamAddress());
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelAllTeams.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        TextView teamName,teamScore;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teamName = itemView.findViewById(R.id.tex1);
            teamScore = itemView.findViewById(R.id.tex2);
            context = itemView.getContext();
        }
    }
}