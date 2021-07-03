package com.example.CaliFit;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;


public class ExerciseActivity extends AppCompatActivity implements ExerciseActivityPresenter.ViewInterface {
    ArrayList<Exercise> exercises = new ArrayList<>();
    ArrayList<Exercise> exercisesToShow = new ArrayList<>();
    ArrayList<Exercise> exercisesToCheck = new ArrayList<>();

    private String screenCat;
    private TableLayout tableLayout;
    private ScrollView scrollView;
    private TextView namePreviewExercise;
    private ConstraintLayout constraintLayout;
    private static Toast mToast;
    private String whichWorkout;

    ExerciseActivityPresenter exerciseActivityPresenter;



    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        Intent receivedIntent = getIntent();
        screenCat = receivedIntent.getStringExtra(receivedIntent.EXTRA_TEXT);
        exerciseActivityPresenter = new ExerciseActivityPresenter(this);
        findViewsById();

        whichWorkout = HomeActivity.getDB().getString("whichWorkout");
        exercisesToShow = (ArrayList)HomeActivity.getDB().getListObject(screenCat+"ListToShow", Exercise.class);
        exercisesToCheck = (ArrayList)HomeActivity.getDB().getListObject(screenCat+"Exercises"+whichWorkout, Exercise.class);

        if(exercisesToCheck.size() > 0) {
            exercisesToCheck.get(0).printName();
        }
        showTable();

    }

    private void findViewsById() {
        //Set the top name TextView to push/pull/legs/core
        namePreviewExercise = (TextView) findViewById(R.id.namePreviewExercise);
        namePreviewExercise.setText(screenCat + " Übungen");

        tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
    }

    public void showTable() {
        int i = 0;
        for (Exercise e: exercisesToShow) {
            TableRow tableRow1 = new TableRow(this);
            TableRow tableRow2 = new TableRow(this);

            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            tableRow1.setLayoutParams(lp);
            //name
            TextView exerciseName = new TextView(this);
            exerciseName.setText(e.name);
            exerciseName.setTextColor(Color.BLACK);
            exerciseName.setTextSize(18);
            exerciseName.setPadding(2,2,2,2);
            exerciseName.setWidth(300);
            exerciseName.setHeight(150);
            tableRow1.addView(exerciseName);

            //button
            Button button = new Button(this);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (exercises.size() >= 3) {
                        showAToast("Du kannst maximal 3 Übungen zu einer Gruppe hinzufügen!", 0);
                    } else {
                        if (exercises.contains(e)) {
                            showAToast("Diese Übung wurde bereits hinzugefügt!", 0);
                        } else {
                            exercises.add(e);
                            showAToast(e.name + " wurde zu " + screenCat + " Übungen hinzugefügt!", 0);
                        }
                        for (Exercise eToCheck : exercisesToCheck) {
                            for (Exercise e : exercises) {
                                if (e.name.equals(eToCheck.name)) {
                                    showAToast("Diese Übung wurde bereits hinzugefügt!", 0);
                                    exercises.remove(e);
                                } else continue;
                            }
                        }
                    }
                }

            });
            button.setPadding(2,2,2,2);
            button.setText("Add");
            tableRow1.addView(button);

            //description
            TextView exerciseDescription = new TextView(this);
            exerciseDescription.setText(e.description);
            exerciseDescription.setTextColor(Color.BLACK);
            exerciseDescription.setPadding(2,2,2,2);
            exerciseDescription.setWidth(800);
            tableRow2.addView(exerciseDescription);

            //video
            ImageView imageView = new ImageView(this);
            imageView.setPadding(0,20,0,20);
            Drawable image = getResources().getDrawable(R.drawable.ic_play_80dp);
            imageView.setImageDrawable(image);
            tableRow2.addView(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(e.linkToVideo));
                    startActivity(i);
                }
            });

            tableLayout.addView(tableRow1, i);
            tableLayout.addView(tableRow2,i+1);

            tableRow1.setPadding(0,50,0,0);

            TableLayout.LayoutParams lp2 = new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            tableLayout.setLayoutParams(lp2);
            tableRow2.setLayoutParams(lp2);
            i+=2;
        }

        constraintLayout.removeAllViewsInLayout();
        scrollView.removeAllViewsInLayout();

        constraintLayout.addView(scrollView);
        scrollView.addView(tableLayout);
        constraintLayout.addView(namePreviewExercise);

        setContentView(constraintLayout);
    }

    @Override
    public void finish() {
        Intent toGive = new Intent();
        toGive.putExtra("Exercises", exercises);
        setResult(RESULT_OK, toGive);
        if(exercises.size() > 0){
            showAToast("Übungen erfolgreich zu " + screenCat + " hinzugefügt!", 1);
            }
        super.finish();
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    //https://stackoverflow.com/questions/6925156/how-to-avoid-a-toast-if-theres-one-toast-already-being-shown
    //Length 0 = short, 1 = long
    public void showAToast(String message, int length){
        if (mToast != null) {
            mToast.cancel();
        }
        if(length == 0){
            mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        }
        if(length == 1){
            mToast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        }
        mToast.show();
    }

}