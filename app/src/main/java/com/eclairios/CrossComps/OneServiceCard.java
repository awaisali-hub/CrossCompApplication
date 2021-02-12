package com.eclairios.CrossComps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.eclairios.CrossComps.Adapter.AdapterOneServiceCard;
import com.eclairios.CrossComps.Model.ModelOneServiceCard;

import java.util.ArrayList;

public class OneServiceCard extends AppCompatActivity {

    RecyclerView recyclerViww;
    AdapterOneServiceCard adapterOneServiceCard;
    ArrayList<ModelOneServiceCard> chatitemmmm = new ArrayList<>() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_service_card);
        recyclerViww = findViewById(R.id.recSer);

        ModelOneServiceCard modelOneServiceCard = new ModelOneServiceCard();
        for(int i = 0;i<10;i++) {
            modelOneServiceCard.setName("Ali");
            modelOneServiceCard.setAddress("jinnah gardin islamabad");
            chatitemmmm.add(modelOneServiceCard);
        }
        adapterOneServiceCard = new AdapterOneServiceCard( chatitemmmm,OneServiceCard.this );
        recyclerViww.setAdapter(adapterOneServiceCard);


    }
}