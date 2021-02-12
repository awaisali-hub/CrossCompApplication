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

public class AdapterAdvanceChallenge  extends RecyclerView.Adapter<AdapterAdvanceChallenge.ViewHolder> {
    List<ModelChallenge> advanceChallengerList;
    Context context;

    public AdapterAdvanceChallenge(List<ModelChallenge> advanceChallengerList, Context context) {
        this.advanceChallengerList = advanceChallengerList;
        this.context = context;

    }

    @NonNull
    @Override
    public AdapterAdvanceChallenge.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.challenge_advance_row,parent,false);
        AdapterAdvanceChallenge.ViewHolder viewHolder = new AdapterAdvanceChallenge.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAdvanceChallenge.ViewHolder holder, int position) {

        holder.challengerName.setText(advanceChallengerList.get(position).getChallengerName());
        holder.challengerScore.setText(advanceChallengerList.get(position).getChallengerScore());
        holder.challengerGender.setText(advanceChallengerList.get(position).getChallengerGender());
        holder.challengerAge.setText(advanceChallengerList.get(position).getChallengerAge());
        holder.challengerAddress.setText(advanceChallengerList.get(position).getChallengerAddress());

    }
    @Override
    public int getItemCount() {
        return advanceChallengerList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        TextView challengerName,challengerScore,challengerGender,challengerAge,challengerAddress;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            challengerName = itemView.findViewById(R.id.challengerName);
            challengerScore = itemView.findViewById(R.id.challengerScore);
            challengerGender = itemView.findViewById(R.id.challengerGender);
            challengerAddress = itemView.findViewById(R.id.challengerAddress);
            challengerAge = itemView.findViewById(R.id.challengerAge);
            context = itemView.getContext();

        }}
}
