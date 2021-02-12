package com.eclairios.CrossComps.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eclairios.CrossComps.Model.ModelChallenge;
import com.eclairios.CrossComps.R;

import java.util.List;

public class AdapterChallenge extends RecyclerView.Adapter<AdapterChallenge.ViewHolder> {
    List<ModelChallenge> basicChallengerList;
    Context context;

    public AdapterChallenge(List<ModelChallenge> basicChallengerList, Context context) {
        this.basicChallengerList = basicChallengerList;
        this.context = context;

    }

    @NonNull
    @Override
    public AdapterChallenge.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.challenge_basic_row,parent,false);
        AdapterChallenge.ViewHolder viewHolder = new AdapterChallenge.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterChallenge.ViewHolder holder, int position) {

        holder.challengerName.setText(basicChallengerList.get(position).getChallengerName());
        holder.challengerScore.setText(basicChallengerList.get(position).getChallengerScore());

    }
    @Override
    public int getItemCount() {
        return basicChallengerList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        TextView challengerName,challengerScore;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            challengerName = itemView.findViewById(R.id.challengerName);
            challengerScore = itemView.findViewById(R.id.challengerScore);
            context = itemView.getContext();

        }}
}

