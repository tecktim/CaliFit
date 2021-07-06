package com.example.CaliFit;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
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
    private View thisView;
    private int viewWidth;
    private int viewHeight;

    ExerciseActivityPresenter exerciseActivityPresenter;



    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        Intent receivedIntent = getIntent();
        screenCat = receivedIntent.getStringExtra(receivedIntent.EXTRA_TEXT);
        exerciseActivityPresenter = new ExerciseActivityPresenter(this);
        findViewsById();

        thisView = new View(this);

        whichWorkout = HomeActivity.getDB().getString("whichWorkout");
        exercisesToShow = (ArrayList)HomeActivity.getDB().getListObject(screenCat+"ListToShow", Exercise.class);
        exercisesToCheck = (ArrayList)HomeActivity.getDB().getListObject(screenCat+"Exercises"+whichWorkout, Exercise.class);

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showTable() {
        int i = 0;
        constraintLayout.removeAllViewsInLayout();
        scrollView.removeAllViewsInLayout();
        for (Exercise e: exercisesToShow) {
            TableRow tableRow1 = new TableRow(this);
            TableRow tableRow2 = new TableRow(this);
            TableLayout.LayoutParams lp2 = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
            tableLayout.setLayoutParams(lp2);
            TableRow.LayoutParams tb = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            tableRow1.setLayoutParams(tb);
            tableRow2.setLayoutParams(tb);

            //name
            TextView exerciseName = new TextView(this);
            exerciseName.setText(e.name);
            exerciseName.setTextColor(Color.BLACK);
            exerciseName.setTextSize(18);
            exerciseName.setPadding(0, 0, 30, 0);
            exerciseName.setTypeface(Typeface.DEFAULT_BOLD);
            exerciseName.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            tableRow1.addView(exerciseName);

            //button
            Button button = new Button(this);
            button.setBackgroundColor(Color.parseColor("#3DDC84"));
            if(whichWorkout.equals("Anfänger") || whichWorkout.equals("Fortgeschritten")){
                button.setBackgroundColor(Color.parseColor("#BAF2D3"));
            }
            button.setOnClickListener(v -> {
                if(whichWorkout.equals("Anfänger") || whichWorkout.equals("Fortgeschritten")){
                    showAToast("Dieses Workout lässt sich nicht bearbeiten.", 0);

                }else {
                if (exercises.size() >= 3 || exercisesToCheck.size() >= 3) {
                    showAToast("Du kannst maximal 3 Übungen zu einer Gruppe hinzufügen!", 0);
                } else {
                    if (exercises.contains(e)) {
                        showAToast("Diese Übung wurde bereits hinzugefügt!", 0);
                    } else {
                        exercises.add(e);
                        showAToast(e.name + " wurde zu " + screenCat + " Übungen hinzugefügt!", 0);
                    }
                    for (Exercise eToCheck : exercisesToCheck) {
                        for (Exercise e1 : exercises) {
                            if (e1.name.equals(eToCheck.name)) {
                                showAToast("Diese Übung wurde bereits hinzugefügt!", 0);
                                exercises.remove(e1);
                            } else continue;
                        }
                    }
                }
                }
            });
            button.setText("+");
            button.setTextSize(20);
            button.setMaxWidth(100);
            TableRow.LayoutParams addButtonLayout = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            button.setLayoutParams(addButtonLayout);
            button.setTypeface(Typeface.DEFAULT_BOLD);
            tableRow1.addView(button);

            //description
            TextView exerciseDescription = new TextView(this);
            exerciseDescription.setText(e.description);
            exerciseDescription.setTextColor(Color.BLACK);
            TableRow.LayoutParams exerciseDescriptionLayout = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            exerciseDescription.setLayoutParams(exerciseDescriptionLayout);
            exerciseDescription.setMaxWidth(750);

            tableRow2.addView(exerciseDescription);

            //video
            ImageView imageView = new ImageView(this);
            Drawable image = getResources().getDrawable(R.drawable.ic_play_80dp);
            imageView.setPadding(0,50,0, 0);
            imageView.setImageDrawable(image);
            imageView.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tableRow2.addView(imageView);
            imageView.setOnClickListener(v -> {
                Intent i1 = new Intent(Intent.ACTION_VIEW);
                i1.setData(Uri.parse(e.linkToVideo));
                startActivity(i1);
            });

            tableRow1.setPadding(8,30,8,0);
            tableRow2.setPadding(8, 0, 8, 30);

            tableLayout.addView(tableRow1, i);
            tableLayout.addView(tableRow2,i+1);
            i+=2;
        }



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
            showAToast("Übungen erfolgreich zu " + screenCat + " hinzugefügt!", 0);
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