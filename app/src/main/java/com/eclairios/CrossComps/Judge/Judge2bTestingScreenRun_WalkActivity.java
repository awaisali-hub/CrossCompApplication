package com.eclairios.CrossComps.Judge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.eclairios.CrossComps.MainActivity;
import com.eclairios.CrossComps.R;
import com.view.circulartimerview.CircularTimerListener;
import com.view.circulartimerview.CircularTimerView;
import com.view.circulartimerview.TimeFormatEnum;

public class Judge2bTestingScreenRun_WalkActivity extends AppCompatActivity {

    TextView walk_meters;
    private boolean clicked = false;
    int walk_Meter = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judge2b_testing_screen_run__walk);
        getSupportActionBar().hide();

        walk_meters = findViewById(R.id.meters_walkRuns);





        CircularTimerView progressBar = findViewById(R.id.progress_circular);
        progressBar.setProgress(0);
        progressBar.setStartingAngle(270);
        progressBar.setClockwise(false);

        progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(clicked) {
                    walk_meters.setText(String.valueOf(walk_Meter));
                    walk_meters.setPaintFlags(walk_meters.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
                    walk_Meter += 10;
                } else {
                    Toast.makeText(Judge2bTestingScreenRun_WalkActivity.this, "Test started", Toast.LENGTH_SHORT).show();
                    progressBar.startTimer();
                }

                clicked = true;
            }
        });

// To Initialize Timer
        progressBar.setCircularTimerListener(new CircularTimerListener() {
            @Override
            public String updateDataOnTick(long remainingTimeInMs) {
                return String.valueOf((int)Math.ceil((remainingTimeInMs / 1000.f)));
            }

            @Override
            public void onTimerFinished() {
                Toast.makeText(Judge2bTestingScreenRun_WalkActivity.this, "TEST FINISHED", Toast.LENGTH_SHORT).show();
                progressBar.setPrefix("");
                progressBar.setSuffix("");
                progressBar.setText("FINISHED THANKS!");
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
                progressBar.setEnabled(false);
            }
        }, 240, TimeFormatEnum.SECONDS, 10);
    }

    public void Judge3a(View view) {
        startActivity(new Intent(Judge2bTestingScreenRun_WalkActivity.this,Judge3a_SquatsActivity.class));
    }
}