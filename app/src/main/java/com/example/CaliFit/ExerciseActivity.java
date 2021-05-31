package com.example.CaliFit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class ExerciseActivity extends AppCompatActivity implements ExerciseActivityPresenter.ViewInterface{
    ArrayList<Exercise> exercises = new ArrayList<>();
    private int PushCounter;
    private int PullCounter;
    private int LegsCounter;
    private int CoreCounter;

    ExerciseActivityPresenter exerciseActivityPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        Intent receivedIntent = getIntent();

        exerciseActivityPresenter  = new ExerciseActivityPresenter(this);

        TextView namePreviewExercise = (TextView) findViewById(R.id.namePreviewExercise);
        //Set the top name TextView to push/pull/legs/core
        namePreviewExercise.setText(receivedIntent.getStringExtra(receivedIntent.EXTRA_TEXT));


        switch (receivedIntent.getStringExtra(receivedIntent.EXTRA_TEXT)){
            case "Push":
                Button addButtonPushUpEasy = findViewById(R.id.exAdd1);
                TextView textPushUpEasy = findViewById(R.id.exDescription1);
                textPushUpEasy.setText("PushUpEasy text description");
                addButtonPushUpEasy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(PushCounter >= 3){
                            Toast.makeText(ExerciseActivity.this, "Only 3 Exercises per Category", Toast.LENGTH_SHORT).show();
                        }else {
                            Exercise pushupeasy = new Exercise("Knee Push up", Exercise.Category.Push);
                            exercises.add(pushupeasy);
                            PushCounter++;
                        }
                    }
                });
                Button addButtonPushUp = findViewById(R.id.exAdd2);
                TextView textPushUp = findViewById(R.id.exDescription2);
                textPushUp.setText("PushUp text description");
                addButtonPushUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(PushCounter >= 3){
                            Toast.makeText(ExerciseActivity.this, "Only 3 Exercises per Category", Toast.LENGTH_SHORT).show();
                        }else {
                            Exercise pushup = new Exercise("Normal Push up", Exercise.Category.Push);
                            exercises.add(pushup);
                            PushCounter++;
                        }
                    }
                });
                Button addButtonPushUpArcher = findViewById(R.id.exAdd3);
                TextView textPushUpArcher = findViewById(R.id.exDescription3);
                textPushUpArcher.setText("PushUpArcher text description");
                addButtonPushUpArcher.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(PushCounter >= 3){
                            Toast.makeText(ExerciseActivity.this, "Only 3 Exercises per Category", Toast.LENGTH_SHORT).show();
                        }else {
                            Exercise pushuparcher = new Exercise("Archer Push up", Exercise.Category.Push);
                            exercises.add(pushuparcher);
                            PushCounter++;
                        }
                    }
                });
                return;

            case "Pull":
                Button addButtonTableRow = findViewById(R.id.exAdd1);
                TextView textTableRow = findViewById(R.id.exDescription1);
                textTableRow.setText("TableRow text description");
                addButtonTableRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(PullCounter >= 3){
                            Toast.makeText(ExerciseActivity.this, "Only 3 Exercises per Category", Toast.LENGTH_SHORT).show();
                        }else {
                            Exercise tablerow = new Exercise("Table/Chair Row", Exercise.Category.Pull);
                            exercises.add(tablerow);
                            PullCounter++;
                        }
                    }
                });
                Button addButtonTowelRow = findViewById(R.id.exAdd2);
                TextView textTowelRow = findViewById(R.id.exDescription2);
                textTowelRow.setText("TowelRow text description");
                addButtonTowelRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(PullCounter >= 3){
                            Toast.makeText(ExerciseActivity.this, "Only 3 Exercises per Category", Toast.LENGTH_SHORT).show();
                        }else {
                            Exercise towerrow = new Exercise("Towel Row", Exercise.Category.Pull);
                            exercises.add(towerrow);
                            PullCounter++;
                        }
                    }
                });
                Button addButtonSuperman = findViewById(R.id.exAdd3);
                TextView textSuperman = findViewById(R.id.exDescription3);
                textSuperman.setText("Superman text description");
                addButtonSuperman.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(PullCounter >= 3){
                            Toast.makeText(ExerciseActivity.this, "Only 3 Exercises per Category", Toast.LENGTH_SHORT).show();
                        }else {
                            Exercise supermans = new Exercise("Supermans", Exercise.Category.Pull);
                            exercises.add(supermans);
                            PullCounter++;
                        }
                    }
                });
                return;
            case "Legs":
                Button addButtonSquat = findViewById(R.id.exAdd1);
                TextView textSquat = findViewById(R.id.exDescription1);
                textSquat.setText("The squat is the holy grail of legs exercises");
                addButtonSquat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(LegsCounter >= 3){
                            Toast.makeText(ExerciseActivity.this, "Only 3 Exercises per Category", Toast.LENGTH_SHORT).show();
                        }else {
                            Exercise squat = new Exercise("Squat", Exercise.Category.Legs);
                            exercises.add(squat);
                            LegsCounter++;
                        }
                    }
                });
                Button addButtonLowSquat = findViewById(R.id.exAdd2);
                TextView textLowSquat = findViewById(R.id.exDescription2);
                textLowSquat.setText("The low squat is a harder variation of the squat");
                addButtonLowSquat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(LegsCounter >= 3){
                            Toast.makeText(ExerciseActivity.this, "Only 3 Exercises per Category", Toast.LENGTH_SHORT).show();
                        }else {
                            Exercise lowsquat = new Exercise("Low Squat", Exercise.Category.Legs);
                            exercises.add(lowsquat);
                            LegsCounter++;
                        }
                    }
                });
                Button addButtonLunge = findViewById(R.id.exAdd3);
                TextView textLunge = findViewById(R.id.exDescription3);
                textLunge.setText("The lunge emphasises single leg strength and hip flexibility");
                addButtonLunge.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(LegsCounter >= 3){
                            Toast.makeText(ExerciseActivity.this, "Only 3 Exercises per Category", Toast.LENGTH_SHORT).show();
                        }else {
                            Exercise lunge = new Exercise("Lunge", Exercise.Category.Legs);
                            exercises.add(lunge);
                            LegsCounter++;
                        }
                    }
                });
                return;
            case "Core":
                Button addButtonCrunch = findViewById(R.id.exAdd1);
                TextView textCrunch = findViewById(R.id.exDescription1);
                textCrunch.setText("Crunch text description");
                addButtonCrunch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(CoreCounter >= 3){
                            Toast.makeText(ExerciseActivity.this, "Only 3 Exercises per Category", Toast.LENGTH_SHORT).show();
                        }else {
                            Exercise crunch = new Exercise("Crunch", Exercise.Category.Core);
                            exercises.add(crunch);
                            CoreCounter++;
                        }
                    }
                });
                Button addButtonLegRaise = findViewById(R.id.exAdd2);
                TextView textLegRaise = findViewById(R.id.exDescription2);
                textLegRaise.setText("Leg Raise text description");
                addButtonLegRaise.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(CoreCounter >= 3){
                            Toast.makeText(ExerciseActivity.this, "Only 3 Exercises per Category", Toast.LENGTH_SHORT).show();
                        }else {
                            Exercise legraises = new Exercise("Leg Raises", Exercise.Category.Core);
                            exercises.add(legraises);
                            CoreCounter++;
                        }
                    }
                });
                Button addButtonVUp = findViewById(R.id.exAdd3);
                TextView textVUp = findViewById(R.id.exDescription3);
                textVUp.setText("V-Up text description");
                addButtonVUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(CoreCounter >= 3){
                            Toast.makeText(ExerciseActivity.this, "Only 3 Exercises per Category", Toast.LENGTH_SHORT).show();
                        }else {
                            Exercise vup = new Exercise("V-Ups", Exercise.Category.Core);
                            exercises.add(vup);
                            CoreCounter++;

                        }
                    }
                });
                return;
        }
    }

    @Override
    public void finish() {

        Intent toGive = new Intent();
        toGive.putExtra("Exercises", exercises);
        System.out.println(exercises);
        setResult(RESULT_OK, toGive);
        super.finish();
    }

    @Override
    protected void onPause(){
        super.onPause();

    }
}