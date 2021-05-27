package com.example.CaliFit;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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
                intent.putExtra(Intent.EXTRA_TEXT, "Push");
                startActivityForResult(intent, 1);
            }
        });



        Button pullButton = findViewById(R.id.pull_button);
        pullButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutActivity.this, ExerciseActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, "Pull");
                startActivityForResult(intent, 2);
            }
        });



        Button legsButton = findViewById(R.id.legs_button);
        legsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutActivity.this, ExerciseActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, "Legs");
                startActivityForResult(intent, 3);
            }
        });



        Button coreButton = findViewById(R.id.core_button);
        coreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutActivity.this, ExerciseActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, "Core");
                startActivityForResult(intent, 4);
            }
        });

        TextView namePreviewWorkout = (TextView) findViewById(R.id.namePreviewWorkout);
        namePreviewWorkout.setText(receivedIntent.getCharSequenceExtra(receivedIntent.EXTRA_TEXT));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent toReceive) {
        if (resultCode == RESULT_OK && requestCode == 1){
            Exercise toAdd = (Exercise)toReceive.getSerializableExtra("Exercise");
            workoutActivityPresenter.getModel().addListItem(toAdd);
        }
    }

    @Override
    public void onRestart(){
        super.onRestart();
        TextView pushExercise0 = findViewById(R.id.push_exercise_text);
        pushExercise0.setText(workoutActivityPresenter.getModel().PushList.get(0).name);
    }
}