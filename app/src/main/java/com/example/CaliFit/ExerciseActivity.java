package com.example.CaliFit;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ExerciseActivity extends AppCompatActivity implements ExerciseActivityPresenter.ViewInterface {
    ArrayList<Exercise> exercises = new ArrayList<>();
    ArrayList<Exercise> exercisesToShow = new ArrayList<>();

    private TinyDB tinyDB;
    private DatabaseReference ref;
    private String screenCat;
    private TableLayout tableLayout;
    private ScrollView scrollView;
    private TextView namePreviewExercise;
    private ConstraintLayout constraintLayout;
    private Toast mToast;
    private String whichWorkout;
    private ArrayList<Exercise> toCheck;

    ExerciseActivityPresenter exerciseActivityPresenter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        Intent receivedIntent = getIntent();
        screenCat = receivedIntent.getStringExtra(receivedIntent.EXTRA_TEXT);

        exerciseActivityPresenter = new ExerciseActivityPresenter(this);

        //Set the top name TextView to push/pull/legs/core
        namePreviewExercise = (TextView) findViewById(R.id.namePreviewExercise);
        namePreviewExercise.setText(screenCat + " Übungen");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference(screenCat);
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);

        tinyDB= WorkoutActivity.getDB();
        whichWorkout = tinyDB.getString("whichWorkout");
        toCheck = (ArrayList)tinyDB.getListObject(screenCat+"Exercises"+whichWorkout, Exercise.class);

        for(Exercise e:toCheck){
            e.printName();
        }

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot s : snapshot.getChildren()){
                    Exercise e = new Exercise();
                    String name = s.child("dbName").getValue(String.class);
                    String description = s.child("dbDescription").getValue(String.class);
                    String linkToVideo = s.child("dbLinkToVideo").getValue(String.class);
                    e.setCategory(setCategory());
                    e.setName(name);
                    e.setDescription(description);
                    e.setLinkToVideo(linkToVideo);
                    exercisesToShow.add(e);
                }
                showTable();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("##################", "failed to read value", error.toException());
            }
        });
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
                    if(exercises.size() >= 3){
                        showAToast("Du kannst maximal 3 Übungen zu einer Gruppe hinzufügen!", 0);
                        //Toast.makeText(ExerciseActivity.this, "Du kannst maximal 3 Übungen zu einer Gruppe hinzufügen!", Toast.LENGTH_SHORT).show();
                    }else {
                        if(exercises.contains(e)){
                            showAToast("Diese Übung wurde bereits hinzugefügt!", 0);
                            //Toast.makeText(ExerciseActivity.this, "Diese Übung wurde bereits hinzugefügt!", Toast.LENGTH_SHORT).show();
                        }else {
                            exercises.add(e);
                            showAToast(e.name + " wurde zu " + screenCat + " Übungen hinzugefügt!", 0);
                            //Toast.makeText(ExerciseActivity.this, e.name + " wurde zu " + screenCat + " Übungen hinzugefügt!", Toast.LENGTH_SHORT).show();
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
            //exerciseDescription.setHeight(1100);
            //exerciseDescription.setTooltipText(e.description);
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

    private Exercise.Category setCategory() {
        Exercise.Category exerciseCategory = null;
        switch (screenCat) {
            case "Push":
                exerciseCategory = Exercise.Category.Push;
                break;
            case "Pull":
                exerciseCategory = Exercise.Category.Pull;
                break;
            case "Legs":
                exerciseCategory = Exercise.Category.Legs;
                break;
            case "Core":
                exerciseCategory = Exercise.Category.Core;
                break;
        }
        return exerciseCategory;
    }

    @Override
    public void finish() {

        Intent toGive = new Intent();
        toGive.putExtra("Exercises", exercises);
        setResult(RESULT_OK, toGive);
        if(exercises.size() > 0){
            showAToast("Übungen erfolgreich zu " + screenCat + " hinzugefügt!", 1);
            //Toast.makeText(this, "Übungen erfolgreich zu " + screenCat + " hinzugefügt!", Toast.LENGTH_LONG).show();
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