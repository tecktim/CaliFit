package com.example.CaliFit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

//@SuppressWarnings("unchecked")
public class WorkoutActivity extends AppCompatActivity implements WorkoutActivityPresenter.ViewInterface{

    WorkoutActivityPresenter workoutActivityPresenter;
    private TableLayout tableLayout;
    private ScrollView scrollView;
    private ConstraintLayout constraintLayout;
    private TextView namePreviewWorkout;
    private ArrayList<Exercise> PushList;
    private ArrayList<Exercise> PullList;
    private ArrayList<Exercise> LegsList;
    private ArrayList<Exercise> CoreList;
    private int tableRowCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        Intent receivedIntent = getIntent();

        workoutActivityPresenter  = new WorkoutActivityPresenter(this);

        constraintLayout = findViewById(R.id.constraintLayout);
        tableLayout = findViewById(R.id.tableLayout);
        scrollView = findViewById(R.id.scrollView);
        namePreviewWorkout = (TextView) findViewById(R.id.namePreviewWorkout);
        namePreviewWorkout.setText(receivedIntent.getCharSequenceExtra(receivedIntent.EXTRA_TEXT));

        fillLists(workoutActivityPresenter);
        showWorkoutTables();


        /*Button pushButton = new Button(this);
        pushButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WorkoutActivity.this, ExerciseActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, "Push");
                startActivityForResult(intent, 1);
            }
        });*/


/*
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
        });*/


    }

    private void fillLists(WorkoutActivityPresenter workoutActivityPresenter) {
        PushList = (ArrayList)workoutActivityPresenter.getModel().getList(Exercise.Category.Push);
        PullList = (ArrayList)workoutActivityPresenter.getModel().getList(Exercise.Category.Pull);
        LegsList = (ArrayList)workoutActivityPresenter.getModel().getList(Exercise.Category.Legs);
        CoreList = (ArrayList)workoutActivityPresenter.getModel().getList(Exercise.Category.Core);
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

    private void showPushWorkout(ArrayList<Exercise> list){

        if(tableRowCount == 1) {
            TextView pushHeader = new TextView(this);
            pushHeader.setText("Push Exercises:");
            pushHeader.setTextColor(Color.BLACK);
            pushHeader.setPadding(20, 20, 20, 20);
            tableLayout.addView(pushHeader);
            tableRowCount++;
        }

        for(Exercise e : list){
            TableRow tableRow = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            tableRow.setLayoutParams(lp);


            TextView exerciseName =  new TextView(this);
            exerciseName.setText(e.name);
            exerciseName.setTextColor(Color.BLACK);
            exerciseName.setPadding(20,20,20,20);
            tableRow.addView(exerciseName);



            Button exeRemove = new Button(this);
            exeRemove.setText("Remove");
            tableRow.addView(exeRemove);
            exeRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    workoutActivityPresenter.getModel().getList(Exercise.Category.Push).remove(e);
                    if(tableRowCount<1){
                    tableRowCount--;}
                    tableLayout.removeView(tableRow);
                    tableLayout.removeView(exeRemove);
                }
            });
            tableLayout.addView(tableRow, tableRowCount);
            tableRowCount++;
        }
    }


    private void showPullWorkout(ArrayList<Exercise> list){
        if(list.size() > 0){
            Log.d("#######", list.get(0).name);
        }
    }
    private void showCoreWorkout(ArrayList<Exercise> list){
        if(list.size() > 0){
            Log.d("#######", list.get(0).name);
        }
    }
    private void showLegsWorkout(ArrayList<Exercise> list){
        if(list.size() > 0){
            Log.d("#######", list.get(0).name);
        }
    }
    private void showWorkoutTables(){
        if(tableRowCount == 0){showAddButtons();}
        showPushWorkout(PushList);
        /*showPullWorkout(PullList);
        showPullWorkout(CoreList);
        showPullWorkout(LegsList);*/

        TableLayout.LayoutParams lp2 = new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        tableLayout.setLayoutParams(lp2);

        constraintLayout.removeAllViewsInLayout();
        scrollView.removeAllViewsInLayout();

        constraintLayout.addView(scrollView);
        scrollView.addView(tableLayout);
        constraintLayout.addView(namePreviewWorkout);

        setContentView(constraintLayout);
    }

    private void showAddButtons() {
        TableRow addButtonsRow = new TableRow(this);
        addButtonsRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
        //push
        Button pushButton = new Button(this);
        pushButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WorkoutActivity.this, ExerciseActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, "Push");
                startActivityForResult(intent, 1);
            }
        });
        pushButton.setPadding(20, 20, 20, 20);
        pushButton.setWidth(tableLayout.getWidth()/4);
        pushButton.setText("Push");
        addButtonsRow.addView(pushButton);
        //pull
        Button pullButton = new Button(this);
        pullButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WorkoutActivity.this, ExerciseActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, "Pull");
                startActivityForResult(intent, 2);
            }
        });
        pullButton.setPadding(20, 20, 20, 20);
        pullButton.setWidth(tableLayout.getWidth()/4);
        pullButton.setText("Pull");
        addButtonsRow.addView(pullButton);
        //legs
        Button legsButton = new Button(this);
        legsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WorkoutActivity.this, ExerciseActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, "Legs");
                startActivityForResult(intent, 1);
            }
        });
        legsButton.setPadding(20, 20, 20, 20);
        legsButton.setWidth(tableLayout.getWidth()/4);
        legsButton.setText("Legs");
        addButtonsRow.addView(legsButton);
        //core
        Button coreButton = new Button(this);
        coreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WorkoutActivity.this, ExerciseActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, "Core");
                startActivityForResult(intent, 0);
            }
        });
        coreButton.setPadding(20, 20, 20, 20);
        coreButton.setWidth(tableLayout.getWidth()/4);
        coreButton.setText("Core");
        addButtonsRow.addView(coreButton);


        tableLayout.addView(addButtonsRow);
        tableRowCount++;
    }

    @Override
    public void onRestart(){
        super.onRestart();
        showWorkoutTables();
    }
}