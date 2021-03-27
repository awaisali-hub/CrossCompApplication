package com.eclairios.CrossComps.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eclairios.CrossComps.Interface.IndividualChallengeInterface;
import com.eclairios.CrossComps.Model.ModelAllUser;
import com.eclairios.CrossComps.Model.RegisterServiceEventModel;
import com.eclairios.CrossComps.R;
import com.eclairios.CrossComps.ServiceCoordinator.CoordinatorServiceSummaryScreenActivity;

import java.util.List;

public class RegisterServiceEventAdapter extends RecyclerView.Adapter<RegisterServiceEventAdapter.ViewHolder> {
    List<RegisterServiceEventModel> registerServiceEventModels;
    Context context;

    public RegisterServiceEventAdapter(List<RegisterServiceEventModel> registerServiceEventModels, Context context) {
        this.registerServiceEventModels = registerServiceEventModels;
        this.context = context;
    }

    @NonNull
    @Override
    public RegisterServiceEventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_services_event_row,parent,false);
        RegisterServiceEventAdapter.ViewHolder viewHolder = new RegisterServiceEventAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RegisterServiceEventAdapter.ViewHolder holder, int position) {

        holder.serviceName.setText(registerServiceEventModels.get(position).getServiceName());

        holder.serviceName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,CoordinatorServiceSummaryScreenActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return registerServiceEventModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        TextView serviceName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.serviceOrEvent);
            context = itemView.getContext();
        }
    }
}