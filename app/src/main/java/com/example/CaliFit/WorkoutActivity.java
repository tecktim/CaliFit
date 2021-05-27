package com.example.CaliFit;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WorkoutActivity extends AppCompatActivity implements WorkoutActivityPresenter.ViewInterface{

    WorkoutActivityPresenter workoutActivityPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        Intent receivedIntent = getIntent();

        workoutActivityPresenter  = new WorkoutActivityPresenter(this);



        Button pushButton = findViewById(R.id.push_button);
        pushButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WorkoutActivity.this, ExerciseActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.push_exercise));
                intent.putExtra("modelParcel", workoutActivityPresenter.getModel());
                startActivityForResult(intent, 1);
            }
        });



        Button pullButton = findViewById(R.id.pull_button);
        pullButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutActivity.this, ExerciseActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.pull_exercise));

                startActivity(intent);
            }
        });



        Button legsButton = findViewById(R.id.legs_button);
        legsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutActivity.this, ExerciseActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.legs_exercise));
            }
        });



        Button coreButton = findViewById(R.id.core_button);
        coreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutActivity.this, ExerciseActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.core_exercise));
                startActivity(intent);
            }
        });

        TextView namePreviewWorkout = (TextView) findViewById(R.id.namePreviewWorkout);
        namePreviewWorkout.setText(receivedIntent.getCharSequenceExtra(receivedIntent.EXTRA_TEXT));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent model) {
        if (resultCode == RESULT_OK && requestCode == 1){
            if(model.hasExtra("modelParcel")){
                workoutActivityPresenter.setModel(model.<Model>getParcelableExtra("modelParcel"));
            }
        }
        System.out.println();
    }

    @Override
    public void onRestart(){
        super.onRestart();
        TextView pushView = findViewById(R.id.push_exercise_text);
        //System.out.println(workoutActivityPresenter.getModel().PushList.get(0).name);

    }
}