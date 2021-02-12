package com.eclairios.CrossComps.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eclairios.CrossComps.Interface.IndividualChallengeInterface;
import com.eclairios.CrossComps.Model.ModelAllUser;
import com.eclairios.CrossComps.R;

import java.util.List;

public class AdapterAllUser extends RecyclerView.Adapter<AdapterAllUser.ViewHolder> {
    List<ModelAllUser> modelAllUsers;
    Context context;
    private IndividualChallengeInterface listener;

    public AdapterAllUser(List<ModelAllUser> modelAllUsers, Context context, IndividualChallengeInterface listener) {
        this.modelAllUsers = modelAllUsers;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterAllUser.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        AdapterAllUser.ViewHolder viewHolder = new AdapterAllUser.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAllUser.ViewHolder holder, int position) {

        holder.userName.setText(modelAllUsers.get(position).getUserName());
        holder.UserScore.setText(modelAllUsers.get(position).getUserScore());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             listener.MakeIndividualChallenge(modelAllUsers.get(position).getUserID(),modelAllUsers.get(position).getUserScore(),modelAllUsers.get(position).getGender(),modelAllUsers.get(position).getAge(),modelAllUsers.get(position).getAddress());
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelAllUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        TextView userName,UserScore;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.tex1);
            UserScore = itemView.findViewById(R.id.tex2);
            context = itemView.getContext();
        }
    }
}