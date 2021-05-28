package com.example.CaliFit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;

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
        if (resultCode == RESULT_OK){
            switch(requestCode) {
                case 1:Exercise pushToAdd = (Exercise)toReceive.getSerializableExtra("Exercise");
                workoutActivityPresenter.getModel().addListItem(pushToAdd);
                case 2:Exercise pullToAdd = (Exercise)toReceive.getSerializableExtra("Exercise");
                workoutActivityPresenter.getModel().addListItem(pullToAdd);
                case 3:Exercise legsToAdd = (Exercise)toReceive.getSerializableExtra("Exercise");
                workoutActivityPresenter.getModel().addListItem(legsToAdd);
                case 4:Exercise coreToAdd = (Exercise)toReceive.getSerializableExtra("Exercise");
                workoutActivityPresenter.getModel().addListItem(coreToAdd);
                default:
                    return;
            }
        }

    }

    @Override
    public void onRestart(){
        super.onRestart();
        Iterator<Exercise> itr = workoutActivityPresenter.getModel().PushList.iterator();
        TextView pushExercise1 = findViewById(R.id.push_exercise_text1);
        TextView pushExercise2 = findViewById(R.id.push_exercise_text2);
        TextView pushExercise3 = findViewById(R.id.push_exercise_text3);
        TextView pullExercise1 = findViewById(R.id.pull_exercise_text1);
        TextView pullExercise2 = findViewById(R.id.pull_exercise_text2);
        TextView pullExercise3 = findViewById(R.id.pull_exercise_text3);
        TextView coreExercise1 = findViewById(R.id.core_exercise_text1);
        TextView coreExercise2 = findViewById(R.id.core_exercise_text2);
        TextView coreExercise3 = findViewById(R.id.core_exercise_text3);
        TextView legsExercise1 = findViewById(R.id.legs_exercise_text1);
        TextView legsExercise2 = findViewById(R.id.legs_exercise_text2);
        TextView legsExercise3 = findViewById(R.id.legs_exercise_text3);
        ArrayList<TextView> nameBoxes = new ArrayList<>();
        nameBoxes.add(pushExercise1); nameBoxes.add(pushExercise2); nameBoxes.add(pushExercise3); nameBoxes.add(pullExercise1); nameBoxes.add(pullExercise2); nameBoxes.add(pullExercise3); nameBoxes.add(legsExercise1); nameBoxes.add(legsExercise2); nameBoxes.add(legsExercise3); nameBoxes.add(coreExercise1); nameBoxes.add(coreExercise2); nameBoxes.add(coreExercise3);

        for(int i = 0; i < workoutActivityPresenter.getModel().PushList.size(); i++){
            nameBoxes.get(i).setText(workoutActivityPresenter.getModel().PushList.get(i).name);
            nameBoxes.get(i).setVisibility(View.VISIBLE);
        }
        for(int i = 0; i < workoutActivityPresenter.getModel().PullList.size(); i++){
            nameBoxes.get(i).setText(workoutActivityPresenter.getModel().PullList.get(i).name);
            nameBoxes.get(i).setVisibility(View.VISIBLE);
        }
        for(int i = 0; i < workoutActivityPresenter.getModel().LegsList.size(); i++){
            nameBoxes.get(i).setText(workoutActivityPresenter.getModel().LegsList.get(i).name);
            nameBoxes.get(i).setVisibility(View.VISIBLE);
        }
        for(int i = 0; i < workoutActivityPresenter.getModel().CoreList.size(); i++){
            nameBoxes.get(i).setText(workoutActivityPresenter.getModel().CoreList.get(i).name);
            nameBoxes.get(i).setVisibility(View.VISIBLE);
        }


    }
}