package com.eclairios.CrossComps.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eclairios.CrossComps.Model.ModelTable;
import com.eclairios.CrossComps.R;

import java.util.List;

public class AdapterTable extends RecyclerView.Adapter<AdapterTable.ViewHolder> {
    List<ModelTable> tableList;
    Context context;
    public AdapterTable(List<ModelTable> tableList, Context context) {
        this.tableList = tableList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterTable.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowtable,parent,false);
        AdapterTable.ViewHolder viewHolder = new AdapterTable.ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterTable.ViewHolder holder, int position) {
        holder.monthYear.setText(tableList.get(position).getMonthYear());
        holder.age.setText(tableList.get(position).getAge());
        holder.a.setText(tableList.get(position).getA());
        holder.b.setText(tableList.get(position).getB());
        holder.c.setText(tableList.get(position).getC());
        holder.d.setText(tableList.get(position).getD());
        holder.total.setText(tableList.get(position).getTotal());

    }
    @Override
    public int getItemCount() {
        Log.e( "vjnvjbjcnjn", "getItemCount: "+tableList.size()  );
        return tableList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        TextView monthYear,age,a,b,c,d,total;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            monthYear = itemView.findViewById(R.id.monthYear);
            age = itemView.findViewById(R.id.age);
            a = itemView.findViewById(R.id.a);
            b = itemView.findViewById(R.id.b);
            c = itemView.findViewById(R.id.c);
            d = itemView.findViewById(R.id.d);
            total = itemView.findViewById(R.id.total);
            context = itemView.getContext();
        }}
}
