package com.eclairios.CrossComps.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eclairios.CrossComps.Model.ModelOneServiceCard;
import com.eclairios.CrossComps.R;

import java.util.List;

public class AdapterOneServiceCard extends RecyclerView.Adapter<AdapterOneServiceCard.ViewHolder> {

        List<ModelOneServiceCard> chatItemmmm;
        Context context;

    public AdapterOneServiceCard(List<ModelOneServiceCard> chatItemmmm, Context context) {
        this.chatItemmmm = chatItemmmm;
        this.context = context;
    }

    @NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowoneservicecard,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
        }

@Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameS.setText(chatItemmmm.get(position).getName());
        holder.addressS.setText(chatItemmmm.get(position).getAddress());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View view) {
////        Intent intent = new Intent(view.getContext(), CrossComp.class);
////        context.startActivity(intent);
//        }
//        });
        }


@Override
public int getItemCount() {

        return chatItemmmm.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder {
    private final Context context;
    TextView nameS,addressS;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        nameS = itemView.findViewById(R.id.coordinaderNameS);
        addressS = itemView.findViewById(R.id.coordinaderAddressS);
        context = itemView.getContext();


    }}
}
