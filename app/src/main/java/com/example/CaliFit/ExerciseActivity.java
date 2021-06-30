package com.example.CaliFit;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.VideoView;

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

    private DatabaseReference ref;
    private String screenCat;
    private TableLayout tableLayout;
    private ScrollView scrollView;
    private TextView namePreviewExercise;
    private ConstraintLayout constraintLayout;

    ExerciseActivityPresenter exerciseActivityPresenter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        Intent receivedIntent = getIntent();
        screenCat = receivedIntent.getStringExtra(receivedIntent.EXTRA_TEXT);
        System.out.println("screen category is: " + screenCat);

        exerciseActivityPresenter = new ExerciseActivityPresenter(this);

        //Set the top name TextView to push/pull/legs/core
        namePreviewExercise = (TextView) findViewById(R.id.namePreviewExercise);
        namePreviewExercise.setText(screenCat);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference(screenCat);
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        scrollView = (ScrollView) findViewById(R.id.exerciseScrollView);
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);



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
                    e.printName();
                    exercisesToShow.add(e);
                }
                showTable();
                System.out.println("Wer geht schon hhier hin lol");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("##################", "failed to read value", error.toException());
            }
        });



        Log.d("###########Length", String.valueOf(exercisesToShow.size()));

    }

    public void showTable() {
        int i = 0;
        for (Exercise e: exercisesToShow) {
            TableRow tableRow = new TableRow(this);
            //TableRow tableRow2 = new TableRow(this);

            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            tableRow.setLayoutParams(lp);
            //name
            TextView exerciseName = new TextView(this);
            exerciseName.setText(e.name);
            exerciseName.setTextColor(Color.BLACK);
            exerciseName.setPadding(2,2,2,2);
            exerciseName.setWidth(300);
            exerciseName.setHeight(150);
            tableRow.addView(exerciseName);
            //description
            TextView exerciseDescription = new TextView(this);
            exerciseDescription.setText(e.description);
            exerciseDescription.setTextColor(Color.BLACK);
            exerciseDescription.setPadding(2,2,2,2);
            exerciseDescription.setWidth(350);
            exerciseDescription.setHeight(150);
            //exerciseDescription.setTooltipText(e.description);
            tableRow.addView(exerciseDescription);
            //video
            VideoView videoView = new VideoView(this);
            videoView.setVideoURI(Uri.parse(e.linkToVideo));
            tableRow.addView(videoView);
            //button
            Button button = new Button(this);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    exercises.add(e);
                }
            });
            button.setPadding(2,2,2,2);
            button.setText("Add");
            tableRow.addView(button);

            tableLayout.addView(tableRow, i);
            TableLayout.LayoutParams lp2 = new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
            tableLayout.setLayoutParams(lp2);
            Log.d("###########" + i, "tablerows added to tableLayout");
            i++;
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
        System.out.println(exercises);
        setResult(RESULT_OK, toGive);
        super.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}