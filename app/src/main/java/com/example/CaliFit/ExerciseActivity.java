package com.example.CaliFit;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ExerciseActivity extends AppCompatActivity implements ExerciseActivityPresenter.ViewInterface{
    Exercise thisExercise;

    ExerciseActivityPresenter exerciseActivityPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        Intent receivedIntent = getIntent();

        exerciseActivityPresenter  = new ExerciseActivityPresenter(this);

        TextView namePreviewExercise = (TextView) findViewById(R.id.namePreviewExercise);
        //Set the top name TextView to push/pull/legs/core
        namePreviewExercise.setText(receivedIntent.getCharSequenceExtra(receivedIntent.EXTRA_TEXT + " Exercises"));





        Button addButtonPushUp = findViewById(R.id.addButtonPushup);
        addButtonPushUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thisExercise = new Exercise("Push up", Exercise.Category.Push);

            }
        });




    }

    @Override
    public void finish() {
        System.out.println(thisExercise.name);
        Intent toGive = new Intent();
        toGive.putExtra("Exercise", thisExercise);
        setResult(RESULT_OK, toGive);

        super.finish();
    }

    @Override
    protected void onPause(){
        super.onPause();

    }
}