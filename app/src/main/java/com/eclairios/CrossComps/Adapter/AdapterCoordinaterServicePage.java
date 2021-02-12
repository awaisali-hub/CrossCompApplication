package com.eclairios.CrossComps.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eclairios.CrossComps.Model.ModelCoordinaterServicePage;
import com.eclairios.CrossComps.Interface.MyInterface;
import com.eclairios.CrossComps.Model.ModelWeekDays;
import com.eclairios.CrossComps.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterCoordinaterServicePage extends RecyclerView.Adapter<AdapterCoordinaterServicePage.ViewHolder> {


    AdapterWeekDays adapterWeekDays;
    ArrayList<ModelWeekDays> weekDaysList = new ArrayList<>() ;

    List<ModelCoordinaterServicePage> listCoordinater;
    Context context;
    private MyInterface listener;

    public AdapterCoordinaterServicePage(List<ModelCoordinaterServicePage> listCoordinater, Context context, MyInterface listener) {
        this.listCoordinater = listCoordinater;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterCoordinaterServicePage.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowcoordinater,parent,false);
        AdapterCoordinaterServicePage.ViewHolder viewHolder = new AdapterCoordinaterServicePage.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCoordinaterServicePage.ViewHolder holder, int position) {
        holder.CoordinaterName.setText(listCoordinater.get(position).getCoordinaterName());
        holder.CoordinaterService.setText(listCoordinater.get(position).getCoordinateService());
        holder.StartTime.setText(listCoordinater.get(position).getStartTime());
        holder.EndTime.setText(listCoordinater.get(position).getEndTime());

        String weekDays = listCoordinater.get(position).getWeekDays();
        Log.e("weekDays", "onBindViewHolder: "+weekDays );
        weekDaysList = new ArrayList<>() ;
        String[] weekDayList = weekDays.split(",");
        for(int i =0 ; i<weekDayList.length; i++){
            Log.e("splitName", i+" onPostExecute: "+weekDayList[i]);
            String s=weekDayList[i].substring(0,1);
            Log.e("subsyt", "onBindViewHolder: "+s);
            ModelWeekDays servicePage = new ModelWeekDays();
            servicePage.setMonday(s);
            weekDaysList.add(servicePage);
        }
        adapterWeekDays = new AdapterWeekDays(weekDaysList,context);
        holder.recyclerViewWeekDays.setAdapter(adapterWeekDays);

        holder.showAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                listener.ShowAppointment(listCoordinater.get(position).getCoordinatorServiceID());
            }
        });
        holder.makeAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.MakeAppointment(listCoordinater.get(position).getCoordinatorServiceID());
          //       listener.ShowAppointment(listCoordinater.get(position).getCoordinatorServiceID());
            }
        });


    }
    @Override
    public int getItemCount() {
        return listCoordinater.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        TextView CoordinaterName,CoordinaterService,StartTime,EndTime,showAppointment,makeAppointment;
        TextView sunday,monday,tuesday,wednesday,thursday,friday,saturday;
        RecyclerView recyclerViewWeekDays;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CoordinaterName = itemView.findViewById(R.id.coordinaterName);
            CoordinaterService = itemView.findViewById(R.id.coordinaterSerice);
            StartTime = itemView.findViewById(R.id.startTime);
            EndTime = itemView.findViewById(R.id.endTime);
            makeAppointment = itemView.findViewById(R.id.makeAppointment);
            showAppointment = itemView.findViewById(R.id.showApointmentment);
            context = itemView.getContext();
//            sunday = itemView.findViewById(R.id.sunday);
//            monday = itemView.findViewById(R.id.monday);
//            tuesday = itemView.findViewById(R.id.tuesday);
//            wednesday = itemView.findViewById(R.id.wednesday);
//            thursday = itemView.findViewById(R.id.thursday);
//            friday = itemView.findViewById(R.id.friday);
//            saturday = itemView.findViewById(R.id.saturday);
            recyclerViewWeekDays = itemView.findViewById(R.id.weekDaysRecyclerView);
        }}
}
