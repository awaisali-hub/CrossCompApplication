package com.eclairios.CrossComps.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eclairios.CrossComps.CustomLoader.WaitDialog;
import com.eclairios.CrossComps.EventAndServices.CoordinatorServicePage;
import com.eclairios.CrossComps.EventAndServices.CrossComp;
import com.eclairios.CrossComps.EventAndServices.EventCrossCompActivity;
import com.eclairios.CrossComps.EventAndServices.EventDetailActivity;
import com.eclairios.CrossComps.Model.ModelHorizontal;
import com.eclairios.CrossComps.R;

import java.util.List;

public class MyReservationAdapter extends RecyclerView.Adapter<MyReservationAdapter.ViewHolder> {

    List<ModelHorizontal> chatItem;
    Context context;

    public MyReservationAdapter(List< ModelHorizontal > chatItem, Context context) {
        this.chatItem = chatItem;
        this.context = context;

    }

    @NonNull
    @Override
    public MyReservationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        MyReservationAdapter.ViewHolder viewHolder = new MyReservationAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyReservationAdapter.ViewHolder holder, int position) {


        WaitDialog.hideDialog();
        holder.Text11.setText(chatItem.get(position).getServiceName());
        holder.Text22.setText(chatItem.get(position).getServiceAddress());

        String facilityID = chatItem.get(position).getServiceID();
        String date = chatItem.get(position).getServiceReservationDate();

        String serviceType = chatItem.get(position).getItem_type();

        String Send_Event_Time_ID = chatItem.get(position).getEvent_Time_ID();


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(serviceType.equals("service")){
                    Intent intent = new Intent(context, CrossComp.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    intent.putExtra("serviceName", holder.Text11.getText().toString());
                    intent.putExtra("serviceAddress",holder.Text22.getText().toString());
                    intent.putExtra("FacilityID",facilityID);
                    intent.putExtra("formatServiceDate",date);
                    context.startActivity(intent);
                }else{
                    Intent intent = new Intent(context, EventCrossCompActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("Send_Event_Time_ID",Send_Event_Time_ID);
                    intent.putExtra("eventName",holder.Text11.getText().toString());
                    intent.putExtra("eventAddress",holder.Text22.getText().toString());
                    intent.putExtra("EVENT_ID",facilityID);
                    context.startActivity(intent);
                }


            }
        });

    }


    @Override
    public int getItemCount() {
        return chatItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        TextView Text11,Text22;
        LinearLayout linearLayoutCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Text11 = itemView.findViewById(R.id.tex1);
            Text22 = itemView.findViewById(R.id.tex2);
            context = itemView.getContext();
            linearLayoutCard = itemView.findViewById(R.id.linearLayoutCard);

        }}
}

