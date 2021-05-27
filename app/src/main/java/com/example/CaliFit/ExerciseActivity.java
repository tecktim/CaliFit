package com.example.CaliFit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ExerciseActivity extends AppCompatActivity implements ExerciseActivityPresenter.ViewInterface{


    ExerciseActivityPresenter exerciseActivityPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        Intent receivedIntent = getIntent();

        exerciseActivityPresenter  = new ExerciseActivityPresenter(receivedIntent.<Model>getParcelableExtra("modelParcel"), this);

        TextView namePreviewExercise = (TextView) findViewById(R.id.namePreviewExercise);
        namePreviewExercise.setText(receivedIntent.getCharSequenceExtra(receivedIntent.EXTRA_TEXT));





        Button addButtonPushUp = findViewById(R.id.addButtonPushup);
        addButtonPushUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Exercise PushUp = new Exercise("Push up", Exercise.Category.Push);
                exerciseActivityPresenter.addExercise(PushUp);
            }
        });




    }

    @Override
    public void finish() {
        Intent returnModel = new Intent();
        returnModel.putExtra("modelParcel", exerciseActivityPresenter.getModel());
        setResult(RESULT_OK, returnModel);

        super.finish();
    }

    @Override
    protected void onPause(){
        super.onPause();

    }
}