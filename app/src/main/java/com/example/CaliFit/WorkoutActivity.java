package com.example.CaliFit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

//@SuppressWarnings("unchecked")
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
                case 1: ArrayList<Exercise> pushToAdd = (ArrayList<Exercise>)toReceive.getSerializableExtra("Exercises");
                    for (Exercise exercise : pushToAdd) {
                        workoutActivityPresenter.getModel().addListItem(exercise);
                    }
                    break;
                case 2:ArrayList<Exercise> pullToAdd = (ArrayList<Exercise>)toReceive.getSerializableExtra("Exercises");
                    for (Exercise exercise : pullToAdd) {
                        workoutActivityPresenter.getModel().addListItem(exercise);
                    }
                    break;
                case 3:ArrayList<Exercise> legsToAdd = (ArrayList<Exercise>)toReceive.getSerializableExtra("Exercises");
                    for (Exercise exercise : legsToAdd) {
                        workoutActivityPresenter.getModel().addListItem(exercise);
                    }
                    break;
                case 4:ArrayList<Exercise> coreToAdd = (ArrayList<Exercise>)toReceive.getSerializableExtra("Exercises");
                    for (Exercise exercise : coreToAdd) {
                        workoutActivityPresenter.getModel().addListItem(exercise);
                    }
                    break;
                default:
                    return;
            }
        }

    }

    @Override
    public void onRestart(){
        super.onRestart();
        ArrayList<Exercise> PushList = (ArrayList)workoutActivityPresenter.getModel().getList(Exercise.Category.Push);
        ArrayList<Exercise> PullList = (ArrayList)workoutActivityPresenter.getModel().getList(Exercise.Category.Pull);
        ArrayList<Exercise> LegsList = (ArrayList)workoutActivityPresenter.getModel().getList(Exercise.Category.Legs);
        ArrayList<Exercise> CoreList = (ArrayList)workoutActivityPresenter.getModel().getList(Exercise.Category.Core);

        TextView pushExercise1 = findViewById(R.id.push_exercise_text1);
        TextView pushExercise2 = findViewById(R.id.push_exercise_text2);
        TextView pushExercise3 = findViewById(R.id.push_exercise_text3);
        TextView pullExercise1 = findViewById(R.id.pull_exercise_text4);
        TextView pullExercise2 = findViewById(R.id.pull_exercise_text5);
        TextView pullExercise3 = findViewById(R.id.pull_exercise_text6);
        TextView coreExercise1 = findViewById(R.id.core_exercise_text7);
        TextView coreExercise2 = findViewById(R.id.core_exercise_text8);
        TextView coreExercise3 = findViewById(R.id.core_exercise_text9);
        TextView legsExercise1 = findViewById(R.id.legs_exercise_text10);
        TextView legsExercise2 = findViewById(R.id.legs_exercise_text11);
        TextView legsExercise3 = findViewById(R.id.legs_exercise_text12);
        ArrayList<TextView> nameBoxes = new ArrayList<>();
        nameBoxes.add(pushExercise1); nameBoxes.add(pushExercise2); nameBoxes.add(pushExercise3); nameBoxes.add(pullExercise1); nameBoxes.add(pullExercise2); nameBoxes.add(pullExercise3); nameBoxes.add(legsExercise1); nameBoxes.add(legsExercise2); nameBoxes.add(legsExercise3); nameBoxes.add(coreExercise1); nameBoxes.add(coreExercise2); nameBoxes.add(coreExercise3);

        for(int i = 0; i < workoutActivityPresenter.getModel().PushList.size(); i++){
            nameBoxes.get(i).setText(workoutActivityPresenter.getModel().PushList.get(i).name);
            nameBoxes.get(i).setVisibility(View.VISIBLE);
        }
        for(int i = 0; i < workoutActivityPresenter.getModel().PullList.size(); i++){
            nameBoxes.get(i + 3).setText(workoutActivityPresenter.getModel().PullList.get(i).name);
            nameBoxes.get(i + 3).setVisibility(View.VISIBLE);
        }
        for(int i = 0; i < workoutActivityPresenter.getModel().LegsList.size(); i++){
            nameBoxes.get(i + 6).setText(workoutActivityPresenter.getModel().LegsList.get(i).name);
            nameBoxes.get(i + 6).setVisibility(View.VISIBLE);
        }
        for(int i = 0; i < workoutActivityPresenter.getModel().CoreList.size(); i++){
            nameBoxes.get(i + 9).setText(workoutActivityPresenter.getModel().CoreList.get(i).name);
            nameBoxes.get(i + 9).setVisibility(View.VISIBLE);
        }


    }
}