package com.eclairios.CrossComps.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eclairios.CrossComps.CoordinatorServicePage;
import com.eclairios.CrossComps.CustomLoader.WaitDialog;
import com.eclairios.CrossComps.EventDetailActivity;
import com.eclairios.CrossComps.R;
import com.eclairios.CrossComps.CrossComp;
import com.eclairios.CrossComps.Model.ModelHorizontal;

import java.io.IOException;
import java.util.List;


public class AdapterHorizontal extends RecyclerView.Adapter<AdapterHorizontal.ViewHolder> {

    List<ModelHorizontal> chatItem;
    Context context;

    public AdapterHorizontal(List< ModelHorizontal > chatItem, Context context) {
        this.chatItem = chatItem;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(position == 1){
//            holder.Text11.setText(chatItem.get(position).getCoordinatorName());
            holder.Text11.setText("Celebrate Client Success");

            holder.Text22.setText(chatItem.get(position).getCoordinatorAddress());

//            holder.Text11.setBackground(context.getDrawable(R.drawable.blue_bc));
//            holder.linearLayoutCard.setBackground(context.getDrawable(R.drawable.blue_bc));

        }else {

            holder.Text11.setText(chatItem.get(position).getCoordinatorName());
            holder.Text22.setText(chatItem.get(position).getCoordinatorAddress());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        try{
                            new WaitDialog(context).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                        if(position == 1){
                            Intent intent = new Intent(view.getContext(), EventDetailActivity.class);
                            intent.putExtra("coordinatorName",chatItem.get(position).getCoordinatorName());
                            intent.putExtra("coordinatorID",chatItem.get(position).getCoordinatorID());
                            context.startActivity(intent);
                        }else{
                            Intent intent = new Intent(view.getContext(), CoordinatorServicePage.class);
                            intent.putExtra("coordinatorName",chatItem.get(position).getCoordinatorName());
                            intent.putExtra("coordinatorID",chatItem.get(position).getCoordinatorID());
                            context.startActivity(intent);
                        }

                    }
                }, 1500);

            }
        });
    }


    @Override
    public int getItemCount() {
        Log.e( "vjnvjbjcnjn", "getItemCount: "+chatItem.size()  );
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
