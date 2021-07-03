package com.example.CaliFit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
    private String whichWorkout;
    private Intent receivedIntent;

    @Override
    protected void onStop() {
        super.onStop();
        //getListsFromTinyDB();
        //fillLists(workoutActivityPresenter);
        //setListsToTinyDB();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //getListsFromTinyDB();
        //fillLists(workoutActivityPresenter);
        //setListsToTinyDB();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //getListsFromTinyDB();
        //fillLists(workoutActivityPresenter);
        //setListsToTinyDB();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //getListsFromTinyDB();
        //fillLists(workoutActivityPresenter);
        //setListsToTinyDB();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        receivedIntent = getIntent();

        whichWorkout = (String) receivedIntent.getCharSequenceExtra(receivedIntent.EXTRA_TEXT);

        workoutActivityPresenter  = new WorkoutActivityPresenter(this);

        findViewsById();

        Toast.makeText(this, "Wähle eine Kategorie, um Übungen hinzuzufügen!", Toast.LENGTH_LONG).show();

        HomeActivity.getDB().putString("whichWorkout", whichWorkout);

        getListsFromTinyDB();
        fillLists(workoutActivityPresenter);
        //setListsToTinyDB();
        setButtonHandlers();
        showWorkoutTables();
    }

    private void findViewsById() {
        constraintLayout = findViewById(R.id.constraintLayout);
        tableLayout = findViewById(R.id.tableLayout);
        scrollView = findViewById(R.id.scrollView);
        namePreviewWorkout = (TextView) findViewById(R.id.namePreviewWorkout);
        namePreviewWorkout.setText(whichWorkout);
        pushButton = (Button) findViewById(R.id.pushButton);
        pullButton = (Button) findViewById(R.id.pullButton);
        legsButton = (Button) findViewById(R.id.legsButton);
        coreButton = (Button) findViewById(R.id.coreButton);
    }

    private void setButtonHandlers() {
        //push
        pushButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WorkoutActivity.this, ExerciseActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, "Push");
                startActivityForResult(intent, 1);
                setListsToTinyDB();
                //HomeActivity.getDB().putString("whichWorkout", whichWorkout);

            }
        });
        //pull
        pullButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WorkoutActivity.this, ExerciseActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, "Pull");
                startActivityForResult(intent, 2);
                setListsToTinyDB();
                //HomeActivity.getDB().putString("whichWorkout", whichWorkout);

            }
        });
        //legs
        legsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WorkoutActivity.this, ExerciseActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, "Legs");
                startActivityForResult(intent, 3);
                setListsToTinyDB();
                //HomeActivity.getDB().putString("whichWorkout", whichWorkout);

            }
        });
        //core
        coreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WorkoutActivity.this, ExerciseActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, "Core");
                startActivityForResult(intent, 4);
                setListsToTinyDB();
                //HomeActivity.getDB().putString("whichWorkout", whichWorkout);

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
                    HomeActivity.getDB().putListObject("PushExercises"+whichWorkout, pushToAdd);
                    break;
                case 2:
                    ArrayList<Exercise> pullToAdd = (ArrayList<Exercise>) toReceive.getSerializableExtra("Exercises");
                    for (Exercise exercise : pullToAdd) {
                        workoutActivityPresenter.getModel().addListItem(exercise);
                    }
                    HomeActivity.getDB().putListObject("PullExercises"+whichWorkout, pullToAdd);
                    break;
                case 3:
                    ArrayList<Exercise> legsToAdd = (ArrayList<Exercise>) toReceive.getSerializableExtra("Exercises");
                    for (Exercise exercise : legsToAdd) {
                        workoutActivityPresenter.getModel().addListItem(exercise);
                    }
                    HomeActivity.getDB().putListObject("LegsExercises"+whichWorkout, legsToAdd);

                    break;
                case 4:
                    ArrayList<Exercise> coreToAdd = (ArrayList<Exercise>) toReceive.getSerializableExtra("Exercises");
                    for (Exercise exercise : coreToAdd) {
                        workoutActivityPresenter.getModel().addListItem(exercise);
                    }
                    HomeActivity.getDB().putListObject("CoreExercises"+whichWorkout, coreToAdd);
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

                tableRow.addView(exerciseName);

                //button um zu removen
                Button exeRemove = new Button(this);
                exeRemove.setText("Entfernen");
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
                        setListsToTinyDB();
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

        whichWorkout = (String) receivedIntent.getCharSequenceExtra(receivedIntent.EXTRA_TEXT);
        //getListsFromTinyDB();
        fillLists(workoutActivityPresenter);
        //setListsToTinyDB();
        clearScreen();
        showWorkoutTables();
    }

    private void setListsToTinyDB(){
        HomeActivity.getDB().putListObject("PushExercises"+whichWorkout, PushList);
        HomeActivity.getDB().putListObject("PullExercises"+whichWorkout, PullList);
        HomeActivity.getDB().putListObject("LegsExercises"+whichWorkout, LegsList);
        HomeActivity.getDB().putListObject("CoreExercises"+whichWorkout, CoreList);
    }

    private void getListsFromTinyDB(){
        workoutActivityPresenter.setPushList((ArrayList)HomeActivity.getDB().getListObject("PushExercises"+whichWorkout, Exercise.class));
        workoutActivityPresenter.setPullList((ArrayList) HomeActivity.getDB().getListObject("PullExercises"+whichWorkout, Exercise.class));
        workoutActivityPresenter.setLegsList((ArrayList)HomeActivity.getDB().getListObject("LegsExercises"+whichWorkout, Exercise.class));
        workoutActivityPresenter.setCoreList((ArrayList)HomeActivity.getDB().getListObject("CoreExercises"+whichWorkout, Exercise.class));
    }

    private void clearScreen() {
        for(int i = tableRowCount - 1; i >= 0; i--){
            tableLayout.removeViewAt(i);
            tableRowCount--;
        }
    }
}