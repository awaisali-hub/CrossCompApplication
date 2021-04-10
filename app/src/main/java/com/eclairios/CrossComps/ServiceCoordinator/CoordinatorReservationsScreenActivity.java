package com.eclairios.CrossComps.ServiceCoordinator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eclairios.CrossComps.Adapter.ParentAdapter;
import com.eclairios.CrossComps.Model.Child;
import com.eclairios.CrossComps.Model.ParentChild;
import com.eclairios.CrossComps.R;

import java.util.ArrayList;

public class CoordinatorReservationsScreenActivity extends AppCompatActivity {
    private RecyclerView recyclerViewParent;
    ArrayList<ParentChild> parentChildObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_reservations_screen);
        getSupportActionBar().hide();


        recyclerViewParent = (RecyclerView) findViewById(R.id.rv_parent);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewParent.setLayoutManager(manager);
        recyclerViewParent.setHasFixedSize(true);

        ParentAdapter parentAdapter = new ParentAdapter(this, createData());
        recyclerViewParent.setAdapter(parentAdapter);

    }

    private ArrayList<ParentChild> createData() {
        parentChildObj = new ArrayList<>();
        ArrayList<Child> list1 = new ArrayList<>();
        ArrayList<Child> list2 = new ArrayList<>();
        ArrayList<Child> list3 = new ArrayList<>();
        ArrayList<Child> list4 = new ArrayList<>();
        ArrayList<Child> list5 = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Child c1 = new Child();
            c1.setChild_name("Holloway" + (i + 1));
            list1.add(c1);
        }

        for (int i = 0; i < 5; i++) {
            Child c2 = new Child();
            c2.setChild_name("Elizabath" + (i + 1));
            list2.add(c2);
        }


        for (int i = 0; i < 2; i++) {
            Child c3 = new Child();
            c3.setChild_name("Kaity Pairy" + (i + 1));
            list3.add(c3);
        }


        for (int i = 0; i < 4; i++) {
            Child c4 = new Child();
            c4.setChild_name("Justin" + (i + 1));
            list4.add(c4);
        }

        for (int i = 0; i < 2; i++) {
            Child c5 = new Child();
            c5.setChild_name("Haider" + (i + 1));
            list5.add(c5);
        }


        ParentChild pc1 = new ParentChild();
        pc1.setChild(list1);
        parentChildObj.add(pc1);

        ParentChild pc2 = new ParentChild();
        pc2.setChild(list2);
        parentChildObj.add(pc2);


        ParentChild pc3 = new ParentChild();
        pc3.setChild(list3);
        parentChildObj.add(pc3);

        ParentChild pc4 = new ParentChild();
        pc4.setChild(list4);
        parentChildObj.add(pc4);

        ParentChild pc5 = new ParentChild();
        pc5.setChild(list5);
        parentChildObj.add(pc5);


        return parentChildObj;
    }

    public void moveToServiceCoordinater(View view){
startActivity(new Intent(CoordinatorReservationsScreenActivity.this,ServiceCoordinatorHomePageActivity.class));
finish();
    }

}