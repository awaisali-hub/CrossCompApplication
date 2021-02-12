package com.eclairios.CrossComps.Adapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.eclairios.CrossComps.Model.ModelServiceName;
import com.eclairios.CrossComps.R;

import java.util.ArrayList;

public class ServiceName extends AppCompatActivity {
    RecyclerView recyclerViewSN;
    AdpterServiceName adpterServiceName;
    ArrayList<ModelServiceName> serviceNameList = new ArrayList<ModelServiceName>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_name);

        recyclerViewSN = findViewById(R.id.recSerName);
        ModelServiceName  modelServiceName = new ModelServiceName();
        for(int i = 0;i<10;i++) {
            modelServiceName.setServiceName("Service Name");
            serviceNameList.add(modelServiceName);
        }
        adpterServiceName = new AdpterServiceName( serviceNameList,ServiceName.this );
        recyclerViewSN.setAdapter(adpterServiceName);
    }
}