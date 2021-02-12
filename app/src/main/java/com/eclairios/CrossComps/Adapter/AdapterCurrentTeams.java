package com.eclairios.CrossComps.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eclairios.CrossComps.R;
import com.eclairios.CrossComps.Model.ModelCurrentTeams;


import java.util.List;

public class AdapterCurrentTeams extends RecyclerView.Adapter<AdapterCurrentTeams.ViewHolder> {

    List<ModelCurrentTeams> chatItemmm;
    Context context;

    public AdapterCurrentTeams(List<ModelCurrentTeams> chatItemmm, Context context) {
        this.chatItemmm = chatItemmm;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterCurrentTeams.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlistcurrentteams,parent,false);
        AdapterCurrentTeams.ViewHolder viewHolder = new AdapterCurrentTeams.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCurrentTeams.ViewHolder holder, int position) {
        holder.Text1111.setText(chatItemmm.get(position).getTeamUsers());
        Log.e(" nxnzbcnzxbcm", "onBindViewHolder: "+chatItemmm.get(position).getTeamUsers() );
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(), CrossComp.class);
//                context.startActivity(intent);
//            }
//        });
    }


    @Override
    public int getItemCount() {

        return chatItemmm.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        TextView Text1111;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Text1111 = itemView.findViewById(R.id.teamsnamee);
            context = itemView.getContext();


        }}}
