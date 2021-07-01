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
    private Button pushButton, pullButton, legsButton, coreButton;
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
        pushButton = (Button) findViewById(R.id.pushButton);
        pullButton = (Button) findViewById(R.id.pullButton);
        legsButton = (Button) findViewById(R.id.legsButton);
        coreButton = (Button) findViewById(R.id.coreButton);


        setButtonHandlers();

        fillLists(workoutActivityPresenter);

        showWorkoutTables();
    }

    private void setButtonHandlers() {
        //push
        pushButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WorkoutActivity.this, ExerciseActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, "Push");
                startActivityForResult(intent, 1);
            }
        });
        //pull
        pullButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WorkoutActivity.this, ExerciseActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, "Pull");
                startActivityForResult(intent, 2);
            }
        });
        //legs
        legsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WorkoutActivity.this, ExerciseActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, "Legs");
                startActivityForResult(intent, 3);
            }
        });
        //core
        coreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WorkoutActivity.this, ExerciseActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, "Core");
                startActivityForResult(intent, 4);
            }
        });
    }

    private void fillLists(WorkoutActivityPresenter workoutActivityPresenter) {
        PushList = (ArrayList)workoutActivityPresenter.getModel().getList(Exercise.Category.Push);
        PullList = (ArrayList)workoutActivityPresenter.getModel().getList(Exercise.Category.Pull);
        LegsList = (ArrayList)workoutActivityPresenter.getModel().getList(Exercise.Category.Legs);
        CoreList = (ArrayList)workoutActivityPresenter.getModel().getList(Exercise.Category.Core);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent toReceive) {

        super.onActivityResult(requestCode, resultCode, toReceive);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    ArrayList<Exercise> pushToAdd = (ArrayList<Exercise>) toReceive.getSerializableExtra("Exercises");
                    for (Exercise exercise : pushToAdd) {
                        workoutActivityPresenter.getModel().addListItem(exercise);
                    }
                    break;
                case 2:
                    ArrayList<Exercise> pullToAdd = (ArrayList<Exercise>) toReceive.getSerializableExtra("Exercises");
                    for (Exercise exercise : pullToAdd) {
                        workoutActivityPresenter.getModel().addListItem(exercise);
                    }
                    break;
                case 3:
                    ArrayList<Exercise> legsToAdd = (ArrayList<Exercise>) toReceive.getSerializableExtra("Exercises");
                    for (Exercise exercise : legsToAdd) {
                        workoutActivityPresenter.getModel().addListItem(exercise);
                    }
                    break;
                case 4:
                    ArrayList<Exercise> coreToAdd = (ArrayList<Exercise>) toReceive.getSerializableExtra("Exercises");
                    for (Exercise exercise : coreToAdd) {
                        workoutActivityPresenter.getModel().addListItem(exercise);
                    }
                    break;
                default:
                    return;
            }
        }

    }

    private void showWorkout(ArrayList<Exercise> list){
        for(Exercise e : list){
                //table row erstellen + params setzen
                TableRow tableRow = new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                tableRow.setLayoutParams(lp);

                //text view für name
                TextView exerciseName =  new TextView(this);
                exerciseName.setText(e.name);
                exerciseName.setTextColor(Color.BLACK);
                exerciseName.setPadding(100,20,100,20);
                exerciseName.setHeight(100);
                exerciseName.setHeight(300);

                tableRow.addView(exerciseName);

                //button um zu removen
                Button exeRemove = new Button(this);
                exeRemove.setText("Remove");
                tableRow.addView(exeRemove);
                exeRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        workoutActivityPresenter.getModel().getList(e.category).remove(e);
                        switch(e.category) {
                            case Push: PushList.remove(e);
                            break;
                            case Pull: PullList.remove(e);
                            break;
                            case Legs: LegsList.remove(e);
                            break;
                            case Core: CoreList.remove(e);
                            break;
                        }
                        tableLayout.removeView(tableRow);
                        tableLayout.removeView(exeRemove);
                        tableRowCount--;
                    }
                });
                tableLayout.addView(tableRow, tableRowCount);
                tableRowCount++;

        }
    }

    private void showTitle(Exercise.Category cat) {

        TextView header = new TextView(this);
        header.setText(cat + " Exercises:");
        header.setTextColor(Color.BLACK);
        header.setPadding(50, 20, 20, 20);
        tableLayout.addView(header, tableRowCount);
        tableRowCount++;
    }

    private void showWorkoutTables(){
        showTitle(Exercise.Category.Push);
        showWorkout(PushList);
        showTitle(Exercise.Category.Pull);
        showWorkout(PullList);
        showTitle(Exercise.Category.Legs);
        showWorkout(LegsList);
        showTitle(Exercise.Category.Core);
        showWorkout(CoreList);
        Log.d("### CORE LIST LENGTH ##", String.valueOf(CoreList.size()));

        TableLayout.LayoutParams lp2 = new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        tableLayout.setLayoutParams(lp2);

        constraintLayout.removeView(scrollView);
        constraintLayout.removeView(namePreviewWorkout);

        scrollView.removeAllViewsInLayout();

        constraintLayout.addView(scrollView);
        scrollView.addView(tableLayout);
        constraintLayout.addView(namePreviewWorkout);

        setContentView(constraintLayout);
    }

    @Override
    public void onRestart(){
        super.onRestart();
        fillLists(workoutActivityPresenter);
        clearScreen();
        showWorkoutTables();
    }

    private void clearScreen() {
        for(int i = tableRowCount - 1; i >= 0; i--){
            tableLayout.removeViewAt(i);
            tableRowCount--;
        }
    }
}